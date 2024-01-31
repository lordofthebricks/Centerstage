package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Stafe Blue 1")
public class StrafeBlue1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        double actualDistance = 0;
        double desiredDistance = 10;
        waitForStart();

        robot.encoderDrive(0.6,13.5,13.5,13.5,13.5,4);
        robot.wrist.setPosition(0.55);
        robot.leftGrip.setPower(-1);
        sleep(4000);
        robot.encoderDrive(0.6,19.4,-19.4,-19.4,19.4,3);
        robot.encoderDrive(0.6,100,100,100,100,3);
        robot.wrist.setPosition(0.55);
        robot.leftGrip.setPower(-1);
        robot.rightGrip.setPower(-1);
    }
}