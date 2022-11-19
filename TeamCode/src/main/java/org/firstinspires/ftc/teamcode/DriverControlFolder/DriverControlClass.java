package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.FieldCenter;
import org.firstinspires.ftc.teamcode.RobotCenter;

// Youtube video for programming Tele-Op: https://www.youtube.com/watch?v=gnSW2QpkGXQ
// Robot center programming
// tensor https://www.youtube.com/watch?v=2FmcHiLCwTU help

@TeleOp(name = "DriverControlClass1", group = "Tele-Op")
public class DriverControlClass extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private RobotCenter robotCenter;
    private FieldCenter fieldCenter;

    public void runOpMode() {
        telemetry.clear();
        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            robotCenter = new RobotCenter(telemetry, hardwareMap);
            fieldCenter = new FieldCenter(telemetry, hardwareMap);

        } catch (Exception exception) {
            telemetry.addLine(exception.getMessage());
            telemetry.update();
        }
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            try {
                controller.update();

                fieldCenter.drive(controller.gamepad1X, controller.gamepad1Y, controller.gamepad2Rot);
            } catch (Exception exception) {
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
