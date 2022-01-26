package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Warehouse side BLUE")
public class FreightFrenzy_WarehouseSideAutoBlue extends FreightFrenzy_BaseAutoSetup {
    long startTimeInMs;

    @Override
    public void initOdometryCoordinates() {
        robot.odometer.x = 54;
        robot.odometer.y = 2;
    }

    @Override
    public void runRobotAuto() {
        startTimeInMs = System.currentTimeMillis();
        telemetry.addData("start ms", startTimeInMs);
        telemetry.update();

        // drive around TSE and score pre-loaded freight
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        if (this.tse_position == TSE_Position.LEFT) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 21 / 90, robot.armHinge);
            robot.odStrafe(-40, 1, 60, 19, 4, 1200, 0.05, false);
            robot.odStrafe(-45, 0.4, 64, 29, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/4);

            robot.odStrafe(0, 1, 63, 12, 4, 1500, 0.05, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 49 / 90, robot.armHinge);
            robot.odStrafe(-35, 1, 65, 15, 4, 1200, 0.05, false);
            robot.odStrafe(-35, 0.4, 68, 25, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, 66, 12, 4, 1200, 0.05, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(20, 1, 67, 10, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -165, robot.armTurnstile);
            robot.odTurn(140, 1, 1000);
            robot.odStrafe(140, 1, 76, 24, 4, 1000, 0.05, false);
            robot.odStrafe(140, 0.4, 77, 28, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -55, robot.armTurnstile);
            robot.odStrafe(140, 1, 73, 14, 4, 1100, 0.04, false);

        }

        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 17 / 90, robot.armHinge);
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.odTurn(90, 1, 500);
        robot.odStrafe(90, 1, 68, 2, 3, 1800);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 5 / 90, robot.armHinge);

        //while time left > 1 cycle time, cycle
        while (System.currentTimeMillis() - startTimeInMs < 22000) {
            // starting at (angle=90deg, x=68, y=3)

            // dig through warehouse for freight
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(90, 1, 46, 5, 3, 800, 0.05, false);
            int c = 0;
            while (robot.distanceSensor.getDistance(DistanceUnit.CM) > robot.MIN_CM_FOR_NO_FREIGHT && c < 2) {
                robot.odStrafe(90+(c*6), 0.5, 41-(c*3), 5+(c*5), 4, 1000, 0.05, false);
                //if (robot.distanceSensor.getDistance(DistanceUnit.CM) > robot.MIN_CM_FOR_NO_FREIGHT) {break;}
                robot.odStrafe(78+(c*6), 0.6, 34-(c*3), 5+(c*5), 4, 1000, 0.05, false);
                if (robot.distanceSensor.getDistance(DistanceUnit.CM) < robot.MIN_CM_FOR_NO_FREIGHT) {break;}

                robot.odStrafe(86+(c*6), 0.7, 43-c, 5+(c*5), 4, 1000, 0.05, false);

                c++;
            }
            if (System.currentTimeMillis() - startTimeInMs > 23000) {
                break;
            }

            // leave warehouse
            robot.odStrafe(90, 0.8, 44, 3, 3, 1200, 0.05, false);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(90, 1, 65, 5, 3, 1200, 0.05, false);

            // score freight
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -168, robot.armTurnstile);
            robot.odStrafe(100, 1, 68, 10, 4, 1000, 0.05, false);
            robot.odTurn(140, 1, 400, 0.01, false);
            robot.odStrafe(140, 1, 76, 26, 4, 1000, 0.05, false);
            robot.odStrafe(140, 0.4, 77, 30, 2, 1200);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -55, robot.armTurnstile);
            robot.odStrafe(140, 1, 72, 12, 4, 1500, 0.05, false);

            robot.intake.setPower(0);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 16 / 90, robot.armHinge);
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odTurn(90, 1, 450, 0.01, false);
            robot.odStrafe(90, 1, 68, 2, 3, 1600);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 5 / 90, robot.armHinge);
        }

        // park
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(90, 1, 42, 5, 3, 800, 0.05, false);
        if (System.currentTimeMillis() - startTimeInMs < 28750) {
            robot.odStrafe(90, 0.8, 42, 32, 4, 1200);
        }
        if (System.currentTimeMillis() - startTimeInMs < 28750) {
            robot.odStrafe(90, 0.6, 24, 32, 4, 1200);
        }
    }
}
