package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Disabled
public class ArmMovementRed2_p2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();
//for the first four lines the robot moves forward 24 inches, opens the left grip and then turn right 90 degrees, and after that the robot moves forward 37 inches
        robot.encoderDrive(0.6, 24, 24, 24, 24, 3);
        robot.leftGrip.setPower(0.5);
        robot.encoderDrive(0.6, 19.4, -19.4, -19.4, 19.4, 3);
        robot.encoderDrive(0.6, 37, 37, 37, 37, 3);
        //the next five lines tell the robot to set the arm to the fullest position, do something with the wrist (I don't know if position 0.43 is up or down for the wrist) open the right grip, then set the arm to the idle position
        robot.setArmPosition(170);
        robot.arm.setPower(0.5);
        robot.wrist.setPosition(0.43);
        robot.rightGrip.setPower(0.5);
        robot.setArmPosition(0);
        //the rest of the code just says to strafe to the left and then reverse 24 inches
        robot.encoderDrive(0.6, 24, -24, 24, -24, 3);
        robot.encoderDrive(0.6, -24, -24, -24, -24, 3);
    }
}
