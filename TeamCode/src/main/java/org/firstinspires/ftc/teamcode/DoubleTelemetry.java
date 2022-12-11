package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DoubleTelemetry {

    private final Telemetry telemetry;
    private final FtcDashboard dashboard;
    private TelemetryPacket packet;

    public DoubleTelemetry(Telemetry telemetry, FtcDashboard dashboard) {
        this.telemetry = telemetry;
        this.dashboard = dashboard;
    }

    public void initializePacket() {
        packet = new TelemetryPacket();
    }

    public void clear() {
        telemetry.clear();
        packet.clearLines();
    }

    public void addLine(String str) {
        telemetry.addLine(str);
        packet.put("", str);
    }

    public void addData(String label, String data) {
        telemetry.addData(label, data);
        packet.put("", data);
    }
    public void addData(String label, double data) {
        telemetry.addData(label, data);
        packet.put("", data);
    }

    public void update() {
        telemetry.update();
        dashboard.sendTelemetryPacket(packet);
    }
}
