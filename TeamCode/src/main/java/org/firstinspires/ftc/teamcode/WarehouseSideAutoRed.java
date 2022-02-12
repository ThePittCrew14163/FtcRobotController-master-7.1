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
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 23 / 90, robot.armHinge);
            robot.odStrafe(12, 1, -76, 17, 4, 1200, 0.025, true);
            robot.odometer.odSleep(60);
            robot.odStrafe(15, 0.4, -77, 22, 1.5, 1100);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER*1.3);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, -63, 12, 4, 1500, 0.025, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 49 / 90, robot.armHinge);
            robot.odStrafe(35, 1, -65, 19, 4, 1200, 0.025, true);
            robot.odometer.odSleep(60);
            robot.odStrafe(35, 0.4, -68, 25, 2, 1100);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, -66, 12, 4, 1200, 0.025, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 73 / 90, robot.armHinge);
            robot.odStrafe(-20, 1, -66, 10, 4, 1200, 0.025, false);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 150, robot.armTurnstile);
            robot.odTurn(-140, 1, 1000);
            robot.intakeFlap.setPosition(0);
            robot.odStrafe(-140, 1, -75, 24, 4, 1000, 0.025, true);
            robot.odometer.odSleep(60);
            robot.odStrafe(-140, 0.4, -76, 27, 2, 1100);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 60, robot.armTurnstile);
            robot.intakeFlap.setPosition(1);
            robot.odStrafe(-140, 1, -73, 14, 4, 1100, 0.04, false);

        }
        robot.intake.setPower(0);

        this.AfterScorePreload();
        this.DoCycles();
    }
    public void AfterScorePreload() {

        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30 / 90, robot.armHinge);
        if (this.tse_position == TSE_Position.RIGHT) {
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 45, robot.armTurnstile);
        } else {
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        }
        robot.odTurn(-90, 1, 700, 0.01, false);
        if (this.tse_position == TSE_Position.RIGHT) {
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        }
    }
    public void DoCycles() {
        robot.odStrafe(-90, 1, -68, 2, 3, 1100);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 3 / 90, robot.armHinge);

        //while time left > 1 cycle time, cycle
        int c = 0;
        while (System.currentTimeMillis() - startTimeInMs < 21000) {
            // starting at (angle=90deg, x=68, y=3)

            // dig through warehouse for freight
            robot.resetFreightHoldingTacking();
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(-90, 1, -46, 5, 3, 800, 0.02, false);
            robot.odStrafe(-115, 0.8, -38, 8, 4, 800, 0.03, false, true);
            robot.odStrafe(-50, 0.8, -33, 8, 3, 1200, 0.015, false, true);
            if (!robot.isHoldingFreight()) {
                robot.odStrafe(-150, 0.8, -28, 5, 3, 1100, 0.07, false, true);
                robot.odStrafe(-95, 0.8, -36, 8, 3, 500, 0.02, false);
            }
            if (System.currentTimeMillis() - startTimeInMs > 22000) {
                break;
            }

            // leave warehouse
            robot.odStrafe(-90, 0.8, -44, 3, 3, 1200, 0.025, false);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 73 / 90, robot.armHinge);
            robot.odStrafe(-90, 1, -65, 5, 3, 1100, 0.025, false);

            // score freight
            robot.motorTurnNoReset(1, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * (160+c), robot.armTurnstile);
            robot.odStrafe(-100, 1, -70, 10, 5, 700, 0.025, false);
            robot.odTurn(-140, 1, 400, 0.015, false);
            robot.intakeFlap.setPosition(0);
            robot.odStrafe(-140, 1, -75, 26, 5, 1000, 0.025, true);
            robot.odometer.odSleep(50);
            robot.odStrafe(-140, 0.45, -77, 30, 1.5, 1000);

            robot.odometer.odSleep(100);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1100);

            robot.motorTurnNoReset(1, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 60, robot.armTurnstile);
            robot.intakeFlap.setPosition(1);
            robot.odStrafe(-140, 1, -72, 11, 4, 1000, 0.025, false);

            robot.intake.setPower(0);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30/90, robot.armHinge);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * 35, robot.armTurnstile);
            robot.odTurn(-90, 1, 450, 0.015, false);
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odStrafe(-92, 1, -68, 2, 3, 900);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 3 / 90, robot.armHinge);

            c+=2;
        }

        // park
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(-90, 1, -42, 5, 6, 700, 0.025, false);
        robot.odStrafe(-90, 1, -36, 5, 3, 600, 0.025, false);
        robot.odTurn(-90, 0.9, 900);
    }
}
