package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Disabled
public class ArmMovementBlue2_p2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();

        robot.encoderDrive(0.6, 24, 24, 24, 24, 3);
        robot.wrist.setPosition(.43);
        robot.leftGrip.setPower(0.5);
        robot.wrist.setPosition(.55);
        robot.encoderDrive(0.6, -19.4, 19.4, 19.4, -19.4, 3);
        robot.encoderDrive(0.6, -35, -35, -35, -35, 3);
        robot.setArmPosition(160);
        robot.wrist.setPosition(.43);
        robot.rightGrip.setPower(0.5);
        robot.setArmPosition(0);
        robot.encoderDrive(0.6, -24, 24, -24, 24, 3);
        robot.encoderDrive(0.6, -24, -24, -24, -24, 3);
        robot.leftGrip.setPower(1);
        robot.rightGrip.setPower(0);
    }
}
