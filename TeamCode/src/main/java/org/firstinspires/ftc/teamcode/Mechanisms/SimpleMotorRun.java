package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
//aaa

@TeleOp(name = "SimpleMotorRun")
public class SimpleMotorRun extends LinearOpMode{
    public int position = 0;
    public DcMotor motor = this.hardwareMap.get(DcMotor.class, "motor");
    public void runOpMode() throws InterruptedException{
        waitForStart();
        while(opModeIsActive()){
            //aaa

        if(gamepad1.a){
            position += 2;
            position = Range.clip(position, 0, 500);
            motor.setTargetPosition(position);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(0.6);
            telemetry.update();
            sleep(5);

        }else if(gamepad1.b){
            position -=2;
            position = Range.clip(position, 0, 500);
            motor.setTargetPosition(position);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(0.6);
            telemetry.update();
            sleep(5);

        }

        telemetry.addData(">", "position: " + position);
        telemetry.addData(">", "Going up ");
        telemetry.update();
        }


    }
}
