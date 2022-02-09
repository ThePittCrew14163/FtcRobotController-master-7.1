package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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
            robot.odStrafe(-40, 1, 60, 19, 4, 1200, 0.025, false);
            robot.odStrafe(-45, 0.4, 64, 28, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/4);

            robot.odStrafe(0, 1, 63, 12, 4, 1500, 0.025, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 49 / 90, robot.armHinge);
            robot.odStrafe(-35, 1, 65, 15, 4, 1200, 0.025, false);
            robot.odStrafe(-35, 0.4, 68, 25, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/3);

            robot.odStrafe(0, 1, 66, 12, 4, 1200, 0.025, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(20, 1, 67, 10, 4, 1200, 0.025, false);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -165, robot.armTurnstile);
            robot.odTurn(140, 1, 1000);
            robot.intakeFlap.setPosition(0);
            robot.odStrafe(140, 1, 75, 23, 4, 1000, 0.025, false);
            robot.odStrafe(140, 0.4, 76, 27, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -60, robot.armTurnstile);
            robot.intakeFlap.setPosition(1);
            robot.odStrafe(140, 1, 73, 14, 4, 1100, 0.04, false);

        }

        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30 / 90, robot.armHinge);
        if (this.tse_position == TSE_Position.RIGHT) {
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -45, robot.armTurnstile);
        } else {
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        }
        robot.odTurn(90, 1, 500, 0.01, false);
        if (this.tse_position == TSE_Position.RIGHT) {
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        }
        robot.odStrafe(90, 1, 68, 2, 3, 1300);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 5 / 90, robot.armHinge);

        //while time left > 1 cycle time, cycle
        int c = 0;
        while (System.currentTimeMillis() - startTimeInMs < 21000) {
            // starting at (angle=90deg, x=68, y=3)

            // dig through warehouse for freight
            robot.resetFreightHoldingTacking();
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(90, 1, 46, 5, 3, 800, 0.025, false);
            robot.odStrafe(105, 0.8, 38, 8, 4, 800, 0.02, false, true);
            robot.odStrafe(50, 0.8, 33, 8, 3, 1200, 0.012, false, true);
            if (!robot.isHoldingFreight()) {
                robot.odStrafe(110, 0.8, 29, 7, 3, 1000, 0.02, false, true);
                robot.odStrafe(95, 0.8, 36, 8, 3, 1000, 0.02, false);
            }
            
            if (System.currentTimeMillis() - startTimeInMs > 22000) {
                break;
            }

            // leave warehouse
            robot.odStrafe(90, 0.8, 44, 3, 3, 1200, 0.025, false);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(90, 1, 65, 5, 3, 1200, 0.025, false);

            // score freight
            robot.motorTurnNoReset(1, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -162, robot.armTurnstile);
            robot.odStrafe(100, 1, 70, 10, 4, 1000, 0.025, false);
            robot.odTurn(140, 1, 400, 0.015, false);
            robot.intakeFlap.setPosition(0);
            robot.odStrafe(140, 1, 75, 22, 4, 1000, 0.025, false);
            robot.odStrafe(140, 0.4, 79, 29, 1.5, 1000);

            robot.odometer.odSleep(100);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1100);

            robot.motorTurnNoReset(1, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -55, robot.armTurnstile);
            robot.intakeFlap.setPosition(1);
            robot.odStrafe(140, 1, 72, 12, 4, 1000, 0.025, false);

            robot.intake.setPower(0);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30/90, robot.armHinge);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -35, robot.armTurnstile);
            robot.odTurn(90, 1, 450, 0.015, false);
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odStrafe(90, 1, 68, 2, 3, 900);
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 3 / 90, robot.armHinge);
        }

        // park
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(90, 1, 42, 5, 3, 800, 0.025, false);
        if (System.currentTimeMillis() - startTimeInMs < 28750) {
            robot.odStrafe(90, 0.8, 42, 32, 4, 1200);
        }
        if (System.currentTimeMillis() - startTimeInMs < 28750) {
            robot.odStrafe(90, 0.6, 24, 32, 4, 1200);
        }
    }
}
