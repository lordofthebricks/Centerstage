package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Autonomous
@Disabled
public class ArmMovementBlue1_p1 extends LinearOpMode {
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
        robot.setArmPosition(170);
        robot.arm.setPower(0.5);
        robot.wrist.setPosition(0.43);
        robot.leftGrip.setPower(-0.5);
        robot.rightGrip.setPower(-0.5);
        robot.setArmPosition(0);
        robot.encoderDrive(0.6, -24, 24, -24, 24, 3);
        robot.encoderDrive(0.6, -24, -24, -24, -24, 3);

    }
}