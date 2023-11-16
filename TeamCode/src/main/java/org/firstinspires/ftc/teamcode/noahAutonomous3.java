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

        robot.encoderDrive(0.6, -hardware.TILE * 4, hardware.TILE * 4, -hardware.TILE * 4, hardware.TILE * 4, 3);


    }
}
