package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

 @Autonomous
public class ArmMovementBlue1_p1 extends LinearOpMode {


    @Override
    public void runOpMode () throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();

        robot.encoderDrive(0.6,24,24,24,24,3);
        robot.encoderDrive(0.6,19.4,-19.4,-19.4,19.4,3);
        robot.leftGrip.setPosition(0.5);
        robot.encoderDrive(0.6,-38.8,38.8,38.8,38.8,3);
        robot.encoderDrive(0.6,-85,-85,-85,-85,3);
        robot.setArmPosition(170);
        robot.arm.setPower(0.5);
        robot.wrist.setPosition(0.43);
        robot.rightGrip.setPosition(0.5);
        robot.setArmPosition(0);
        robot.encoderDrive(0.6,-24,24,-24,24,3);
        robot.encoderDrive(0.6,-24,-24,-24,-24,3);

    }
}


