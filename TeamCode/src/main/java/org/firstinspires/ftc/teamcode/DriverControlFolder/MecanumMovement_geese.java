package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "Final Drive", group = "Tele-op")
public class MecanumMovement_geese extends LinearOpMode {

    double x1 = 0; // left and right
    double y1 = 0; // front and back
    double x2 = 0; // fixed 45 deg offset for the x-value
    double y2 = 0; // fixed 45 deg offset for the y-value
    double fortyFiveInRads = -Math.PI / 4;
    double cosine45 = Math.cos(fortyFiveInRads);
    double sine45 = Math.sin(fortyFiveInRads);
    SetupClass_geese robot = new SetupClass_geese();
    double INTAKE_HOME = 0.0;
    double INTAKE_MIN = 0.0;
    double INTAKE_MAX = 1;
    double INTAKE_POSITION = INTAKE_HOME;
    double INTAKE_SPEED = 0.1;
    int intakeMotorPos = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("---", "Hi driver, robot wishes you a good day :-)");
        telemetry.update();
        robot.init(hardwareMap);
        robot.intakeServo.setPosition(INTAKE_POSITION);
        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.intakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();
        while (opModeIsActive()) {

            double spin = gamepad1.right_stick_x;//For controlling the spin.
            //getting the y value of the joystick(I put a negative because the joystick is flipped.)
            y1 = -gamepad1.left_stick_y;
            //getting x value of the joystick
            x1 = gamepad1.left_stick_x;
            //recentering robot joystick(45 deg)
            y2 = y1 * cosine45 + x1 * sine45;
            x2 = x1 * cosine45 - y1 * sine45;

            if (spin > 0.2 || spin < -.2) {
                //set robot power levels to rotate
                robot.frontLeftMotor.setPower(spin);
                robot.frontRightMotor.setPower(-spin);
                robot.backLeftMotor.setPower(spin);
                robot.backRightMotor.setPower(-spin);
            } else {
                // Drive forward and backward
                robot.frontLeftMotor.setPower(x2);
                robot.frontRightMotor.setPower(y2);
                robot.backLeftMotor.setPower(y2);
                robot.backRightMotor.setPower(x2);
            }

            if (gamepad1.a) {
                robot.duckSpinner.setPower(1);
            } else {
                robot.duckSpinner.setPower(0.0);
            }
            if (gamepad1.y) {
                robot.duckSpinner.setPower(0.8);
            } else {
                robot.duckSpinner.setPower(0.0);
            }
            //Arm code
            if (gamepad1.dpad_down) {
                intakeMotorPos += 1;
                intakeMotorPos = Range.clip(intakeMotorPos, -250, 0);
                robot.intakeMotor.setTargetPosition(intakeMotorPos);
                robot.intakeMotor.setPower(0.4);

                telemetry.addData(">", "Going down");
            } else if (gamepad1.dpad_up) {
                intakeMotorPos -= 1;
                intakeMotorPos = Range.clip(intakeMotorPos, -250, 0);
                robot.intakeMotor.setTargetPosition(intakeMotorPos);
                robot.intakeMotor.setPower(0.4);
                telemetry.addData(">", "Going up");
            }


            //Claw code
            if (gamepad1.b) {
                INTAKE_POSITION += INTAKE_SPEED;
            } else if (gamepad1.x) {
                INTAKE_POSITION -= INTAKE_SPEED;

            }
            INTAKE_POSITION = Range.clip(INTAKE_POSITION, INTAKE_MIN, INTAKE_MAX);
            robot.intakeServo.setPosition(INTAKE_POSITION);

            telemetry.addData(">", "x2: " + x2);
            telemetry.addData(">", "y2: " + y2);
            telemetry.addData(">", "spin: " + spin);
            telemetry.addData(">", "IntakeClawPosition: " + INTAKE_POSITION);
            telemetry.addData("-", "Arm Position " + robot.intakeMotor.getCurrentPosition());
            telemetry.addData("-", "Arm Target Position:  " + intakeMotorPos);
            telemetry.update();

        }
    }
}