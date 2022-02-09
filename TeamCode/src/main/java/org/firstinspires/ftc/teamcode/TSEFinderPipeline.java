package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TSEFinderPipeline extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();
    public TSE_Position tse_position = TSE_Position.CENTER;
    private int leftBoxX = 0;
    private int centerBoxX = 250;
    private int rightBoxX = 500;
    private int boxWidth = 160;
    private int boxHeight = 140;
    private int boxY = 330;

    public double leftTotal0, centerTotal0, rightTotal0;
    public double leftTotal1, centerTotal1, rightTotal1;
    public double leftTotal2, centerTotal2, rightTotal2;
    public double leftTotal3, centerTotal3, rightTotal3;

    @Override
    public final Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if (workingMatrix.empty()) {
            return input;
        }

        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb);

        Mat matLeft = workingMatrix.submat(boxY, boxY+boxHeight, leftBoxX, leftBoxX+boxWidth);
        Mat matCenter = workingMatrix.submat(boxY, boxY+boxHeight, centerBoxX, centerBoxX+boxWidth);
        Mat matRight = workingMatrix.submat(boxY, boxY+boxHeight, rightBoxX, rightBoxX+boxWidth);

        Imgproc.rectangle(workingMatrix, new Rect(leftBoxX, boxY, boxWidth, boxHeight), new Scalar(0,255,0), 3);
        Imgproc.rectangle(workingMatrix, new Rect(centerBoxX, boxY, boxWidth, boxHeight), new Scalar(0,255,0), 3);
        Imgproc.rectangle(workingMatrix, new Rect(rightBoxX, boxY, boxWidth, boxHeight), new Scalar(0,255,0), 3);

        leftTotal0 = Core.sumElems(matLeft).val[0];
        centerTotal0 = Core.sumElems(matCenter).val[0];
        rightTotal0 = Core.sumElems(matRight).val[0];

        // look for reddest area (the Cr of YCrCb). The TSE is red, and the Cr channel can't tell gray from black or floor tile from hub or barrier
        leftTotal1 = Core.sumElems(matLeft).val[1];
        centerTotal1 = Core.sumElems(matCenter).val[1];
        rightTotal1 = Core.sumElems(matRight).val[1];

        leftTotal2 = Core.sumElems(matLeft).val[2];
        centerTotal2 = Core.sumElems(matCenter).val[2];
        rightTotal2 = Core.sumElems(matRight).val[2];

        leftTotal3 = Core.sumElems(matLeft).val[3];
        centerTotal3 = Core.sumElems(matCenter).val[3];
        rightTotal3 = Core.sumElems(matRight).val[3];

        if (leftTotal1 > centerTotal1) {
            if (leftTotal1 > rightTotal1) {
                // team shipping element is to the left
                tse_position = TSE_Position.LEFT;
            } else {
                // team shipping element is to the right
                tse_position = TSE_Position.RIGHT;
            }
        } else {
            if (centerTotal1 > rightTotal1) {
                // team shipping element is in the center
                tse_position = TSE_Position.CENTER;
            } else {
                // team shipping element is to the right
                tse_position = TSE_Position.RIGHT;
            }
        }

        return workingMatrix;
    }
}

