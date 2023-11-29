package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class Blue1Movement extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();

        robot.encoderDrive(0.5,2,2,2,2,3);
        robot.encoderDrive(0.6,21.810,-21.810,-21.810,21.810,3);
        robot.encoderDrive(0.6,96,96,96,96,3);
    }
}


