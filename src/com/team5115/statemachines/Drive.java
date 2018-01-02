package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;
import com.team5115.Constants;
import com.team5115.PID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends StateMachineBase {
	
	/**
	 * You might be wondering why we are using State Machines instead of the built in wpilibj commands, so let me tell you a story
	 * It all started 15 years ago...
	 * Long story short, we now have state machines
	 * The state machine is how the robot class interfaces with the subsystem to control the robot
	 * The state machine can either have the states progressively being done in sequence, or it can have the driver choose which state to run at a time
	 * This is the state machine for the drivetrain
	 * You should put the inputs from InputManager in the state machine to control the actions of the subsystem
	 */
	
	AutoTurn turn180;
	
	// These are the state names which you reference when you use the setState() method instead of the actual number so the code is easier to understand
	public static final int STOP = 0;
	public static final int DRIVING = 1;

	// Define the variables for the state machine here
	PID pidControllerLeft;
	PID pidControllerRight;
	
	double speed, turn, throttle;
	double dSpeed, dTurn;
	double turnPower;
	double lastSpeed = 0, lastTurn = 0;
	
	double left_speed;
	double right_speed;
	double v_left;
	double v_right;
	
	double kf = 0.1;

	public void update() {
		switch (state) {
		
		case DRIVING:
			if (!Roobit.drivetrain.inuse) {
				// STATE 1 -- DRIVING
				speed = InputManager.getY() * Constants.TOP_SPEED * InputManager.getThrottle();
				turn = InputManager.getX() * Constants.TOP_SPEED * .5 * InputManager.getThrottle();
				//System.out.println(speed + " " + turn);
				
	
				// Turning in place and quickturn button
				if (speed == 0 || InputManager.quickTurn()) {
					turnPower = turn * Constants.QUICK_TURN_POWER;
				} else {
					// Keep in mind speed will always be in [-1, 1]
					turnPower = speed * turn;
				}
				
				left_speed = Roobit.drivetrain.direction * speed + turn;
				right_speed = Roobit.drivetrain.direction * speed - turn;
				
				if (left_speed == 0)
					v_left = 0;
				else
					v_left = kf * left_speed;
				
				if (right_speed == 0)
					v_right = 0;
				else
					v_right = kf * right_speed;
	
				Roobit.drivetrain.drive(v_left, v_right, 1);
				
//				SmartDashboard.putNumber("Left Speed", Roobit.drivetrain.leftSpeed());
//				SmartDashboard.putNumber("Right Speed", Roobit.drivetrain.rightSpeed());
			}

			break;

		}
	}
}
