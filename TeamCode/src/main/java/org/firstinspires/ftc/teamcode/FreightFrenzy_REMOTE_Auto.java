package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="REMOTE (Duck side BLUE)")
public class FreightFrenzy_REMOTE_Auto extends FreightFrenzy_BaseAutoSetup {

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

        } else if (this.tse_position == TSE_Position.CENTER) { // middle tray
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
            robot.odStrafe(0, 1, 124, 22, 4, 3000);
        } else {
            robot.odStrafe(0, 1, 121, 9, 4, 3000);
        }
        robot.odStrafe(15, 0.42, 128, 6, 3, 1000, 0.04, true);
        robot.odStrafe(15, 0.35, 130, 5, 1.5, 1000, 0.04, true);
        robot.odometer.odSleep(400);

        // spin carousel
        robot.duckSpinner.setVelocity(-robot.DUCK_SPINNER_VELOCITY);
        robot.odometer.odSleep(2800);
        robot.duckSpinner.setVelocity(0);

        // pick up duck TODO: make duck-finding vision software?
        robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
        robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS*60/90, robot.armHinge);
        robot.odStrafe(-10, 0.8, 108, 13, 4, 1600, 0.04, false);
        robot.odometer.odSleep(200);
        robot.motorTurnNoReset(0.4, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
        robot.odometer.odSleep(200);
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odTurn(-100, 1, 300);
        robot.odTurn(-150, 0.8, 500);
        robot.odStrafe(-150, 0.7, 107, 17, 4, 1200, 0.04, false);

        // TODO: use distance sensor here?
        robot.odStrafe(-145, 0.4, 121, 16, 3, 3500);

        // score duck
        robot.odStrafe(-110, 1, 111, 19, 4, 1500, 0.05, false);
        robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS*73/90, robot.armHinge);
        robot.odStrafe(-90, 1, 86, 22, 4, 1500, 0.05, false);
        robot.odTurn(0, 1, 400);
        robot.intake.setPower(robot.INTAKE_ON_POWER/2);
        robot.odStrafe(0, 1, 82, 18, 4, 1000, 0.04, false);
        robot.odStrafe(0, 0.4, 82, 24, 4, 1000);

        robot.odometer.odSleep(300);
        robot.intake.setPower(robot.INTAKE_OUTPUT_POWER*0.9);
        robot.odometer.odSleep(1200);

        // grab 2nd duck and score it
        if (this.tse_position == TSE_Position.LEFT) { // bottom tray
            robot.odStrafe(70, 1, 81, 17, 4, 1200, 0.03, false);
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(70, 0.6, 65, 39, 3, 1500);
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS*73/90, robot.armHinge);
            robot.odStrafe(70, 1, 86, 22, 4, 1500, 0.03, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // middle tray
            robot.odStrafe(65, 1, 82, 17, 4, 1200, 0.03, false);
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(65, 0.6, 73, 39, 3, 1500);
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS*73/90, robot.armHinge);
            robot.odStrafe(65, 1, 86, 22, 4, 1500, 0.03, false);

        } else { // top tray
            robot.odStrafe(60, 1, 85, 17, 4, 1200, 0.03, false);
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
            robot.intake.setPower(robot.INTAKE_ON_POWER);
            robot.odStrafe(60, 0.6, 80, 38, 3, 1500);
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS*73/90, robot.armHinge);
            robot.odStrafe(60, 1, 86, 22, 4, 1500, 0.03, false);

        }
        // score 2nd duck
        robot.intake.setPower(robot.INTAKE_ON_POWER/2);
        robot.odTurn(0, 1, 450);
        robot.odStrafe(0, 0.45, 82, 24, 4, 1000);

        robot.odometer.odSleep(300);
        robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
        robot.odometer.odSleep(1200);
        robot.intake.setPower(robot.INTAKE_ON_POWER/4);

        // park
        robot.odStrafe(0, 1, 78, 15, 5, 1000, 0.03, false);
        robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*5/90, robot.armHinge);
        robot.odStrafe(0, 1, 77, 8, 4, 1000, 0.03, false);
        robot.odTurn(90, 1, 500);
        robot.odStrafe(90, 1, 67, 3, 4, 1000);

        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(90, 1, 42, 5, 4, 2000);
        robot.odTurn(90, 1, 700);

    }
}
