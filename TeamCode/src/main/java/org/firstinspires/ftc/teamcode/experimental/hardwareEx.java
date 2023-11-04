package org.firstinspires.ftc.teamcode.experimental;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class hardwareEx {

    private LinearOpMode myopmode;
    private DcMotorEx frontL;
    private DcMotorEx frontR;
    private DcMotorEx backL;
    private DcMotorEx backR;
    private Servo sling;
    private Servo leftGrip;
    private Servo rightGrip;

    public WebcamName cam;

    public HardwareMap hwMap;

    public hardwareEx(LinearOpMode myopmode) {
        this.myopmode = myopmode;
    }

    public void init(HardwareMap hwMp){

        frontL = hwMp.get(DcMotorEx.class, "FrontLeft");
        frontR = hwMp.get(DcMotorEx.class, "FrontRight");
        backL = hwMp.get(DcMotorEx.class, "BackLeft");
        backR = hwMp.get(DcMotorEx.class, "BackRight");
        sling = hwMp.get(Servo.class, "Sling");
        leftGrip = hwMp.get(Servo.class, "LeftGrip");
        rightGrip = hwMp.get(Servo.class, "RightGrip");
//        cam = hwMp.get(WebcamName.class, "Webcam1");

        frontR.setDirection(DcMotorSimple.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void tankControl(Gamepad gp){
        if(gp.left_stick_y >= 0.2){
            frontL.setPower(gp.left_stick_y);
            backL.setPower(gp.left_stick_y);
        }
        if (gp.right_stick_y >= 0.2){
            frontR.setPower(gp.right_stick_y);
            backR.setPower(gp.right_stick_y);
        }
    }


}
