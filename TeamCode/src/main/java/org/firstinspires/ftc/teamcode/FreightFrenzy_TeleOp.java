package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@TeleOp(name="Freight Frenzy Drive")
public class FreightFrenzy_TeleOp extends LinearOpMode {
    public FreightFrenzyRobot robot = new FreightFrenzyRobot();

    OpenCvInternalCamera phoneCam;
    PhoneColorerPipeline pipeline;

    // Drivetrain variables
    double adjustAngle = 0;
    double robotAngle = adjustAngle;
    double leftStickAngle = 0;
    double theta;  // difference between robot heading and the direction it should travel towards.
    double leftStickR = 0; // distance from 0-1 from gamepad1.left_stick center to edge. is used to set power level to drive train motors.
    double xWheelsPower; // wheel 2 and 3 power
    double yWheelsPower; // wheel 1 and 4 power
    double speed;  // speed adjustment for robot to turn towards robotAngle.
    double difference;
    double sign;

    final double DRIVETRAIN_ADJUST_POWER = 0.012;

    /**
     * delay in milliseconds
     */
    int intake_switch_delay = 500;
    int last_intake_switch = (int)System.currentTimeMillis();
    public boolean run_intake = false;

    final double TSET_TURNSTILE_INCREMENT = 0.05;
    final double TSET_PIVOT_INCREMENT = 0.02;

    double tset_turnstile_position = 1;
    double tset_pivot_position = 0.75;

