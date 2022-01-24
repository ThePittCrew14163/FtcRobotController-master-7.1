package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Warehouse side (Red or Blue)")
public class FreightFrenzy_WarehouseSideAuto extends FreightFrenzy_BaseAutoSetup {
    double startTimeInMs;

    @Override
    public void initOdometryCoordinates() {
        if (this.side == Alliance.Blue) {
            robot.odometer.x = 54;
        } else {
            robot.odometer.x = -64.5;
        }
        robot.odometer.y = 2;
    }

    @Override
    public void runRobotAuto() {
        startTimeInMs = (int)System.currentTimeMillis();
        telemetry.addData("start ms", startTimeInMs);
        telemetry.update();
        if (this.side == Alliance.Red) {
            this.redSideAuto();
        } else {
            this.blueSideAuto();
        }

        // score pre-loaded freight

        // loop until robot has less than 4-5 seconds {
        //     align with wall and go into warehouse
        //     dig into the freight pile until distance sensor detects freight or until ~3 seconds are up
        //     drive out and score that freight
        // }

        // park

    }

    public void redSideAuto() {
        // TODO: finish blue side, then copy here and adjust
    }

    public void blueSideAuto() {
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

            robot.odStrafe(0, 1, 60, 19, 4, 1500, 0.05, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 49 / 90, robot.armHinge);
            robot.odStrafe(-30, 1, 63, 16, 4, 1200, 0.05, false);
            robot.odStrafe(-30, 0.4, 66, 26, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, 60, 19, 4, 1500, 0.05, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(20, 1, 67, 12, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -135, robot.armTurnstile);
            robot.odTurn(110, 1, 1000);
            robot.odStrafe(110, 0.7, 75, 23, 4, 1000, 0.05, false);
            robot.odStrafe(110, 0.4, 76, 28, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odStrafe(110, 1, 75, 15, 4, 1000, 0.04, false);

        }

        telemetry.addData("start ms", startTimeInMs);
        telemetry.addData("current ms",  (int)System.currentTimeMillis());
        telemetry.addData("ms diff",  (int)System.currentTimeMillis() - startTimeInMs);
        telemetry.update();
        robot.odometer.odSleep(6000);

        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 16 / 90, robot.armHinge);
        robot.odTurn(90, 1, 700);
        robot.odStrafe(90, 1, 68, 3, 3, 1500);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 6 / 90, robot.armHinge);

        //while time left > 1 cycle time, cycle
        while ((int)System.currentTimeMillis() - 24000 < startTimeInMs) {
            // starting at (angle=90deg, x=68, y=3)

            // dig through warehouse for freight
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(90, 1, 46, 5, 3, 800, 0.05, false);
            int c = 0;
            while (robot.distanceSensor.getDistance(DistanceUnit.CM) > robot.MIN_CM_FOR_NO_FREIGHT && c < 3) {
                robot.odStrafe(90+(c*6), 0.5, 43-(c*2), 5+(c*5), 4, 1000, 0.05, false);
                if (robot.distanceSensor.getDistance(DistanceUnit.CM) > robot.MIN_CM_FOR_NO_FREIGHT) {break;}
                robot.odStrafe(78+(c*6), 0.6, 40-(c*3), 5+(c*5), 4, 1000, 0.05, false);
                if (robot.distanceSensor.getDistance(DistanceUnit.CM) > robot.MIN_CM_FOR_NO_FREIGHT) {break;}

                robot.odStrafe(86+(c*6), 0.7, 44-c, 5+(c*5), 4, 1000, 0.05, false);

                c++;
            }
            if ((int)System.currentTimeMillis() - 25000 < startTimeInMs) {
                break;
            }

            // leave warehouse
            robot.odStrafe(90, 0.7, 44, 3, 3, 1200, 0.05, false);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(90, 1, 65, 5, 3, 1200, 0.05, false);

            // score freight
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -135, robot.armTurnstile);
            robot.odStrafe(110, 1, 74, 21, 4, 1000, 0.05, false);
            robot.odStrafe(110, 0.4, 76, 28, 2, 1300);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odStrafe(110, 1, 74, 15, 4, 1500, 0.05, false);

            robot.intake.setPower(0);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 16 / 90, robot.armHinge);
            robot.odTurn(90, 1, 700);
            robot.odStrafe(90, 1, 68, 3, 3, 1500);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 6 / 90, robot.armHinge);
        }

        // park
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(90, 1, 42, 5, 3, 800, 0.05, false);
        if ((int)System.currentTimeMillis() - 28750 < startTimeInMs) {
            robot.odStrafe(90, 0.8, 42, 32, 4, 1200);
        }
        if ((int)System.currentTimeMillis() - 28750 < startTimeInMs) {
            robot.odStrafe(90, 0.6, 24, 32, 4, 1200);
        }
    }
}
