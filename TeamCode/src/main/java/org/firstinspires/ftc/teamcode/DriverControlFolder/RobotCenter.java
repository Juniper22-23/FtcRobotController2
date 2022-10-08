package org.firstinspires.ftc.teamcode.DriverControlFolder;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivetrain;

public class RobotCenter extends Drivetrain {
    public RobotCenter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    public void drive(double gamepadX, double gamepadY, double gamepadRot) {
        double rotationEffectiveness = 0.50;
        double xyEffectiveness = 0.75;

        double turn = gamepadRot * rotationEffectiveness;
        double x = gamepadX * xyEffectiveness;
        double y = gamepadY * xyEffectiveness;

        double theta = Math.atan2(y,x);
        double power = Math.hypot(x,y);
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin),Math.abs(cos));

        double leftBackPower = power * cos/max + turn;
        double leftFrontPower = power * sin/max - turn;
        double rightBackPower = power * sin/max + turn;
        double rightFrontPower = power * cos/max + turn;

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
    }
}