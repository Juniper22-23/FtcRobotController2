package org.firstinspires.ftc.teamcode.Tests;

import static org.junit.Assert.assertEquals;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotLibs.Drivetrain;
import org.junit.Before;
import org.junit.Test;

public class DrivetrainTest {

    HardwareMap hardwareMap;
    Drivetrain drivetrain;

    public static final double DOUBLE_DELTA = 0.0001;

    @Before
    public void before() {
        hardwareMap = new HardwareMap(null);
        for (Drivetrain.MotorName name : Drivetrain.MotorName.values())
        {
            hardwareMap.put(name.name(), new MockDcMotor(name.name()));
        }
        drivetrain = new Drivetrain(hardwareMap);
    }

    @Test
    public void testDrivetrainInstantiation() {
        assertEquals("LEFT_BACK", ((MockDcMotor)drivetrain.leftBackMotor).getName());
        assertEquals("RIGHT_BACK", ((MockDcMotor)drivetrain.rightBackMotor).getName());
        assertEquals("LEFT_FRONT", ((MockDcMotor)drivetrain.leftFrontMotor).getName());
        assertEquals("RIGHT_FRONT", ((MockDcMotor)drivetrain.rightFrontMotor).getName());

        assertEquals(DcMotor.Direction.REVERSE, drivetrain.leftBackMotor.getDirection());
        assertEquals(DcMotor.Direction.FORWARD, drivetrain.rightBackMotor.getDirection());
        assertEquals(DcMotor.Direction.REVERSE, drivetrain.leftFrontMotor.getDirection());
        assertEquals(DcMotor.Direction.FORWARD, drivetrain.rightFrontMotor.getDirection());
    }

    @Test
    public void testSettingMotorPower() {
        drivetrain.setPowers(0.3, 0.1,0.2, 0.4);

        assertDrivePowers(0.3, 0.1, 0.2, 0.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSettingInvalidMotorPower() {
        drivetrain.setPowers(0, -10, 10, 1);
    }

    @Test
    public void testForwardMecanumDriveSpeedAssignment() {
        drivetrain.mecanumDrive(1, 0, 0);

        assertDrivePowers(1, 1,1, 1);
    }

    @Test
    public void testStrafeMecanumDriveSpeedAssignment() {
        drivetrain.mecanumDrive(0, 1, 0);

        assertDrivePowers(1, -1, 1, -1);
    }

    @Test
    public void testRotateMecanumDriveSpeedAssignment() {
        drivetrain.mecanumDrive(0, 0, 1);

        assertDrivePowers(1, 1, -1, -1);
    }

    public void assertDrivePowers(double LF, double LB, double RB, double RF)
    {
        assertEquals(LF, drivetrain.leftFrontMotor.getPower(), DOUBLE_DELTA);
        assertEquals(LB, drivetrain.leftBackMotor.getPower(), DOUBLE_DELTA);
        assertEquals(RB, drivetrain.rightBackMotor.getPower(), DOUBLE_DELTA);
        assertEquals(RF, drivetrain.rightFrontMotor.getPower(), DOUBLE_DELTA);
    }
}