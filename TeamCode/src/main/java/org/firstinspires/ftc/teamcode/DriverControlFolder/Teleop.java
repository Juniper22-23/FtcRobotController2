/*
package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.FieldCenter;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;
import org.firstinspires.ftc.teamcode.Mechanisms.LinearSlidesMechanism;
import org.firstinspires.ftc.teamcode.RobotCenter;
//import org.firstinspires.ftc.teamcode.RobotCenter;


@TeleOp(name = "TeleopNew", group = "Tele-Op")
public class Teleop extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private RobotCenter robotCenter;
    private FieldCenter fieldCenter;
    private LinearSlidesMechanism linearSlides;
    private ConeTransporter coneTransporter;


    public void runOpMode() {
        telemetry.clear();
        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            // fieldCenter = new FieldCenter(telemetry, hardwareMap);
            coneTransporter = new ConeTransporter(telemetry, hardwareMap);

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
                //FIELDCENTER_______________________________________________________________________________
                // fieldCenter.drive(controller);

                //CONETRANSPORTER___________________________________________________________________________
                if (controller.liftPos0) {
                    coneTransporter.setRiseLevel(0);
                    coneTransporter.setGripperPosition(1);
                } else if (controller.liftPos1) {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.setGripperPosition(1);
                } else if (controller.liftPos2) {
                    coneTransporter.setRiseLevel(2);
                    coneTransporter.setGripperPosition(1);
                } else if (controller.liftPos3) {
                    coneTransporter.setRiseLevel(3);
                    coneTransporter.setGripperPosition(1);
                }
                if(controller.gripperOpen) {
                    coneTransporter.setGripperPosition(0);
                }
                coneTransporter.lift();

            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
*/
