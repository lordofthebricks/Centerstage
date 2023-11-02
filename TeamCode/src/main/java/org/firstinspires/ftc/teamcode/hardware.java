package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class hardware {

    public DcMotor frontL;
    public DcMotor frontR;
    public DcMotor backL;
    public DcMotor backR;
    public Servo sling;
    public Servo leftGrip;
    public Servo rightGrip;

    public WebcamName cam;

    public HardwareMap hwMap;

    public void init(HardwareMap hwMp){

        frontL = hwMp.get(DcMotor.class, "FrontLeft");
        frontR = hwMp.get(DcMotor.class, "FrontRight");
        backL = hwMp.get(DcMotor.class, "BackLeft");
        backR = hwMp.get(DcMotor.class, "BackRight");
        sling = hwMp.get(Servo.class, "Sling");
        leftGrip = hwMp.get(Servo.class, "LeftGrip");
        rightGrip = hwMp.get(Servo.class, "RightGrip");
//        cam = hwMp.get(WebcamName.class, "Webcam1");

        frontR.setDirection(DcMotorSimple.Direction.REVERSE);
        backR.setDirection(DcMotorSimple.Direction.REVERSE);

    }


}
