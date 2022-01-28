package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="REMOTE (Duck side BLUE)")
class FreightFrenzy_REMOTE_Auto extends FreightFrenzy_BaseAutoSetup {

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
            robot.odStrafe(0, 1, 110, 20, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*88, robot.armTurnstile);
            robot.odStrafe(0, 1, 110, 36, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*21/90, robot.armHinge);
            robot.odStrafe(0, 0.4, 103, 43, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/4);

            robot.odStrafe(0, 1, 117, 42, 4, 1500, 0.05, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*49/90, robot.armHinge);
            robot.odStrafe(0, 1, 94, 16, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*34, robot.armTurnstile);
            robot.odStrafe(0, 0.4, 94, 25, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);
            robot.intake.setPower(robot.INTAKE_ON_POWER/2);
            robot.odometer.odSleep(300);

            robot.odStrafe(0, 1, 95, 16, 4, 1500, 0.05, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*74/90, robot.armHinge);
            robot.odStrafe(0, 1, 99, 16, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*62, robot.armTurnstile);
            robot.odStrafe(0, 0.4, 98, 32, 2, 1500);

            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*67/90, robot.armHinge);
            robot.odometer.odSleep(800);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER*0.8);
            robot.odometer.odSleep(1200);
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*80/90, robot.armHinge);
            robot.odometer.odSleep(800);

            robot.odStrafe(0, 1, 108, 16, 4, 1500, 0.05, false);

        }
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.55, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*10, robot.armTurnstile);
        robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*105/90, robot.armHinge);
        if (this.tse_position == TSE_Position.LEFT) {
            robot.odStrafe(0, 1, 124, 19, 4, 3000);
        } else {
            robot.odStrafe(0, 1, 121, 9, 4, 3000);
        }
        robot.odStrafe(15, 0.45, 128, 6, 3, 1000);
        robot.odStrafe(15, 0.35, 130, 5, 1.5, 1000, 0.05, true);
        robot.odometer.odSleep(400);

        // spin carousel
        robot.duckSpinner.setVelocity(-robot.DUCK_SPINNER_VELOCITY);
        robot.odometer.odSleep(3000);
        robot.duckSpinner.setVelocity(0);

        // pick up duck TODO: make duck-finding vision software?
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*60/90, robot.armHinge);
        robot.odStrafe(-10, 0.8, 102, 13, 4, 1600, 0.04, false);
        robot.motorTurnNoReset(0.4, robot.ARM_HINGE_UP_CLICKS*6/90, robot.armHinge);
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odTurn(-150, 0.8, 1100);
        robot.odStrafe(-150, 0.7, 100, 17, 4, 1200, 0.04, false);

        // TODO: use distance sensor here?
        robot.odStrafe(-145, 0.38, 121, 16, 3, 4000);

        // score duck
        robot.odStrafe(-150, 0.8, 108, 19, 4, 1500, 0.05, false);
        robot.intake.setPower(robot.INTAKE_ON_POWER/2);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*72/90, robot.armHinge);
        robot.odStrafe(-180, 1, 98, 23, 4, 1000, 0.04, false);
        robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*-180, robot.armTurnstile);
        robot.odStrafe(-180, 1, 87, 26, 4, 1500, 0.04, false);
        robot.odStrafe(-180, 0.4, 84, 28, 4, 1200);

        robot.odometer.odSleep(600);
        robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
        robot.odometer.odSleep(1200);

        // park
        robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*-55, robot.armTurnstile);
        robot.odStrafe(125, 1, 76, 12, 4, 1000, 0.05, false);

        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30/90, robot.armHinge);
        robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -35, robot.armTurnstile);
        robot.odTurn(90, 1, 400, 0.01, false);
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.odStrafe(90, 1, 68, 2, 3, 1200);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 5 / 90, robot.armHinge);

        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(90, 1, 42, 5, 3, 800, 0.05, false);

    }
}