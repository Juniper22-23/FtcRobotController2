package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Controller;
import org.firstinspires.ftc.teamcode.FieldCenterAuto;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;

import java.util.HashMap;
import java.util.Map;

enum TeleopModes {
    DRIVER_CONTROL, GRIP
}

enum GripperModes {
    GRIPPER_DOWN, GRIPPER_UP
}

@TeleOp(name = "TeleopNew3", group = "Tele-Op")
public class Teleop extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private FieldCenterAuto fieldCenterAuto;
    private ConeTransporter coneTransporter;

    private TeleopModes teleopMode;
    private GripperModes gripperMode;

    private HashMap<GripperModes, Double> gripperTimes;

    private ElapsedTime timer;
    double startTime;



    public void runOpMode() {
        telemetry.clear();
        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            fieldCenterAuto = new FieldCenterAuto(telemetry, hardwareMap);
            coneTransporter = new ConeTransporter(telemetry, hardwareMap);

            gripperTimes = new HashMap<GripperModes, Double>();
            gripperTimes.put(GripperModes.GRIPPER_DOWN, 1000.0);
            gripperTimes.put(GripperModes.GRIPPER_UP, 1000.0);

            timer = new ElapsedTime();
            timer.reset();
            startTime = timer.milliseconds();

            teleopMode = TeleopModes.DRIVER_CONTROL;
            gripperMode = GripperModes.GRIPPER_DOWN;
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
                //GRIPPER__________________________________________________________________________________

                telemetry.addData("", timer.milliseconds());
                telemetry.addData("startTime", startTime);

                if (controller.leftBumper) {
                    if (teleopMode == TeleopModes.DRIVER_CONTROL) {
                        teleopMode = TeleopModes.GRIP;
                    }
                }

                if (teleopMode == TeleopModes.DRIVER_CONTROL) {
                    if (controller.a) {
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
                    } else if(controller.b){
                        coneTransporter.setRiseLevel(0);
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.lift();
                    }

                    if(controller.dpadUp){
                        coneTransporter.moveUp();
                    }else if(controller.dpadDown){
                        coneTransporter.moveDown();
                    }
                } else if (teleopMode == TeleopModes.GRIP) {
                    if (gripperMode == GripperModes.GRIPPER_DOWN) {
                        // checks how long it's been since the timer was reset, which is the time when the gripper started moving
                        boolean gripperReady = timer.milliseconds() - startTime > gripperTimes.get(GripperModes.GRIPPER_DOWN);
                        boolean liftReady = coneTransporter.linearSlides.getCurrentPosition() == coneTransporter.LINEAR_SLIDES_IN_CONE;

                        if (!gripperReady) {
                            coneTransporter.setGripperPosition(1.0);
                            coneTransporter.grip();
                        }

                        if (!liftReady && gripperReady) {
                            coneTransporter.setRiseLevel(-1);
                            coneTransporter.lift();
                        }

                        if (gripperReady && liftReady) {
                            timer.reset();
                            gripperMode = GripperModes.GRIPPER_UP;
                        }
                    } else if (gripperMode == GripperModes.GRIPPER_UP) {
                        boolean gripperReady = timer.milliseconds() - startTime > gripperTimes.get(GripperModes.GRIPPER_UP);
                        boolean liftReady = coneTransporter.linearSlides.getCurrentPosition() == coneTransporter.LINEAR_SLIDES_NORM;

                        if (!gripperReady) {
                            coneTransporter.setGripperPosition(0.75);
                            coneTransporter.grip();
                        }

                        if (!liftReady && gripperReady) {
                            coneTransporter.setRiseLevel(0);
                            coneTransporter.lift();
                        }

                        if (gripperReady && liftReady) {
                            timer.reset();
                            gripperMode = GripperModes.GRIPPER_DOWN;
                            teleopMode = TeleopModes.DRIVER_CONTROL;
                        }
                    }
                }

              /*  if(controller.leftBumper){
                    coneTransporter.setRiseLevel(-1);
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
                    coneTransporter.lift();
                    //if(coneTransporter.linearSlides.getCurrentPosition() == 0){
                        coneTransporter.setGripperPosition(.75);
                        coneTransporter.grip();
                    //}
                } else {
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
                }*/

               /*if(controller.leftBumper && !(controller.rightBumper)){
                    coneTransporter.setGripperPosition(.75);
                    coneTransporter.grip();
               }

               if(controller.rightBumper && !(controller.leftBumper)){
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
               }*/

            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}