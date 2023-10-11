package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class teleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        hardware robot = new hardware();
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()){

            //tank controls
            robot.frontL.setPower(gamepad1.left_stick_y);
            robot.frontR.setPower(gamepad1.right_stick_y);
            robot.backL.setPower(gamepad1.left_stick_y);
            robot.backR.setPower(gamepad1.right_stick_y);

            if (gamepad1.x){
                robot.sling.setPosition(1);
            } else if (gamepad1.y) {
                robot.sling.setPosition(0);
            }

            while (gamepad1.right_stick_x == 1) {

                robot.frontL.setPower(.6);
                robot.frontR.setPower(-.6);
                robot.backL.setPower(.6);
                robot.backR.setPower(-.6);
            }
            //This is the Strafe

            while (gamepad1.left_stick_x == -1) {
                robot.frontL.setPower(-.6);
                robot.frontR.setPower(.6);
                robot.backL.setPower(-.6);
                robot.backR.setPower(.6);
            }
        }


    }
}
