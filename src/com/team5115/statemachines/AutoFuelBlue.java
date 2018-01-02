package com.team5115.statemachines;

import com.team5115.Prefs;
import com.team5115.robot.Roobit;

import edu.wpi.first.wpilibj.Timer;

public class AutoFuelBlue extends StateMachineBase {
	
	/**
	 * This state machine contains the routine for the fuel path of the autonomous section
	 */
	
	public static final int INIT = 1;
	public static final int DRIVING = 2;
	public static final int TURNING = 3;
	public static final int DRIVING2 = 4;
	public static final int AIMING = 5;
	public static final int SHOOTING = 6;
	
	AutoDrive firstleg;
	AutoDrive secondleg;
	AutoTurn turn;
	AimFuel af;
	
	double startTime = 0;
	
	public AutoFuelBlue() {
		System.out.println("Making afb");
		firstleg = new AutoDrive(5, 10, -Prefs.getFuelFirstLeg(), 0, 0);
		secondleg = new AutoDrive(2.5, 5, Prefs.getFuelSecondLeg(), 0, 0);
		turn = new AutoTurn(2.5, 10, Prefs.getFuelTurn());
		af = new AimFuel();
	}

	
	public void update() {
		System.out.println(Timer.getFPGATimestamp() - startTime);
		if(Timer.getFPGATimestamp() - startTime >= 10 && startTime != 0){
			setState(SHOOTING);
		}
		switch (state) {
		case INIT:
			startTime = Timer.getFPGATimestamp();
			
			af.setState(AimFuel.START);
			firstleg.setState(AutoDrive.START);
			secondleg.setState(AutoDrive.START);
			turn.setState(AutoTurn.START);
			setState(DRIVING);
			break;
		case DRIVING:
			firstleg.update();
			System.out.println("driving1");
			if (firstleg.isFinished()) {
				firstleg.setState(AutoDrive.STOP);
				setState(TURNING);
				//setState(SHOOTING);
			}
			break;
		case TURNING:
			turn.update();
			System.out.println("turning");
			if (turn.isFinished()){
				turn.setState(AutoTurn.STOP);
				setState(AIMING);
			}
			break;
		case DRIVING2:
			secondleg.update();
			System.out.println("driving2");
			if (secondleg.isFinished()) {
				secondleg.setState(AutoDrive.START);
				setState(AIMING);
			}
			break;
		case AIMING:
			if (af.aimed)
				setState(SHOOTING);
			else {
				af.update();
				System.out.println("aiming");
			}
			break;
		case SHOOTING:
			Roobit.fuel.setState(FuelManipulatorManager.SHOOT);
			System.out.println("Shooting");
			break;
		}
	}

}
