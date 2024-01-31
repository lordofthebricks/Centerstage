
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

 @Autonomous
public class ArmMovementBlue2 extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);
        double actualDistance = 0;
        double desiredDistance = 10;
        waitForStart();

        //moves to middle spike mark and places pixel on the spike mark
        robot.encoderDrive(0.6,16,16,16,16,3);
        robot.wrist.setPosition(0.55);
        robot.leftGrip.setPower(-1);
        sleep(4000);
        robot.leftGrip.setPower(0);
        sleep(1000);
        robot.wrist.setPosition(0.43);
        sleep(1000);
        //moves back to starting position, turns, and moves down to the board and then strafes in front of the board
        robot.encoderDrive(0.6,-14,-14,-14,-14,3);
        robot.encoderDrive(0.6,-19.4,19.4,19.4,-19.4,3);
        robot.encoderDrive(0.6,-72,-72,-72,-72,3);
        robot.encoderDrive(0.6,30,-30,30,-30,3);
//        robot.encoderDrive(0.6,-30,-30,-30,-30,3);
        //Distance sensor stuff to move the correct distance in front of the board
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

        //the robot drops the pixel onto the backboard, moves to the left and then parks in the backstage
        robot.rightGrip.setPower(-1);
        sleep(2000);
        robot.rightGrip.setPower(0);
        robot.setArmPosition(10);
        robot.encoderDrive(0.6,-22,22,-22,22,3);
        robot.encoderDrive(0.6,-10,-10,-10,-10,3);
    }
}
