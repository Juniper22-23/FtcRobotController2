//package org.firstinspires.ftc.teamcode;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.Drivetrain;
//public class RobotCenter extends Drivetrain {
//    public RobotCenter(Telemetry telemetry, HardwareMap hardwareMap) {
//        super(telemetry, hardwareMap);
//    }
//
//    public void drive(Controller controller) {
//        double gamepadX;
//        double gamepadY;
//        double gamepadRot;
//
//        if (Math.abs(controller.gamepad1X) > 0.01) {
//            gamepadX = controller.gamepad1X;
//        } else if (Math.abs(controller.gamepad2X) > 0.01) {
//            gamepadX = controller.gamepad2X;
//        } else {
//            gamepadX = 0;
//        }
//        if (Math.abs(controller.gamepad1Y) > 0.01) {
//            gamepadY = controller.gamepad1Y;
//        } else if (Math.abs(controller.gamepad2Y) > 0.01) {
//            gamepadY = controller.gamepad2Y;
//        } else {
//            gamepadY = 0;
//        }
//        if (Math.abs(controller.gamepad1Rot) > 0.01) {
//            gamepadRot = controller.gamepad1Rot;
//        } else if (Math.abs(controller.gamepad2Rot) > 0.01) {
//            gamepadRot = controller.gamepad2Rot;
//        } else {
//            gamepadRot = 0;
//        }
//
//
//        double rotationEffectiveness = 1;
//        double xyEffectiveness = 1;
//
//        // gamepadRot is negated because in math, a counterclockwise rotation is positive
//        // (think unit circle), but on the controller, we expect the robot to rotate clockwise when
//        // we push the stick to the right. Pushing the stick to the right outputs a positive value.
//        double turn = -gamepadRot * rotationEffectiveness;
//        double x = gamepadX * xyEffectiveness;
//        double y = gamepadY * xyEffectiveness;
//
//        double theta = Math.atan2(y,x);
//        double power = Math.hypot(x,y);
//        double sin = Math.sin(theta - Math.PI/4);
//        double cos = Math.cos(theta - Math.PI/4);
//        double max = Math.max(Math.abs(sin),Math.abs(cos));
//
//        double leftBackPower = power * sin/max + turn;
//        double leftFrontPower = power * cos/max + turn;
//        double rightBackPower = power * cos/max - turn;
//        double rightFrontPower = power * sin/max - turn;
//
//        if ((power + Math.abs(turn)) > 1) {
//            leftFrontPower /= power + turn;
//            rightFrontPower /= power + turn;
//            leftBackPower /= power + turn;
//            rightBackPower /= power + turn;
//        }
//
//        leftBackMotor.setPower(leftBackPower);
//        leftFrontMotor.setPower(leftFrontPower);
//        rightBackMotor.setPower(rightBackPower);
//        rightFrontMotor.setPower(rightFrontPower);
//
//        telemetry.addLine("MOTOR POWERS Method 2 Accuracy:");
//        telemetry.addData("leftBackPower: ", leftBackPower);
//        telemetry.addData("leftFrontPower: ", leftFrontPower);
//        telemetry.addData("rightBackPower: ", rightBackPower);
//        telemetry.addData("rightFrontPower: ", rightFrontPower);
//    }
//}
