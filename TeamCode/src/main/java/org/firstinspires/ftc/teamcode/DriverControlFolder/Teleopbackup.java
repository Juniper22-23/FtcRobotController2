package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.FieldCenterAuto;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;

@TeleOp(name = "Teleopbackup", group = "Tele-Op")
public class Teleopbackup extends LinearOpMode {

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
        coneTransporter.initialize();
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
                if (controller.leftTrigger >= 0.2f) {
                    rotationToggle = true;
                } else{
                    rotationToggle = false;
                }
                if (controller.leftTrigger >= 0.2f) {
                    strafeToggle = true;
                } else {
                    strafeToggle = false;
                }

                fieldCenterAuto.drive(gamepadX, gamepadY, gamepadRot, rotationToggle, strafeToggle);

                //CONETRANSPORTER___________________________________________________________________________
                if (controller.b) {
                    coneTransporter.setRiseLevel(0);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if (controller.a) {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if (controller.x) {
                    coneTransporter.setRiseLevel(2);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if (controller.y) {
                    coneTransporter.setRiseLevel(3);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                } else if(controller.dpadDown){
                    coneTransporter.setRiseLevel(-1);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.lift();
                }

                if(controller.dpadLeft){
                    coneTransporter.moveUp();
                } else if(controller.dpadRight){
                    coneTransporter.moveDown();
                }

                //GRIPPER__________________________________________________________________________________

                if(controller.leftBumper && !(controller.rightBumper)){
                    coneTransporter.setGripperPosition(.75);
                    coneTransporter.grip();
                }

                if(controller.rightBumper && !(controller.leftBumper)){
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
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