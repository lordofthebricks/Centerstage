package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;


@TeleOp(name = "Open Cv Test")
public class openCVDetection extends LinearOpMode {

    DoubleVision vision = new DoubleVision(this);
    hardware robot = new hardware(this);


    @Override
    public void runOpMode() throws InterruptedException {

        teamObjectDetection tod = new teamObjectDetection();

        VisionPortal visionPortal = new VisionPortal.Builder()
//                .setCamera(robot.cam)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessors(tod)
                .build();

        waitForStart();
        while(opModeIsActive()){



        }



    }
}
