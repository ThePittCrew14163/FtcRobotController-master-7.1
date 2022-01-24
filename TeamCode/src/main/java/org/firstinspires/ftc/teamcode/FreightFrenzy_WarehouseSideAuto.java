package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Warehouse side (Red or Blue)")
public class FreightFrenzy_WarehouseSideAuto extends FreightFrenzy_BaseAutoSetup {

    @Override
    public void initOdometryCoordinates() {
        if (this.side == Alliance.Blue) {
            robot.odometer.x = 54;
        } else {
            robot.odometer.x = -64.5;
        }
        robot.odometer.y = 2;
    }

    @Override
    public void runRobotAuto() {
        if (this.side == Alliance.Red) {
            this.redSideAuto();
        } else {
            this.blueSideAuto();
        }

        // score pre-loaded freight

        // loop until robot has less than 4-5 seconds {
        //     align with wall and go into warehouse
        //     dig into the freight pile until distance sensor detects freight or until ~3 seconds are up
        //     drive out and score that freight
        // }

        // park

    }

    public void redSideAuto() {
        // TODO: finish blue side, then copy here and adjust
    }

    public void blueSideAuto() {
        // drive around TSE and score pre-loaded freight
        robot.intake.setPower(robot.INTAKE_ON_POWER / 2);
        if (this.tse_position == TSE_Position.LEFT) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 21 / 90, robot.armHinge);
            robot.odStrafe(-40, 1, 60, 19, 4, 1200, 0.05, false);
            robot.odStrafe(-45, 0.4, 64, 29, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 60, 19, 4, 1500, 0.05, false);

        } else if (this.tse_position == TSE_Position.CENTER) { // bottom tray
            robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 45 / 90, robot.armHinge);
            robot.odStrafe(-30, 1, 63, 16, 4, 1200, 0.05, false);
            robot.odStrafe(-30, 0.4, 66, 26, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.odStrafe(0, 1, 60, 19, 4, 1500, 0.05, false);

        } else { // top tray
            robot.motorTurnNoReset(0.8, robot.ARM_HINGE_UP_CLICKS * 75 / 90, robot.armHinge);
            robot.odStrafe(20, 1, 67, 12, 4, 1200, 0.05, false);
            robot.motorTurnNoReset(0.6, (int) robot.ARM_TURNSTILE_CLICKS_PER_DEG * -135, robot.armTurnstile);
            robot.odTurn(110, 1, 1000);
            robot.odStrafe(110, 0.7, 76, 23, 4, 1000, 0.05, false);
            robot.odStrafe(110, 0.4, 77, 28, 2, 1500);

            robot.odometer.odSleep(300);
            robot.intake.setPower(robot.INTAKE_OUTPUT_POWER);
            robot.odometer.odSleep(1200);

            robot.motorTurnNoReset(0.6, 0, robot.armTurnstile);
            robot.odStrafe(110, 1, 75, 15, 4, 1500, 0.05, false);

        }
        robot.intake.setPower(0);
        robot.motorTurnNoReset(0.7, robot.ARM_HINGE_UP_CLICKS * 3 / 90, robot.armHinge);
        robot.odTurn(90, 1, 900);
        robot.odStrafe(90, 1, 68, 3, 3, 1500);

        robot.intake.setPower(robot.INTAKE_ON_POWER);
        robot.odStrafe(90, 1, 42, 5, 3, 1400);

        //while time left > 1 cycle time, cycle
    }
}
