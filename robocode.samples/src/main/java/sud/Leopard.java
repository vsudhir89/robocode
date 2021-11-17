package sud;

import robocode.*;
import robocode.Robot;
import robocode.util.Utils;

import java.awt.*;

public class Leopard extends Robot {
    private int turnDirection = 1; // clockwise or counterclockwise
    private int distance = 75;

    @Override
    public void run() {
        setBodyColor(Color.BLACK);
        setGunColor(Color.ORANGE);
        setRadarColor(Color.DARK_GRAY);

        while (true) {
            turnRight(6 * turnDirection);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        if (event.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        turnRight(event.getBearing());
        ahead(event.getDistance() + 5); // want to ram him so ahead by + 5
        scan(); // want to move again
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        turnRight(Utils.normalRelativeAngleDegrees(180 - (getHeading() - event.getHeading()))); // Try to turn away from the bullet direction
        // move ahead;
        ahead(distance);
        distance = distance * -1;
        scan();
    }

    /**
     * onHitRobot:  Turn to face robot, fire hard, and ram him again!
     */
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        turnRight(e.getBearing());

        // Determine a shot that won't kill the robot...
        // We want to ram him instead for bonus points
        if (e.getEnergy() > 16) {
            fire(3);
        } else if (e.getEnergy() > 10) {
            fire(2);
        } else if (e.getEnergy() > 4) {
            fire(1);
        } else if (e.getEnergy() > 2) {
            fire(.5);
        } else if (e.getEnergy() > .4) {
            fire(.1);
        }
        ahead(40); // Ram him again!
    }

    public void onHitWall(HitWallEvent e) {
        // Move away from the wall

    }

    @Override
    public void onWin(WinEvent event) {
        for (int i = 0 ; i < 35 ; i++) { // Victory Dance!
            turnRight(60);
            turnLeft(60);
        }
    }
}
