package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;

public abstract class BasicDrivetrain extends Mechanism {

    protected DcMotor leftBackMotor;
    protected DcMotor leftFrontMotor;
    protected DcMotor rightBackMotor;
    protected DcMotor rightFrontMotor;

    protected double imuAngle;

    public BasicDrivetrain(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        leftBackMotor = this.hardwareMap.get(DcMotor.class, "leftBackMotor");
        leftFrontMotor = this.hardwareMap.get(DcMotor.class,"leftFrontMotor");
        rightBackMotor = this.hardwareMap.get(DcMotor.class,"rightBackMotor");
        rightFrontMotor = this.hardwareMap.get(DcMotor.class,"rightFrontMotor");

        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public abstract void drive(BasicController controller);

    public DcMotor getLeftBackMotor() { return leftBackMotor; }
    public DcMotor getLeftFrontMotor() { return leftFrontMotor; }
    public DcMotor getRightBackMotor() { return rightBackMotor; }
    public DcMotor getRightFrontMotor() { return rightFrontMotor; }

    public void setImuAngle(double newImuAngle) { imuAngle = newImuAngle; }
    public double getImuAngle() { return imuAngle; }
}
