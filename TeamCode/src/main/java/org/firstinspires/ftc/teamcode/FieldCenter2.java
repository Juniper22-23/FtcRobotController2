package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@TeleOp(name = "FieldCenterTweaked", group = "Field Center")
public class FieldCenter2 extends Drivetrain {
    //This is the tweaked version of Field Center.
    public FieldCenter2(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

    }

    @Override
    public void drive(double gamepadX, double gamepadY, double gamepadRot) {
        double rotationEffectiveness = 1;
        double xyEffectiveness = 1;

        // gamepadRot is negated because in math, a counterclockwise rotation is positive
        // (think unit circle), but on the controller, we expect the robot to rotate clockwise when
        // we push the stick to the right. Pushing the stick to the right outputs a positive value.
        double turn = -gamepadRot * rotationEffectiveness;
        double controllerX = gamepadX * xyEffectiveness;
        double controllerY = gamepadY * xyEffectiveness;
        double[] controllerVector = {controllerX, controllerY};


        double imuMeasure = readFromIMU();

        double[] rotatedVector = Mathematics.rotate(controllerVector, imuMeasure);
        double rotatedX = rotatedVector[0];
        double rotatedY = rotatedVector[1];

        double theta = Math.atan2(rotatedY, rotatedX);
        double power = Math.hypot(rotatedX, rotatedY);
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin),Math.abs(cos));

        double leftBackPower = power * sin/max + turn;
        double leftFrontPower = power * cos/max + turn;
        double rightBackPower = power * cos/max - turn;
        double rightFrontPower = power * sin/max - turn;

        double rcw = pJoystick->turn();
        double forward = pJoystick->controllerY() * -1; /* Invert stick Y axis */
        double strafe = pJoystick->controllerX();

        double pi = 3.1415926;

        double gyro_degrees = ahrs->turn();
        double gyro_radians = gyro_degrees * pi/180;
        double temp = forward * cos(gyro_radians) +
                strafe * sin(gyro_radians);
        strafe = -forward * sin(gyro_radians) +
                strafe * cos(gyro_radians);
         forward = temp;

        if ((power + Math.abs(turn)) > 1) {
            leftFrontPower /= power + turn;
            rightFrontPower /= power + turn;
            leftBackPower /= power + turn;
            rightBackPower /= power + turn;
        }

        leftBackMotor.setPower(leftBackPower);
        leftFrontMotor.setPower(leftFrontPower);
        rightBackMotor.setPower(rightBackPower);
        rightFrontMotor.setPower(rightFrontPower);

        telemetry.addLine("MOTOR POWERS Method 2 Accuracy:");
        telemetry.addData("imuMeasure: ", imuMeasure);
        telemetry.addData("leftBackPower: ", leftBackPower);
        telemetry.addData("leftFrontPower: ", leftFrontPower);
        telemetry.addData("rightBackPower: ", rightBackPower);
        telemetry.addData("rightFrontPower: ", rightFrontPower);
    }
}