package com.team5115.statemachines;

import com.team5115.Constants;
import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;
import com.team5115.systems.Camera;

public class CameraManager extends StateMachineBase {
	
	/**
	 * This state machine controls the Camera subsystem, which is used to move the driver camera
	 */
	
	// These states are for the different angles the camera would be pointing at
	public static final int BOILER = 0;
	public static final int CLIMBER = 1;
	public static final int INTAKE = 2;
	
	public void update() {
		if (InputManager.getHat() == 0) {
			Roobit.camera.setAngle(Constants.BOILER_ANGLE);
		} else if (InputManager.getHat() == 270)
			Roobit.camera.setAngle(Constants.CLIMBER_ANGLE);
		else if (InputManager.getHat() == 180)
			Roobit.camera.setAngle(Constants.INTAKE_ANGLE);
	
	}
	
}
