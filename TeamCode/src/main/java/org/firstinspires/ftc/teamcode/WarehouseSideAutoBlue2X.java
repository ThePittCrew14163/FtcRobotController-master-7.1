package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * After scoring the preload in the alliance hub,
 * this auto collects other team's preload, which should be left against the wall behind their robot,
 * and it is assumed that they've parked in the loading zone.
 */
@Autonomous(name="2X Duck side BLUE", group = "pick up alliance partner's preload")
class WarehouseSideAutoBlue2X extends WarehouseSideAutoBlue {
    @Override
    public void AfterScorePreload() {

        if (tse_position == TSE_Position.RIGHT) { // arm is way to the side and heading is 140deg
            robot.motorTurnNoReset(0.5, robot.ARM_HINGE_UP_CLICKS * 30 / 90, robot.armHinge);
            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odTurn(0, 1, 900);
        }
        robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 3 / 90, robot.armHinge);

        robot.odStrafe(0, 0.6, 68, 17, 4, 700, 0.02, false);
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odTurn(-150, 1, 800);
        robot.odStrafe(-150, 0.8, 67, 15.5, 4, 1100, 0.02, false);
        robot.odStrafe(-145, 0.47, 115, 16, 4, 5000, 0.02, false);
        robot.odTurn(-70, 0.75, 1200);

        // score 2nd preload
        if (tse_position == TSE_Position.LEFT) {  // bottom tray
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 22 / 90, robot.armHinge);
            robot.odStrafe(-90, 1, 78, 18, 4, 1100, 0.02, true);
            robot.intake.setPower(robot.INTAKE_ON_POWER / 3);
            robot.intakeFlap.setPosition(0);
            robot.odTurn(0, 1, 450);
            robot.odStrafe(0, 0.45, 79, 21, 2, 1000);

            robot.odometer.odSleep(200);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER * 1.3);
            robot.odometer.odSleep(1200);

        } else if (tse_position == TSE_Position.CENTER) {  // middle tray
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 49 / 90, robot.armHinge);
            robot.odStrafe(-90, 1, 78, 18, 4, 1100, 0.02, true);
            robot.intake.setPower(robot.INTAKE_ON_POWER / 3);
            robot.intakeFlap.setPosition(0);
            robot.odTurn(0, 1, 450);
            robot.odStrafe(0, 0.45, 79, 22, 2, 1000);

            robot.odometer.odSleep(200);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER * 1.1);
            robot.odometer.odSleep(1200);

        } else { // top tray
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 72 / 90, robot.armHinge);
            robot.odStrafe(-90, 1, 78, 18, 4, 1100, 0.02, true);
            robot.intake.setPower(robot.INTAKE_ON_POWER / 3);
            robot.intakeFlap.setPosition(0);
            robot.odTurn(0, 1, 450);
            robot.odStrafe(0, 0.45, 79, 24, 2, 1000);
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 68 / 90, robot.armHinge);

            robot.odometer.odSleep(200);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER * 1.1);
            robot.odometer.odSleep(1200);
            robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 72 / 90, robot.armHinge);
        }
        robot.intake.setPower(robot.INTAKE_ON_POWER/3.5);
        robot.intakeFlap.setPosition(1);
    }
}
