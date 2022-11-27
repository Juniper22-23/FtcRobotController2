package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutonomousClass extends LinearOpMode {

    // declare class variables here
    private TensorFlowCorrect tensorFlow;
    private int tensorFlowValue = 1;

    public void runOpMode() {

        tensorFlow = new TensorFlowCorrect(telemetry, hardwareMap);
        waitForStart();
        tensorFlowValue = tensorFlow.useTensor();
        telemetry.addData("tensorFlowValue", tensorFlowValue);
        telemetry.update();

        while (opModeIsActive()) {
            // main loop
        }
    }
}
