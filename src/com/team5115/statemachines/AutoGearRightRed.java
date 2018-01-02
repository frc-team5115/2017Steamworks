package com.team5115.statemachines;

import com.team5115.Prefs;

public class AutoGearRightRed extends StateMachineBase {
	
	/**
	 * This state machine contains the routine for the gear path from the right on the red team of the autonomous section
	 */
	
	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int AIMING = 3;
	public static final int TURNING = 4;
	public static final int DRIVING2 = 5;
	public static final int DRIVING3 = 6;
	AutoDrive firstleg;
	AutoTurn turn;
	AutoDrive secondleg;
	AimGear ag;
	AutoDrive thirdleg;
	
	public AutoGearRightRed() {
		firstleg = new AutoDrive(5, 10, -Prefs.getBoilerFirstLeg(), 0, 1.5);
		turn = new AutoTurn(2.5, 10, -Prefs.getBoilerTurn());
		secondleg = new AutoDrive(2.5, 5, -Prefs.getBoilerSecondLeg(), 0, 0);
		thirdleg = new AutoDrive(2.5, 5, -Prefs.getBoilerThirdLeg(), 0, 0);
		ag = new AimGear();
	}
	
	public void update() {
		switch (state) {
		case INIT:
			ag.setState(AimGear.START);
			firstleg.setState(AutoDrive.START);
			turn.setState(AutoTurn.START);
			secondleg.setState(AutoDrive.START);
			thirdleg.setState(AutoDrive.START);
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
			System.out.println("AIMING");
			if (ag.isFinished()) {
				ag.setState(AimGear.STOP);
				setState(DRIVING3);
			}
			break;
		case TURNING:
			turn.update();
			if (turn.isFinished()) {
				turn.setState(AutoDrive.STOP);
				setState(DRIVING2);
			}
			break;
		case DRIVING2:
			secondleg.update();
			if (secondleg.isFinished()) {
				secondleg.setState(AutoDrive.STOP);
				setState(AIMING);
			}
			break;
		case DRIVING3:
			thirdleg.update();
			if (thirdleg.isFinished()) {
				thirdleg.setState(AutoDrive.STOP);
			}
			break;
		}
	}
}