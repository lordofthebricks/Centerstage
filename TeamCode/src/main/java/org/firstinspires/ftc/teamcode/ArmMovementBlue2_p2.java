package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class ArmMovementBlue2_p2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();

        robot.encoderDrive(0.6, 24, 24, 24, 24, 3);
        robot.wrist.setPosition(.43);
        robot.leftGrip.setPosition(0.5);
        robot.wrist.setPosition(.55);
        robot.encoderDrive(0.6, -19.4, 19.4, 19.4, -19.4, 3);
        robot.encoderDrive(0.6, -35, -35, -35, -35, 3);
        robot.setArmPosition(150);
        robot.wrist.setPosition(.43);
        robot.rightGrip.setPosition(0.5);
        robot.setArmPosition(0);
        robot.encoderDrive(0.6, -24, 24, -24, 24, 3);
        robot.encoderDrive(0.6, -24, -24, -24, -24, 3);
        robot.leftGrip.setPosition(1);
        robot.rightGrip.setPosition(0);
    }
}
