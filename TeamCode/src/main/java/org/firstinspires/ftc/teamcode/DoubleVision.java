/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * This OpMode illustrates the basics of using both AprilTag recognition and TensorFlow
 * Object Detection.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
public class DoubleVision {

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "TeamElements.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.

    // Define the labels recognized in the model for TFOD (must be in training order!)

    private static final String[] LABELS = {
            "RedEl",
            "BlueEl"
    };
    LinearOpMode myOpMode;
    private Recognition recognition1;

    public DoubleVision(LinearOpMode myOpMode) {
        this.myOpMode = myOpMode;
    }

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    public RedAndBlueElements rabe;
    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    public AprilTagProcessor getAprilTag() {
        return aprilTag;
    }

    public VisionPortal getVisionPortal() {
        return visionPortal;
    }

    public boolean getWebCamStatus(){
        return USE_WEBCAM;
    }

    hardware robot;

    /**
     * Initialize AprilTag and TFOD.
     */
    public void initDoubleVision(hardware robot) {
        // -----------------------------------------------------------------------------------------
        // AprilTag Configuration
        // -----------------------------------------------------------------------------------------
        this.robot = robot;

        aprilTag = new AprilTagProcessor.Builder().setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
            .build();

        aprilTag.setDecimation(2);
        // -----------------------------------------------------------------------------------------
        // TFOD Configuration
        // -----------------------------------------------------------------------------------------

        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();

        rabe = new RedAndBlueElements();

        // -----------------------------------------------------------------------------------------
        // Camera Configuration
        // -----------------------------------------------------------------------------------------

            visionPortal = new VisionPortal.Builder()
                    .setCamera(robot.cam)
                    .addProcessors(tfod, aprilTag, rabe)
                    .build();
            visionPortal.setProcessorEnabled(rabe, true);
    }   // end initDoubleVision()

    /**
     * Add telemetry about AprilTag detections.
     */
    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        myOpMode.telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                myOpMode.telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                myOpMode.telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                myOpMode.telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                myOpMode.telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            }
        }   // end for() loop

    }   // end method telemetryAprilTag()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {
        List<Recognition> currentRecognitions = tfod.getRecognitions();
//        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

//            telemetry.addData(""," ");
//            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
//            telemetry.addData("- Position", "%.0f / %.0f", x, y);
//            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

    }   // end method telemetryTfod()

    public int tfodLocation(int pos){
        Integer location = 0;
        double x = 0;

        //make sure that only tensorflow is enabled
        visionPortal.setProcessorEnabled(aprilTag,false);
        visionPortal.setProcessorEnabled(tfod, true);
//        visionPortal.setProcessorEnabled(pixtfod, false);
        robot.encoderDrive(0.5,-1, -1, -1 , -1 , 1);
        //get a list of recognitions from tensor flow
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        myOpMode.telemetry.addLine("Beginning tensorflow scan");
        myOpMode.telemetry.update();
        if (currentRecognitions != null) {
            // Step through the list of recognitions and x location for each one.
            for (Recognition recognition : currentRecognitions) {
                x = (recognition.getLeft() + recognition.getRight()) / 2;

                recognition1 = recognition;
            }   // end for() loop
        }
        //check to see if recognition is in front of the robot
        if (recognition1 != null){
            location = 2;

        }else {

            //move robot to scan other location
            if (pos == 2) {
                robot.encoderDrive(0.7, 12, -12, 12, -12, 2);
                if (tfod.getRecognitions() != null) {
                    location = 3;
                } else {
                    location = 1;
                }
            }else {
                robot.encoderDrive(0.7,-12,12,-12,12,1);
                if (tfod.getRecognitions() != null) {
                    location = 1;
                } else {
                    location = 3;
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            //move robot back to starting pos
//            if (pos == 2) {
//                robot.encoderDrive(0.7,-12,12,-12,12,1);
//            }else {
//                robot.encoderDrive(0.7, 12, -12, 12, -12, 2);
//            }

        }


        return location;
    }


    public int rabeLocation(int pos){
        Integer location = 0;
        double x = 0;

        //make sure that only tensorflow is enabled
        visionPortal.setProcessorEnabled(aprilTag,false);
        visionPortal.setProcessorEnabled(tfod, false);
        visionPortal.setProcessorEnabled(rabe, true);
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //check to see if recognition is in front of the robot
        if (rabe.isDetected()){
            location = 2;

        }else {
            //come off the wall to scan
            robot.encoderDrive(0.6,-4,-4,-4,-4,2);
            //move robot to scan other location
            if (pos == 2) {
                robot.encoderDrive(0.7, 12, -12, 12, -12, 2);
                if (rabe.isDetected()) {
                    location = 3;
                } else {
                    location = 1;
                }
            }else {
                robot.encoderDrive(0.7,-12,12,-12,12,1);
                if (rabe.isDetected()) {
                    location = 1;
                } else {
                    location = 3;
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


        return location;
    }

    public void switchProcessor(boolean apriltagIsActive){
        if (apriltagIsActive){
            visionPortal.setProcessorEnabled(tfod, false);
            visionPortal.setProcessorEnabled(aprilTag,true);

        }else{

            visionPortal.setProcessorEnabled(aprilTag, false);
            visionPortal.setProcessorEnabled(tfod,true);
        }
    }

    public void setManualExposure(int exposureMS, int gain) throws InterruptedException {
        //Wait for the camera to be open, then use the controls
//         Make sure camera is streaming before we try to set the exposure controls


        // Set camera controls unless we are stopping.

            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);

                sleep(50);
            }
            exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);

    }

}   // end class
