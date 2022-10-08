package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.RobotCenter;

// Youtube video for programming Tele-Op: https://www.youtube.com/watch?v=gnSW2QpkGXQ
// Robot center programming

@TeleOp
public class DriverControlClass extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private RobotCenter robotCenter;


    public void runOpMode() {

        // setup
        controller = new Controller(gamepad1, gamepad2);
        robotCenter = new RobotCenter(telemetry, hardwareMap);


        while (opModeIsActive()) {
            controller.update();

            robotCenter.drive(controller.gamepad1X, controller.gamepad1Y, controller.gamepad1Rot);
        }
    }
}
