package org.firstinspires.ftc.teamcode.fieldCenterTest;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mathematics;

public class BasicFieldCenter extends BasicDrivetrain {
    public BasicFieldCenter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    @Override
    public void drive(double gamepadX, double gamepadY, double gamepadRot) {
        telemetry.addData("gamepadX: ", gamepadX);
        telemetry.addData("gamepadY: ", gamepadY);
        telemetry.addData("gamepadRot: ", gamepadRot);

        double rotationEffectiveness = 1.0;
        double xyEffectiveness = 1.0;

        // gamepadRot is negated because in math, a counterclockwise rotation is positive
        // (think unit circle), but on the controller, we expect the robot to rotate clockwise when
        // we push the stick to the right. Pushing the stick to the right outputs a positive value.
        double turn = -gamepadRot * rotationEffectiveness;
        double controllerX = gamepadX * xyEffectiveness;
        double controllerY = gamepadY * xyEffectiveness;
        double[] controllerVector = {controllerX, controllerY};
        telemetry.addData("controllerVector[0]: ", controllerVector[0]);
        telemetry.addData("controllerVector[1]: ", controllerVector[1]);

        double imuMeasure = getImuAngle();
        telemetry.addData("imuMeasure: ", imuMeasure);

        double[] rotatedVector = Mathematics.rotate(controllerVector, imuMeasure);
        double rotatedX = rotatedVector[0];
        double rotatedY = rotatedVector[1];
        telemetry.addData("rotatedX: ", rotatedX);
        telemetry.addData("rotatedY: ", rotatedY);


        double theta = Math.atan2(rotatedY, rotatedX);
        telemetry.addData("theta: ", theta);
        double power = Math.hypot(rotatedX, rotatedY);
        telemetry.addData("power: ", power);
        double sin = Math.sin(theta - Math.PI/4);
        telemetry.addData("sin: ", sin);
        double cos = Math.cos(theta - Math.PI/4);
        telemetry.addData("cos: ", cos);
        double max = Math.max(Math.abs(sin),Math.abs(cos));
        telemetry.addData("max: ", max);

        double leftBackPower = power * sin/max + turn;
        double leftFrontPower = power * cos/max + turn;
        double rightBackPower = power * cos/max - turn;
        double rightFrontPower = power * sin/max - turn;
        telemetry.addLine("before capping: ");
        telemetry.addData("leftBackPower: ", leftBackPower);
        telemetry.addData("leftFrontPower: ", leftFrontPower);
        telemetry.addData("rightBackPower: ", rightBackPower);
        telemetry.addData("rightFrontPower: ", rightFrontPower);

        if ((power + Math.abs(turn)) > 1) {
            leftFrontPower /= power + Math.abs(turn);
            rightFrontPower /= power + Math.abs(turn);
            leftBackPower /= power + Math.abs(turn);
            rightBackPower /= power + Math.abs(turn);
            telemetry.addLine("powers were above one");
        }

        telemetry.addLine("after capping: ");
        telemetry.addData("leftBackPower: ", leftBackPower);
        telemetry.addData("leftFrontPower: ", leftFrontPower);
        telemetry.addData("rightBackPower: ", rightBackPower);
        telemetry.addData("rightFrontPower: ", rightFrontPower);

        leftBackMotor.setPower(leftBackPower);
        leftFrontMotor.setPower(leftFrontPower);
        rightBackMotor.setPower(rightBackPower);
        rightFrontMotor.setPower(rightFrontPower);
    }
}
