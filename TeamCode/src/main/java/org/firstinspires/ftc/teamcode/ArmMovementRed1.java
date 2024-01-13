package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous
public class ArmMovementRed1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        double actualDistance = 0;
        double desiredDistance = 10;
        waitForStart();

        robot.encoderDrive(0.6,15,15,15,15,3);
        robot.wrist.setPosition(0.55);
        robot.leftGrip.setPower(-1);
        sleep(4000);
        robot.leftGrip.setPower(0);
        sleep(1000);
        robot.wrist.setPosition(0.43);
        sleep(1000);
        robot.encoderDrive(0.6,6,6,6,6,3);
        robot.encoderDrive(0.6,21.5,-21.5,-21.5,21.5,3);
//        robot.encoderDrive(0.6,-30,-30,-30,-30,3);
        actualDistance = robot.distance.getDistance(DistanceUnit.INCH);
        while (actualDistance > desiredDistance){

            double boardApproachMovement = actualDistance - desiredDistance;

            boardApproachMovement = -boardApproachMovement;

            robot.encoderDrive(0.7, boardApproachMovement, boardApproachMovement, boardApproachMovement, boardApproachMovement, 10);

            actualDistance = robot.distance.getDistance(DistanceUnit.INCH);
        }
        desiredDistance = 8;

        robot.setArmPosition(170);
        robot.wrist.setPosition(0.43);
        while (actualDistance > desiredDistance){

            double boardApproachMovement = actualDistance - desiredDistance;

            boardApproachMovement = -boardApproachMovement;

            robot.encoderDrive(0.7, boardApproachMovement, boardApproachMovement, boardApproachMovement, boardApproachMovement, 4);

            actualDistance = robot.distance.getDistance(DistanceUnit.INCH);
        }


        robot.rightGrip.setPower(-1);
        sleep(2000);
        robot.rightGrip.setPower(0);
        robot.setArmPosition(0);
        robot.encoderDrive(0.6,24,-24,24,-24,3);
        robot.encoderDrive(0.6,-10,-10,-10,-10,3);
    }
}