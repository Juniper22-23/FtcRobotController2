package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Controller {
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private GamepadKeyboard gamepadKeyboard1;
    private GamepadKeyboard gamepadKeyboard2;

    public boolean freightOuttake;
    public boolean freightIntake;
    public boolean dumperToggle;
    public boolean carouselOperation;
    public boolean dumperCollect;
    public boolean dumperDump;
    public boolean liftPos0;
    public boolean liftPos1;
    public boolean liftPos2;
    public boolean liftPos3;

    //wheel

    public double gamepad1X;
    public double gamepad1Y;
    public boolean collectorSlowLeft;
    public boolean collectorSlowRight;
    public boolean collectorSlowDown;
    public boolean collectorSlowUp;
    public double gamepad1Rot;
    public double liftX;
    public double liftY;
    public boolean liftSlowLeft;
    public boolean liftSlowRight;
    public boolean liftSlowDown;
    public boolean liftSlowUp;
    public double liftRot;

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
        dumperCollect = gamepadKeyboard2.activeBefore.contains("left_bumper");
        dumperDump = gamepadKeyboard2.activeBefore.contains("right_bumper");
        liftPos0 = gamepadKeyboard2.activeBefore.contains("b");
        liftPos1 = gamepadKeyboard2.activeBefore.contains("a");
        liftPos2 = gamepadKeyboard2.activeBefore.contains("x");
        liftPos3 = gamepadKeyboard2.activeBefore.contains("y");

        gamepad1X = gamepad1.left_stick_x;
        gamepad1Y = -gamepad1.left_stick_y;
        collectorSlowLeft = gamepad1.dpad_left;
        collectorSlowRight = gamepad1.dpad_right;
        collectorSlowDown = gamepad1.dpad_down;
        collectorSlowUp = gamepad1.dpad_up;
        gamepad1Rot = gamepad1.right_stick_x;
        liftX = -gamepad2.left_stick_x;
        liftY = gamepad2.left_stick_y;
        liftSlowLeft = gamepad2.dpad_left;
        liftSlowRight = gamepad2.dpad_right;
        liftSlowDown = gamepad2.dpad_down;
        liftSlowUp = gamepad2.dpad_up;
        liftRot = gamepad2.right_stick_x;

        collectorRevCCW = gamepad1.left_trigger;
        collectorRevCW = gamepad1.right_trigger;
        liftRevCCW = gamepad2.left_trigger;
        liftRevCW = gamepad2.right_trigger;
    }
}
