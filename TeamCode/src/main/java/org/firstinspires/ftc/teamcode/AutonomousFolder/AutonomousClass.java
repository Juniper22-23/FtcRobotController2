package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutonomousClass extends LinearOpMode {

    // declare class variables here
    //private SampleMecanumDrive drive;
    //private FreightTransporter frightTransporter;
    private TensorFlowClass tensorFlow;
    private FtcDashboard dashboard;
    private int tensorFlowValue = 0;


    public void runOpMode() {
        telemetry.clear();
        try {
            tensorFlow = new TensorFlowClass(telemetry, hardwareMap);
            dashboard = FtcDashboard.getInstance();
        } catch (Exception exception) {
            telemetry.addLine("Outside of the while loop:");
            telemetry.addLine(exception.getMessage());
            telemetry.update();
        }


        waitForStart();
        telemetry.update();
        while (opModeIsActive()) {
            try {
                TelemetryPacket packet = new TelemetryPacket();
                tensorFlowValue = tensorFlow.getRecognition(packet);
                telemetry.addData("tensorflowValue", tensorFlowValue);
                dashboard.sendTelemetryPacket(packet);
            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
