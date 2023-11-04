package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class hardware {

    private LinearOpMode myopmode;
    public DcMotorEx frontL;
    public DcMotorEx frontR;
    public DcMotorEx backL;
    public DcMotorEx backR;
    public Servo sling;
    public Servo leftGrip;
    public Servo rightGrip;
    static final double     COUNTS_PER_MOTOR_REV    = 537.7;//356.3 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    public WebcamName cam;

    public HardwareMap hwMap;
    private ElapsedTime runtime;


    public hardware(LinearOpMode myopmode) {
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

    public void encoderDrive ( double speed,
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
                frontL.getCurrentPosition();
                frontR.getCurrentPosition();
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

}
