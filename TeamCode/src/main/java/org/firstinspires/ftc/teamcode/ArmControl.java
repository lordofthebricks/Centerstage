package org.firstinspires.ftc.teamcode;

public class ArmControl implements Runnable{


    hardwareTeleop robot;

    public ArmControl(hardwareTeleop robot) {
        this.robot = robot;
    }

    @Override
    public void run() {
        if (robot.getArmCurrentDegree() > 0){
            robot.setArmPosition(0);
        }else {
            robot.setArmPosition(170);
        }
    }
}
