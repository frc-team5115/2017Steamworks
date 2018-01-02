package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;
import com.team5115.Constants;
import com.team5115.PID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlywheelManagerBangBang extends StateMachineBase {
	
	/**
	 * This state machine controls the flywheel shooter, which shoots the ball
	 */
	
	public static final int STOP = 0;
	public static final int SHOOT = 1;
	public static final int STARVE = 2;
	
	PID pidController;
	
	double speed;
	
	double kp = 0.03, ki = 0.000, kd = 0.0002;
	
	public FlywheelManagerBangBang() {
		pidController = new PID();
	}

	public void update() {
		switch (state) {
		case STOP:
			Roobit.flywheel.idle();
			break;
			
		case SHOOT:
			//double speed = pidController.getPID(Constants.OPTIMUM_FLYWHEEL_RPM, Robot.flywheel.getFlywheelRPM(), kp, ki, kd);
			if (Roobit.flywheel.getFlywheelRPM() > Constants.OPTIMUM_FLYWHEEL_RPM) {
				speed = 0;
			} else {
				speed = 1;
			}
			Roobit.flywheel.spinUp(speed);
			
			System.out.println("Flywheel Speed " + speed);
			System.out.println("Flywheel RPM " + Roobit.flywheel.getFlywheelRPM());
			
			SmartDashboard.putNumber("FLywheel RPM", Roobit.flywheel.getFlywheelRPM());
			
			break;
			
		case STARVE:
			Roobit.flywheel.back();
			break;
		}
	}
	
	public boolean atSpeed() {
		return Constants.OPTIMUM_FLYWHEEL_RPM < Roobit.flywheel.getFlywheelRPM();
	}
}
