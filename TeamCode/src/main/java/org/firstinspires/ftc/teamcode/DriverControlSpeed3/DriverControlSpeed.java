package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.RobotCenter;
import org.firstinspires.ftc.teamcode.RobotCenterExample;

// Youtube video for programming Tele-Op: https://www.youtube.com/watch?v=gnSW2QpkGXQ
// Robot center programming
// tensor https://www.youtube.com/watch?v=2FmcHiLCwTU help

@TeleOp
public class DriverControlSpeed extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private RobotCenter RobotCenterExample;

    public void runOpMode() {
        telemetry.clear();
        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            RobotCenterExample = new RobotCenter(telemetry, hardwareMap);

        } catch (Exception exception) {
            telemetry.addLine(exception.getMessage());
            telemetry.update();
        }
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            try {
                controller.update();

                RobotCenterExample.drive(controller.gamepad1X, controller.gamepad1Y, controller.gamepad1Rot);
            } catch (Exception exception) {
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
