package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.DoubleTelemetry;

@Autonomous
public class AutonomousClass extends LinearOpMode {

    // declare class variables here
    // private SampleMecanumDrive drive;
    private TensorFlowClass tensorFlow;
    private FtcDashboard dashboard;
    private DoubleTelemetry doubleTelemetry;
    private int tensorFlowValue = 0;


    public void runOpMode() {
        telemetry.clear();
        try {
            dashboard = FtcDashboard.getInstance();
            doubleTelemetry = new DoubleTelemetry(telemetry, dashboard);
            tensorFlow = new TensorFlowClass(doubleTelemetry, hardwareMap);
        } catch (Exception exception) {
            telemetry.addLine("Outside of the while loop:");
            telemetry.addLine(exception.getMessage());
            telemetry.update();
        }

        waitForStart();
        doubleTelemetry.update();
        while (opModeIsActive()) {
            try {
                doubleTelemetry.initializePacket();
                tensorFlowValue = tensorFlow.getRecognition();
                doubleTelemetry.addData("tensorflowValue", tensorFlowValue);
            } catch (Exception exception) {
                doubleTelemetry.addLine("Inside of the while loop:");
                doubleTelemetry.clear();
                doubleTelemetry.addLine(exception.getMessage());
            }
            doubleTelemetry.update();
        }
    }
}
