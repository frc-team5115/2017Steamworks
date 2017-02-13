package com.team5115.systems;

import com.ctre.CANTalon;
//import com.kauailabs.navx.frc.AHRS;
import com.team5115.Constants;


import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
	
	/**
	 * Here, in a subsystem class, we run one of the systems of the robot, in this case the drivetrain
	 * All of the basic functions of the drivetrain are put here
	 * The state machine for driving controls the subsystem to produce actions
	 */

	public boolean inuse;
	
	// define objects
	CANTalon frontleft;
	CANTalon frontright;
	CANTalon backleft;
	CANTalon backright;
//	AHRS navx;
	
	// define variables
	public double lastLeftSpeed = 0;
	public double lastRightSpeed = 0;
	
	public int direction;
	
	// Runs in robotInit() when the robot is turned on
	public DriveTrain() {
	// This is a constructor class, it constructs all of the objects necessary for using the drivetrain
		frontleft = new CANTalon(Constants.FRONT_LEFT_MOTOR_ID);
		frontright = new CANTalon(Constants.FRONT_RIGHT_MOTOR_ID);
		backleft = new CANTalon(Constants.BACK_LEFT_MOTOR_ID);
		backright = new CANTalon(Constants.BACK_RIGHT_MOTOR_ID);
		backleft.changeControlMode(CANTalon.TalonControlMode.Follower);
		backright.changeControlMode(CANTalon.TalonControlMode.Follower);
//		navx = new AHRS(SerialPort.Port.kMXP);
		frontleft.setInverted(true);
		backleft.setInverted(true);
		backleft.set(frontleft.getDeviceID());
		backright.set(frontright.getDeviceID());
		direction = 1;
	}
	
	/**
	 * All these other methods are used by the state machines to run the drivetrain
	 */
	
	public void drive(double leftSpeed, double rightSpeed, double throttle) {
		if (leftSpeed > 1) {
			rightSpeed /= leftSpeed;
			leftSpeed = 1;
		}
		if (rightSpeed > 1) {
			leftSpeed /= rightSpeed;
			rightSpeed = 1;
		}
		
		frontleft.set(-direction * leftSpeed);
		frontright.set(-direction * rightSpeed);

	}
	
	// These methods are called getters, as they get values from the drivetrain
	public double leftDist() {
		double leftDist = backleft.getPosition() * direction;
		return leftDist / 1440 * 7.5 * Math.PI / 12;
	}
	    
	public double rightDist() {
		double rightDist = -backright.getPosition() * direction;
		return rightDist / 1440 * 7.5 * Math.PI / 12;
	}
	    
	public double distanceTraveled() {
		return (leftDist() + rightDist()) / 2;
	}
	    
	public double leftSpeed() {
		double leftspeed = backleft.getSpeed();
	    return ((leftspeed * 7.5 * Math.PI * 10) / (1440 * 12));
	}
	 
	public double rightSpeed() {
		double rightspeed = -backright.getSpeed();
	    return ((rightspeed * 7.5 * Math.PI * 10) / (1440 * 12));
	}
	 
	public double averageSpeed() {
		return (frontright.getSpeed() + frontleft.getSpeed()) / 2;
	}
	 
	public double leftAcceleration() {
		double acceleration = (leftSpeed() - lastLeftSpeed) / Constants.DELAY;
		lastLeftSpeed = leftSpeed();
		return acceleration;
			
	}
		 
	public double rightAcceleration() {
		double acceleration = (rightSpeed() - lastRightSpeed) / Constants.DELAY;
		lastRightSpeed = leftSpeed();
		return acceleration;
	}	 
	
	// This method resets the values given by the encoders to a default of 0
	public void resetEncoders() {
		backleft.setPosition(0);
		backright.setPosition(0);
	}
	 
//	public double getYaw() {
//		return navx.getYaw();
//	}
//	    
//	public double getPitch() {
//		return navx.getPitch();
//	}
//	    
//	public double getRoll() {
//		return navx.getRoll();
//	}
//	  
//	public void resetGyro() {
//		 navx.reset();
//	}
		    
}