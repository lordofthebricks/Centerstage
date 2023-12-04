package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class ArmMovementBlue2 extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();

        robot.encoderDrive(0.6,24,24,24,24,3);
        robot.encoderDrive(0.6,-22,22,22,-22,3);
        robot.encoderDrive(0.6,-30,-30,-30,-30,3);
        robot.setArmPosition(170);
        robot.wrist.setPosition(0.7);
        robot.leftGrip.setPosition(0.5);
        robot.rightGrip.setPosition(0.5);
        robot.encoderDrive(0.6,6,6,6,6,3);
        robot.leftGrip.setPosition(1);
        robot.rightGrip.setPosition(0);
        //robot.wrist.setPosition(0.4);
        //robot.setArmPosition(-170);
        robot.encoderDrive(0.6,-25,25,-25,25,3);
        robot.encoderDrive(0.6,-24,-24,-24,-24,3);
    }
}
