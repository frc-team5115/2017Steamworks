package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;

public class SwitchDirection extends StateMachineBase {
	
	/**
	 * All that this state machine does is switches the direction that the robot is being driven in
	 */
	
	public void update() {
		switch(state) {
		case 0:
			if (InputManager.switchDirection()) {
				Roobit.drivetrain.direction *= -1;
				setState(1);
			}
			break;
		case 1:
			if (!InputManager.switchDirection()) {
				setState(0);
			}
		}
	}

}