    private double intakePower = 0;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, this);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new PhoneColorerPipeline();
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



        // Start robot at starting position (x, y) //
        robot.odometer.x = 0; // we don't use odometry in teleOp so it doesn't matter where the initial coords are
        robot.odometer.y = 0;

        // Set the adjusted robot heading
        if (Math.abs(gamepad2.right_stick_x) + Math.abs(gamepad2.right_stick_y) > 0.2) {
            adjustAngle = Math.atan2(-gamepad2.right_stick_y, gamepad2.right_stick_x) - Math.PI / 2;
        }
        adjustAngle = adjustAngle * 180 / Math.PI;
        robotAngle = adjustAngle;

        telemetry.addData("adjusted robot heading", adjustAngle);
        telemetry.update();

        // Wait for the game to start
        waitForStart();

        // Set servo initial positions
        robot.setOdoPodsUp();
        //robot.setOdoPodsDown();
        robot.setTSETtoInitPosition();
        robot.intakeFlap.setPosition(1);

        robot.armTurnstile.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armHinge.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // run until the end of the match (driver presses STOP)
        OdometryPosition position;
        while (opModeIsActive()) {
            position = robot.odometer.getCurrentPosition();

            // ###############################################################
            //  ######      CONTROLS TO MAKE THE DRIVETRAIN MOVE.      ######
            robot.angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            // now use right stick input to get the robot's heading. the if statement ensures that the joystick is a distance away from the center where readings will be accurate.
            if (Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.right_stick_y) > 0.6) {
                robotAngle = (Math.atan2(-gamepad1.right_stick_y, gamepad1.right_stick_x) * 180 / Math.PI) - 90;
                if (robotAngle <= -180) {
                    robotAngle += 360;
                }
                if (robotAngle >= 180) {
                    robotAngle -= 360;
                }
            }
            if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                leftStickAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) + (Math.PI * 5 / 4);
                if (leftStickAngle >= Math.PI) {
                    leftStickAngle -= Math.PI * 2;
                }
                theta = (robotAngle / 180) * Math.PI - leftStickAngle;
                xWheelsPower = Math.cos(theta);
                yWheelsPower = Math.sin(theta);
            } else {
                xWheelsPower = 0;
                yWheelsPower = 0;
            }
            difference = (position.angle - robotAngle) + adjustAngle;
            if (Math.abs(difference) > 180) {
                if (difference < 0) {
                    sign = -1;
                } else {
                    sign = 1;
                }
                difference = sign * (360 - Math.abs(difference));
                speed = -(difference) * DRIVETRAIN_ADJUST_POWER;
            } else {
                speed = (difference) * DRIVETRAIN_ADJUST_POWER;
            }

            leftStickR = Math.sqrt((Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2))) * 1.42;

            // this code here ensures that the robot can turn without having to strafe or sit there making a whining noise.
            double speed1 = speed, speed2 = speed; // speed1 is for the front wheels 1 and 2, and speed2 is for the back wheels 3 and 4.

            if (robot.MIN_DRIVE_BASE_TURN_POWER/2 <= Math.abs(speed) && Math.abs(speed) < robot.MIN_DRIVE_BASE_TURN_POWER) {
                // only the back wheels move, meaning that the robot can turn but at a lower speed.
                speed2 *= 2;
                speed1 = 0;
            } else if (Math.abs(speed) < robot.MIN_DRIVE_BASE_TURN_POWER/2) {
                // at a certain threshold you'll get no movement, but the motors will whine. thus, it's best to just stop them.
                speed1 = 0;
                speed2 = 0;
            }
            robot.wheel1.setPower(yWheelsPower * leftStickR + speed1);
            robot.wheel4.setPower(yWheelsPower * leftStickR - speed2);
            robot.wheel2.setPower(xWheelsPower * leftStickR - speed1);
            robot.wheel3.setPower(xWheelsPower * leftStickR + speed2);



            // ###################################################################
            //  ###### OTHER GAMEPAD1 (A) CONTROLS (intake & duck spinner) ######
            if (gamepad1.left_bumper) {
                intakePower = 0;
            } else if (gamepad1.right_bumper) {
                intakePower = robot.INTAKE_OUTPUT_POWER;
            } else if (gamepad1.left_trigger > 0.2) {
                intakePower = gamepad1.left_trigger + 0.1;
            }
            robot.intake.setPower(intakePower);

            // phone turns from red to green when a freight is grabbed
            pipeline.updatePhoneColor(robot.distanceSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("phoneColor R", pipeline.phoneColor.val[0]);
            telemetry.addData("phoneColor G", pipeline.phoneColor.val[1]);

            if (gamepad1.dpad_left) {
                robot.duckSpinner.setVelocity(-robot.DUCK_SPINNER_VELOCITY);
            } else if (gamepad1.dpad_right) {
                robot.duckSpinner.setVelocity(robot.DUCK_SPINNER_VELOCITY);
            } else {
                robot.duckSpinner.setVelocity(0);
            }

            if (gamepad1.y) {
                robot.intakeFlap.setPosition(1);
            } else if (gamepad1.a) {
                robot.intakeFlap.setPosition(0);
            } else {
                if (robot.armHinge.getCurrentPosition() > robot.ARM_HINGE_UP_CLICKS*59/90) {
                    robot.intakeFlap.setPosition(1);
                } else if (robot.armHinge.getCurrentPosition() < robot.ARM_HINGE_UP_CLICKS*60/90) {
                    robot.intakeFlap.setPosition(0);
                }
            }

            // ##############################################################
            //  ######    GAMEPAD2 (B) CONTROLS (arm & TSE turret)    ######

            // Controls for the arm
            if (robot.armHinge.getCurrentPosition() < robot.ARM_HINGE_UP_CLICKS*15/90 &&
                robot.armHinge.getCurrentPosition() > robot.ARM_HINGE_UP_CLICKS*75/90) {
                robot.armHinge.setPower(gamepad2.left_stick_y-0.02);
            } else {
                robot.armHinge.setPower(gamepad2.left_stick_y);
            }
            robot.armTurnstile.setPower(-gamepad2.right_stick_x/1.4);
            telemetry.addData("clicks for arm turnstile", robot.armTurnstile.getCurrentPosition());
            telemetry.addData("clicks for arm hinge", robot.armHinge.getCurrentPosition());


            // Team Shipping Element Turret controls
            if (gamepad2.left_trigger > 0.2) {
                tset_turnstile_position -= TSET_TURNSTILE_INCREMENT;
            } else if (gamepad2.right_trigger > 0.2) {
                tset_turnstile_position += TSET_TURNSTILE_INCREMENT;
            } else if (gamepad2.left_bumper) {
                tset_turnstile_position -= TSET_TURNSTILE_INCREMENT/6;
            } else if (gamepad2.right_bumper) {
                tset_turnstile_position += TSET_TURNSTILE_INCREMENT/6;
            }
            if (tset_turnstile_position > 1) {tset_turnstile_position = 1;}
            if (tset_turnstile_position < 0) {tset_turnstile_position = 0;}
            robot.TSET_Turnstile.setPosition(tset_turnstile_position);

            if (gamepad2.dpad_down) {
                tset_pivot_position += TSET_PIVOT_INCREMENT;
            } else if (gamepad2.dpad_up) {
                tset_pivot_position -= TSET_PIVOT_INCREMENT;
            } else if (gamepad2.dpad_left) {
                tset_pivot_position -= TSET_PIVOT_INCREMENT/3;
            } else if (gamepad2.dpad_right) {
                tset_pivot_position += TSET_PIVOT_INCREMENT/3;
            }
            if (tset_pivot_position > 1) {tset_pivot_position = 1;}
            if (tset_pivot_position < 0) {tset_pivot_position = 0;}
            robot.TSET_Pivot.setPosition(tset_pivot_position);

            if (gamepad2.a) {
                robot.TSET_Extender1.setPosition(0);
                robot.TSET_Extender2.setPosition(1);
                robot.TSET_Extender3.setPosition(0);
            } else if (gamepad2.y) {
                robot.TSET_Extender1.setPosition(1);
                robot.TSET_Extender2.setPosition(0);
                robot.TSET_Extender3.setPosition(1);
            } else {
                robot.TSET_Extender1.setPosition(0.5);
                robot.TSET_Extender2.setPosition(0.5);
                robot.TSET_Extender3.setPosition(0.5);
            }

            telemetry.addData("Odo-given X", position.x);
            telemetry.addData("Odo-given Y", position.y);
            telemetry.addData("distance sensor reading", robot.distanceSensor.getDistance(DistanceUnit.CM));
            /////////////////     UPDATE TELEMETRY     /////////////////
            telemetry.update();
        }
        phoneCam.stopStreaming();
        phoneCam.closeCameraDevice();
    }
}
