package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.DoubleTelemetry;

public class AutomatorClass {
    protected DoubleTelemetry telemetry;
    protected HardwareMap hardwareMap;

    public AutomatorClass(DoubleTelemetry telemetry, HardwareMap hardwareMap) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
    }
}
