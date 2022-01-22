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
        robot.intake.setPower(robot.INTAKE_ON_POWER/2);
        if (this.tse_position == TSE_Position.LEFT) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*30/90, robot.armHinge);
            robot.odStrafe(0, 1, 110, 20, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*85, robot.armTurnstile);
            robot.odStrafe(0, 1, 110, 36, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*21/90, robot.armHinge);
            robot.odStrafe(0, 0.4, 103, 43, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 117, 42, 4, 1500, 0.05, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*45/90, robot.armHinge);
            robot.odStrafe(0, 1, 94, 16, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*33, robot.armTurnstile);
            robot.odStrafe(0, 0.4, 94, 26, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 95, 16, 4, 1500, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*90, robot.armTurnstile);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*75/90, robot.armHinge);
            robot.odStrafe(0, 1, 99, 18, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*60, robot.armTurnstile);
            robot.odStrafe(0, 0.4, 98, 33, 2, 1500);

            robot.odometer.odSleep(800);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 108, 16, 4, 1500, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*90, robot.armTurnstile);

        }
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*105/90, robot.armHinge);
        if (this.tse_position == TSE_Position.LEFT) {
            robot.odStrafe(0, 1, 125, 19, 4, 3000);
        } else {
            robot.odStrafe(0, 1, 122, 9, 4, 3000);
        }
        robot.odStrafe(15, 0.4, 132, 6, 2, 1200, 0.05, true);
        robot.odometer.odSleep(500);

        // spin carousel
        robot.duckSpinner.setVelocity(-robot.DUCK_SPINNER_VELOCITY);
        robot.odometer.odSleep(3300);

        // pick up duck TODO: make duck-finding vision software
        robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*180, robot.armTurnstile);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*45/90, robot.armHinge);
        robot.odStrafe(20, 0.8, 117, 17, 4, 1300, 0.05, false);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(40, 0.7, 111, 15, 4, 1200, 0.05, false);

        // TODO: use distance sensor here?
        robot.odStrafe(40, 0.7, 113, 13, 3, 1000, 0.05, false);
        robot.odStrafe(45, 0.6, 124, 12, 3, 4000);
        robot.intake.setPower(robot.INTAKE_ON_POWER/3);

        // score duck
        robot.odStrafe(0, 1, 129, 22, 4, 2000, 0.05, false);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*75/90, robot.armHinge);
        robot.odStrafe(0, 1, 130, 39, 4, 2000, 0.05, false);
        robot.odTurn(-90, 1, 900);

        robot.odStrafe(-90, 1, 113, 46, 4, 2000, 0.05, false);
        robot.odStrafe(-90, 0.4, 101, 46, 2, 1000);
        robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
        robot.odometer.odSleep(1200);

        // park
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.odStrafe(-90, 1, 118, 53, 4, 2000, 0.05, false);
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
        robot.odTurn(90, 1, 1700);
        robot.odStrafe(90, 0.7, 134, 47, 4, 1300, 0.05, false);
        robot.odStrafe(90, 0.5, 139, 34, 3, 1300, 0.05, true);


    }
}
