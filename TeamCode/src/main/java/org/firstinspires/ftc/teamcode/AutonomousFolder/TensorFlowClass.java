package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.DoubleTelemetry;

import java.util.List;
import java.util.ArrayList;

public class TensorFlowClass extends AutomatorClass {
    private static final String vuforiaKey = "AYsOO+//////AAABmc2TlGXRrEpYsw1UPuEGK+xj/ybwffSKuL7ffLuhzm51GEM5ccJWjBO5XFTOmYCYiEVlCv2HpTa9+ZICMavximYCuGhJ0hCKtGTlYtTg/5AjtO6b1v9vswwtvKchMpcTFfhHK1A18R7FFbiJfQjTzznx1/Q6Et4TIRBqcEA5u7syU8suWSjWc3W/Kcy7ieQuQ15FoQsPQaw6JDZY32+xdyZlYbZ2MWfe0sPvQtkD+yJVa2wwZbBZTAoO6Q+rvRsHysQy0AqFB3sUKmRVvsV7gk7EQQ+edWgauCwgS23XZqwgkp5J/hKSZ/0SnXDuh1UQofuQFFxy57c9X18Cdky/Xl3hNTGUz393C8267cvb+JQy";

    private final TFObjectDetector tfod;
    private final VuforiaLocalizer vuforia;

    private static final String modelFile = "PowerPlayWorkingModel2.tflite";
    // private static final String[] labels = {"juniper", "SycamoreLogo", "aviator"};
    private static final String[] labels = {"SycamoreLogo", "aviator", "juniper"};

    public ArrayList<String> recognitionLabels;

    public TensorFlowClass(DoubleTelemetry telemetry, HardwareMap hardwareMap) {
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
        tfod = ClassFactory.getInstance().createTFObjectDetector(tParams, vuforia);
        tfod.loadModelFromAsset(modelFile, labels);

        recognitionLabels = new ArrayList<String>();

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.0, (16.0/9.0));
            telemetry.addLine("[TERRY]: Terry thinks he can be smart today.");
        } else {
            telemetry.addLine("[TERRY]: Terry is being dumb right now, please check back later.");
        }
    }

    public int getRecognition() {
        if (tfod != null) {
            //tfod.setZoom(1.0, (16.0 / 9.0));  // This is already set in the TensorFlowClass
            List<Recognition> recognitions = tfod.getRecognitions();
            if (recognitions == null) {
                telemetry.addLine("[TFOD]: Recognitions is null...");
            } else {
                recognitionLabels.clear();
                if (recognitions.size() == 0) {
                    telemetry.addLine("[TFOD]: Nothing is recognized...");
                } else {
                    telemetry.addData("[TFOD]: Recognized this many objects ", recognitions.size());
                    for (Recognition recognition : recognitions) {
                        telemetry.addData("item", recognition.getLabel());
                        recognitionLabels.add(recognition.getLabel());
                    }
                }
                if (recognitionLabels.contains("juniper")) {
                        return 3;
                } else if (recognitionLabels.contains("SycamoreLogo")){
                        return 2;
                } else if (recognitionLabels.contains("aviator")) {
                        return 1;
                }
            }
        } else {
            telemetry.addLine("[TFOD]: TFOD is null...");
        }
        return 0;
    }
}