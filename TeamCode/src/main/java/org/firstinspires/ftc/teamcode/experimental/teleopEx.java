package org.firstinspires.ftc.teamcode.experimental;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware;

@TeleOp
public class teleopEx extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardwareEx robot = new hardwareEx(this);

        robot.init(hardwareMap);

        waitForStart();

//        while(opModeIsActive()){
//
//            //tank controls
//            robot.frontL.setPower(gamepad1.left_stick_y);
//            robot.frontR.setPower(gamepad1.right_stick_y);
//            robot.backL.setPower(gamepad1.left_stick_y);
//            robot.backR.setPower(gamepad1.right_stick_y);
//
//
//
//            if (gamepad1.right_bumper && gamepad1.x){
//                //latch open
//                robot.sling.setPosition(0.5);
//            } else if (gamepad1.left_bumper && gamepad1.x) {
//                //latch closed
//                robot.sling.setPosition(1);
//            }
//
//            while (gamepad1.right_stick_x == 1) {
//
//                robot.frontL.setPower(.6);
//                robot.frontR.setPower(-.6);
//                robot.backL.setPower(-.6);
//                robot.backR.setPower(.6);
//            }
//            //This is the Strafe
//
//            while (gamepad1.left_stick_x == -1) {
//                robot.frontL.setPower(-.6);
//                robot.frontR.setPower(.6);
//                robot.backL.setPower(.6);
//                robot.backR.setPower(-.6);
//            }
//
//
//            if (gamepad1.right_trigger == 1){
//                robot.rightGrip.setPosition(1);
//            }else {
//                robot.rightGrip.setPosition(0);
//            }
//
//            if (gamepad1.left_trigger == 1){
//                robot.leftGrip.setPosition(1);
//            }else {
//                robot.leftGrip.setPosition(0);
//            }
//            //experimental autodrive
//
//
//
//
//
//        }


    }
}
