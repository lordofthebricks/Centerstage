package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name = "Team Element Detection Red")
public class TeamDetectionR2 extends LinearOpMode {

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

        int location = 0;
        int desiredTagId = 0;
        double moveAmount = 0;
        double desiredDistance = 10;
        double actualDistance = 0;
        double boardApproachMovement = 0;

        vision.switchProcessor(false);

        waitForStart();

        robot.wrist.setPosition(0.43);

        // the pos value that rabeLocation takes indicates which direction for the robot to strafe to check for team element, 1 to strafe left, 2 to strafe right
        //IMPORTANT: This will move the robot forward a few inches when the element is not in center

        vision.switchProcessor(false);
        location = vision.rabeLocation(2);
        //spin robot around for the placing
        robot.encoderDrive(0.6, -3,-3,-3,-3,2);
        robot.encoderDrive(0.4, 38.8,-38.8,-38.8, 38.8, 4);
        robot.encoderDrive(0.6, -3, -3, -3, -3, 3);

        double firstApproachDistance = -24;
        switch (location){
            case 1:
                desiredTagId = 4;
                robot.encoderDrive(0.6, firstApproachDistance, firstApproachDistance, firstApproachDistance, firstApproachDistance, 3);
                robot.encoderDrive(0.6, -19.4, 19.4, 19.4, -19.4, 3);
                //the next three lines tell the robot to open the claw and then turn 180 degrees
                robot.wrist.setPosition(0.55);
                robot.leftGrip.setPower(-0.5);
                sleep(4000);
                robot.leftGrip.setPower(0);
                robot.wrist.setPosition(0.44);
                robot.encoderDrive(0.6, 38.8, -38.8, -38.8, 38.8, 3);
                break;
            case 2:
                desiredTagId = 5;
                //go forward to place on center line
                robot.encoderDrive(0.6,11,11,11,11,3);
                //place the pixel
                robot.wrist.setPosition(0.55);
                robot.leftGrip.setPower(-1);
                sleep(4000);
                robot.leftGrip.setPower(0);
                sleep(500);
                robot.wrist.setPosition(0.43);
                sleep(500);
                //turn the robot and drive forward a tile
                robot.encoderDrive(0.6,19.2,-19.2,-19.2,19.2,3);
                robot.encoderDrive(0.6,-24,-24,-24,-24,3);

                break;
            case 3:
                desiredTagId = 6;
                robot.encoderDrive(0.7, 24, -24, 24, -24, 3);
                robot.encoderDrive(0.6, firstApproachDistance, firstApproachDistance, firstApproachDistance, firstApproachDistance, 3);
                robot.encoderDrive(0.6, -19.4, 19.4, 19.4, -19.4, 3);
                robot.wrist.setPosition(0.55);
                robot.leftGrip.setPower(-0.5);
                sleep(1000);
                robot.leftGrip.setPower(0);
                robot.wrist.setPosition(0.44);
                break;
        }

        telemetry.addData("Distance to board", actualDistance);
        telemetry.update();
// commented out distance sensor code as distance sensor added encoder drive as temp fix

//        while (actualDistance > desiredDistance){
//
//            boardApproachMovement = actualDistance - desiredDistance;
//
//            boardApproachMovement = -boardApproachMovement;
//
//            robot.encoderDrive(0.7, boardApproachMovement, boardApproachMovement, boardApproachMovement, boardApproachMovement, 8);
//
////            actualDistance = robot.distance.getDistance(DistanceUnit.INCH);
//        }
        desiredDistance = 8.7;



        vision.switchProcessor(true);
        sleep(2000);
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        if (!currentDetections.isEmpty()) {


            for (AprilTagDetection detection : currentDetections) {
                // Look to see if we have size info on this tag.
                if (detection.metadata != null) {
                    //  Check to see if we want to track towards this tag.

                    if ((desiredTagId < 0) || (detection.id == desiredTagId)) {
                        // Yes, we want to use this tag.
                        targetFound = true;
                        desiredTag = detection;
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
            if (desiredTag != null) {
                moveAmount = Math.abs(desiredTag.ftcPose.x);
                if (desiredTag.ftcPose.x < 0) {
                    robot.encoderDrive(0.5, moveAmount, -moveAmount, moveAmount, -moveAmount, 4);
                } else {
                    robot.encoderDrive(0.5, -moveAmount, moveAmount, -moveAmount, moveAmount, 4);
                }
            }
        }

        robot.setArmPosition(170);
        actualDistance = robot.distance.getDistance(DistanceUnit.INCH);


        boardApproachMovement = actualDistance - desiredDistance;

        boardApproachMovement = -boardApproachMovement;

        robot.encoderDrive(0.7, boardApproachMovement, boardApproachMovement, boardApproachMovement, boardApproachMovement, 8);

//            actualDistance = robot.distance.getDistance(DistanceUnit.INCH);


        robot.rightGrip.setPower(-1);
        sleep(3000);
        robot.rightGrip.setPower(0);
        // robot.encoderDrive(0.6,24,-24,24,-24,3); //straffe
        robot.encoderDrive(0.6,2.5,2.5,2.5,2.5,3);
        robot.setArmPosition(0);
        robot.encoderDrive(0.6,30.5,-30.5,30.5,-30.5,3); //straffe
        robot.encoderDrive(0.6,-10,-10,-10,-10,3);









    }
}
