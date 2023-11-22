package org.firstinspires.ftc.teamcode;

import android.telephony.CellIdentity;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp
public class teleop extends LinearOpMode {


    private AprilTagProcessor aprilTag;



    @Override
    public void runOpMode() throws InterruptedException {


        hardware robot = new hardware(this);
        DoubleVision Vision = new DoubleVision(this);
        robot.init(hardwareMap);
        Vision.initDoubleVision(robot);

        aprilTag = Vision.getAprilTag();

        aprilTag.setDecimation(3);

        waitForStart();

        while(opModeIsActive()){

            //Gamepad 1
            //tank controls

            robot.frontL.setPower(-gamepad1.right_stick_y*0.7);
            robot.backL.setPower(-gamepad1.right_stick_y*0.7);


            robot.frontR.setPower(-gamepad1.left_stick_y*0.7);
            robot.backR.setPower(-gamepad1.left_stick_y*0.7);


            //Dpad controls
            if (aprilTag.getDetections() != null) {


            }


            if (gamepad1.right_bumper && gamepad1.x){
                //latch open
                robot.sling.setPosition(0.5);
            } else if (gamepad1.left_bumper && gamepad1.x) {
                //latch closed
                robot.sling.setPosition(1);
            }

            while (gamepad1.left_stick_x == -1) {

                robot.frontL.setPower(.6);
                robot.frontR.setPower(-.6);
                robot.backL.setPower(-.6);
                robot.backR.setPower(.6);
            }
            //This is the Strafe

            while (gamepad1.right_stick_x == 1) {
                robot.frontL.setPower(-.6);
                robot.frontR.setPower(.6);
                robot.backL.setPower(.6);
                robot.backR.setPower(-.6);
            }



            //Gamepad2
            //gripper controls
            if (gamepad2.right_trigger == 1){
                robot.rightGrip.setPosition(0.5);
            }else {
                robot.rightGrip.setPosition(0);
            }

            if (gamepad2.left_trigger == 1){
                robot.leftGrip.setPosition(0.5);
            }else {
                robot.leftGrip.setPosition(1);
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

                robot.setArmPosition(0.9, 170);

            } else if (gamepad2.y) {

                robot.setArmPosition(0.4, 0);
//                robot.armControl(0.9, -180);
            } else {
                //robot.arm.setPower(0);

            }

            if (gamepad2.a){

            }

            if(gamepad2.left_bumper){
                robot.wrist.setPosition(0.6);
            } else if (gamepad2.right_bumper) {
                robot.wrist.setPosition(0.4);
            }


        }
    }
}
