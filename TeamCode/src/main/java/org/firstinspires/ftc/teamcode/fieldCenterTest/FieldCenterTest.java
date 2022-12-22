//package org.firstinspires.ftc.teamcode.fieldCenterTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.opentest4j.AssertionFailedError;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//
//public class FieldCenterTest {
//
//    Telemetry telemetry;
//    HardwareMap hardwareMap;
//    BasicController controller;
//    BasicFieldCenter fieldCenter;
//
//    private MockDcMotor leftBackMotor;
//    private MockDcMotor leftFrontMotor;
//    private MockDcMotor rightBackMotor;
//    private MockDcMotor rightFrontMotor;
//
//    private final double DOUBLE_DELTA = 0.0001;
//
//    @BeforeEach
//    public void beforeEach() {
//        this.telemetry = (new BasicLinearOpMode()).getTelemetry();
//
//        hardwareMap = new HardwareMap(null);
//        hardwareMap.put("leftBackMotor", new MockDcMotor("leftBackMotor"));
//        hardwareMap.put("leftFrontMotor", new MockDcMotor("leftFrontMotor"));
//        hardwareMap.put("rightBackMotor", new MockDcMotor("rightBackMotor"));
//        hardwareMap.put("rightFrontMotor", new MockDcMotor("rightFrontMotor"));
//
//        controller = new BasicController();
//
//        fieldCenter = new BasicFieldCenter(telemetry, hardwareMap);
//        fieldCenter.setImuAngle(0.0);
//
//        leftBackMotor = (MockDcMotor)fieldCenter.getLeftBackMotor();
//        leftFrontMotor = (MockDcMotor)fieldCenter.getLeftFrontMotor();
//        rightBackMotor = (MockDcMotor)fieldCenter.getRightBackMotor();
//        rightFrontMotor = (MockDcMotor)fieldCenter.getRightFrontMotor();
//    }
//
//    @Test
//    public void testFieldCenterInstantiation() {
//        assertEquals("leftBackMotor", leftBackMotor.getName());
//        assertEquals("leftFrontMotor", leftFrontMotor.getName());
//        assertEquals("rightBackMotor", rightBackMotor.getName());
//        assertEquals("rightFrontMotor", rightFrontMotor.getName());
//
//        assertEquals(DcMotor.Direction.REVERSE, leftBackMotor.getDirection());
//        assertEquals(DcMotor.Direction.REVERSE, leftFrontMotor.getDirection());
//        assertEquals(DcMotor.Direction.FORWARD, rightBackMotor.getDirection());
//        assertEquals(DcMotor.Direction.FORWARD, rightFrontMotor.getDirection());
//    }
//
//    @Test
//    public void testSettingMotorPower() {
//        final double LEFT_BACK_POWER = 0.1;
//        final double LEFT_FRONT_POWER = 0.2;
//        final double RIGHT_BACK_POWER = 0.3;
//        final double RIGHT_FRONT_POWER = 0.4;
//
//        setMotorPowers(LEFT_BACK_POWER, LEFT_FRONT_POWER, RIGHT_BACK_POWER, RIGHT_FRONT_POWER);
//        assertMotorPowers(LEFT_BACK_POWER, LEFT_FRONT_POWER, RIGHT_BACK_POWER, RIGHT_FRONT_POWER);
//    }
//
//    @Test
//    public void testSettingInvalidMotorPower() {
//        final double LEFT_BACK_POWER = -10.0;
//        final double LEFT_FRONT_POWER = -10.0;
//        final double RIGHT_BACK_POWER = 10.0;
//        final double RIGHT_FRONT_POWER = 10.0;
//
//        setMotorPowers(LEFT_BACK_POWER, LEFT_FRONT_POWER, RIGHT_BACK_POWER, RIGHT_FRONT_POWER);
//
//        assertThrows(AssertionFailedError.class, () ->
//                assertMotorPowers(LEFT_BACK_POWER, LEFT_FRONT_POWER, RIGHT_BACK_POWER, RIGHT_FRONT_POWER)
//        );
//    }
//
//    @Test
//    public void testForwardMotorPower() {
//        controller.gamepad1X = 0.0;
//        controller.gamepad1Y = 1.0;
//        controller.gamepad1Rot = 0.0;
//        fieldCenter.drive(controller);
//
//        assertMotorPowers(1.0, 1.0, 1.0, 1.0);
//    }
//
//    @Test
//    public void testStrafeMotorPower() {
//        controller.gamepad1X = 1.0;
//        controller.gamepad1Y = 0.0;
//        controller.gamepad1Rot = 0.0;
//        fieldCenter.drive(controller);
//
//        assertMotorPowers(-1.0, 1.0, 1.0, -1.0);
//    }
//
//    @Test
//    public void testRotateMotorPower() {
//        controller.gamepad1X = 0.0;
//        controller.gamepad1Y = 0.0;
//        controller.gamepad1Rot = 1.0;
//        fieldCenter.drive(controller);
//
//        assertMotorPowers(-1.0, -1.0, 1.0, 1.0);
//    }
//
//    private void setMotorPowers(double leftBackPower, double leftFrontPower, double rightBackPower, double rightFrontPower) {
//        leftBackMotor.setPower(leftBackPower);
//        leftFrontMotor.setPower(leftFrontPower);
//        rightBackMotor.setPower(rightBackPower);
//        rightFrontMotor.setPower(rightFrontPower);
//    }
//
//    private void assertMotorPowers(double leftBackPower, double leftFrontPower, double rightBackPower, double rightFrontPower) {
//        assertEquals(leftBackPower, leftBackMotor.getPower(), DOUBLE_DELTA);
//        assertEquals(leftFrontPower, leftFrontMotor.getPower(), DOUBLE_DELTA);
//        assertEquals(rightBackPower, rightBackMotor.getPower(), DOUBLE_DELTA);
//        assertEquals(rightFrontPower, rightFrontMotor.getPower(), DOUBLE_DELTA);
//    }
//}