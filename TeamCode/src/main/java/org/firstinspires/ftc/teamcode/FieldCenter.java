package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FieldCenter extends Drivetrain {

    private double STRAFE_TOGGLE_FACTOR = 0.5;
    private double ROTATION_TOGGLE_FACTOR = 0.5;

    private boolean strafeToggle = false;
    private boolean rotationToggle = false;

    public FieldCenter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    @Override
    public void drive(Controller controller) {
        double gamepadX;
        double gamepadY;
        double gamepadRot;

        if (Math.abs(controller.gamepad1X) > 0.01) {
            gamepadX = controller.gamepad1X;
        } else if (Math.abs(controller.gamepad2X) > 0.01) {
            gamepadX = controller.gamepad2X;
        } else {
            gamepadX = 0;
        }
        if (Math.abs(controller.gamepad1Y) > 0.01) {
            gamepadY = controller.gamepad1Y;
        } else if (Math.abs(controller.gamepad2Y) > 0.01) {
            gamepadY = controller.gamepad2Y;
        } else {
            gamepadY = 0;
        }
        if (Math.abs(controller.gamepad1Rot) > 0.01) {
            gamepadRot = controller.gamepad1Rot;
        } else if (Math.abs(controller.gamepad2Rot) > 0.01) {
            gamepadRot = controller.gamepad2Rot;
        } else {
            gamepadRot = 0;
        }

        if (controller.gamepad1RotationToggle || controller.gamepad2RotationToggle) {
            rotationToggle = !rotationToggle;
        }
        if (rotationToggle) {
            gamepadRot *= ROTATION_TOGGLE_FACTOR;
        }
        if (controller.gamepad1StrafeToggle || controller.gamepad2StrafeToggle) {
            strafeToggle = !strafeToggle;
        }
        if (strafeToggle) {
            gamepadX *= STRAFE_TOGGLE_FACTOR;
            gamepadY *= STRAFE_TOGGLE_FACTOR;
        }

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

        double imuMeasure = readFromIMU();
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