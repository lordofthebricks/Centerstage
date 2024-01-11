package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class hardware {

    private LinearOpMode myopmode;
    public DcMotorEx frontL;
    public DcMotorEx frontR;
    public DcMotorEx backL;
    public DcMotorEx backR;
    public Servo sling;
    public CRServo leftGrip;
    public CRServo rightGrip;
    public DcMotorEx arm;
    public DcMotorEx slider;
    public Servo wrist;
    public CRServo tape;
    public DcMotorEx hang;
    public DistanceSensor distance;

    //constants for we
    static final double     COUNTS_PER_MOTOR_REV    = 537.7;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    public WebcamName cam;

    public Rev2mDistanceSensor distance2M;

    public HardwareMap hwMap;
    private ElapsedTime runtime = new ElapsedTime();

    static final double ARM_COUNTS_PER_MOTOR_REV = 1425.1;

    static final double ARM_GEAR_REDUCTION = 2;

    static final double ARM_COUNTS_PER_DEGREE = (ARM_COUNTS_PER_MOTOR_REV * ARM_GEAR_REDUCTION) / 360;

    static final double TURN_INCHES = 19.4;

    static final int TILE = 24;

    private int armCurrentDegree = 0;

    public hardware(LinearOpMode myopmode) {
        this.myopmode = myopmode;
    }

    public void init(@NonNull HardwareMap hwMp){

        frontL = hwMp.get(DcMotorEx.class, "FrontLeft");
        frontR = hwMp.get(DcMotorEx.class, "FrontRight");
        backL = hwMp.get(DcMotorEx.class, "BackLeft");
        backR = hwMp.get(DcMotorEx.class, "BackRight");
        sling = hwMp.get(Servo.class, "Sling");
        leftGrip = hwMp.get(CRServo.class, "LeftGrip");
        rightGrip = hwMp.get(CRServo.class, "RightGrip");
        arm = hwMp.get(DcMotorEx.class, "Arm");
        slider = hwMp.get(DcMotorEx.class, "Slider");
        wrist = hwMp.get(Servo.class, "Wrist");
        cam = hwMp.get(WebcamName.class, "Webcam 1");
//        hang = hwMp.get(DcMotorEx.class,  "Hang");
//        tape = hwMp.get(CRServo.class,"Tape");
        distance = hwMp.get(DistanceSensor.class, "Distance");


        frontR.setDirection(DcMotorSimple.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        distance2M = (Rev2mDistanceSensor) distance;
        rightGrip.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public int getArmCurrentDegree() {
        return armCurrentDegree;
    }

    public void setArmCurrentDegree(int armCurrentDegree) {
        this.armCurrentDegree = armCurrentDegree;
    }

    public void encoderDrive (double speed,
                              double Left_Bottom_Inches,
                              double Right_Bottom_Inches,
                              double Right_Top_Inches,
                              double Left_Top_Inches,
                              double timeoutS){
        int newLeftBottomTarget;
        int newRightBottomTarget;
        int newRightTopTarget;
        int newLeftTopTarget;

        // Ensure that the opmode is still active
        if (myopmode.opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftBottomTarget = backL.getCurrentPosition() + (int) (Left_Bottom_Inches * COUNTS_PER_INCH);
            newRightBottomTarget = backR.getCurrentPosition() + (int) (Right_Bottom_Inches * COUNTS_PER_INCH);
            newRightTopTarget = frontR.getCurrentPosition() + (int) (Right_Top_Inches * COUNTS_PER_INCH);
            newLeftTopTarget = frontL.getCurrentPosition() + (int) (Left_Top_Inches * COUNTS_PER_INCH);

            backL.setTargetPosition(newLeftBottomTarget);
            backR.setTargetPosition(newRightBottomTarget);
            frontR.setTargetPosition(newRightTopTarget);
            frontL.setTargetPosition(newLeftTopTarget);

            // Turn On RUN_TO_POSITION
            backL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            backL.setPower(Math.abs(speed));
            backR.setPower(Math.abs(speed));
            frontL.setPower(Math.abs(speed));
            frontR.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (myopmode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (backL.isBusy() && backR.isBusy() && frontL.isBusy() && frontR.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1", "Running to %7d :%7d", newLeftBottomTarget, newRightBottomTarget, newLeftTopTarget, newRightTopTarget);
                //telemetry.addData("Path2", "Running at %7d :%7d", robot.Left_Bottom.getCurrentPosition(), robot.Right_Bottom.getCurrentPosition());
                //frontL.getCurrentPosition();
                //frontR.getCurrentPosition()
                //telemetry.update();
            }

            // Stop all motion;
            backL.setPower(0);
            backR.setPower(0);
            frontL.setPower(0);
            frontR.setPower(0);

            // Turn off RUN_TO_POSITION
            backL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }


    public void encoderDriveDistance (double speed,
                              double inchesDistance,
                              double timeoutS){
        int newLeftBottomTarget;
        int newRightBottomTarget;
        int newRightTopTarget;
        int newLeftTopTarget;


        double targetInches = distance.getDistance(DistanceUnit.INCH) - inchesDistance;
        targetInches = -targetInches;
        // Ensure that the opmode is still active
        if (myopmode.opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftBottomTarget = backL.getCurrentPosition() + (int) (targetInches * COUNTS_PER_INCH);
            newRightBottomTarget = backR.getCurrentPosition() + (int) (targetInches * COUNTS_PER_INCH);
            newRightTopTarget = frontR.getCurrentPosition() + (int) (targetInches * COUNTS_PER_INCH);
            newLeftTopTarget = frontL.getCurrentPosition() + (int) (targetInches * COUNTS_PER_INCH);

            backL.setTargetPosition(newLeftBottomTarget);
            backR.setTargetPosition(newRightBottomTarget);
            frontR.setTargetPosition(newRightTopTarget);
            frontL.setTargetPosition(newLeftTopTarget);

            // Turn On RUN_TO_POSITION
            backL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            backL.setPower(Math.abs(speed));
            backR.setPower(Math.abs(speed));
            frontL.setPower(Math.abs(speed));
            frontR.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (myopmode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (backL.isBusy() && backR.isBusy() && frontL.isBusy() && frontR.isBusy())) {

                // Display it for the driver.
                //telemetry.addData("Path1", "Running to %7d :%7d", newLeftBottomTarget, newRightBottomTarget, newLeftTopTarget, newRightTopTarget);
                //telemetry.addData("Path2", "Running at %7d :%7d", robot.Left_Bottom.getCurrentPosition(), robot.Right_Bottom.getCurrentPosition());
                //frontL.getCurrentPosition();
                //frontR.getCurrentPosition()
                //telemetry.update();
            }

            // Stop all motion;
            backL.setPower(0);
            backR.setPower(0);
            frontL.setPower(0);
            frontR.setPower(0);

            // Turn off RUN_TO_POSITION
            backL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }

//    public void armControl (double speed, double moveDegrees){
//
//        int targetDegree = (int) ((getArmCurrentDegree() + moveDegrees) * ARM_COUNTS_PER_DEGREE);
//
//
//
//        if (myopmode.opModeIsActive() && ((getArmCurrentDegree() + moveDegrees) <= 190 && (getArmCurrentDegree() + moveDegrees) >= 0)){
//
//            arm.setTargetPosition(targetDegree);
//            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            arm.setPower(Math.abs(speed));
//
//            while(arm.isBusy()){
//                myopmode.telemetry.addData("Arm Position: ", arm.getCurrentPosition());
//                myopmode.telemetry.update();
//            }
//            arm.setPower(0);
//            setArmCurrentDegree( (int) (getArmCurrentDegree() + moveDegrees));
//            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }
//
//    }

    public void setArmPosition (double degrees){

        int targetDegree = (int) (degrees * ARM_COUNTS_PER_DEGREE);


        myopmode.telemetry.addLine("Target Set");
        myopmode.telemetry.update();
        if (myopmode.opModeIsActive() && (degrees <= 190 && degrees>= 0)){

            arm.setTargetPosition(targetDegree);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (getArmCurrentDegree() < targetDegree){
                arm.setVelocity(1500); // this is in motor ticks
            } else if (getArmCurrentDegree() > targetDegree) {
                arm.setVelocity(1000);
            }else {
                return;
            }
            while(arm.isBusy()){
                myopmode.telemetry.addData("Arm Position: ", arm.getCurrentPosition());
                myopmode.telemetry.update();
            }
            arm.setPower(0);
            setArmCurrentDegree( (int) (degrees));
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
}
