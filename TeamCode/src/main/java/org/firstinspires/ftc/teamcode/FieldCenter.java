package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FieldCenter extends Drivetrain {
    public FieldCenter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    @Override
    public void drive(double gamepadX, double gamepadY, double gamepadRot) {
        telemetry.addData("gamepadX: ", gamepadX);
        telemetry.addData("gamepadY: ", gamepadY);
        telemetry.addData("gamepadRot: ", gamepadRot);

        double rotationEffectiveness = 1.0;
        telemetry.addData("rotationEffectiveness: ", rotationEffectiveness);
        double xyEffectiveness = 1.0;
        telemetry.addData("xyEffectiveness: ", xyEffectiveness);

        // gamepadRot is negated because in math, a counterclockwise rotation is positive
        // (think unit circle), but on the controller, we expect the robot to rotate clockwise when
        // we push the stick to the right. Pushing the stick to the right outputs a positive value.
        double turn = -gamepadRot * rotationEffectiveness;
        telemetry.addData("turn: ", turn);
        double controllerX = gamepadX * xyEffectiveness;
        telemetry.addData("controllerX: ", controllerX);
        double controllerY = gamepadY * xyEffectiveness;
        telemetry.addData("controllerY: ", controllerY);
        double[] controllerVector = {controllerX, controllerY};
        telemetry.addData("controllerVector[0]: ", controllerVector[0]);
        telemetry.addData("controllerVector[1]: ", controllerVector[1]);

        double imuMeasure = readFromIMU();
        telemetry.addData("imuMeasure: ", imuMeasure);

        double[] rotatedVector = Mathematics.rotate(controllerVector, imuMeasure);
        telemetry.addData("rotatedVector[0]: ", rotatedVector[0]);
        telemetry.addData("rotatedVector[1]: ", rotatedVector[1]);
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
            leftFrontPower /= power + turn;
            rightFrontPower /= power + turn;
            leftBackPower /= power + turn;
            rightBackPower /= power + turn;
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