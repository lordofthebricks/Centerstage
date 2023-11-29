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

        robot.encoderDrive(0.5,1,1,1,1,3);
        robot.encoderDrive(0.6,-28.50,28.50,28.50,-28.50,3);
        robot.encoderDrive(0.6,48,48,48,48,3);
    }}



