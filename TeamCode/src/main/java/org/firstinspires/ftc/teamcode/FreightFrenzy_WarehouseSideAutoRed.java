package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "RED Warehouse side")
public class FreightFrenzy_WarehouseSideAutoRed extends FreightFrenzy_BaseAutoSetup {
    long startTimeInMs;

    @Override
    public void initOdometryCoordinates() {
        robot.odometer.x = -64.5;
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
            robot.odStrafe(0, 1, -77, 18, 4, 1200, 0.05, false);
            robot.odStrafe(5, 0.4, -78, 25, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, -63, 12, 4, 1500, 0.05, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 49 / 90, robot.armHinge);
            robot.odStrafe(35, 1, -65, 15, 4, 1200, 0.05, false);
            robot.odStrafe(35, 0.4, -68, 25, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, -66, 12, 4, 1200, 0.05, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(-20, 1, -66, 10, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 150, robot.armTurnstile);
            robot.odTurn(-140, 1, 1000);
            robot.odStrafe(-140, 1, -75, 24, 4, 1000, 0.05, false);
            robot.odStrafe(-140, 0.4, -76, 27, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 60, robot.armTurnstile);
            robot.odStrafe(-140, 1, -73, 14, 4, 1100, 0.04, false);

        }

        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30 / 90, robot.armHinge);
        if (this.tse_position == TSE_Position.RIGHT) {
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 45, robot.armTurnstile);
        } else {
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        }
        robot.odTurn(-90, 1, 500, 0.01, false);
        if (this.tse_position == TSE_Position.RIGHT) {
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        }
        robot.odStrafe(-90, 1, -68, 2, 3, 1300);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 5 / 90, robot.armHinge);

        //while time left > 1 cycle time, cycle
        int c = 0;
        while (System.currentTimeMillis() - startTimeInMs < 21000) {
            // starting at (angle=90deg, x=68, y=3)

            // dig through warehouse for freight
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(-90, 1, -46, 5, 3, 800, 0.05, false);
            int c2 = 0;
            while (robot.distanceSensor.getDistance(DistanceUnit.CM) > robot.MIN_CM_FOR_NO_FREIGHT && c2 < 2) {
                robot.odStrafe(-90-(c*6), 0.5, -40+(c*3), 5+(c*8), 4, 1000, 0.05, false);
                //if (robot.distanceSensor.getDistance(DistanceUnit.CM) > robot.MIN_CM_FOR_NO_FREIGHT) {break;}
                robot.odStrafe(-78-(c*6), 0.6, -34+(c*4), 6+(c*9), 4, 1000, 0.05, false);
                if (robot.distanceSensor.getDistance(DistanceUnit.CM) < robot.MIN_CM_FOR_NO_FREIGHT || c >= 1) {break;}

                robot.odStrafe(-86-(c*6), 0.7, -42+c, 5+(c*7), 4, 1000, 0.05, false);

                c++;
                c2++;
            }
            c--;
            if (System.currentTimeMillis() - startTimeInMs > 22000) {
                break;
            }

            // leave warehouse
            robot.odStrafe(-90, 0.8, -44, 3, 3, 1200, 0.05, false);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(-90, 1, -65, 5, 3, 1200, 0.05, false);

            // score freight
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 150, robot.armTurnstile);
            robot.odStrafe(-100, 1, -68, 10, 4, 1000, 0.05, false);
            robot.odTurn(-140, 1, 400, 0.01, false);
            robot.odStrafe(-140, 1, -72, 22, 4, 1000, 0.05, false);
            robot.odStrafe(-140, 0.4, -75, 27, 2, 1100);
            robot.odStrafe(-140, 1, -72, 23, 4, 1000, 0.05, false);
            robot.odStrafe(-140, 0.4, -76, 30, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER*0.85);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 55, robot.armTurnstile);
            robot.odStrafe(-140, 1, -72, 11, 4, 1000, 0.05, false);

            robot.intake.setPower(0);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30/90, robot.armHinge);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 35, robot.armTurnstile);
            robot.odTurn(-90, 1, 450, 0.01, false);
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odStrafe(-90, 1, -68, 2, 3, 1200);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 5 / 90, robot.armHinge);
        }

        // park
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(-90, 1, -42, 5, 3, 800, 0.05, false);
        if (System.currentTimeMillis() - startTimeInMs < 28750) {
            robot.odStrafe(-90, 0.8, -42, 32, 4, 1200);
        }
        if (System.currentTimeMillis() - startTimeInMs < 28750) {
            robot.odStrafe(-90, 0.6, -24, 32, 4, 1200);
        }
    }
}
