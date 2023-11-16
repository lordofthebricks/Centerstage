package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous
public class noahAutonomous3 extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();
//Moves 1" off the wall
        robot.encoderDrive(0.5,1,1,1,1,3);
//Spins robot 90 degrees
        robot.encoderDrive(0.6,-14.25,14.25,14.25,-14.25,3);
//Noves the robot straight
        robot.encoderDrive(0.6,96,96,96,96,3);

    }
}
