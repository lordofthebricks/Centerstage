package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Multitasking Iterative OpMode", group="Iterative Opmode")

public class testTeleop extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private hardwareTeleop robot = new hardwareTeleop(this);

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        robot.init(hardwareMap);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    private void runDriveTask() {
        // Setup a variable for each drive wheel to save power level for telemetry


        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // leftPower = -gamepad1.left_stick_y ;
        // rightPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels

    }

    private boolean buttonAPressed = false;
    private void runOperatorTask() {
        if (!buttonAPressed && gamepad2.a) {
            // button A is pressed
            capstoneTaskStep = 1; //start capstone task
            buttonAPressed = true;
        } else if (buttonAPressed && !gamepad2.a) {
            // button A is released
            buttonAPressed = false;
        }
    }

    private int capstoneTaskStep = 0;
    private double expiredTime = 0.0;
    private void runCapstoneTask() {
        switch (capstoneTaskStep) {
            case 1:
                // raise capstone servo.
                expiredTime = runtime.time() + 1.0;
                capstoneTaskStep = 2;
                break;
            case 2:
                if (runtime.time() >= expiredTime) {
                    // open capstone holder.
                    expiredTime = runtime.time() + 1.0;
                    capstoneTaskStep = 3;
                }
                break;
            case 3:
                if (runtime.time() >= expiredTime) {
                    // lower capstone servo.
                    expiredTime = runtime.time() + 1.0;
                    capstoneTaskStep = 4;
                }
                break;
            case 4:
            //... etc
                break;
            default:
                break;
        }
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        runDriveTask();
        runOperatorTask();
        runCapstoneTask();

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}