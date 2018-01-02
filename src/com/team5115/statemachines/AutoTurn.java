package com.team5115.statemachines;

import com.team5115.ForwardProfile;
import com.team5115.PID;
import com.team5115.TurnProfile;
import com.team5115.robot.Roobit;

import edu.wpi.first.wpilibj.Timer;

public class AutoTurn extends StateMachineBase {
	
	/**
	 * This state machine is similar to AutoDrive, but it ignores the distance parameter
	 * Instead, it pivots in place rather than curving 
	 * Ideally, this would only be used in aiming, as curving while moving is more time efficient
	 */
	
	public static final int STOP = 0;
	public static final int START = 1;
	public static final int DRIVING = 2;
	
	TurnProfile tp;
	
	PID pidControllerLeft;
	PID pidControllerRight;

	double t;
	double startTime;
	double error_left;
	double error_right;
	double v_left;
	double v_right;
	double a;
	
	double kv = 0.1;
	double ka = 0;
	double kp = 0.1;
	double ki = 0.8;
	double kd = 0.001;
	
	public AutoTurn(double maxv, double maxa, double angle) {
		tp = new TurnProfile(maxv, maxa, angle);
		pidControllerLeft = new PID();
		pidControllerRight = new PID();
		
		a = angle;
		
	}
	
	public void update() {
		switch(state) {
		case START:
			startTime = Timer.getFPGATimestamp();
			setState(DRIVING);
			break;
			
		case DRIVING:
			
			t = Timer.getFPGATimestamp() - startTime;
			
			double accel_left = tp.getAcceleration(t);
			double vel_left = tp.getVelocity(t);
			double accel_right = -tp.getAcceleration(t);
			double vel_right = -tp.getVelocity(t);
			
			
			v_left = kv * vel_left + ka * accel_left + pidControllerLeft.getPID(vel_left, Roobit.drivetrain.leftSpeed(), kp, ki, kd);
			v_right = kv * vel_right + ka * accel_right + pidControllerRight.getPID(vel_right, Roobit.drivetrain.rightSpeed(), kp, ki, kd);
			
			Roobit.drivetrain.drive(v_left, v_right, 1);
			
			break;
			
		}
	}
	
	public boolean isFinished() {
		return (tp.getAcceleration(t) == 0 && tp.getVelocity(t) == 0);		//Will it work? Nobody knows...
	}

}
