package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="  Duck side    (Red or Blue)")
public class FreightFrenzy_DuckSideAuto extends FreightFrenzy_BaseAutoSetup {

    @Override
    public void initOdometryCoordinates() {
        if (this.side == Alliance.Blue) {
            robot.odometer.x = 100;
        } else {
            robot.odometer.x = -112;
        }
        robot.odometer.y = 2;
    }

    @Override
    public void runRobotAuto() {
        if (this.side == Alliance.Red) {
            this.redSideDuckAuto();
        } else {
            this.blueSideDuckAuto();
        }
    }

    public void redSideDuckAuto() {
        // TODO: finish blue side, then copy here and adjust
        robot.odStrafe(0, 0.4, -112, 24, 4);
        robot.odStrafe(0, 0.4, -100, 18, 4);
        robot.odTurn(120, 0.5, 1500);
        robot.odStrafe(120, 0.4, -120, 35, 4);
        robot.odStrafe(90, 0.4, -130, 35, 4);
        robot.odStrafe(0, 0.4, -112, 2, 4);

    }

    public void blueSideDuckAuto() {
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
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*75/90, robot.armHinge);
            robot.odStrafe(0, 1, 99, 16, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*65, robot.armTurnstile);
            robot.odStrafe(0, 0.4, 98, 32, 2, 1500);

            robot.odometer.odSleep(800);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

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
        robot.odometer.odSleep(3300);
        robot.duckSpinner.setVelocity(0);

        // pick up duck TODO: make duck-finding vision software?
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*60/90, robot.armHinge);
        robot.odStrafe(-10, 0.8, 105, 13, 4, 1600, 0.04, false);
        robot.motorTurnNoReset(0.4, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odTurn(-150, 0.8, 1100);
        robot.odStrafe(-150, 0.7, 103, 17, 4, 1200, 0.04, false);

        // TODO: use distance sensor here?
        robot.odStrafe(-145, 0.38, 121, 15, 3, 4000);

        // score duck
        robot.odStrafe(-150, 0.7, 123, 24, 4, 1500, 0.05, false);
        robot.intake.setPower(robot.INTAKE_ON_POWER/2);
        robot.odStrafe(-180, 1, 129, 34, 4, 1500, 0.04, false);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*75/90, robot.armHinge);
        robot.odStrafe(-180, 1, 130, 42, 4, 1000, 0.04, false);
        robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*-180, robot.armTurnstile);
        robot.odTurn(-90, 1, 900, 0.005, false);


        robot.odStrafe(-90, 1, 113, 48, 4, 2000, 0.04, false);
        if (this.tse_position == TSE_Position.LEFT) {
            robot.odStrafe(-90, 0.4, 105, 48, 2, 1000);
        } else {
            robot.odStrafe(-90, 0.4, 102, 48, 2, 1000);
        }
        robot.odometer.odSleep(500);
        robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
        robot.odometer.odSleep(1200);

        // park
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.odStrafe(-90, 1, 116, 53, 4, 2000, 0.04, false);
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
        robot.odTurn(-180, 1, 600, 0.008, false);
        robot.odTurn(90, 1, 800, 0.008, true);
        robot.odStrafe(90, 0.7, 133, 47, 4, 1300, 0.05, false);
        robot.odStrafe(90, 0.5, 140, 33, 3, 1300, 0.05, true);

    }
}
