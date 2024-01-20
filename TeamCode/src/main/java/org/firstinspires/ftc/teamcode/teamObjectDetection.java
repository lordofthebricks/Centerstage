package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class teamObjectDetection implements VisionProcessor {

    Mat region1;

    Mat hsv = new Mat();
    Mat hue = new Mat();

    int avg1;

    final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(270,360);

    final int REGION_WIDTH = 100;
    final int REGION_HEIGHT = 100;
    static final Scalar BLUE = new Scalar(0, 0, 255);
    Paint paint = new Paint();




    Point region1_pointA = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region1_pointB = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
    private boolean detection = false;


    void inputToH(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(hsv, hue, 0);
    }


    @Override
    public void init(int width, int height, CameraCalibration calibration) {
    //


//        region1 = hue.submat(new Rect(region1_pointA, region1_pointB));
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        inputToH(frame);

        region1 = hue.submat(new Rect(region1_pointA, region1_pointB));
        avg1 = (int) Core.mean(region1).val[0];

        Imgproc.rectangle(
                frame, // Buffer to draw on
                region1_pointA, // First point which defines the rectangle
                region1_pointB, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                2);

        if ((avg1 <=125 && avg1 >= 115) || (avg1 <= 5 || avg1 >= 175)){
            detection = true;
        }else {
            detection = false;
        }

        return detection;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        paint.setColor(Color.RED);
        if ((boolean) userContext == true){
            canvas.drawText("Detected", 200, 200, paint);
        }else {
            canvas.drawText(" not Detected", 200, 200, paint);

        }


    }

    boolean isDetected(){
        return detection;
    }


}
