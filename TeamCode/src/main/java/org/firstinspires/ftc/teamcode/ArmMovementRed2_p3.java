package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class ArmMovementRed2_p3 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();
//the first four lines move the robot 24 inches forward, then the robot moves to the right, open the left grip, and then move 37 inches forward
        robot.encoderDrive(0.6, 24, 24, 24, 24, 3);
        robot.encoderDrive(0.6, 19.4, -19.4, -19.4, 19.4, 3);
        robot.leftGrip.setPower(0.5);
        robot.encoderDrive(0.6, 37, 37, 37, 37, 3);
        //the next five lines set the arm to the fullest position, do the wrist, open the right grip, and then put the arm back to the original position
        robot.setArmPosition(170);
        robot.arm.setPower(0.5);
        robot.wrist.setPosition(0.43);
        robot.rightGrip.setPower(0.5);
        robot.setArmPosition(0);
        //the last lines strafe left and then reverse 24 inches
        robot.encoderDrive(0.6, 24, -24, 24, -24, 3);
        robot.encoderDrive(0.6, -24, -24, -24, -24, 3);
    }
}
