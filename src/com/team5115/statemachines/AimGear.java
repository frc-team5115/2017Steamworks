package com.team5115.statemachines;

import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Roobit;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AimGear extends StateMachineBase {
	
	/**
	 * This state machine is used to aim to place the gear
	 * It aims to align with the retroreflective tape on either side of the gear peg
	 */
	
	public static final int STOP = 0;
	public static final int START = 1;
	public static final int AIMING = 2;
	
	public boolean aimed;
	
	double xError = Roobit.gearOffset;
	
	PID pidController;
	double kp = 0.005;
	double ki = 0.008;//0.008;
	double kd = 0;//0.0003;
	double offset = -10;
	
	public AimGear() {
	}
	
	public void update() {
		switch(state) {	
		case START:
			pidController = new PID();
			
			aimed = false;
			
			if (Math.abs(Roobit.gearOffset + offset) > Constants.GEAR_TOLERANCE) {
				setState(AIMING);
				Roobit.drivetrain.inuse = true;
			}  else {
				aimed = true;
			}

			break;
			
		case AIMING:
			//Run PID to get drivetrain power, tell the drivetrain to do that, then test if the PID loop has finished.
			xError = Roobit.gearOffset + offset;
			double power = pidController.getPID(0, xError, kp, ki, kd);
			if (Math.abs(xError) > 80) {
				Roobit.drivetrain.drive(0, 0, 1);
				Roobit.drivetrain.inuse = false;
			} else { 
				Roobit.drivetrain.drive(-power, power, 1);
			}
			System.out.println(xError + " " + power);
			SmartDashboard.putNumber("gearOffset", xError);
			
			// Once finished, stop driving and let other things use the drivetrain
			if (pidController.isFinished(Constants.GEAR_TOLERANCE, Constants.GEAR_DTOLERANCE) && Math.abs(Roobit.drivetrain.leftSpeed()) < 0.1 && Math.abs(Roobit.drivetrain.rightSpeed()) < 0.1) {
				aimed = true;
				Roobit.drivetrain.drive(0, 0, 1);
				Roobit.drivetrain.inuse = false;
			}
			
			break;
			
		}
	}
	
	public boolean isFinished() {
		return aimed;
	}

}
