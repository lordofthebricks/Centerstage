package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class hardwareTeleop {

    private OpMode myopmode;
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
    public DcMotorEx tape;
    public DcMotorEx hang;



    //constants for we
    static final double     COUNTS_PER_MOTOR_REV    = 537.7;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    public WebcamName cam;

    public HardwareMap hwMap;
    private ElapsedTime runtime = new ElapsedTime();


    public hardwareTeleop(OpMode myOpmode) {
        this.myopmode = myOpmode;
    }

    static final double ARM_COUNTS_PER_MOTOR_REV = 1425.1;

    static final double ARM_GEAR_REDUCTION = 2;

    static final double ARM_COUNTS_PER_DEGREE = (ARM_COUNTS_PER_MOTOR_REV * ARM_GEAR_REDUCTION) / 360;

    static final int TILE = 24;

    private int armCurrentDegree = 0;


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
        hang = hwMp.get(DcMotorEx.class,  "Hang");
        tape = hwMp.get(DcMotorEx.class,"Tape");


        hang.setDirection(DcMotorSimple.Direction.REVERSE);
        frontR.setDirection(DcMotorSimple.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightGrip.setDirection(DcMotorSimple.Direction.REVERSE);



    }

    public int getArmCurrentDegree() {
        return armCurrentDegree;
    }

    public void setArmCurrentDegree(int armCurrentDegree) {
        this.armCurrentDegree = armCurrentDegree;
    }

    public void setArmPosition (double degrees){

        int targetDegree = (int) (degrees * ARM_COUNTS_PER_DEGREE);


        myopmode.telemetry.addLine("Target Set");
        myopmode.telemetry.update();
        if ((degrees <= 190 && degrees>= 0)){

            arm.setTargetPosition(targetDegree);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (getArmCurrentDegree() < targetDegree){
                arm.setVelocity(1500); // this is in motor ticks
            } else if (getArmCurrentDegree() > targetDegree) {
                arm.setVelocity(1000);
            }else {
                return;
            }
            runtime.reset();
            while(arm.isBusy()){
                myopmode.telemetry.addData("Arm Position: ", arm.getCurrentPosition());
                myopmode.telemetry.update();
            }
            arm.setPower(0);
            setArmCurrentDegree( (int) (degrees));
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

    public void setArmPosition (double degrees, int timeout){

        int targetDegree = (int) (degrees * ARM_COUNTS_PER_DEGREE);



        if (degrees <= 190 && degrees>= 0){

            arm.setTargetPosition(targetDegree);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (getArmCurrentDegree() < targetDegree){
                arm.setVelocity(1500); // this is in motor ticks
            } else if (getArmCurrentDegree() > targetDegree) {
                arm.setVelocity(1000);
            }else {
                return;
            }
            runtime.startTime();
            runtime.reset();
            while(arm.isBusy() && timeout < runtime.seconds()){

                myopmode.telemetry.addData("Arm Position: ", getArmCurrentDegree());
                myopmode.telemetry.update();
            }

            arm.setPower(0);
            setArmCurrentDegree((int) (degrees));
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }

}
