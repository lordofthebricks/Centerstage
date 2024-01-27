/*
 * Copyright (c) 2023 Sebastian Erives
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import java.util.ArrayList;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class RedAndBlueElements implements VisionProcessor {


    Paint paint = new Paint();
    /**
     * These are our variables that will be
     * modifiable from the variable tuner.
     *
     * Scalars in OpenCV are generally used to
     * represent color. So our values in the
     * lower and upper Scalars here represent
     * the Y, Cr and Cb values respectively.
     *
     * YCbCr, like most color spaces, range
     * from 0-255, so we default to those
     * min and max values here for now, meaning
     * that all pixels will be shown.
     */
    public Scalar redLower = new Scalar(0, 154, 0);
    public Scalar blueLower = new Scalar(0, 0, 150);
    public Scalar upper = new Scalar(255, 255, 255);

    public double detectionPercent = 0.15;

    /**
     * This will allow us to choose the color
     * space we want to use on the live field
     * tuner instead of hardcoding it
     */
    ColorSpace colorSpace = ColorSpace.YCrCb;


    final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(170,360);

    final int REGION_WIDTH = 325;
    final int REGION_HEIGHT = 50;

    Point region1_pointA = new Point(
        REGION1_TOPLEFT_ANCHOR_POINT.x,
        REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region1_pointB = new Point(
        REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
        REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);


    //get area of detection region
    int detectionArea = REGION_WIDTH*REGION_HEIGHT;
    /**
     * A good practice when typing EOCV pipelines is
     * declaring the Mats you will use here at the top
     * of your pipeline, to reuse the same buffers every
     * time. This removes the need to call mat.release()
     * with every Mat you create on the processFrame method,
     * and therefore, reducing the possibility of getting a
     * memory leak and causing the app to crash due to an
     * "Out of Memory" error.
     */
    private Mat ycrcbMatBlue      = new Mat();
    private Mat binaryMatBlue     = new Mat();
    private Mat ycrcbMatRed       = new Mat();
    private Mat binaryMatRed      = new Mat();
    private Mat maskedInputMat    = new Mat();
    private Mat binaryMat         = new Mat();
    private Mat detectionRegion   = new Mat();
    private int avg1;
    private boolean isDetected = false;

    /**
     * Enum to choose which color space to choose
     * with the live variable tuner isntead of
     * hardcoding it.
     */
    enum ColorSpace {
        /*
         * Define our "conversion codes" in the enum
         * so that we don't have to do a switch
         * statement in the processFrame method.
         */
        RGB(Imgproc.COLOR_RGBA2RGB),
        HSV(Imgproc.COLOR_RGB2HSV),
        YCrCb(Imgproc.COLOR_RGB2YCrCb),
        Lab(Imgproc.COLOR_RGB2Lab);

        //store cvtCode in a public var
        public int cvtCode = 0;

        //constructor to be used by enum declarations above
        ColorSpace(int cvtCode) {
            this.cvtCode = cvtCode;
        }
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
    
        Imgproc.cvtColor(frame, ycrcbMatBlue, colorSpace.cvtCode);
        Imgproc.cvtColor(frame, ycrcbMatRed, colorSpace.cvtCode);

        paint.setColor(Color.RED);
        paint.setTextSize(20);

        /**
         * This is where our thresholding actually happens.
         * Takes our "ycrcbMat" as input and outputs a "binary"
         * Mat to "binaryMat" of the same size as our input.
         * "Discards" all the pixels outside the bounds specified
         * by the scalars above (and modifiable with EOCV-Sim's
         * live variable tuner.)
         *
         * Binary meaning that we have either a 0 or 255 value
         * for every pixel.
         *
         * 0 represents our pixels that were outside the bounds
         * 255 represents our pixels that are inside the bounds
         */

        Core.inRange(ycrcbMatRed, redLower, upper, binaryMatRed);
        Core.inRange(ycrcbMatBlue, blueLower, upper, binaryMatBlue);

        /*
         * Release the reusable Mat so that old data doesn't
         * affect the next step in the current processing
         */
        maskedInputMat.release();

       
//        combine the two binary Mats into one mat that masks both red and blue
        Core.bitwise_or(binaryMatBlue, binaryMatRed, binaryMat);
//        use the mask on the actual frame so that the user can see what is masked out
        Core.bitwise_and(frame, frame, maskedInputMat, binaryMat);

        /**
         * Add some nice and informative telemetry messages
         */
        /*
         * Different from OpenCvPipeline, you cannot return
         * a Mat from processFrame. Therefore, we will take
         * advantage of the fact that anything drawn onto the
         * passed `frame` object will be displayed on the
         * viewport. We will just return null here.
         */
        maskedInputMat.copyTo(frame);


        
        // create area to look for detections
        detectionRegion = binaryMat.submat(new Rect(region1_pointA, region1_pointB));

//        draw a rectangle to show user where the robot is looking for detections
        Imgproc.rectangle(
                frame, // Buffer to draw on
                region1_pointA, // First point which defines the rectangle
                region1_pointB, // Second point which defines the rectangle
                new Scalar(0, 0, 255), // The color the rectangle is drawn in
                2);

        //create look to see if there are more than x% of pixels that are not empty
        if (Core.countNonZero(detectionRegion) >= detectionArea * detectionPercent) {
            isDetected = true;

        } else{
            isDetected = false;
        }
        
        //release frames

        detectionRegion.release();

        return isDetected;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
    
        if ((boolean) userContext) {
            canvas.drawText("Detected", 200, 200, paint);
        }else{
            canvas.drawText("not Detected", 200, 200, paint);
        }

    }

    public boolean isDetected() {
        return isDetected;
    }
}

