package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous
public class Red1Movement extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();
//Moves 1" off the wall
        robot.encoderDrive(0.5,4,4,4,4,3);
//Spins robot 90 degrees
        robot.encoderDrive(0.6,-19.4,19.4,19.4,-19.4,3);
//moves the robot straight
        robot.encoderDrive(0.6,91,91,91,91,3);

    }
}
