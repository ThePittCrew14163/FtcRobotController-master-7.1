package org.firstinspires.ftc.teamcode;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PhoneColorerPipeline extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();
    private Scalar phoneColor = new Scalar(255, 0, 0);
    public final double MAX_DISTANCE = 13.0;
    public final double MIN_DISTANCE = 9.0;
    public final double DISTANCE_DIFF = MAX_DISTANCE-MIN_DISTANCE;

    @Override
    public final Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if (workingMatrix.empty()) {
            return input;
        }

        Imgproc.rectangle(workingMatrix, new Rect(0, 0, 720, 480), phoneColor);

        return workingMatrix;
    }

    public void updatePhoneColor(double distanceSensorReading) {
        if (distanceSensorReading > MAX_DISTANCE) {
            phoneColor.val[1] = 0;
        } else if (distanceSensorReading < MIN_DISTANCE) {
            phoneColor.val[1] = 255;
        } else {
            phoneColor.val[1] = (MAX_DISTANCE - distanceSensorReading)*255/DISTANCE_DIFF;
        }
        phoneColor.val[0] = 255 - phoneColor.val[0];
    }
}


