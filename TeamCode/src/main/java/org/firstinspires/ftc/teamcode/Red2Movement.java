package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous

public class Red2Movement extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();

        robot.encoderDrive(0.5,6,6,6,6,3);
        robot.encoderDrive(0.6,-19.4,19.4,19.4,-19.4,3);
        robot.encoderDrive(0.6,43,43,43,43,3);
    }}



