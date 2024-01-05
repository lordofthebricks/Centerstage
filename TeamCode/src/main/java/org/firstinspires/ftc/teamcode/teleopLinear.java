package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp
public class teleopLinear extends LinearOpMode {


    private AprilTagProcessor aprilTag;
    double maxSpeed = 0.7;


    @Override
    public void runOpMode() throws InterruptedException {


        hardware robot = new hardware(this);
        DoubleVision Vision = new DoubleVision(this);
        robot.init(hardwareMap);
        Vision.initDoubleVision(robot);

        aprilTag = Vision.getAprilTag();

        aprilTag.setDecimation(3);

        waitForStart();

        while(opModeIsActive()) {

            //Gamepad 1
            //tank controls

            robot.frontL.setPower(-gamepad1.right_stick_y * 0.7);
            robot.backL.setPower(-gamepad1.right_stick_y * 0.7);


            robot.frontR.setPower(-gamepad1.left_stick_y * 0.7);
            robot.backR.setPower(-gamepad1.left_stick_y * 0.7);


            //Dpad controls
            if (aprilTag.getDetections().isEmpty() == false) {
                double range = aprilTag.getDetections().get(0).ftcPose.range;
                if (range < 20){
                    maxSpeed = 0.4;
                } else {
                    maxSpeed = 0.7;
                }
            }

            if (gamepad1.dpad_up && gamepad1.dpad_left) {
                robot.frontR.setPower(-.5);
                robot.backL.setPower(-.5);
            }

            if (gamepad1.dpad_up && gamepad1.dpad_right) {
                robot.backR.setPower(-.5);
                robot.frontL.setPower(-.5);
            }

            if (gamepad1.dpad_down && gamepad1.dpad_left) {
                robot.backR.setPower(.5);
                robot.frontL.setPower(.5);
            }

            if (gamepad1.dpad_down && gamepad1.dpad_right) {
                robot.frontR.setPower(.5);
                robot.backL.setPower(.5);
            }

            if (gamepad1.dpad_up){
                robot.frontR.setPower(maxSpeed);
                robot.frontL.setPower(maxSpeed);
                robot.backR.setPower(maxSpeed);
                robot.backL.setPower(maxSpeed);

            }
            if (gamepad1.dpad_left){
                robot.frontR.setPower(maxSpeed);
                robot.frontL.setPower(-maxSpeed);
                robot.backR.setPower(-maxSpeed);
                robot.backL.setPower(maxSpeed);

            }
            if (gamepad1.dpad_right){
                robot.frontR.setPower(-maxSpeed);
                robot.frontL.setPower(maxSpeed);
                robot.backR.setPower(maxSpeed);
                robot.backL.setPower(-maxSpeed);

            }
            if (gamepad1.dpad_down){
                robot.frontR.setPower(-maxSpeed);
                robot.frontL.setPower(-maxSpeed);
                robot.backR.setPower(-maxSpeed);
                robot.backL.setPower(-maxSpeed);
            }



            if (gamepad1.right_bumper && gamepad1.x){
                //latch open
                robot.sling.setPosition(0.5);
            } else if (gamepad1.left_bumper && gamepad1.x) {
                //latch closed
                robot.sling.setPosition(1);
            }

            if (gamepad1.left_stick_x == -1) {

                robot.frontL.setPower(.6);
                robot.frontR.setPower(-.6);
                robot.backL.setPower(-.6);
                robot.backR.setPower(.6);
            }
            //This is the Strafe

            if (gamepad1.right_stick_x == 1) {
                robot.frontL.setPower(-.6);
                robot.frontR.setPower(.6);
                robot.backL.setPower(.6);
                robot.backR.setPower(-.6);
            }



            //Gamepad2
            //gripper controls
            if (gamepad2.right_trigger == 1){
                robot.rightGrip.setPower(0.5);
            }else {
                robot.rightGrip.setPower(0);
            }

            if (gamepad2.left_trigger == 1){
                robot.leftGrip.setPower(0.5);
            }else {
                robot.leftGrip.setPower(1);
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
            if (gamepad2.x) {

                robot.setArmPosition(170);

            } else if (gamepad2.y) {

                robot.setArmPosition(0);
//                robot.armControl(0.9, -180);
            } else {
                //robot.arm.setPower(0);

            }



            if(gamepad2.a){
                robot.wrist.setPosition(0.6);
            } else if (gamepad2.b) {
                robot.wrist.setPosition(0.4);
            }


        }
    }
}
