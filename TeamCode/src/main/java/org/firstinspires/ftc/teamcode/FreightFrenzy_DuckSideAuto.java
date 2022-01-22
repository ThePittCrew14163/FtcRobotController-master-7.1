package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="  Duck side    (Red or Blue)")
public class FreightFrenzy_DuckSideAuto extends FreightFrenzy_BaseAutoSetup {

    @Override
    public void initOdometryCoordinates() {
        if (this.side == Alliance.Blue) {
            robot.odometer.x = 100;
        } else {
            robot.odometer.x = -112.5;
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

    }

    public void blueSideDuckAuto() {
        // drive around TSE and score pre-loaded freight
        robot.intake.setPower(robot.INTAKE_ON_POWER/2);
        if (this.tse_position == TSE_Position.LEFT) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*30/90, robot.armHinge);
            robot.odStrafe(0, 1, 110, 22, 6, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*90, robot.armTurnstile);
            robot.odStrafe(0, 1, 110, 43, 6, 1200, 0.05, false);
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*21/90, robot.armHinge);
            robot.odStrafe(0, 0.4, 104, 43, 3, 1500);

            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 117, 42, 6, 1500, 0.05, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*45/90, robot.armHinge);
            robot.odStrafe(0, 1, 94, 22, 6, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*33, robot.armTurnstile);
            robot.odStrafe(0, 0.3, 94, 26, 3, 1500);

            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 95, 16, 6, 1500, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*90, robot.armTurnstile);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*80/90, robot.armHinge);
            robot.odStrafe(0, 1, 99, 22, 6, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*45, robot.armTurnstile);
            robot.odStrafe(0, 0.3, 98, 30, 3, 1500);

            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 108, 16, 6, 1500, 0.05, false);
            robot.motorTurnNoReset(0.6, (int)robot.ARM_TURNSTILE_CLICKS_PER_DEG*90, robot.armTurnstile);

        }
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*105/90, robot.armHinge);
        robot.odStrafe(15, 1, 130, 6, 4, 5000);
        robot.odometer.odSleep(500);

        // spin carousel
        robot.duckSpinner.setVelocity(-robot.DUCK_SPINNER_VELOCITY);
        robot.odometer.odSleep(3000);

        // pick up duck TODO: make duck-finding vision software

        // score duck

        // park

    }
}
