package org.firstinspires.ftc.teamcode.scrimmage;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;



@TeleOp
public class Teleop  extends OpMode {
    Hardware robot = new Hardware();

    double forwardSpeed = .6;
    double backwardSpeed = -.6;


    @Override
    public void loop() {

    }

    @Override
    public void init() {

        if (gamepad1.left_stick_y == 1) {
            robot.FrontLeft.setPower(forwardSpeed);
            robot.BackLeft.setPower(forwardSpeed);
        } else {
            robot.FrontLeft.setPower(0);
            robot.BackLeft.setPower(0);
        }

        if (gamepad1.right_stick_y == 1) {
            robot.FrontRight.setPower(forwardSpeed);
            robot.BackRight.setPower(forwardSpeed);
        } else {
            robot.FrontRight.setPower(0);
            robot.BackRight.setPower(0);
        }

        if (gamepad1.left_stick_y == -1) {
            robot.FrontLeft.setPower(backwardSpeed);
            robot.BackLeft.setPower(backwardSpeed);
        } else {
            robot.FrontLeft.setPower(0);
            robot.BackLeft.setPower(0);
        }

        if (gamepad1.right_stick_y == -1) {
            robot.FrontRight.setPower(backwardSpeed);
            robot.BackRight.setPower(backwardSpeed);
        } else {
            robot.FrontRight.setPower(0);
            robot.BackRight.setPower(0);
        }

        if (gamepad1.left_stick_x == -1) {
            robot.FrontLeft.setPower(forwardSpeed);
            robot.BackLeft.setPower(backwardSpeed);
            robot.FrontRight.setPower(backwardSpeed);
            robot.BackRight.setPower(forwardSpeed);
        } else {
            robot.FrontLeft.setPower(0);
            robot.BackLeft.setPower(0);
            robot.FrontRight.setPower(0);
            robot.BackRight.setPower(0);
        }

        if (gamepad1.right_stick_x == 1) {
            robot.FrontLeft.setPower(backwardSpeed);
            robot.BackLeft.setPower(forwardSpeed);
            robot.FrontRight.setPower(forwardSpeed);
            robot.BackRight.setPower(backwardSpeed);
        } else {
            robot.FrontLeft.setPower(0);
            robot.BackLeft.setPower(0);
            robot.FrontRight.setPower(0);
            robot.BackRight.setPower(0);
        }



        }
    }

