package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class AutonomousClass extends LinearOpMode {

    /*
    pseudocode:
    1) Drive forward 3.75 blocks to reach high shipping hub and drop of cone
    2) Spline back 1/2 block, and left 1/2 block, 180 degrees turn - AND lift linear slides
    3) Drive Left 1/2 block, pick up cone, drive right 1/2 block
    4) Spline forward 1/2 block and right 1/2 block, 180 degrees turn - AND lift linear slides
    5) Drop off the cone on high junction
    6) Repeat Cycle

    Between Blue and Red:
    1) drive in Y direction 3.75 blocks
    2) drive in -Y .5 block, X .5 block, 180
    3) drive X .75 block
    4) drive -x .75 block
    5) drive Y .5 block, -x .5 block, 180
    */

    // declare class variables here
    private SampleMecanumDrive drivetrain;
    private TrajectoryClass trajectoryClass;
    private TensorFlowClass tensorFlow;
    private FtcDashboard dashboard;
    private DoubleTelemetry doubleTelemetry;
    private ConeTransporter coneTransporter;

    public double runningX;
    public double runningY;
    public double runningHeading;
    public double startX = 0.0;
    public double startY =  0.0;
    private int tensorFlowValue = 0;

    public void runOpMode() {
        telemetry.clear();
        try {
            //Initialize class variables here
            drivetrain = new SampleMecanumDrive(hardwareMap);
            trajectoryClass = new TrajectoryClass(doubleTelemetry, hardwareMap);
            dashboard = FtcDashboard.getInstance();
            doubleTelemetry = new DoubleTelemetry(telemetry, dashboard);
            tensorFlow = new TensorFlowClass(doubleTelemetry, hardwareMap);
            coneTransporter = new ConeTransporter(telemetry, hardwareMap);

            runningX = startX;
            runningY = startY;
            runningHeading = Math.toRadians(-90);
            trajectoryClass.setPosition(runningX, runningY, runningHeading);

        } catch (Exception exception) {
            telemetry.addLine("Outside of the while loop:");
            telemetry.addLine(exception.getMessage());
            telemetry.update();
        }

        waitForStart();
        doubleTelemetry.update();
        while (opModeIsActive()) {
            try {

                //tensorflow
                doubleTelemetry.initializePacket();
                tensorFlowValue = tensorFlow.getRecognition();
                doubleTelemetry.addData("tensorflowValue", tensorFlowValue);

                //driving to the high junction to drop preload_____________________________________________________________________
                trajectoryClass.strafeLeft(runningX, runningY, runningHeading, 90); //add distance
                runningX = trajectoryClass.getPositionX(startX);
                runningY = trajectoryClass.getPositionY(startY);
                coneTransporter.setRiseLevel(3);
                coneTransporter.lift();
                trajectoryClass.forward(runningX, runningY, runningHeading, 90); //add distance
                coneTransporter.setGripperPosition(0.75);
                coneTransporter.grip();
                trajectoryClass.backward(runningX, runningY, runningHeading, 90); //add distance

                //Cycle - junction to cone pickup, and back to junction_________________________________________________________________
                coneTransporter.setRiseLevel(0);
                coneTransporter.lift();
                trajectoryClass.splineToPosition(runningX, runningY, runningHeading, runningX, runningY, runningHeading); //add distance
                runningX = trajectoryClass.getPositionX(startX);
                runningY = trajectoryClass.getPositionY(startY);
                trajectoryClass.forward(runningX, runningY, runningHeading, 90); //add distance
                runningX = trajectoryClass.getPositionX(startX);
                runningY = trajectoryClass.getPositionY(startY);
                //gripper method
                trajectoryClass.backward(runningX, runningY, runningHeading, 90); //add distance
                runningX = trajectoryClass.getPositionX(startX);
                runningY = trajectoryClass.getPositionY(startY);
                trajectoryClass.splineToPosition(runningX, runningY, runningHeading, runningX, runningY, runningHeading); //add distance
                runningX = trajectoryClass.getPositionX(startX);
                runningY = trajectoryClass.getPositionY(startY);
                coneTransporter.setRiseLevel(3);
                coneTransporter.lift();
                trajectoryClass.forward(runningX, runningY, runningHeading, 90); //add distance
                runningX = trajectoryClass.getPositionX(startX);
                runningY = trajectoryClass.getPositionY(startY);

                //Parking - tensorflow_____________________________________________________________________________
                if (tensorFlowValue == 3) {
                    trajectoryClass.strafeLeft(runningX, runningY, runningHeading, 90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                    trajectoryClass.backward(runningX, runningY, runningHeading, 90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                } else if(tensorFlowValue == 2){
                    trajectoryClass.strafeLeft(runningX, runningY, runningHeading, 90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                } else if (tensorFlowValue == 1){
                    trajectoryClass.strafeLeft(runningX, runningY, runningHeading, 90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                    trajectoryClass.forward(runningX, runningY, runningHeading, 90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                }
            } catch (Exception exception) {
                doubleTelemetry.addLine("Inside of the while loop:");
                doubleTelemetry.clear();
                doubleTelemetry.addLine(exception.getMessage());
            }
            doubleTelemetry.update();
        }
    }
}
