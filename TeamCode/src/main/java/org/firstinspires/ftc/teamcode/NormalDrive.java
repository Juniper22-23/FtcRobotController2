package org.firstinspires.ftc.teamcode;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorBNO055IMUCalibration;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "FieldCentricVideo")
public class NormalDrive extends LinearOpMode {
    //Heading: Y-Axis
    //Pitch: X-axis
    //Roll: Z-Axis
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;

    public BNO055IMU imu;

    Orientation angles;
    Acceleration gravity;

    @Override
    public void runOpMode(){
        double driveTurn;
        //double driveVertical;
        //double driveHorizontal;

        double gamepadXCoordinate;
        double gamepadYCoordinate;
        double gamepadHypot;
        double gamepadDegree;
        double robotDegree;
        double movementDegree;
        double gamepadXControl;
        double gamepadYControl;

        frontLeft = hardwareMap.dcMotor.get("FrontLeft");
        frontRight = hardwareMap.dcMotor.get("FrontRight");
        backRight = hardwareMap.dcMotor.get("BackRight");
        backLeft = hardwareMap.dcMotor.get("BackLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.java";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        waitForStart();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);


        while(opModeIsActive()){
            driveTurn = gamepad1.left_stick_x;
            //driveVertical = gamepad1.right_stick_y;
            //driveHorizontal = gamepad1.right_stick_x;

            gamepadXCoordinate = gamepad1.right_stick_x;
            gamepadYCoordinate = gamepad1.right_stick_y;
            gamepadHypot = Range.clip(Math.hypot(gamepadXCoordinate,gamepadYCoordinate),0,1);
            gamepadDegree = Math.atan2(gamepadYCoordinate,gamepadXCoordinate);
            robotDegree = getAngle();
            movementDegree = gamepadDegree - robotDegree;
            gamepadXControl = Math.cos(Math.toRadians(movementDegree)) * gamepadHypot;
            gamepadYControl = Math.sin(Math.toRadians(movementDegree)) * gamepadHypot;

            frontRight.setPower(gamepadYControl * Math.abs(gamepadYControl) - gamepadXControl * Math.abs(gamepadXControl) + driveTurn);
            frontRight.setPower(gamepadYControl * Math.abs(gamepadYControl) + gamepadXControl * Math.abs(gamepadXControl) + driveTurn);
            frontRight.setPower(gamepadYControl * Math.abs(gamepadYControl) + gamepadXControl * Math.abs(gamepadXControl) - driveTurn);
            frontRight.setPower(gamepadYControl * Math.abs(gamepadYControl) - gamepadXControl * Math.abs(gamepadXControl) - driveTurn);

            /*frontRight.setPower(driveVertical - driveHorizontal + driveTurn);
            backRight.setPower(driveVertical + driveHorizontal + driveTurn);
            frontLeft.setPower(driveVertical + driveHorizontal - driveTurn);
            backLeft.setPower(driveVertical - driveHorizontal - driveTurn);*/
        }
        telemetry.update();
    }
    void composeTelemetry(){
        telemetry.addAction(new Runnable() {
            @Override
            public void run() {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYZ, AngleUnit.DEGREES);
                gravity = imu.getGravity();
            }
        });
    }
    public double getAngle(){
       return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYZ, AngleUnit.DEGREES).firstAngle;
    }
}
