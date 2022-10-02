package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutomatorClass {
    protected Telemetry telemetry;
    protected HardwareMap hardwareMap;

    public AutomatorClass(Telemetry telemetry, HardwareMap hardwareMap) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
    }
}
