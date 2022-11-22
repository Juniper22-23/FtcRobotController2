package org.firstinspires.ftc.teamcode.Mechanisms;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.Mechanism;

public class LinearSlidesMechanism extends Mechanism {
    /*Ticks needed for each level:
     * Low:3.52370014617
     * Medium:6.13384840259
     * High:8.74399665901
     * InCone:-0.52202965128
     * */
    public DcMotor linearSlides = hardwareMap.get(DcMotor.class, "LinearSlides");
    SetupClass robot = new SetupClass();
    public float diameterOfSpool = 30.48f;
    public float linearSlidesSpeed = 1f;
    //public int LINEAR_SLIDES_IN_CONE = -50;
    public int LINEAR_SLIDES_LOW = 200;
    public int LINEAR_SLIDES_MEDIUM = 400;
    public int LINEAR_SLIDES_HIGH = 600;
    public int LINEAR_SLIDES_NORM = 0;
    public int LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
    public double ticks;
    public int ticksAsInt;



    public int equate(double rotations) {
        ticks = rotations * (384.5 / (diameterOfSpool * Math.PI));
        ticksAsInt = (int)ticks;
        return ticksAsInt;
    }

    public LinearSlidesMechanism(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
    }

    public void Move() {
        linearSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (gamepad1.y) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_HIGH;
            robot.LinearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            robot.LinearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LinearSlides.setPower(linearSlidesSpeed);
        } else if (gamepad1.x) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_MEDIUM;
            robot.LinearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            robot.LinearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LinearSlides.setPower(linearSlidesSpeed);
        } else if (gamepad1.a) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_LOW;
            robot.LinearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            robot.LinearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LinearSlides.setPower(linearSlidesSpeed);
        } else if (gamepad1.b) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
            robot.LinearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            robot.LinearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LinearSlides.setPower(linearSlidesSpeed);
        }

        telemetry.addData(">", "LinearSlidesCurrent: " + LINEAR_SLIDES_CURRENT);
        telemetry.addData(">", "Tick: " + ticksAsInt);
        telemetry.addData(">", "Power: " + linearSlidesSpeed);
        telemetry.update();
    }


    public void stop() {
        linearSlides.setPower(0);
        linearSlides.setTargetPosition(0);
        robot.LinearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}
