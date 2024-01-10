package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name = " Pixel Detection Autonomous red")
public class PixelDetectionR2 extends LinearOpMode {

    AprilTagProcessor aprilTag;
    TfodProcessor tfod;
    AprilTagDetection desiredTag;
    Boolean targetFound;
    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        DoubleVision vision = new DoubleVision(this);

        robot.init(hardwareMap);
        vision.initDoubleVision(robot);

        aprilTag = vision.getAprilTag();


        int desiredTagId = 0;
        double moveAmount = 0;

        waitForStart();

        robot.wrist.setPosition(0.43);
        //tensorFlow Detection returns 1, 2, or 3 depending on which third of the screen the team element is on
        switch (vision.pixtfodLocation()){
            case 1:
                desiredTagId = 4;
                robot.encoderDrive(0.6,12,-12,12,-12,4);
                robot.setArmPosition(180);
                robot.leftGrip.setPower(0.5);
                sleep(500);
                robot.leftGrip.setPower(1);
                robot.setArmPosition(1);
                robot.encoderDrive(0.6,-12,12,-12,12,4);
                break;
            case 2:
                desiredTagId = 5;
                robot.encoderDrive(0.6,10,10,10,10,4);
                robot.setArmPosition(180);
                robot.leftGrip.setPower(0.5);
                sleep(500);
                robot.leftGrip.setPower(1);
                robot.setArmPosition(1);
                robot.encoderDrive(0.6,-10,-10,-10,-10, 4);
                break;
            case 3:
                desiredTagId = 6;
                robot.encoderDrive(0.6,-12,12,-12,12,4);
                robot.setArmPosition(180);
                robot.leftGrip.setPower(0.5);
                sleep(500);
                robot.leftGrip.setPower(1);
                robot.setArmPosition(1);
                robot.encoderDrive(0.6,12,-12,12,-12,4);
                break;
        }
        robot.encoderDrive(0.6,2,2,2,2,2);
        robot.encoderDrive(0.6,hardware.TURN_INCHES,-hardware.TURN_INCHES,hardware.TURN_INCHES,hardware.TURN_INCHES,3);
        robot.encoderDrive(0.6,hardware.TILE,hardware.TILE,hardware.TILE,hardware.TILE,5);
        robot.setArmPosition(170);


        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections){
            // Look to see if we have size info on this tag.
            if (detection.metadata != null) {
                //  Check to see if we want to track towards this tag.

                if ((desiredTagId < 0) || (detection.id == desiredTagId)) {
                    // Yes, we want to use this tag.
                    targetFound = true;
                    desiredTag = detection;
                    moveAmount = desiredTag.ftcPose.x;
                    break;  // don't look any further.
                } else {
                    // This tag is in the library, but we do not want to track it right now.
                    telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
                }
            } else {
                // This tag is NOT in the library, so we don't have enough information to track to it.
                telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
        }
        double absmoveAmount = Math.abs(moveAmount);
        if(moveAmount < 0){

            robot.encoderDrive(0.5,absmoveAmount,-absmoveAmount,absmoveAmount,-absmoveAmount,4);
        }else {
            robot.encoderDrive(0.5,-absmoveAmount,absmoveAmount,-absmoveAmount,absmoveAmount,4);
        }

        double forwardMove = desiredTag.ftcPose.range - 12;

        robot.encoderDrive(0.5,forwardMove,forwardMove,forwardMove,forwardMove,5);








    }
}
