package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "SimpleMotorRun")
public class SimpleMotorRun extends LinearOpMode{
    SetupClass robot = new SetupClass();
    public int position = 0;
    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap);
        waitForStart();
        while(opModeIsActive()){

        if(gamepad1.a){
            position += 2;
            position = Range.clip(position, 0, 500);
            robot.LinearSlides.setTargetPosition(position);
            robot.LinearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LinearSlides.setPower(0.6);
            telemetry.update();
            sleep(5);

        }else if(gamepad1.b){
            position -=2;
            position = Range.clip(position, 0, 500);
            robot.LinearSlides.setTargetPosition(position);
            robot.LinearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LinearSlides.setPower(0.6);
            telemetry.update();
            sleep(5);

        }

        telemetry.addData(">", "position: " + position);
        telemetry.addData(">", "Going up ");
        telemetry.update();
        }


    }
}
