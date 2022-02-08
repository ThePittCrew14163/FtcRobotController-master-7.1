package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Duck side BLUE")
public class FreightFrenzy_DuckSideAutoBlue extends FreightFrenzy_BaseAutoSetup {

    @Override
    public void initOdometryCoordinates() {
        robot.odometer.x = 100;
        robot.odometer.y = 2;
    }

    @Override
    public void runRobotAuto() {
        // drive around TSE and score pre-loaded freight
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        if (this.tse_position == TSE_Position.LEFT) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*30/90, robot.armHinge);
            robot.odStrafe(0, 1, 110, 20, 4, 1200, 0.025, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*88, robot.armTurnstile);
            robot.odStrafe(0, 1, 110, 36, 4, 1200, 0.025, false);
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*21/90, robot.armHinge);
            robot.odStrafe(0, 0.4, 103, 43, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/4);

            robot.odStrafe(0, 1, 117, 42, 4, 1500, 0.025, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*49/90, robot.armHinge);
            robot.odStrafe(0, 1, 94, 16, 4, 1200, 0.025, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*34, robot.armTurnstile);
            robot.odStrafe(0, 0.4, 94, 25, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/2);
            robot.odometer.odSleep(300);

            robot.odStrafe(0, 1, 95, 18, 4, 1500, 0.025, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*74/90, robot.armHinge);
            robot.odStrafe(0, 1, 99, 16, 4, 1200, 0.025, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*62, robot.armTurnstile);
            robot.intakeFlap.setPosition(0);
            robot.odStrafe(0, 0.4, 98, 32, 2, 1500);

            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*67/90, robot.armHinge);
            robot.odometer.odSleep(800);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*80/90, robot.armHinge);
            robot.odometer.odSleep(800);

            robot.odStrafe(0, 1, 108, 16, 4, 1500, 0.025, false);
            robot.intakeFlap.setPosition(1);

        }
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.55, 0, robot.armTurnstile);
        robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*105/90, robot.armHinge);
        if (this.tse_position == TSE_Position.LEFT) {
            robot.odStrafe(0, 1, 122, 21, 4, 3000);
            robot.odStrafe(15, 0.45, 126, 13, 3, 1000, 0.015, true);
            robot.odometer.odSleep(50);
        } else {
            robot.odStrafe(0, 1, 121, 9, 4, 3000);
            robot.odStrafe(15, 0.42, 126, 6, 3, 1000, 0.02, true);
        }
        robot.odStrafe(15, 0.35, 130, 5, 1.5, 1000, 0.015, true);
        robot.odometer.odSleep(100);

        // spin carousel
        robot.duckSpinner.setVelocity(-robot.DUCK_SPINNER_VELOCITY*0.5);
        robot.odometer.odSleep(3200);
        robot.duckSpinner.setVelocity(0);

        // pick up duck TODO: make duck-finding vision software?
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*35/90, robot.armHinge);
        robot.odStrafe(-10, 1, 115, 8, 4, 1600, 0.04, false);
        robot.motorTurnNoReset(0.6, 0, robot.armHinge);
        robot.odometer.odSleep(500);
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odTurn(-100, 1, 300);
        robot.odTurn(-150, 0.8, 500);
        robot.odStrafe(-150, 0.8, 111, 15.5, 4, 1200, 0.02, false);

        // TODO: use distance sensor here?
        robot.odStrafe(-145, 0.38, 121, 16, 3, 4000);

        // score duck
        robot.odStrafe(-150, 0.7, 123, 24, 4, 1500, 0.025, false);
        robot.odStrafe(-180, 1, 129, 34, 4, 1500, 0.02, false);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*75/90, robot.armHinge);
        robot.intakeFlap.setPosition(0);
        robot.odStrafe(-180, 1, 130, 42, 4, 1000, 0.02, false);
        robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*-180, robot.armTurnstile);
        robot.odTurn(-90, 1, 900, 0.005, false);

        robot.intake.setPower(robot.INTAKE_ON_POWER/2);
        robot.odStrafe(-90, 1, 113, 48, 4, 2000, 0.02, false);
        if (this.tse_position == TSE_Position.LEFT) {
            robot.odStrafe(-90, 0.4, 105, 48, 2, 1000);
        } else {
            robot.odStrafe(-90, 0.4, 102, 48, 2, 1000);
        }
        robot.odometer.odSleep(500);
        robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
        robot.odometer.odSleep(1200);

        // park
        robot.motorTurnNoReset(1, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*-60, robot.armTurnstile);
        robot.intakeFlap.setPosition(1);
        robot.odStrafe(-90, 1, 114, 51, 4, 2000, 0.02, false);
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*45/90, robot.armHinge);
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.odTurn(-180, 1, 600, 0.008, false);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*3/90, robot.armHinge);
        robot.odTurn(90, 1, 800, 0.008, true);
        robot.odStrafe(90, 0.7, 132, 40, 4, 1300, 0.025, false);
        robot.odStrafe(90, 0.5, 140, 32, 3, 1300, 0.03, true);

    }
}
