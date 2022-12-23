package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class AutonomousClass extends LinearOpMode {

    // declare class variables here
    private SampleMecanumDrive drivetrain;
    private TensorFlowClass tensorFlow;
    private FtcDashboard dashboard;
    private DoubleTelemetry doubleTelemetry;
    private ConeTransporter coneTransporter;

    public static double runningX;
    public static double runningY;
    private double startX = 0;
    private double startY =  0;
    private int tensorFlowValue = 0;


    public void runOpMode() {
        telemetry.clear();
        try {
            drivetrain = new SampleMecanumDrive(hardwareMap);
            dashboard = FtcDashboard.getInstance();
            doubleTelemetry = new DoubleTelemetry(telemetry, dashboard);
            tensorFlow = new TensorFlowClass(doubleTelemetry, hardwareMap);
            coneTransporter = new ConeTransporter(telemetry, hardwareMap);

            runningX = startX;
            runningY = startY;


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
