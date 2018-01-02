package com.team5115.statemachines;

import com.team5115.Constants;
import com.team5115.ForwardProfile;
import com.team5115.PID;
import com.team5115.TurnProfile;
import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;

import edu.wpi.first.wpilibj.Timer;

public class AimFuel extends StateMachineBase {
	
	/**
	 * This state machine uses the vision from the raspberry pi to aim the robot to be aligned with the high goal
	 */

	public static final int STOP = 0;
	public static final int START = 1;	// Initialization state of the state machine
	public static final int AIMINGX = 2;	// State where the robot aims in the X direction
	public static final int FINEX = 3;
	public static final int AIMINGY = 4;
	public static final int FINEY = 5;
	
	public boolean aimed;
	
	AutoDrive ad;
	PID pidControllerX;
	PID pidControllerY;
	
	double kpx = 0.01;
	double kix = 0.0001;
	double kdx = 0;
	
	double kpxf = 0.03;
	double kixf = 0.02;
	double kdxf = 0.0001;
	
	double kpy = 0.025;
	double kiy = 0.00;
	double kdy = 0;
	
	double kpyf = 0.01;
	double kiyf = 0.006;
	double kdyf = 0;

	public double xError = Roobit.xOffset;
	public double yError = Roobit.yOffset;
	
	double xSetpoint = 3;
	double ySetpoint = 39;
	
	double xpower;
	double ypower;

	public AimFuel() {
		pidControllerX = new PID();
		pidControllerY = new PID();
	}
	
	public void update() {
		if (InputManager.cancel()) {
			aimed = true;
			setState(STOP);
			Roobit.drivetrain.inuse = false;
		}
		
		switch(state) {
		
		case STOP:
			break;
			
		case START:
			aimed = false;
			//System.out.println("Start");
			Roobit.drivetrain.inuse = true;
			pidControllerX = new PID();
			pidControllerY = new PID();
			setState(AIMINGX);
			
			break;
			
		case AIMINGX:
			xError = Roobit.xOffset;
			if (Math.abs(xError) < 10) {
				xpower = pidControllerX.getPID(xSetpoint, xError, kpx, kix, kdx);
			} else {
				xpower = pidControllerX.getPID(xSetpoint, xError, kpx, kix, kdx);
			}
			if (Math.abs(xError) >= 60) {
				//setState(AIMINGY);
			} else
				Roobit.drivetrain.drive(-xpower, xpower, 1);
			
			System.out.println("X Error: " + pidControllerX.error + " speed: " + Roobit.drivetrain.leftSpeed());
			
			if (pidControllerX.isFinished(Constants.FUEL_TOLERANCE_X, Constants.FUEL_DTOLERANCE) && Math.abs(Roobit.drivetrain.leftSpeed()) < 0.1 && Math.abs(Roobit.drivetrain.rightSpeed()) < 0.1) {
			//	System.out.println("AIMING X Finished");
				setState(AIMINGY);
			}
			
			break;
		
//		case FINEX:
//			xError = Roobit.xOffset;
//			xpower = pidControllerX.getPID(xSetpoint, xError, kpxf, kixf, kdxf);
//			if (Math.abs(xError) == 80) {
//				setState(AIMINGY);
//			} else
//				Roobit.drivetrain.drive(-xpower, xpower, 1);
//			
//			System.out.println("X Error: " + pidControllerX.error + " derror: " + pidControllerX.derror);
//			
//			if (pidControllerX.isFinished(Constants.FUEL_TOLERANCE_X, Constants.FUEL_DTOLERANCE) && Math.abs(Roobit.drivetrain.leftSpeed()) < 0.1 && Math.abs(Roobit.drivetrain.rightSpeed()) < 0.1) {
//				System.out.println("AIMING X Finished");
//
//				aimed = true;
//				Roobit.drivetrain.drive(0, 0, 1);
//				Roobit.drivetrain.inuse = false;
//			}
//			break;
			
		case AIMINGY:
			// Once finished, stop driving and let other things use the drivetrain
			yError = Roobit.yOffset;
			if (Math.abs(yError) < 10) {
				ypower = pidControllerY.getPID(ySetpoint, yError, kpy, kiy, kdy);
			} else {
				ypower = pidControllerY.getPID(ySetpoint, yError, kpy, kiy, kdy);
			}
			if (Math.abs(yError) == 120) {
				Roobit.drivetrain.drive(0, 0, 1);
				Roobit.drivetrain.inuse = false;
				
			} else {
				Roobit.drivetrain.drive(ypower, ypower, 1);
				System.out.println("Y Error: " + (yError - ySetpoint) + " derror: " + pidControllerY.derror + " power: " + ypower);
			}
			
			if (pidControllerY.isFinished(Constants.FUEL_TOLERANCE_Y, Constants.FUEL_DTOLERANCE) && Math.abs(Roobit.drivetrain.leftSpeed()) < 0.1 && Math.abs(Roobit.drivetrain.rightSpeed()) < 0.1) {
				Roobit.drivetrain.drive(0, 0, 1);
				System.out.println("AIMING Y Finished");
				aimed = true;
				setState(STOP);
			}

			break;
			
//		case FINEY:		//unused currently
//			yError = Roobit.yOffset;
//			ypower = pidControllerY.getPID(ySetpoint, yError, kpyf, kiyf, kdyf);
//			if (yError == 120) {
//				Roobit.drivetrain.drive(0, 0, 1);
//				Roobit.drivetrain.inuse = false;
//				
//			} else {
//				Roobit.drivetrain.drive(ypower, ypower, 1);
//				System.out.println("Y Error: " + pidControllerY.error + " derror: " + pidControllerY.derror + " power: " + ypower);
//			}
//			
//			if (pidControllerY.isFinished(Constants.FUEL_TOLERANCE_Y, Constants.FUEL_DTOLERANCE) && Math.abs(Roobit.drivetrain.leftSpeed()) < 0.01 && Math.abs(Roobit.drivetrain.rightSpeed()) < 0.01) {
//				aimed = true;
//				Roobit.drivetrain.drive(0, 0, 1);
//				Roobit.drivetrain.inuse = false;
//				System.out.println("AIMING Y Finished");
//			}
//			break;
			
		}
	}
}