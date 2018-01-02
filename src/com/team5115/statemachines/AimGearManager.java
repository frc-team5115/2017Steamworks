package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;

public class AimGearManager extends StateMachineBase {
	
	public static final int DRIVING = 1;
	public static final int AIMING = 2;
	
	AimGear ag;
	
	public AimGearManager() {
		ag = new AimGear();
	}
	
	public void update() {
		switch (state) {
		case DRIVING:
			if (InputManager.aimGear()) {
				ag.setState(AimGear.START);
				Roobit.drivetrain.inuse = true;
				setState(AIMING);
			}
			
			break;
			
		case AIMING:
			ag.update();
			if (ag.isFinished()) {
				ag.setState(AimGear.STOP);
				Roobit.drivetrain.inuse = false;
				setState(DRIVING);
			}
		}
	}

}
