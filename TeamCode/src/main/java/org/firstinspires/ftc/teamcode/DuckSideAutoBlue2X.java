package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * After scoring the preload in the alliance hub and delivering the duck,
 * this auto collects other team's preload, which should be left against the wall behind their robot,
 * and it is assumed that they've parked well in the warehouse so we can park there too.
 */
@Autonomous(name="2X Duck side BLUE", group = "pick up alliance partner's preload")
public class DuckSideAutoBlue2X extends DuckSideAutoBlue {
    @Override
    public void AfterScorePreloadAndDeliver() {

        // collect other team's preload, which should be left against the wall behind their robot
        robot.odStrafe(23, 1, 93, 8, 4, 1000, 0.03, false);
        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odTurn(155, 1, 800);
        robot.odStrafe(155, 0.8, 91, 15.5, 4, 1100, 0.02, false);
        robot.odStrafe(155, 0.48, 60, 17, 4, 5000, 0.02, false);
        robot.odTurn(70, 0.75, 1200);

        // score 2nd preload (doesn't get bonus but does earn 6 points and then another 6 in teleOp)
        robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 72 / 90, robot.armHinge);
        robot.odStrafe(90, 1, 84, 18, 4, 1100, 0.02, true);
        robot.intake.setPower(robot.INTAKE_ON_POWER / 3);
        robot.intakeFlap.setPosition(0);
        robot.odTurn(0, 1, 450);
        robot.odStrafe(0, 0.45, 82, 23, 1.5, 1000);
        robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 68 / 90, robot.armHinge);

        robot.odometer.odSleep(200);
        robot.intake.setPower(robot.INTAKE_OUTPUT_POWER * 1.1);
        robot.odometer.odSleep(1200);
        robot.motorTurnNoReset(1, robot.ARM_HINGE_UP_CLICKS * 72 / 90, robot.armHinge);

        robot.intake.setPower(robot.INTAKE_ON_POWER/3.5);
        robot.intakeFlap.setPosition(1);

        // park
        robot.odStrafe(0, 1, 78, 15, 5, 1000, 0.03, false);
        robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS*7/90, robot.armHinge);
        robot.odStrafe(0, 0.8, 77, 10, 4, 1000, 0.03, true);
        robot.odTurn(90, 1, 450, 0.008, false);
        robot.odStrafe(95, 1, 67, 3, 4, 1000);

        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(92, 1, 45, 3, 6, 2000, 0.03, false);
        robot.odStrafe(92, 0.5, 39, 3, 3, 2000);
        robot.odTurn(90, 1, 700);
    }
}
