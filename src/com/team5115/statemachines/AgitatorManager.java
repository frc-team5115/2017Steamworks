package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;
import com.team5115.Constants;

import edu.wpi.first.wpilibj.Timer;

public class AgitatorManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Agitator subsystem which is used to move the balls in the container around
	 */

	public static final int STOP = 0;
	public static final int FEED = 1;	// Gives balls to the shooter
	public static final int STARVE = 3;	// Keeps balls away from the shooter
	
	double startTime = 0;
	double switchTime = 0.6;
	double pauseTime = 0;

	public void update() {
		switch (state) {
		
		case STOP:
			Roobit.agitator.stop();
			break;
			
		case FEED:
			Roobit.agitator.feed();
			break;
			
		case STARVE:
			Roobit.agitator.starve();
			break;
		}
	}
}
