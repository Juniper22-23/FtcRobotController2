package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;
import java.util.ArrayList;
// the wold is not flat
public class TensorFlowClass extends AutomatorClass {
    private static final String vuforiaKey = "AYsOO+//////AAABmc2TlGXRrEpYsw1UPuEGK+xj/ybwffSKuL7ffLuhzm51GEM5ccJWjBO5XFTOmYCYiEVlCv2HpTa9+ZICMavximYCuGhJ0hCKtGTlYtTg/5AjtO6b1v9vswwtvKchMpcTFfhHK1A18R7FFbiJfQjTzznx1/Q6Et4TIRBqcEA5u7syU8suWSjWc3W/Kcy7ieQuQ15FoQsPQaw6JDZY32+xdyZlYbZ2MWfe0sPvQtkD+yJVa2wwZbBZTAoO6Q+rvRsHysQy0AqFB3sUKmRVvsV7gk7EQQ+edWgauCwgS23XZqwgkp5J/hKSZ/0SnXDuh1UQofuQFFxy57c9X18Cdky/Xl3hNTGUz393C8267cvb+JQy";

    private TFObjectDetector terry;
    private VuforiaLocalizer vuforia;

    private static final String modelTerry = "<tflite file>";
    private static final String[] labelTerry = {"image1", "image2", "image3"};

    public ArrayList<String> recognitionLabels;
    public double terryDuckLeft;
    public double terryDuckTop;
    public double terryDuckRight;
    public double terryDuckBottom;

    public TensorFlowClass(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        VuforiaLocalizer.Parameters vParams = new VuforiaLocalizer.Parameters();
        vParams.vuforiaLicenseKey = vuforiaKey;
        vParams.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(vParams);

        int monitorView = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tParams = new TFObjectDetector.Parameters(monitorView);
        tParams.minResultConfidence = 0.8f;
        tParams.isModelTensorFlow2 = true;
        tParams.inputSize = 320;
        terry = ClassFactory.getInstance().createTFObjectDetector(tParams, vuforia);
        terry.loadModelFromAsset(modelTerry, labelTerry);

        recognitionLabels = new ArrayList<String>();

        if (terry != null) {
            terry.activate();
            terry.setZoom(1.0, (16.0/9.0));
            telemetry.addLine("[TERRY]: Terry thinks he can be smart today.");
        } else {
            telemetry.addLine("[TERRY]: Terry is being dumb right now, please check back later.");
        }
    }

    public int getTensation() {
        if (terry != null) {
            terry.setZoom(1.0, (16.0 / 9.0));
            List<Recognition> terrySmarts = terry.getRecognitions();
            if (terrySmarts == null) {
                telemetry.addLine("[TERRY]: Terry is not smart right now, please check back later.");
            } else {
                telemetry.addLine("[TERRY]: Terry thinks he sees something!");
                recognitionLabels.clear();
                if (terrySmarts.size() == 0) {
                    telemetry.addLine("[TERRY]: Well never mind, he thought he was seeing something.");
                } else {
                    telemetry.addData("[TERRY]: Terry recognizes this many objects ", terrySmarts.size());
                    int i = 0;
                    for (Recognition terrySmart : terrySmarts) {
                        telemetry.addData("item", terrySmart.getLabel());
                        recognitionLabels.add(terrySmart.getLabel());
                        if (terrySmart.getLabel().equals("Duck")) {
                            terryDuckRight = terrySmart.getRight();
                            telemetry.addData("    left", terrySmart.getLeft());
                            telemetry.addData("    top", terrySmart.getTop());
                            telemetry.addData("    right", terryDuckRight);
                            telemetry.addData("    bottom", terrySmart.getBottom());
                        } else {
                            telemetry.addData("    left", terrySmart.getLeft());
                            telemetry.addData("    top", terrySmart.getTop());
                            telemetry.addData("    right", terrySmart.getRight());
                            telemetry.addData("    bottom", terrySmart.getBottom());
                        }

                        i++;
                    }
                }
                if (recognitionLabels.contains("Duck")) {
                    if (terryDuckRight < 360) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else {
                    return 1;
                }
            }
        } else {
            telemetry.addLine("[TERRY]: Terry is being dumb right now, please check back later.");
        }
        return 0;
    }
}