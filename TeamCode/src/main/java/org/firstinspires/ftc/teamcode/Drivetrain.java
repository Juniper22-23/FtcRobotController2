package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Drivetrain extends Mechanism {
    protected DcMotor leftBackMotor;
    protected DcMotor leftFrontMotor;
    protected DcMotor rightBackMotor;
    protected DcMotor rightFrontMotor;

    //test2.0

    protected BNO055IMU imu;

    public Drivetrain(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        leftBackMotor = this.hardwareMap.get(DcMotor.class, "leftBackMotor");
        leftFrontMotor = this.hardwareMap.get(DcMotor.class,"leftFrontMotor");
        rightBackMotor = this.hardwareMap.get(DcMotor.class,"rightBackMotor");
        rightFrontMotor = this.hardwareMap.get(DcMotor.class,"rightFrontMotor");

        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = this.hardwareMap.get(BNO055IMU.class, "imu");
        initializeIMU();

        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void initializeIMU() {
        // don't touch please
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public double readFromIMU() {
        return -imu.getAngularOrientation().firstAngle;
    }



    public abstract void drive(Controller controller);
}