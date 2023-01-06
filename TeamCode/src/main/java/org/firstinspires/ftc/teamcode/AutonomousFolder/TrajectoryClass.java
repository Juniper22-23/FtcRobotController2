package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


public class TrajectoryClass extends AutomatorClass {

    SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);

    public TrajectoryClass(DoubleTelemetry doubleTelemetry, HardwareMap hardwareMap) {
        super(doubleTelemetry, hardwareMap);
    }

    public void setPosition(double runningX, double runningY, double runningHeading){
        drivetrain.setPoseEstimate(new Pose2d(runningX, runningY, runningHeading));
    }

    public double getPositionX(double startX){
        return drivetrain.getPoseEstimate().getX() - startX;

    }

    public double getPositionY(double startY){
        return drivetrain.getPoseEstimate().getX() - startY;
    }

    public void forward(double x, double y, double heading, double distance) {
        Trajectory goForward = drivetrain.trajectoryBuilder(new Pose2d(x, y, heading))
                .forward(distance)
                .build();
        drivetrain.followTrajectory(goForward);
    }

    public void backward(double x, double y, double heading, double distance) {
        Trajectory goBackward = drivetrain.trajectoryBuilder(new Pose2d(x, y, heading))
                .back(distance)
                .build();
        drivetrain.followTrajectory(goBackward);
    }

    public void lineToPosition (double x, double y, double heading, double newX, double newY) {
        Trajectory lineToPosition = drivetrain.trajectoryBuilder(new Pose2d(x, y, heading))
                .lineTo(new Vector2d(newX, newY))
                .build();
        drivetrain.followTrajectory(lineToPosition);
    }

    public void strafeLeft(double x, double y, double heading, double distance) {
        Trajectory strafeLeft = drivetrain.trajectoryBuilder(new Pose2d(x, y, heading))
                .strafeLeft(distance)
                .build();
        drivetrain.followTrajectory(strafeLeft);
    }

    public void strafeRight(double x, double y, double heading, double distance) {
        Trajectory strafeRight = drivetrain.trajectoryBuilder(new Pose2d(x, y, heading))
                .strafeRight(distance)
                .build();
        drivetrain.followTrajectory(strafeRight);
    }

    public void strafeToPosition (double x, double y, double heading, double newX, double newY) {
        Trajectory strafeToPosition = drivetrain.trajectoryBuilder(new Pose2d(x, y, heading))
                .strafeTo(new Vector2d(newX, newY))
                .build();
        drivetrain.followTrajectory(strafeToPosition);
    }

    public void splineToPosition (double x, double y, double heading, double newX, double newY, double newHeading) {
        Trajectory splineToPosition = drivetrain.trajectoryBuilder(new Pose2d(x, y, Math.toRadians(heading)))
                .splineTo(new Vector2d(newX, newY), Math.toRadians(newHeading))
                .build();
        drivetrain.followTrajectory(splineToPosition);

    }
}

