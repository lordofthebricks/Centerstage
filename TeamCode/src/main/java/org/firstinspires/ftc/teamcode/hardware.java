package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class hardware {

    public DcMotor frontL;
    public DcMotor frontR;
    public DcMotor backL;
    public DcMotor backR;
    public Servo sling;

    public HardwareMap hwMap;

    public void init(HardwareMap hwMp){

        frontL = hwMp.get(DcMotor.class, "FrontLeft");
        frontR = hwMp.get(DcMotor.class, "FrontRight");
        backL = hwMp.get(DcMotor.class, "BackLeft");
        backR = hwMp.get(DcMotor.class, "BackRight");
        sling = hwMp.get(Servo.class, "Sling");

    }


}
