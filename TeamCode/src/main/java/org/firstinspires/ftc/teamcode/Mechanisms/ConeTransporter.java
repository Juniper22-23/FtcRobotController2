package org.firstinspires.ftc.teamcode.Mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivetrain;
import org.firstinspires.ftc.teamcode.Mathematics;
import org.firstinspires.ftc.teamcode.Mechanism;

public class ConeTransporter extends Mechanism {
    /*
    This is the general explanation for this class:
    liftPos0 = position to pick up the cone
    liftPos1 = lowest junction
    liftPos2 = medium junction
    liftPos3 = high junction

    gripperClose = gripper closed i.e. not grasping the cone - NOT ACTUALLY USED
    gripperOpen = gripper expanded i.e. grasping the cone
    */

    //LINEAR SLIDES________________________________________________________________________________
    private DcMotor linearSlides;
    private int riseLevel = 0;
    public float diameterOfSpool = 30.48f;
    public float linearSlidesSpeed = 0.75f;
    //public int LINEAR_SLIDES_IN_CONE = -50;
    public double LINEAR_SLIDES_LOW = 337.5;// 13.5 inches converted to mm(low junction)
    public double LINEAR_SLIDES_MEDIUM = 587.5;// 23.5 inches converted to mm(medium junction)
    public double LINEAR_SLIDES_HIGH = 837.5;// 33.5 inches converted to mm(high junction)
    public double LINEAR_SLIDES_NORM = 0;
    public double LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
    public double ticks;
    public double ticksPerRotation = 384.5;// 435 RPM motor 5202 GoBilda TPR
    public int ticksAsInt;
    public int currentTick;

    //GRIPPER______________________________________________________________________________________
    private Servo gripper;
    public double gripperPosition;

    public ConeTransporter(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        linearSlides = this.hardwareMap.get(DcMotor.class, "linearSlides");
        linearSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gripper = this.hardwareMap.get(Servo.class, "gripper");
    }

    private int equate(double height) {
        ticks = ticksPerRotation * (height / (diameterOfSpool * Math.PI) );
        ticksAsInt = (int)ticks;
        return ticksAsInt;
    }

    public void grip(){
        gripper.setPosition(gripperPosition);
    }

    public void lift(){
        rise(riseLevel);
    }

    private void rise(int riseLevel){
        if(riseLevel == 0){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if(riseLevel == 1){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_LOW;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if(riseLevel == 2){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_MEDIUM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if(riseLevel == 3){
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_HIGH;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);

        }
    }

    public void setRiseLevel(int level){
        riseLevel = level;
    }

    public void setGripperPosition(double position){
        gripperPosition = position;
    }

    public int getRiseLevel(int level){
        return riseLevel;
    }

    public double getGripperPosition(double position){
        return gripperPosition;
    }
}

