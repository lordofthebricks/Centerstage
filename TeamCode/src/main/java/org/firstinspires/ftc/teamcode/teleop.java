package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp
public class teleop extends OpMode {


    private AprilTagProcessor aprilTag;
    double maxSpeed = 0.7;
    private hardwareTeleop robot;
    ArmControl armControl;
    Thread armThread;

    @Override
    public void init() {

        robot = new hardwareTeleop(this);

        robot.init(hardwareMap);

        initAprilTag();

        aprilTag.setDecimation(3);

        armControl = new ArmControl(robot);
        armThread = new Thread(armControl);

    }



    @Override
    public void loop() {

        //Gamepad 1
        //tank controls

        robot.frontL.setPower(-gamepad1.right_stick_y * 0.7);
        robot.backL.setPower(-gamepad1.right_stick_y * 0.7);


        robot.frontR.setPower(-gamepad1.left_stick_y * 0.7);
        robot.backR.setPower(-gamepad1.left_stick_y * 0.7);


        //Dpad controls
//        if (aprilTag.getDetections().isEmpty() == false) {
//            double range = aprilTag.getDetections().get(0).ftcPose.range;
//            if (range < 20) {
//                maxSpeed = 0.4;
//            } else {
//                maxSpeed = 0.7;
//            }
//        }

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

        if (gamepad1.dpad_up) {
            robot.frontR.setPower(maxSpeed);
            robot.frontL.setPower(maxSpeed);
            robot.backR.setPower(maxSpeed);
            robot.backL.setPower(maxSpeed);

        }
        if (gamepad1.dpad_left) {
            robot.frontR.setPower(maxSpeed);
            robot.frontL.setPower(-maxSpeed);
            robot.backR.setPower(-maxSpeed);
            robot.backL.setPower(maxSpeed);

        }
        if (gamepad1.dpad_right) {
            robot.frontR.setPower(-maxSpeed);
            robot.frontL.setPower(maxSpeed);
            robot.backR.setPower(maxSpeed);
            robot.backL.setPower(-maxSpeed);

        }
        if (gamepad1.dpad_down) {
            robot.frontR.setPower(-maxSpeed);
            robot.frontL.setPower(-maxSpeed);
            robot.backR.setPower(-maxSpeed);
            robot.backL.setPower(-maxSpeed);
        }


        if (gamepad1.right_bumper && gamepad1.x) {
            //latch open
            robot.sling.setPosition(0.5);

        } else if (gamepad1.left_bumper && gamepad1.x) {
            //latch closed
            robot.sling.setPosition(0);
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
        if (gamepad2.right_trigger == 1) {
            robot.rightGrip.setPosition(0.5);
        } else {
            robot.rightGrip.setPosition(0);
        }

        if (gamepad2.left_trigger == 1) {
            robot.leftGrip.setPosition(0.5);
        } else {
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

            armThread.start();

        }

        if (gamepad2.a) {

            robot.wrist.setPosition(0.57);
        } else if (gamepad2.b) {

            robot.wrist.setPosition(0.4);

        }

    }

    @Override
    public void stop() {

    }

    private void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder().setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .build();
        VisionPortal visionPortal = new VisionPortal.Builder()
                .setCamera(robot.cam)
                .addProcessors(aprilTag)
                .build();
    }


}
