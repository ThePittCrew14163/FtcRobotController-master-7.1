package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "RED Warehouse side", group = "normal warehouse autos")
public class WarehouseSideAutoRed extends FreightFrenzy_BaseAutoSetup {
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
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 24 / 90, robot.armHinge);
            robot.odStrafe(12, 1, -76, 16, 4, 1200, 0.025, true);
            robot.odometer.odSleep(70);
            robot.odStrafe(15, 0.4, -77, 21, 1.5, 1100);

            robot.odometer.odSleep(200);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER*1.2);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, -66, 12, 4, 1500, 0.025, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 47 / 90, robot.armHinge);
            robot.odStrafe(35, 1, -65, 19, 4, 1200, 0.025, true);
            robot.odometer.odSleep(60);
            robot.odStrafe(35, 0.4, -68, 24.5, 2, 1100);

            robot.odometer.odSleep(100);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, -66, 12, 4, 1200, 0.025, false);

        } else { // top tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 74 / 90, robot.armHinge);
            robot.odStrafe(35, 1, -65, 20, 4, 1200, 0.025, true);
            robot.intakeFlap.setPosition(0);
            robot.odometer.odSleep(60);
            robot.odStrafe(35, 0.4, -68, 25.5, 2, 1100);

            robot.odometer.odSleep(100);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);
            robot.intakeFlap.setPosition(1);

            robot.odStrafe(0, 1, -66, 12, 4, 1200, 0.025, false);

        }
        robot.intake.setPower(0);

        this.AfterScorePreload();
        this.DoCycles();
    }
    public void AfterScorePreload() {

        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30 / 90, robot.armHinge);
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.odTurn(-90, 1, 700, 0.01, false);
    }
    public void DoCycles() {
        robot.odStrafe(-92, 1, -68, 6, 3, 1000, 0.02, false);
        robot.odStrafe(-92, 1, -68, 2, 3, 300, 0.02, false);
        robot.motorTurnNoReset(0.5, 0, robot.armHinge);

        //while time left > 1 cycle time, cycle
        int c = 0;
        while (System.currentTimeMillis() - startTimeInMs < 21000) {
            // starting at (angle=90deg, x=68, y=3)

            // dig through warehouse for freight
            robot.resetFreightHoldingTacking();
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(-92, 1, -45, 2, 3, 900, 0.02, false);
            robot.odStrafe(-120, 0.8, -25-c, 8, 4, 1300, 0.02, false, true);
            robot.odStrafe(-50, 0.7, -31-c, 11, 3, 1100, 0.014, false, true);
            if (!robot.isHoldingFreight()) {
                robot.odStrafe(-140, 0.8, -30-c, 4, 3, 1100, 0.02, false, true);
            }
            if (System.currentTimeMillis() - startTimeInMs > 21600) {
                break;
            }

            // leave warehouse
            robot.odStrafe(-90, 1, -44, 3, 3, 1200, 0.02, false);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 74 / 90, robot.armHinge);
            robot.odStrafe(-90, 1, -65, 3, 3, 1100, 0.02, false);

            // score freight
            robot.motorTurnNoReset(1, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * (160+c), robot.armTurnstile);
            robot.odStrafe(-100, 1, -70, 10, 5, 700, 0.025, false);
            robot.odTurn(-140, 1, 400, 0.015, false);
            robot.intakeFlap.setPosition(0);
            robot.odStrafe(-140, 1, -73, 24, 5, 1000, 0.025, true);
            robot.odometer.odSleep(90);
            robot.odStrafe(-140, 0.45, -77, 31, 1.5, 1000);

            robot.odometer.odSleep(100);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1100);

            robot.motorTurnNoReset(1, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 60, robot.armTurnstile);
            robot.intakeFlap.setPosition(1);
            robot.odStrafe(-100, 1, -74, 14, 4, 1000, 0.025, true);

            robot.intake.setPower(0);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30/90, robot.armHinge);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 35, robot.armTurnstile);
            robot.odTurn(-88, 1, 450, 0.015, false);
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odStrafe(-92, 1, -68, 6, 3, 1000, 0.02, false);
            robot.odStrafe(-92, 1, -68, 2, 3, 300, 0.02, false);
            robot.motorTurnNoReset(0.5, 0, robot.armHinge);

            c+=3;
        }

        // park
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(-92, 1, -40, 4, 6, 700, 0.025, false);
        robot.odStrafe(-90, 1, -35, 4, 3, 800, 0.025, false);
        robot.odTurn(-90, 0.9, 900);
    }
}
