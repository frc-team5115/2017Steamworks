package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;

public class ClimberManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Climber subsystem, which climbs up the rope during the endgame
	 */

	public static final int STOP = 0;
	public static final int CLIMB = 1;
	
	public void update() {
		switch(state) {
		case STOP:
			Roobit.climber.stopClimb();
			if(InputManager.climb()) {
				setState(CLIMB);
			}
			break;
			
		case CLIMB:
			Roobit.climber.climbUp();
			Roobit.fuel.setState(FuelManipulatorManager.CLIMB);
			if (InputManager.cancel() || !InputManager.climb()) {
				setState(STOP);
			}
			break;
		}
	}
}
