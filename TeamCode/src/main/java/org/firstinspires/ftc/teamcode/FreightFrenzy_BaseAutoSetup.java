package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

/**
 * odometry (x,y) is (0,0) for the back corner of the robot's alliance's warehouse.
 *
 * The robot must start on a tile between the wall and a barcode.
 * The robot must be positioned with its back flat against the wall and its left side aligned with the inside of the left seam of the tile it starts on.
 * For the blue side warehouse setup, the robot can't line up with the seam and must have the side of its front left wheel touching the barrier.
 */
public class FreightFrenzy_BaseAutoSetup extends LinearOpMode {
    public FreightFrenzyRobot robot = new FreightFrenzyRobot();

    OpenCvInternalCamera phoneCam;
    TSEFinderPipeline pipeline;
    TSE_Position tse_position;

    /**
     * Start robot
     */
    @Override
    public void runOpMode() {
        robot.init(hardwareMap, this);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new TSEFinderPipeline();
        phoneCam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // supported camera resolutions (for the gold E4) are: 1280x720, 960x720, 768x432, 720x480, 640x480, 320x240, 176x144
                phoneCam.startStreaming(720, 480, OpenCvCameraRotation.SIDEWAYS_RIGHT);
            }

            @Override
            public void onError(int i) {
                // display given error because I don't know what else to do
                telemetry.addData("Error integer", i);
            }
        });

        initOdometryCoordinates();

        // Set servo initial positions
        robot.setOdoPodsDown();
        robot.setTSETtoInitPosition();

        while (!isStarted() && !isStopRequested()) {
            double angle = robot.odometer.getCurrentPosition().angle;
            tse_position = pipeline.tse_position;
            telemetry.addData("\nLeft cr", pipeline.leftTotal1);
            telemetry.addData("Center cr", pipeline.centerTotal1);
            telemetry.addData("Right cr", pipeline.rightTotal1);
            telemetry.addData("TSE position", pipeline.tse_position);
            telemetry.addData("\nLeft yellow", pipeline.leftTotal0);
            telemetry.addData("Center yellow", pipeline.centerTotal0);
            telemetry.addData("Right yellow", pipeline.rightTotal0);
            telemetry.addData("\nLeft cb", pipeline.leftTotal2);
            telemetry.addData("Center cb", pipeline.centerTotal2);
            telemetry.addData("Right cb", pipeline.rightTotal2);

            telemetry.addData("\nLeft a", pipeline.leftTotal3);
            telemetry.addData("Center a", pipeline.centerTotal3);
            telemetry.addData("Right a", pipeline.rightTotal3);

            telemetry.addData("\nOdo X", robot.odometer.x);
            telemetry.addData("\nOdo Y", robot.odometer.y);
            telemetry.addData("\nOdo angle", angle);

            telemetry.update();

            // Don't burn CPU cycles busy-looping
            robot.odometer.odSleep(50);
        }
        telemetry.clear();
        telemetry.update();

        // duck-side or warehouse side or other auto will run here
        runRobotAuto();

        // make sure that robot pulls up those pods at the end // TODO: should this not be done until teleOp?
        robot.setOdoPodsUp();
    }

    /**
     * Inheriting autonomous classes override this method to add the code for the 30 seconds of robot movement
     */
    public void runRobotAuto() {
    }

    /**
     * Inheriting autonomous classes override this method to set odometry coordinates       <br>
     * Note that odometry (x,y) is (0,0) for the back corner of the robot's alliance's warehouse.
     * example use:                <br>
     *    robot.odometer.x = 0;  <br>
     *    robot.odometer.y = 2;
     */
    public void initOdometryCoordinates() {
        robot.odometer.x = 0;
        robot.odometer.y = 2;
    }
}
