package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class FreightTransporter extends Mechanism {
    private DcMotor collectorMotor;
    private DcMotor liftMotor;
    private Servo dumperServo;
    private DistanceSensor dumperSensor;
    private RevBlinkinLedDriver lights;

    /**
     * Keeps track of the input of the collector controller (gamepad1)
     * This is the dumper toggle the drivers wanted
     * collectorDumpLevel = -1 --> Drivers haven't pushed the dumper toggle
     * collectorDumpLevel = 0  --> Toggle is set to DUMPER_SERVO_POSITIONS[0]
     * collectorDumpLevel = 1  --> Toggle is set to DUMPER_SERVO_POSITIONS[1]
     */
    private int collectorDumpLevel = -1;

    private double collectorMotorSpeed = 0.8;
    private int powerLevel = 0;

    private boolean liftOverride = false;


    /**
     * Stores the starting speed of the lift motor before liftMotorSpeed is modified to account for energy loss from battery use
     */
    private final double LIFT_MOTOR_BASE_SPEED = 0.7;



    private final double LIFT_MOTOR_TPR = 384.5;

    /**
     * Stores the amount of Â±error the lift motor is allowed to settle into when it reaches near the desired pos
     * In encoder ticks
     */
    private final double LIFT_MOTOR_ERROR = 30;

    /**
     * In encoder ticks
     */
    private final double[] LIFT_MOTOR_POSITIONS = {0.0, -169.0, -799.0, -1550.0};
    private final double[] DUMPER_SERVO_POSITIONS = {0.95, 0.6, 0.20};

    /**
     * Initially stores user input, but is modified by other things such as the distance sensor and the dumper toggle
     */
    private int riseLevel = 0;
    private int dumpLevel = 0;

    /**
     * Keeps track of the dump level before the rise funciton modifies it
     * Used to check the supposed pos of dumper before the modifications
     * Used for the dumper toggle to check what the dumper pos is so that it can switch to a different dumper pos
     */
    private int unmodifiedDumpLevel = 0;

    /**
     * Is used as a timer to time movements of different mechanisms
     * In milliseconds (throughout the code)
     */
    private ElapsedTime elapsedTime;

    private double previousTime = 0.0;
    private double currentTime;
    private double changeTime;
    private double previousPos = 0.0;
    private double currentPos;
    private double changePos;
    private double previousSpeed = 0.0;
    private double currentSpeed;
    private double changeSpeed;

    /**
     * Stores the amount of iterations where the lift motor was stuck due to energy loss from battery use
     * In iterations
     */
    private int stuckCounter = 0;

    /**
     * Stores the modifiable speed of the lift motor; initialized at the base speed
     */
    private double liftMotorSpeed = LIFT_MOTOR_BASE_SPEED;


    /**
     * In millimeters
     */
    private double sensorDistance = 0.0;

    /**
     * dumperTimeStamp and sensorTimeStamp stores the time when the distance sensor started detecting close proximity with the freight
     * sensorTimeStamp is used to time the movement of the dumper servo to holding pos
     * dumperTimeStamp is used to time the movement of the collector spinning in reverse
     * Used to compare times with the elapsedTime timer to make sure the correct amount of time passed
     * In milliseconds (throughout the code)
     */
    private double sensorTimeStamp = 0.0;
    private double dumperTimeStamp = 0.0;

    /**
     * Stores the amount of time to wait before the dumper servo moves to hold pos
     * In milliseconds
     */
    private final double HOLD_AFTER_COLLECT = 500;
    private final double HOLD_AFTER_DUMP = 1000;

    /**
     * Stores the amount of distance the servo should sense before it determines close proximity
     * In millimeters
     */
    public final double MINIMUM_RANGE = 100.0;

    public FreightTransporter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        collectorMotor = this.hardwareMap.get(DcMotor.class, "collectorMotor");
        liftMotor = this.hardwareMap.get(DcMotor.class, "liftMotor");
        dumperServo = this.hardwareMap.get(Servo.class, "dumperServo");
        dumperSensor = this.hardwareMap.get(DistanceSensor.class, "dumperSensor");
        lights = this.hardwareMap.get(RevBlinkinLedDriver.class,"lights");
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
        //liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elapsedTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void collect() {
        if (!liftOverride) {
            runCollector(powerLevel);
        }
    }

    public void lift(boolean toggle) {
        unmodifiedDumpLevel = dumpLevel;
        sensorDistance = dumperSensor.getDistance(DistanceUnit.MM);
        rise(riseLevel, toggle);
        dump(dumpLevel);
    }

    public void rise(int level, boolean toggle) {
        calculateLiftSpeed();
        if (Math.abs(liftMotor.getCurrentPosition() - LIFT_MOTOR_POSITIONS[level]) < LIFT_MOTOR_ERROR) {
            liftMotor.setPower(0.0);
            /*
             * Handles actions to perform at the lift motor starting pos
             *
             * collectorDumpLevel = -1 --> Drivers haven't pushed the dumper toggle
             * collectorDumpLevel = 0  --> Toggle is set to DUMPER_SERVO_POSITIONS[0]
             * collectorDumpLevel = 1  --> Toggle is set to DUMPER_SERVO_POSITIONS[1]
             */
            if (level == 0) {
                if (toggle) {
                    handleDumperToggle();
                    // Sets dumpLevel equal to the toggle's value
                    dumpLevel = collectorDumpLevel;
                } else {
                    // The if statement directly below makes sure that the toggle holds the dumper servo in place so it is not automatically sent back to some other pos
                    if (collectorDumpLevel == -1) {
                        handleSensorAtRest();
                    } else {
                        // If the toggle is used, use the toggle's value instead
                        dumpLevel = collectorDumpLevel;
                    }
                }
            } else {
                // Handles cases when the lift motor is at some elevated height ( >0 )
                if (dumpLevel == 2) {
                    // handleSensorAtDump();
                }
            }
            // resets stuckCounter when lift motor is at starting pos
            stuckCounter = 0;
            // resets liftMotorSpeed when lift motor is at starting pos
            liftMotorSpeed = LIFT_MOTOR_BASE_SPEED;
        } else {
            if (liftMotor.getCurrentPosition() < LIFT_MOTOR_POSITIONS[level]) {
                liftMotor.setPower(liftMotorSpeed);
                // resets overriding hierarchy when the dumper is ready to be lifted
                collectorDumpLevel = -1;
                liftOverride = false;
            } else {
                liftMotor.setPower(-liftMotorSpeed);
                // resets overriding hierarchy when the dumper is no longer holding something
                collectorDumpLevel = -1;
                liftOverride = false;
            }
            // increases stuckCounter when lift is stuck for an iteration
            if (currentSpeed == 0.0) {
                stuckCounter++;
            } else {
                stuckCounter = 0;
            }
            // increases the speed when the counter reaches 20
            if (stuckCounter == 20) {
                liftMotorSpeed += 0.05;
                // resets stuckCounter
                stuckCounter = 0;
            }
        }
    }

    public void dump(int positionIndex) {
        dumperServo.setPosition(DUMPER_SERVO_POSITIONS[positionIndex]);
    }

    private void runCollector(int powerLevel) {
        collectorMotor.setPower(collectorMotorSpeed * (double)powerLevel);
    }

    // calculates different variables related to time and pos
    public void calculateLiftSpeed() {
        currentTime = elapsedTime.time(TimeUnit.MILLISECONDS) / 1000.0;
        changeTime = currentTime - previousTime;
        currentPos = (double)liftMotor.getCurrentPosition() / LIFT_MOTOR_TPR;
        changePos = currentPos - previousPos;
        currentSpeed = changePos / changeTime;
        changeSpeed = currentSpeed - previousSpeed;

        // sets variables for next calculation during next iteration
        previousPos = currentPos;
        previousTime = currentTime;
        previousSpeed = currentSpeed;
    }

    private void handleDumperToggle() {
        // Handles the dumper toggle logic
        if (collectorDumpLevel == -1) {
            // Makes sure that when the toggle is pressed, the dumper servo always goes to a different pos than where it used to be
            if (unmodifiedDumpLevel == 0) {
                collectorDumpLevel = 1;
            } else if (unmodifiedDumpLevel == 1) {
                collectorDumpLevel = 0;
            }
        } else if (collectorDumpLevel == 0) {
            collectorDumpLevel = 1;
        } else if (collectorDumpLevel == 1) {
            collectorDumpLevel = 0;
        }
    }

    private void handleSensorAtRest() {
        // Checks for time spent waiting after the distance sensor detects close proximity
        if (sensorDistance < MINIMUM_RANGE) {
            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            // Handles timing the movement of the dumper servo to holding pos
            if (elapsedTime.time(TimeUnit.MILLISECONDS) - sensorTimeStamp > HOLD_AFTER_COLLECT) {
                dumpLevel = 1;
                sensorTimeStamp = elapsedTime.time(TimeUnit.MILLISECONDS);
            }
            // Handles timing the movement of the collector spinning in reverse
            if (elapsedTime.time(TimeUnit.MILLISECONDS) - dumperTimeStamp > 3000) {
                liftOverride = true;
                runCollector(1);
                dumperTimeStamp = elapsedTime.time(TimeUnit.MILLISECONDS);
            }
        } else {
            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            // Waits for close proximity with freight
            liftOverride = false;
            dumpLevel = 0;
            dumperTimeStamp = sensorTimeStamp = elapsedTime.time(TimeUnit.MILLISECONDS);
        }
    }

    private void handleSensorAtDump() {
        // Checks for time spent waiting after the distance sensor no longer detects close proximity
        if (sensorDistance > MINIMUM_RANGE) {
            // Handles timing the movement of the dumper servo to holding pos
            if (elapsedTime.time(TimeUnit.MILLISECONDS) - sensorTimeStamp > HOLD_AFTER_DUMP ) {
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
                dumpLevel = 1;
                sensorTimeStamp = elapsedTime.time(TimeUnit.MILLISECONDS);
            }
        } else {
            // Waits for no close proximity with freight
            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            dumpLevel = 2;
            sensorTimeStamp = elapsedTime.time(TimeUnit.MILLISECONDS);
        }
    }

    private void handleStuckCounter() {
        // increases stuckCounter when lift is stuck for an iteration
        if (currentSpeed == 0.0) {
            stuckCounter++;
        } else {
            stuckCounter = 0;
        }
        // increases the speed when the counter reaches 20
        if (stuckCounter == 20) {
            liftMotorSpeed += 0.05;
            // resets stuckCounter
            stuckCounter = 0;
        }
    }

    public void setCollectorPower(int level) {
        if (!liftOverride) {
            powerLevel = level;
        }
    }

    public void setRiseLevel(int level) {
        riseLevel = level;
    }

    public void setDumpLevel(int level) {
        dumpLevel = level;
    }

    public int getRiseLevel() {
        return riseLevel;
    }

    public int getDumpLevel() {
        return dumpLevel;
    }

    public double getSensorDistance() {
        return sensorDistance;
    }
}