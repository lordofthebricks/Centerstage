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

    double frontSpeed = 0.5;

    double backSpeed = 0.6;

    double adjSpeed = 0.4;
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

        telemetry.addLine("test");
        telemetry.addData("writs Pos:", robot.wrist.getPosition());
        telemetry.update();

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
        if (aprilTag.getDetections().isEmpty() == false) {
            try {
                double range = aprilTag.getDetections().get(0).ftcPose.range;
                if (range < 20) {
                    maxSpeed = 0.4;
                } else {
                    maxSpeed = 0.7;
                }
            } catch (NullPointerException e) {

                telemetry.addLine("An error detecting aprilTags has occured");


            }


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



        if (gamepad2.dpad_left) {
            //latch open
            robot.sling.setPosition(0.5);

        } else if (gamepad2.dpad_right) {
            //latch closed
            robot.sling.setPosition(0);
        }

        if (gamepad1.right_stick_x == 1) {

            robot.frontL.setPower(frontSpeed);
            robot.frontR.setPower(-frontSpeed);
            robot.backL.setPower(-backSpeed);
            robot.backR.setPower(backSpeed);
        }
        //This is the Strafe

        if (gamepad1.left_stick_x == -1) {
            robot.frontL.setPower(-frontSpeed);
            robot.frontR.setPower(frontSpeed);
            robot.backL.setPower(backSpeed);
            robot.backR.setPower(-backSpeed);
        }

        if (gamepad1.right_trigger == 1){
            robot.frontL.setPower(-adjSpeed);
            robot.frontR.setPower(adjSpeed);
            robot.backL.setPower(-adjSpeed);
            robot.backR.setPower(adjSpeed);
        }

        if (gamepad1.left_trigger == 1){
            robot.frontL.setPower(adjSpeed);
            robot.frontR.setPower(-adjSpeed);
            robot.backL.setPower(adjSpeed);
            robot.backR.setPower(-adjSpeed);
        }


        //Gamepad2


        //gampad2 controls
//        if (gamepad2.dpad_up) {
//            robot.frontR.setPower(adjSpeed);
//            robot.frontL.setPower(adjSpeed);
//            robot.backR.setPower(adjSpeed);
//            robot.backL.setPower(adjSpeed);
//
//        }
//        if (gamepad2.dpad_left) {
//            robot.frontR.setPower(frontSpeed);
//            robot.frontL.setPower(-frontSpeed);
//            robot.backR.setPower(-backSpeed);
//            robot.backL.setPower(backSpeed);
//
//        }
//        if (gamepad2.dpad_right) {
//            robot.frontR.setPower(-frontSpeed);
//            robot.frontL.setPower(frontSpeed);
//            robot.backR.setPower(backSpeed);
//            robot.backL.setPower(-backSpeed);
//
//        }
//        if (gamepad2.dpad_down) {
//            robot.frontR.setPower(-adjSpeed);
//            robot.frontL.setPower(-adjSpeed);
//            robot.backR.setPower(-adjSpeed);
//            robot.backL.setPower(-adjSpeed);
//        }


        //Tape Controls
        if (gamepad1.right_bumper == true) {
            robot.tape.setPower(0.5);
        }
        else if (gamepad1.left_bumper == true) {
            robot.tape.setPower(-0.5);
        }
        else {
            robot.tape.setPower(0);
        }


        //Hang Controls
        if (gamepad1.right_stick_button == true) {
            robot.hang.setPower(1);
        }
        else if (gamepad1.left_stick_button == true) {
            robot.hang.setPower(-1);
            robot.tape.setPower(-.3);
            robot.setArmPosition(45);
            robot.wrist.setPosition(0.43);
        }
        else {
            robot.hang.setPower(0);
            robot.tape.setPower(0);
        }


        //gripper controls
        //Controls are backwards; so it has been changed (which will be confusing) - Coach Shari
        if (gamepad2.right_trigger == 1) {
            robot.rightGrip.setPower(1);
        } else if (gamepad2.right_bumper){
            robot.rightGrip.setPower(-1);
        } else {
            robot.rightGrip.setPower(0);
        }

        if (gamepad2.left_trigger == 1) {
            robot.leftGrip.setPower(1);
        } else if (gamepad2.left_bumper){
            robot.leftGrip.setPower(-1);
        } else {
           robot.leftGrip.setPower(0);
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
        if (gamepad2.y) {

            robot.setArmPosition(170);
//            try {
//                armThread.start();
//            } catch (Exception e) {
//                telemetry.addLine("An Error has occurred");
//            }
        }

        if (gamepad2.x){
            robot.setArmPosition(5);
        }

        if (gamepad2.a) {

                robot.wrist.setPosition(0.55);

        } else if (gamepad2.b) {

            robot.wrist.setPosition(0.42);


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
