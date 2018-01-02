package com.team5115.statemachines;

import com.team5115.Prefs;

public class AutoGearCenter extends StateMachineBase {
	
	/**
	 * This state machine contains the routine for the gear path from the center of the autonomous section
	 */
	
	public static final int STOP = 0;
	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int AIMING = 3;
	public static final int DRIVING2 = 4;
	
	AutoDrive firstleg;
	AutoDrive secondleg;
	AimGear ag;
	
	public AutoGearCenter() {
		firstleg = new AutoDrive(3, 5, -Prefs.getCenterFirstLeg(), 0, 0);
		secondleg = new AutoDrive(2.5, 5, -6, 0, 0);
		ag = new AimGear();
	}
	
	public void update() {
		switch (state) {
		case INIT:
			ag.setState(AimGear.START);
			firstleg.setState(AutoDrive.START);
			secondleg.setState(AutoDrive.START);
			setState(DRIVING);
			break;
			
		case DRIVING:
			firstleg.update();
			System.out.println("DRIVING");
			if (firstleg.isFinished()) {
				firstleg.setState(AutoDrive.STOP);
				setState(DRIVING2);
			}
			break;
			
		case AIMING:
			ag.update();
			if (ag.isFinished()) {
				ag.setState(AutoDrive.STOP);
				setState(DRIVING2);
			}
			break;
		
		case DRIVING2:
			secondleg.update();
			System.out.println("DRIVING2");
			if (secondleg.isFinished()) {
				secondleg.setState(AutoDrive.STOP);
			}
			break;
			
		}
	}
}
