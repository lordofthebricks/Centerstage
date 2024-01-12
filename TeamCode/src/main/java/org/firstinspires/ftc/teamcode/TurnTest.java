package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TurnTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        hardware robot = new hardware(this);

        waitForStart();

        robot.encoderDrive(0.5, -20.5,20.5,-20.5, 20.5, 4);
    }
}
