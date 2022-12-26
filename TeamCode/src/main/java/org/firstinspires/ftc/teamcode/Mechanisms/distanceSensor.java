package org.firstinspires.ftc.teamcode.Mechanisms;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class distanceSensor extends LinearOpMode{
    private DistanceSensor dsSensor = this.hardwareMap.get(DistanceSensor.class, "distanceSensor");
    private double distance;
    private Telemetry telemetry;

    public void runOpMode() throws InterruptedException {
        waitForStart();
        while (opModeIsActive()) {
            distance = dsSensor.getDistance(DistanceUnit.MM);
            if (distance <= 30) {
                telemetry.addData("-","Close enough to drop");
            } else if (distance > 30) {
                telemetry.addData("-","FAR FROM DROP ZONE");
            }
            telemetry.update();
        }
    }
}
