package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public class TensorFlowCorrect extends AutomatorClass {

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private static final String TFOD_MODEL_ASSET = "PowerPlay_CORRECT.tflite";
    private static final String[] LABELS = {"SycamoreLogo", "aviator", "juniper"};
    private static final String VUFORIA_KEY = "AYsOO+//////AAABmc2TlGXRrEpYsw1UPuEGK+xj/ybwffSKuL7ffLuhzm51GEM5ccJWjBO5XFTOmYCYiEVlCv2HpTa9+ZICMavximYCuGhJ0hCKtGTlYtTg/5AjtO6b1v9vswwtvKchMpcTFfhHK1A18R7FFbiJfQjTzznx1/Q6Et4TIRBqcEA5u7syU8suWSjWc3W/Kcy7ieQuQ15FoQsPQaw6JDZY32+xdyZlYbZ2MWfe0sPvQtkD+yJVa2wwZbBZTAoO6Q+rvRsHysQy0AqFB3sUKmRVvsV7gk7EQQ+edWgauCwgS23XZqwgkp5J/hKSZ/0SnXDuh1UQofuQFFxy57c9X18Cdky/Xl3hNTGUz393C8267cvb+JQy";

    public TensorFlowCorrect(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey=VUFORIA_KEY;
        parameters.cameraName=hardwareMap.get(WebcamName .class,"Webcam 1");
        //  Instantiate the Vuforia engine
        vuforia=ClassFactory.getInstance().

                createVuforia(parameters);

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence=0.8f;
        tfodParameters.isModelTensorFlow2=true;
        tfodParameters.inputSize=320;
        tfod=ClassFactory.getInstance().

                createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET,LABELS);

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.0, (16.0/9.0));
            telemetry.addLine("[TERRY]: Tfod thinks he can be smart today.");
        } else {
            telemetry.addLine("[TERRY]: Tfod is being dumb right now, please check back later.");
        }
    }

    public int useTensor() {
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Objects Detected", updatedRecognitions.size());
                // step through the list of recognitions and display image position/size information for each one
                // Note: "Image number" refers to the randomized image orientation/number
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals("juniper")){
                        return 1;
                    } else if (recognition.getLabel().equals("SycamoreLogo")){
                        return 2;
                    } else if (recognition.getLabel().equals("aviator")) {
                        return 3;
                    }
                    /*
                    telemetry.addData("", " ");
                    telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                    telemetry.addData("- Position (Row/Col)", "%.0f / %.0f", row, col);
                    telemetry.addData("- Size (Width/Height)", "%.0f / %.0f", width, height);
                    */
                }
                telemetry.update();
            }

            /*
            if(updatedRecognitions.contains("juniper")){
                return 1;
            } else if (updatedRecognitions.contains("SycamoreLogo")){
                return 2;
            } else if (updatedRecognitions.contains("aviator")){
                return 3;
            }
             */
        }else {
            telemetry.addLine("[TERRY]: Terry is being dumb right now, please check back later.");
        }
        return 0;
    }
}
