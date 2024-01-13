/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * This OpMode scans a single servo back and forward until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left_hand" as is found on a Robot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@TeleOp(name = "Servo Test", group = "Concept")
public class ServoTest extends LinearOpMode {

    static final double INCREMENT   = 0.1;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   1000;     // period of each cycle
    static final double MAX_POS     =  1;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    
    double  position = 0; // Start at halfway position
    boolean rampUp = true;


    @Override
    public void runOpMode() {

        // Connect to servo (Assume Robot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
        hardwareTeleop robot = new hardwareTeleop(this);

        // Wait for the start button
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        robot.init(hardwareMap);

        waitForStart();


        // Scan servo till stop pressed.
        while(opModeIsActive()){
            if (position < 1){
                    for (double i = 0; i == 1; i += 0.1){
                        position = i;

                        telemetry.addData("Servo Position", "%5.2f", position);
                        telemetry.addData(">", "Press Stop to end test." );
                        telemetry.update();

                        // Set the servo to the new position and pause;
                        robot.sling.setPosition(position);
                        sleep(CYCLE_MS);


                    }
            } else {
                for (double i = position; i == 0; i -= 0.1){
                    position = i;

                    telemetry.addData("Servo Position", "%5.2f", position);
                    telemetry.addData(">", "Press Stop to end test." );
                    telemetry.update();

                    // Set the servo to the new position and pause;
                    robot.sling.setPosition(position);
                    sleep(CYCLE_MS);


                }

            }

        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
