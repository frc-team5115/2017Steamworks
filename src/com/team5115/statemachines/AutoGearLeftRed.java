package com.team5115.statemachines;

import com.team5115.Prefs;

public class AutoGearLeftRed extends StateMachineBase {
	
	/**
	 * This state machine contains the routine for the gear path from the left on the red team of the autonomous section
	 */
	
	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int AIMING = 3;
	public static final int TURNING = 4;
	public static final int DRIVING2 = 6;
	
	AutoDrive firstleg;
	AutoTurn turn;
	AimGear ag;
	AutoDrive secondleg;
	
	public AutoGearLeftRed() {
		firstleg = new AutoDrive(5, 10, -Prefs.getLoadingFirstLeg(), 0, 0);
		turn = new AutoTurn(3, 10, Prefs.getLoadingTurn());
		ag = new AimGear();
		secondleg = new AutoDrive(2.5, 5, -Prefs.getLoadingSecondLeg(), 0, 0);
	}
	
	public void update() {
		switch (state) {
		case INIT:
			ag.setState(AimGear.START);
			firstleg.setState(AutoDrive.START);
			turn.setState(AutoTurn.START);
			secondleg.setState(AutoDrive.START);
			setState(DRIVING);
			break;
			
		case DRIVING:
			firstleg.update();
			if (firstleg.isFinished()) {
				firstleg.setState(AutoDrive.STOP);
				//setState(AIMING);
				setState(TURNING);
			}
			break;
			
		case AIMING:
			ag.update();
			if (ag.isFinished()) {
				ag.setState(AimGear.STOP);
				setState(DRIVING2);
			}
				
			break;
		case TURNING:
			turn.update();
			if (turn.isFinished()) {
				turn.setState(AutoTurn.STOP);
				setState(DRIVING2);
			}
			break;
		case DRIVING2:
			secondleg.update();
			if (secondleg.isFinished()) {
				secondleg.setState(AutoDrive.STOP);
			}
		}
	}
}