package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;

public class LinearSlides extends Mechanism {
    private DcMotor linearSlides;
    public Gamepad gamepad;
    public float linearSlidesSpeed = 0.8f;
    private int LINEAR_SLIDES_IN_CONE =-50;
    private int LINEAR_SLIDES_LOW = 200;
    private int LINEAR_SLIDES_MEDIUM = 400;
    private int LINEAR_SLIDES_HIGH = 600;
    private int LINEAR_SLIDES_NORM = 0;
    private int LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
    public LinearSlides(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        linearSlides = this.hardwareMap.get(DcMotor.class, "linearSlides");
        linearSlides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void run() {
        /*if(gamepad.y){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_HIGH;
        } else if(gamepad.x){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_MEDIUM;
        }else if(gamepad.b){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_LOW;
        }else if(gamepad.dpad_down){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_IN_CONE;
        }else if(gamepad.a){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
        }*/
        if(gamepad.dpad_up){
            LINEAR_SLIDES_CURRENT+=2;
        } else if(gamepad.dpad_down){
            LINEAR_SLIDES_CURRENT-=2;
        }
        linearSlides.setTargetPosition(LINEAR_SLIDES_CURRENT);
        linearSlides.setPower(linearSlidesSpeed);
        telemetry.addData(">", "LinearSlidesPosition: " + LINEAR_SLIDES_CURRENT);

    }

    public void stop() {
        linearSlides.setPower(0);
    }

}
