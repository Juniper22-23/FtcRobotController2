package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.FieldCenterEthan;
import org.firstinspires.ftc.teamcode.Mechanisms.GripperMechanism;
import org.firstinspires.ftc.teamcode.Mechanisms.LinearSlidesMechanism;


// Youtube video for programming Tele-Op: https://www.youtube.com/watch?v=gnSW2QpkGXQ
// Robot center programming
// tensor https://www.youtube.com/watch?v=2FmcHiLCwTU help

@TeleOp(name = "DriverControlClassEthan", group = "Tele-Op")
public class DriverControlClass_Ethan extends LinearOpMode {

    // declare class variables here
    public Controller controller;
    public FieldCenterEthan fieldCenter;
    public LinearSlidesMechanism linearSlides;
    public GripperMechanism gripperMechanism;

    public void runOpMode() {
        telemetry.clear();
        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            fieldCenter = new FieldCenterEthan(telemetry, hardwareMap);
            linearSlides = new LinearSlidesMechanism(telemetry, hardwareMap);
            gripperMechanism = new GripperMechanism(telemetry, hardwareMap);

        } catch (Exception exception) {
            telemetry.addLine("Outside of the while loop:");
            telemetry.addLine(exception.getMessage());
            telemetry.update();
        }
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            try {
                controller.update();
                linearSlides.run(controller);
                fieldCenter.drive(controller);
                gripperMechanism.run(controller);
            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
