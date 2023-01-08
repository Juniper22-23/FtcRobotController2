package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.Mechanisms.ConeTransporter;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class AutonomousClass2 extends LinearOpMode {

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
    private ElapsedTime timer;

    public double runningX;
    public double runningY;
    public double runningHeading = Math.toRadians(90);
    public double startX = 34;
    public double startY = 65;
    private int tensorFlowValue = 0;
    private int numberOfCycles = 1;
    private int numberOfCones = 15;


    public void runOpMode() {
        telemetry.clear();
        try {
            //Initialize class variables here
            drivetrain = new SampleMecanumDrive(hardwareMap);
            trajectoryClass = new TrajectoryClass(telemetry, hardwareMap);
            dashboard = FtcDashboard.getInstance();
            doubleTelemetry = new DoubleTelemetry(telemetry, dashboard);
            tensorFlow = new TensorFlowClass(telemetry, hardwareMap);
            coneTransporter = new ConeTransporter(telemetry, hardwareMap);
            timer = new ElapsedTime();

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
        telemetry.update();
        while (opModeIsActive()) {
            try {

                //tensorflow
                //doubleTelemetry.initializePacket();
                tensorFlowValue = tensorFlow.getRecognition();
                tensorFlowValue = tensorFlow.getRecognition();
                telemetry.addData("tensorflowValue", tensorFlowValue);

                //driving to the high junction to drop preload_____________________________________________________________________
                coneTransporter.setRiseLevel(3);
                coneTransporter.lift();
                trajectoryClass.splineToPosition(runningX, runningY, runningHeading, runningX, 45, 0); //add distance
                runningX = trajectoryClass.getPositionX(startX);
                runningY = trajectoryClass.getPositionY(startY);
                trajectoryClass.splineToPosition(runningX, runningY, runningHeading, 22.75, 49.21875, runningHeading); //add distance

                timer.reset();
                while (timer.time() < 2000) {
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
                }

                //Cycle - junction to cone pickup, and back to junction_________________________________________________________________
                for(int i = 0; i <= numberOfCycles; i++) {
                    coneTransporter.setRiseLevel(numberOfCones);
                    coneTransporter.lift();
                    trajectoryClass.splineToPosition(runningX, runningY, runningHeading, 55.25, 45, -90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                    coneTransporter.inCone(numberOfCones); //5 cones
                    coneTransporter.lift();
                    numberOfCones--;
                    timer.reset();
                    while (timer.time() < 5000) {
                        coneTransporter.setGripperPosition(0.75);
                        coneTransporter.grip();
                        coneTransporter.setRiseLevel(1); //CAN MAKE FASTER
                        coneTransporter.lift();
                    }
                    coneTransporter.setRiseLevel(3);
                    coneTransporter.lift();
                    trajectoryClass.splineToPosition(runningX, runningY, runningHeading, 22.75, 49.21875, -90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);

                    trajectoryClass.splineToPosition(runningX, runningY, runningHeading, 8.75, 34, 90); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                    trajectoryClass.forward(runningX, runningY, runningHeading, 4.21875); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                    timer.reset();
                    while (timer.time() < 2000) {
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.grip();
                    }
                }

                //Parking - tensorflow_____________________________________________________________________________
                if (tensorFlowValue == 3) {
                    trajectoryClass.splineToPosition(runningX, runningY, runningHeading, 56.5, 45, runningHeading); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                } else if(tensorFlowValue == 2){
                    trajectoryClass.splineToPosition(runningX, runningY, runningHeading, 34, 45, runningHeading); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                } else if (tensorFlowValue == 1){
                    trajectoryClass.splineToPosition(runningX, runningY, runningHeading, 11.5, 45, runningHeading); //add distance
                    runningX = trajectoryClass.getPositionX(startX);
                    runningY = trajectoryClass.getPositionY(startY);
                }

                telemetry.update();
            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
            telemetry.update();
        }
    }
}
