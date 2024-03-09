package org.firstinspires.ftc.teamcode.scrimmage;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Hardware {

    public DcMotor FrontLeft;
    public DcMotor BackLeft;
    public DcMotor FrontRight;
    public DcMotor BackRight;
    public void init (HardwareMap hwMp) {

     FrontLeft = hwMp.get(DcMotor.class,"frontLeft");
     BackLeft = hwMp.get(DcMotor.class,"backLeft");
     FrontRight = hwMp.get(DcMotor.class,"frontRight");
     BackRight = hwMp.get(DcMotor.class,"backRight");
    }
}
