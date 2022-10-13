package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivetrain;
public class RobotCenter extends Drivetrain {
    public RobotCenter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    public void drive(double gamepadX, double gamepadY, double gamepadRot) {
        double rotationEffectiveness = -1; // if you change the neg to pos it will inverse the rotation
        double xyEffectiveness = 1;

        double turn = gamepadRot * rotationEffectiveness;
        double x = gamepadX * xyEffectiveness;
        double y = gamepadY * xyEffectiveness;

        double theta = Math.atan2(y,x);
        double power = Math.hypot(x,y);
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin),Math.abs(cos));

        double leftBackPower = power * sin/max + turn;
        double leftFrontPower = power * cos/max + turn;
        double rightBackPower = power * cos/max - turn;
        double rightFrontPower = power * sin/max - turn;

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

        telemetry.addLine("MOTOR POWERS:");
        telemetry.addData("leftBackPower: ", leftBackPower);
        telemetry.addData("leftFrontPower: ", leftFrontPower);
        telemetry.addData("rightBackPower: ", rightBackPower);
        telemetry.addData("rightFrontPower: ", rightFrontPower);
    }
}