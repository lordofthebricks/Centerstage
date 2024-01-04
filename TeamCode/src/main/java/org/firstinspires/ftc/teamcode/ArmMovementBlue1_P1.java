package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous
public class ArmMovementBlue1_P1 extends LinearOpMode {
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

        int desiredTagId = 5;
        double moveAmount = 0;


        waitForStart();

        robot.encoderDrive(0.6, 26, 26, 26, 26, 3);
        robot.encoderDrive(0.6, -19.4, 19.4, 19.4, -19.4, 3);
        robot.encoderDrive(0.6, -88, -88, -88, -88, 3);

       List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            // Look to see if we have size info on this tag.
            if (detection.metadata != null) {
                //  Check to see if we want to track towards this tag.

                if ((desiredTagId < 0) || (detection.id == desiredTagId)) {
                    // Yes, we want to use this tag.
                    targetFound = true;
                    desiredTag = detection;
                    break;  // don't  look any further.
                } else {
                    // This tag is in the library, but we do not want to track it right now.
                    telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
                }
            } else {
                // This tag is NOT in the library, so we don't have enough information to track to it.
                telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
        }
        moveAmount = Math.abs(desiredTag.ftcPose.x);
        if (desiredTag.ftcPose.x < 0) {

            robot.encoderDrive(0.5, moveAmount, -moveAmount, moveAmount, -moveAmount, 4);
        } else {
            robot.encoderDrive(0.5, -moveAmount, moveAmount, -moveAmount, moveAmount, 4);
        }

        robot.setArmPosition(170);
        robot.arm.setPower(0.5);
        robot.wrist.setPosition(0.43);
        robot.leftGrip.setPosition(0.5);
        robot.rightGrip.setPosition(0.5);
        robot.setArmPosition(0);
        robot.encoderDrive(0.6, -24, 24, -24, 24, 3);
        robot.encoderDrive(0.6, -24, -24, -24, -24, 3);

    }
}