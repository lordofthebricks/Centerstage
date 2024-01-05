package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous
public class ArmMovementRed2_p1 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();
//this first two lines of code tell the robot to move forward one tile and then turn left
        robot.encoderDrive(0.6, 24, 24, 24, 24, 3);
        robot.encoderDrive(0.6, -19.4, 19.4, 19.4, -19.4, 3);
        //the next three lines tell the robot to open the claw and then turn 180 degrees to the right and then move 37 inches forward
        robot.leftGrip.setPower(0.5);
        robot.encoderDrive(0.6, 38.8, -38.8, -38.8, 38.8, 3);
        robot.encoderDrive(0.6, 37, 37, 37, 37, 3);
        //then next five lines of code tell the robot to fully extend the arm and move the wrist and then open the right grip, and then put the arm to the idle position
        robot.setArmPosition(170);
        robot.arm.setPower(0.5);
        robot.wrist.setPosition(0.43);
        robot.rightGrip.setPower(0.5);
        robot.setArmPosition(0);
        //the rest of the code just says to strafe left 24 inches and the reverse 24 inches
        robot.encoderDrive(0.6, 24, -24, 24, -24, 3);
        robot.encoderDrive(0.6, -24, -24, -24, -24, 3);
    }
}
