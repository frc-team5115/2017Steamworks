package com.team5115.statemachines;

import com.team5115.Constants;
import com.team5115.robot.Roobit;

public class Auto extends StateMachineBase {
	
	/**
	 * This state machine contains the autonomous routine
	 * Which routine is used varies depending on what you put into SmartDashboard
	 * This state machine is a manager for the other state machines that contain the routines
	 */
	
	public static final int INITIALIZE = 0;
	public static final int STOP = 1;
	public static final int FUEL = 2;
	public static final int GEAR_LEFT = 3;
	public static final int GEAR_CENTER = 4;
	public static final int GEAR_RIGHT = 5;
	
	//These are all the different autonomous routines
	AutoGearLeftRed aglr;
	AutoGearLeftBlue aglb;
	AutoGearCenter agc;
	AutoGearRightRed agrr;
	AutoGearRightBlue agrb;
	AutoFuelBlue afb;
	AutoFuelRed afr;
	
	int team;
	int action;
	int position;
	
	public Auto(int t, int a, int p) {
		team = t;
		action = a;
		position = p;
		
		aglr = new AutoGearLeftRed();
		aglb = new AutoGearLeftBlue();
		agc = new AutoGearCenter();
		agrr = new AutoGearRightRed();
		agrb = new AutoGearRightBlue();
		afb = new AutoFuelBlue();
		afr = new AutoFuelRed();
	}
	
	public void update() {
		switch (state) {
		//This case exists to initialize all of the routines and set the state 
		case INITIALIZE:
			aglr.setState(AutoGearLeftRed.INIT);
			aglb.setState(AutoGearLeftBlue.INIT);
			agc.setState(AutoGearCenter.INIT);
			agrr.setState(AutoGearRightRed.INIT);
			agrb.setState(AutoGearRightBlue.INIT);
			afb.setState(AutoFuelBlue.INIT);
			afr.setState(AutoFuelRed.INIT);
			
			if(action == 2) {
				state = FUEL;
			}
			else if(action == 1) {
				Roobit.drivetrain.direction = Constants.DIR_GEAR;
				if(position == GEAR_LEFT) {
					state = GEAR_LEFT;
				}
				
				else if(position == GEAR_CENTER) {
					state = GEAR_CENTER;
				}
				
				else if(position == GEAR_RIGHT) {
					state = GEAR_RIGHT;
				}
			}
			break;
			
		//The following cases simply run the different routines for the autonomous phase
		case FUEL:
			if (team == 1) {
				afr.update();
			} else {
				afb.update();
			}
			break;
		
		case GEAR_LEFT:
			if (team == 1) {
				aglr.update();
			} else {
				aglb.update();
			}
			break;
			
		case GEAR_CENTER:
			System.out.println("gear center");
			agc.update();
			break;
			
		case GEAR_RIGHT:
			if (team == 1) {
				agrr.update();
			} else {
				agrb.update();
			}
			break;
		}
	}

}