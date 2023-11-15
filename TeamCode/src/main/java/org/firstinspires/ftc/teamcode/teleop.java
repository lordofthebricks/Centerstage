package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class teleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware(this);
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()){

            //tank controls
            if (gamepad1.left_stick_y >= 0.2 && gamepad1.left_stick_y <= -0.2) {

                robot.frontL.setPower(gamepad1.left_stick_y*0.7);
                robot.backL.setPower(gamepad1.left_stick_y*0.7);
            }

            if (gamepad1.right_stick_y >= 0.2 && gamepad1.right_stick_y <= -0.2) {
                robot.frontR.setPower(gamepad1.right_stick_y*0.7);
                robot.backR.setPower(gamepad1.right_stick_y*0.7);
            }


            if (gamepad1.right_bumper && gamepad1.x){
                //latch open
                robot.sling.setPosition(0.5);
            } else if (gamepad1.left_bumper && gamepad1.x) {
                //latch closed
                robot.sling.setPosition(1);
            }

            while (gamepad1.right_stick_x == 1) {

                robot.frontL.setPower(.6);
                robot.frontR.setPower(-.6);
                robot.backL.setPower(-.6);
                robot.backR.setPower(.6);
            }
            //This is the Strafe

            while (gamepad1.left_stick_x == -1) {
                robot.frontL.setPower(-.6);
                robot.frontR.setPower(.6);
                robot.backL.setPower(.6);
                robot.backR.setPower(-.6);
            }

            //gripper controls
            if (gamepad2.right_trigger == 1){
                robot.rightGrip.setPosition(1);
            }else {
                robot.rightGrip.setPosition(0);
            }

            if (gamepad2.left_trigger == 1){
                robot.leftGrip.setPosition(1);
            }else {
                robot.leftGrip.setPosition(0);
            }

            //slider control
            if (gamepad2.left_stick_y == 1) {
                robot.slider.setPower(0.7);
            } else if (gamepad2.left_stick_y == -1) {
                robot.slider.setPower(-0.7);
            } else {
            robot.slider.setPower(0);
            }

            //Arm control
//            if (gamepad2.right_stick_y == 1) {
//
//                robot.arm.setPower(1.0);
//                robot.arm.setTargetPosition();
//            } else if (gamepad2.right_stick_y == -1) {
//                robot.arm.setPower(-1.0);
//            } else {
//                robot.arm.setPower(0);
//
//            }



        }
    }
}
