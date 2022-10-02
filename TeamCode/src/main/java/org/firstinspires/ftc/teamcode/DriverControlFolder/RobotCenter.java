package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivetrain;

public class RobotCenter extends Drivetrain {
    public RobotCenter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    public void drive(double gamepadX, double gamepadY, double gamepadRot) {
        double rotationEffectiveness;
        double xyEffectiveness;

        double normalRot;
        double normalX;
        double normalY;

        double theta = Math.atan2(normalY,normalX);
        double power = Math.hypot(normalX,normalY);
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta-Math.PI/4);
        double max = Math.max(Math.abs(sin),Math.abs(cos));

        double leftBackPower;
        double leftFrontPower;
        double rightBackPower;
        double rightFrontPower;

        rotationEffectiveness = 0.50;
        xyEffectiveness = 0.75;

        normalRot = gamepadRot * rotationEffectiveness;
        normalX = gamepadX * xyEffectiveness;
        normalY = gamepadY * xyEffectiveness;


        leftBackPower = power * cos/max + normalRot;
        leftFrontPower = power * sin/max - normalRot;
        rightBackPower = power * sin/max + normalRot;
        rightFrontPower = power * cos/max + normalRot;

        leftBackMotor.setPower(leftBackPower);
        leftFrontMotor.setPower(leftFrontPower);
        rightBackMotor.setPower(rightBackPower);
        rightFrontMotor.setPower(rightFrontPower);
    }

    public void revolve(double magnitude) {
        // in inches
        double robotLength = 13.22; // distance between axles
        double spaceFromShipHub = 1.0;
        double shipHubRadius = 9.0;
        //double revolveFactor = ( (shipHubRadius + spaceFromShipHub) / (robotLength + spaceFromShipHub + shipHubRadius) );
        double revolveFactor = 0.3;

        double sign = magnitude / Math.abs(magnitude);

        double leftBackPower;
        double leftFrontPower;
        double rightBackPower;
        double rightFrontPower;

        double leftBackBias = 0.3;
        double leftFrontBias = 0.3;
        double rightBackBias = 0.3;
        double rightFrontBias = 0.3;

        if (magnitude == 0) {
            leftBackPower = -(magnitude + leftBackBias - leftBackBias);
            rightBackPower = magnitude;
            rightFrontPower = -(magnitude * revolveFactor);
        } else if (magnitude == -1) {
            leftBackPower = -(magnitude - leftBackBias);
            rightBackPower = magnitude + rightBackBias;
            rightFrontPower = -(magnitude * revolveFactor) - rightFrontBias;
        } else if (magnitude == 1) {
            leftBackPower = -(magnitude - leftBackBias);
            rightBackPower = magnitude - rightBackBias;
            rightFrontPower = -(magnitude * revolveFactor) - rightFrontBias;
        } else {
            leftBackPower = -magnitude;
            leftFrontPower = magnitude * revolveFactor;
            rightBackPower = magnitude;
            rightFrontPower = -magnitude * revolveFactor;
        }
        leftFrontPower = (magnitude * revolveFactor) + magnitude * leftFrontBias;

        leftBackMotor.setPower(leftBackPower);
        leftFrontMotor.setPower(leftFrontPower);
        rightBackMotor.setPower(rightBackPower);
        rightFrontMotor.setPower(rightFrontPower);

    }
}