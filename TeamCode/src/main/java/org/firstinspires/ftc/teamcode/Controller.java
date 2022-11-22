package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Controller {
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final GamepadKeyboard gamepadKeyboard1;
    private final GamepadKeyboard gamepadKeyboard2;

    public boolean freightOuttake;
    public boolean freightIntake;
    public boolean dumperToggle;
    public boolean carouselOperation;
    public boolean gamepad1StrafeToggle;
    public boolean gamepad2StrafeToggle;
    public boolean gamepad1RotationToggle;
    public boolean gamepad2RotationToggle;
    public boolean liftpos0;
    public boolean liftpos1;
    public boolean liftpos2;
    public boolean liftpos3;

    //wheel=car but if no wheel then no car

    public double gamepad1X;
    public double gamepad1Y;
    public boolean collectorSlowLeft;
    public boolean collectorSlowRight;
    public boolean collectorSlowDown;
    public boolean collectorSlowUp;
    public double gamepad1Rot;
    public double gamepad2X;
    public double gamepad2Y;
    public boolean liftSlowLeft;
    public boolean liftSlowRight;
    public boolean liftSlowDown;
    public boolean liftSlowUp;
    public double gamepad2Rot;
    public boolean y;
    public boolean x;
    public boolean a;
    public boolean b;

    public double collectorRevCCW;
    public double collectorRevCW;
    public double liftRevCCW;
    public double liftRevCW;

    public Controller(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.gamepadKeyboard1 = new GamepadKeyboard(gamepad1);
        this.gamepadKeyboard2 = new GamepadKeyboard(gamepad2);
    }

    public void update() {
        gamepadKeyboard1.update();
        gamepadKeyboard2.update();

        freightOuttake = gamepad1.left_bumper;
        freightIntake = gamepad1.right_bumper;
        dumperToggle = gamepadKeyboard1.activeBefore.contains("y");
        carouselOperation = gamepad1.b;
        gamepad1StrafeToggle = gamepadKeyboard1.activeBefore.contains("left_bumper");
        gamepad2StrafeToggle = gamepadKeyboard2.activeBefore.contains("left_bumper");
        gamepad1RotationToggle = gamepadKeyboard1.activeBefore.contains("right_bumper");
        gamepad2RotationToggle = gamepadKeyboard2.activeBefore.contains("right_bumper");
        liftpos0 = gamepadKeyboard2.activeBefore.contains("b");
        liftpos1 = gamepadKeyboard2.activeBefore.contains("a");
        liftpos2 = gamepadKeyboard2.activeBefore.contains("x");
        liftpos3 = gamepadKeyboard2.activeBefore.contains("y");

        gamepad1X = gamepad1.left_stick_x;
        gamepad1Y = -gamepad1.left_stick_y;
        collectorSlowLeft = gamepad1.dpad_left;
        collectorSlowRight = gamepad1.dpad_right;
        collectorSlowDown = gamepad1.dpad_down;
        collectorSlowUp = gamepad1.dpad_up;
        gamepad1Rot = gamepad1.right_stick_x;
        gamepad2X = gamepad2.left_stick_x;
        gamepad2Y = -gamepad2.left_stick_y;
        liftSlowLeft = gamepad2.dpad_left;
        liftSlowRight = gamepad2.dpad_right;
        liftSlowDown = gamepad2.dpad_down;
        liftSlowUp = gamepad2.dpad_up;
        gamepad2Rot = gamepad2.right_stick_x;
        //Buttons
        y = gamepad1.y;
        x = gamepad1.x;
        a = gamepad1.a;
        b = gamepad1.b;

        collectorRevCCW = gamepad1.left_trigger;
        collectorRevCW = gamepad1.right_trigger;
        liftRevCCW = gamepad2.left_trigger;
        liftRevCW = gamepad2.right_trigger;
    }
}
