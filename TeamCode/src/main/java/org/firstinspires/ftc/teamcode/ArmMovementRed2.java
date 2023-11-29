package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class ArmMovementRed2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        waitForStart();

        robot.setArmPosition(170);
        robot.wrist.setPosition(0.7);
        robot.leftGrip.setPosition(0.5);
        robot.rightGrip.setPosition(0.5);
    }
}
