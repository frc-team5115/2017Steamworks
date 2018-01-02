package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;
import com.team5115.Constants;

public class IntakeManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Intake subsystem
	 */
	
	public static final int STOP = 0;
	public static final int INTAKE = 1;
	public static final int CLIMB = 2;

	public void update() {
		switch (state) {
		case STOP:
			Roobit.intake.stop();
			break;
		case INTAKE:
			Roobit.intake.intake();
			break;
		case CLIMB:
			Roobit.intake.back();
			break;
		}
	}
}
