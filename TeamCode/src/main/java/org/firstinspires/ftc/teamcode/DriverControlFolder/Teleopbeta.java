package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.FieldCenterAuto;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

enum TeleopModes {
    LINEAR_SLIDES_MOVE, GRIP
}

enum SlidesModes {
    SLIDES_DOWN, SLIDES_UP
}

@TeleOp(name = "Teleopbeta", group = "Tele-Op")
public class Teleopbeta extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private FieldCenterAuto fieldCenterAuto;
    private ConeTransporter coneTransporter;

    private TeleopModes teleopMode;
    private SlidesModes slidesModes;

    private HashMap<SlidesModes, Double> slidesTimes = new HashMap<SlidesModes,Double>();

    private ElapsedTime timer;
    double startTime;
    float buffer = 0.2f;
    double interval = 1000.0;
    double time;
    int highestBufferForSlides_DOWN = 7;
    int lowestBufferForSlides_DOWN = -4;
    int highestBufferForSlides_UP = 47;
    int lowestBufferForSlides_UP = 36;




    public void runOpMode() {
        telemetry.clear();
        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            fieldCenterAuto = new FieldCenterAuto(telemetry, hardwareMap);
            coneTransporter = new ConeTransporter(telemetry, hardwareMap);

            slidesTimes.put(SlidesModes.SLIDES_DOWN, 1000.0);
            slidesTimes.put(SlidesModes.SLIDES_UP, 1000.0);

            timer = new ElapsedTime();
            timer.reset();
            time = timer.milliseconds();

            teleopMode = TeleopModes.LINEAR_SLIDES_MOVE;
            slidesModes = SlidesModes.SLIDES_DOWN;
        } catch (Exception exception) {
            telemetry.addLine("Outside of the while loop:");
            telemetry.addLine(exception.getMessage());
            telemetry.update();
        }
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            try {
                controller.update();
                //FIELDCENTER_______________________________________________________________________________
                double gamepadX;
                double gamepadY;
                double gamepadRot;
                boolean rotationToggle = false;
                boolean strafeToggle = false;

                if (Math.abs(controller.gamepad1X) > 0.01) {
                    gamepadX = controller.gamepad1X;
                } else {
                    gamepadX = 0;
                }
                if (Math.abs(controller.gamepad1Y) > 0.01) {
                    gamepadY = controller.gamepad1Y;
                } else {
                    gamepadY = 0;
                }
                if (Math.abs(controller.gamepad1Rot) > 0.01) {
                    gamepadRot = -controller.gamepad1Rot;
                } else {
                    gamepadRot = 0;
                }
                if (controller.gamepad1RotationToggle ) {
                    //right bumper
                    rotationToggle = true;
                }
                if (controller.gamepad1StrafeToggle) {
                    //left bumper
                    strafeToggle = true;
                }

                fieldCenterAuto.drive(gamepadX, gamepadY, gamepadRot, rotationToggle, strafeToggle);


                telemetry.addData("", timer.milliseconds());
                telemetry.addData("startTime", startTime);

                if (controller.leftBumper || controller.rightBumper) {
                    if (teleopMode == TeleopModes.LINEAR_SLIDES_MOVE) {
                        teleopMode = TeleopModes.GRIP;
                    }
                }

                if (teleopMode == TeleopModes.LINEAR_SLIDES_MOVE) {
                    if (controller.a) {
                        coneTransporter.setRiseLevel(1);
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.lift();
                    } else if (controller.x) {
                        coneTransporter.setRiseLevel(2);
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.lift();
                    } else if (controller.y) {
                        coneTransporter.setRiseLevel(3);
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.lift();
                    } else if(controller.b){
                        coneTransporter.setRiseLevel(0);
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.lift();
                    } else if(controller.dpadDown){
                        coneTransporter.setRiseLevel(-1);
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.lift();
                    }

                    if(controller.rightTrigger >= buffer){
                        coneTransporter.moveUp();
                    }else if(controller.leftTrigger >= buffer){
                        coneTransporter.moveDown();
                    }
                } else if (teleopMode == TeleopModes.GRIP) {
                    if(controller.leftBumper){
                        slidesModes = SlidesModes.SLIDES_DOWN;
                    } else if(controller.rightBumper){
                        slidesModes = SlidesModes.SLIDES_UP;
                    }
                    if (slidesModes == SlidesModes.SLIDES_DOWN) {
                        // checks how long it's been since the timer was reset, which is the time when the gripper started moving
                        startTime = timer.now(TimeUnit.MILLISECONDS);
                        boolean gripperReady = timer.milliseconds() - startTime > interval;
                        boolean liftReady = coneTransporter.linearSlides.getCurrentPosition() > lowestBufferForSlides_DOWN && coneTransporter.linearSlides.getCurrentPosition() < highestBufferForSlides_DOWN;

                        if (!gripperReady) {
                            coneTransporter.setGripperPosition(1.0);
                            coneTransporter.grip();

                        } else if (!liftReady) {
                            coneTransporter.setRiseLevel(-1);
                            coneTransporter.lift();
                        } else{
                            slidesModes = SlidesModes.SLIDES_UP;
                        }
                    } else if (slidesModes == SlidesModes.SLIDES_UP) {
                        boolean gripperReady = timer.milliseconds() - startTime > interval;
                        boolean liftReady = coneTransporter.linearSlides.getCurrentPosition() > lowestBufferForSlides_UP && coneTransporter.linearSlides.getCurrentPosition() < highestBufferForSlides_UP;

                        if (!gripperReady) {
                            coneTransporter.setGripperPosition(0.75);
                            coneTransporter.grip();
                        }else if (!liftReady) {
                            coneTransporter.setRiseLevel(0);
                            coneTransporter.lift();
                        }else {
                            timer.reset();
                            teleopMode = TeleopModes.LINEAR_SLIDES_MOVE;
                        }
                    }
                }

            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
