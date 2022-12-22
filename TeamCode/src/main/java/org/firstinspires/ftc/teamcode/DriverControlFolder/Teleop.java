package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.FieldCenterAuto;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;

@TeleOp(name = "TeleopNew", group = "Tele-Op")
public class Teleop extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private FieldCenterAuto fieldCenterAuto;
    private ConeTransporter coneTransporter;

    public void runOpMode() {
        telemetry.clear();
        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            fieldCenterAuto = new FieldCenterAuto(telemetry, hardwareMap);
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
                double gamepadX;
                double gamepadY;
                double gamepadRot;
                boolean rotationToggle = false;
                boolean strafeToggle = false;

                if (Math.abs(controller.gamepad1X) > 0.01) {
                    gamepadX = controller.gamepad1X;
                } else if (Math.abs(controller.gamepad2X) > 0.01) {
                    gamepadX = controller.gamepad2X;
                } else {
                    gamepadX = 0;
                }
                if (Math.abs(controller.gamepad1Y) > 0.01) {
                    gamepadY = controller.gamepad1Y;
                } else if (Math.abs(controller.gamepad2Y) > 0.01) {
                    gamepadY = controller.gamepad2Y;
                } else {
                    gamepadY = 0;
                }
                if (Math.abs(controller.gamepad1Rot) > 0.01) {
                    gamepadRot = -controller.gamepad1Rot;
                } else if (Math.abs(controller.gamepad2Rot) > 0.01) {
                    gamepadRot = -controller.gamepad2Rot;
                } else {
                    gamepadRot = 0;
                }
                if (controller.gamepad1RotationToggle || controller.gamepad2RotationToggle) {
                    rotationToggle = true;
                }
                if (controller.gamepad1StrafeToggle || controller.gamepad2StrafeToggle) {
                    strafeToggle = true;
                }

                fieldCenterAuto.drive(gamepadX, gamepadY, gamepadRot, rotationToggle, strafeToggle);

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

                //GRIPPER__________________________________________________________________________________
                if(controller.leftBumper){
                    coneTransporter.setGripperPosition(.75);
                } else if(controller.rightBumper){
                    coneTransporter.setGripperPosition(1.0);
                }
            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
