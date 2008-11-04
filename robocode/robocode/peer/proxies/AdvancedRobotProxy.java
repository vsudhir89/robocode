/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *     Flemming N. Larsen
 *     - Added getPaintEvents()
 *******************************************************************************/
package robocode.peer.proxies;


import robocode.*;
import robocode.manager.HostManager;
import robocode.peer.RobotStatics;
import robocode.peer.IRobotPeer;
import robocode.peer.robot.RobotClassManager;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;

import java.io.File;
import java.util.List;


/**
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 */
public class AdvancedRobotProxy extends StandardRobotProxy implements IAdvancedRobotPeer {

	public AdvancedRobotProxy(RobotClassManager robotClassManager, HostManager hostManager, IRobotPeer peer, RobotStatics statics) {
		super(robotClassManager, hostManager, peer, statics);
	}

	public boolean isAdjustGunForBodyTurn() {
		getCall();
		return commands.isAdjustGunForBodyTurn();
	}

	public boolean isAdjustRadarForGunTurn() {
		getCall();
		return commands.isAdjustRadarForGunTurn();
	}

	public boolean isAdjustRadarForBodyTurn() {
		getCall();
		return commands.isAdjustRadarForBodyTurn();
	}

	// asynchronous actions
	public void setResume() {
		setCall();
		setResumeImpl();
	}

	public void setStop(boolean overwrite) {
		setCall();
		setStopImpl(overwrite);
	}

	public void setMove(double distance) {
		setCall();
		setMoveImpl(distance);
	}

	public void setTurnBody(double radians) {
		setCall();
		setTurnBodyImpl(radians);
	}

	public void setTurnGun(double radians) {
		setCall();
		setTurnGunImpl(radians);
	}

	public void setTurnRadar(double radians) {
		setCall();
		setTurnRadarImpl(radians);
	}

	// blocking actions
	public void waitFor(Condition condition) {
		waitCondition = condition;
		do {
			execute(); // Always tick at least once
		} while (!condition.test());

		waitCondition = null;
	}

	// fast setters
	public void setMaxTurnRate(double newTurnRate) {
		setCall();
		if (Double.isNaN(newTurnRate)) {
			println("You cannot setMaxTurnRate to: " + newTurnRate);
			return;
		}
		commands.setMaxTurnRate(newTurnRate);
	}

	public void setMaxVelocity(double newVelocity) {
		setCall();
		if (Double.isNaN(newVelocity)) {
			println("You cannot setMaxVelocity to: " + newVelocity);
			return;
		}
		commands.setMaxVelocity(newVelocity);
	}

	// events manipulation
	public void setInterruptible(boolean interruptable) {
		setCall();
		eventManager.setInterruptible(eventManager.getCurrentTopEventPriority(), interruptable);
	}

	public void setEventPriority(String eventClass, int priority) {
		setCall();
		eventManager.setEventPriority(eventClass, priority);
	}

	public int getEventPriority(String eventClass) {
		getCall();
		return eventManager.getEventPriority(eventClass);
	}

	public void removeCustomEvent(Condition condition) {
		setCall();
		eventManager.removeCustomEvent(condition);
	}

	public void addCustomEvent(Condition condition) {
		setCall();
		eventManager.addCustomEvent(condition);
	}

	public void clearAllEvents() {
		setCall();
		eventManager.clearAllEvents(false);
	}

	public List<Event> getAllEvents() {
		getCall();
		return eventManager.getAllEvents();
	}

	public List<StatusEvent> getStatusEvents() {
		getCall();
		return eventManager.getStatusEvents();
	}

	public List<BulletMissedEvent> getBulletMissedEvents() {
		getCall();
		return eventManager.getBulletMissedEvents();
	}

	public List<BulletHitBulletEvent> getBulletHitBulletEvents() {
		getCall();
		return eventManager.getBulletHitBulletEvents();
	}

	public List<BulletHitEvent> getBulletHitEvents() {
		getCall();
		return eventManager.getBulletHitEvents();
	}

	public List<HitByBulletEvent> getHitByBulletEvents() {
		getCall();
		return eventManager.getHitByBulletEvents();
	}

	public List<HitRobotEvent> getHitRobotEvents() {
		getCall();
		return eventManager.getHitRobotEvents();
	}

	public List<HitWallEvent> getHitWallEvents() {
		getCall();
		return eventManager.getHitWallEvents();
	}

	public List<RobotDeathEvent> getRobotDeathEvents() {
		getCall();
		return eventManager.getRobotDeathEvents();
	}

	public List<ScannedRobotEvent> getScannedRobotEvents() {
		getCall();
		return eventManager.getScannedRobotEvents();
	}

	// data
	public File getDataDirectory() {
		getCall();
		commands.setIORobot();
		return robotFileSystemManager.getWritableDirectory();
	}

	public File getDataFile(String filename) {
		getCall();
		commands.setIORobot();
		return new File(robotFileSystemManager.getWritableDirectory(), filename);
	}

	public long getDataQuotaAvailable() {
		getCall();
		return robotFileSystemManager.getMaxQuota() - robotFileSystemManager.getQuotaUsed();
	}
}
