package com.team5115.statemachines;

import com.team5115.robot.InputManager;
import com.team5115.robot.Roobit;
import com.team5115.Constants;

import edu.wpi.first.wpilibj.Timer;

public class FuelManipulatorManager extends StateMachineBase {
	
	/**
	 * This is the top level state machine which controls all of the fuel manipulation of the robot
	 * There are three steps of fuel manipulation: stopped, intaking, and shooting
	 * This state machine controls all of the others in the fuel manipulation loop
	 */
	
	public static final int STOP = 0;
	public static final int INTAKE = 1;
	public static final int AIMING = 2;
	public static final int SPINUP = 3;
	public static final int SHOOT = 4;
	public static final int CLIMB = 5;
	
	boolean canStopShooting = false;
	
	AgitatorManager am;
	FlywheelManager fm;
	IntakeManager im;
	AimFuel aim;
	
	public FuelManipulatorManager() {
		im = new IntakeManager();
		am = new AgitatorManager();
		fm = new FlywheelManager();
		aim = new AimFuel();

	}

	public void update() {
		if (InputManager.cancel())
			setState(INTAKE);
		
		switch (state) {
		case STOP:
			// stop
			System.out.println("FMM stop");
			im.setState(IntakeManager.STOP);
			am.setState(AgitatorManager.STOP);
			fm.setState(FlywheelManager.STOP);
			
			im.update();
		    am.update();
		    fm.update();
		    break;
		    
		case INTAKE:
			// intake
			im.setState(IntakeManager.INTAKE);
			am.setState(AgitatorManager.STARVE);
		    fm.setState(FlywheelManager.STARVE);
		    
		    im.update();
		    am.update();
		    fm.update();
		    
		    if (InputManager.aimFuel()) {
		    	aim.setState(AimFuel.START);
		    	setState(AIMING);
		    }
		    
		    if (InputManager.shoot()) {
		    	fm.setState(FlywheelManager.SHOOT);
		    	setState(SPINUP);
		    }
		    
		    //System.out.println(InputManager.shoot());
		    
			break;
			
		case AIMING:
			// aim
			aim.update();
			
			System.out.println("AIMING");
		    
		    im.update();
		    am.update();
		    fm.update();
			if(InputManager.shoot()) {
				aim.setState(AimFuel.STOP);
				fm.setState(FlywheelManager.SHOOT);
				Roobit.drivetrain.inuse = false;
				setState(SPINUP);
		    }
			if (aim.aimed) {
				aim.setState(AimFuel.STOP);
				fm.setState(FlywheelManager.SHOOT);
				Roobit.drivetrain.inuse = false;
				setState(SPINUP);
			}
			break;
			
		case SPINUP:
			im.update();
		    am.update();
		    fm.update();
		    
		    if (fm.atSpeed()){
		    	am.setState(AgitatorManager.FEED);
		    	setState(SHOOT);
		    }
		    
		    break;
		case SHOOT:
			// shoot
			im.setState(IntakeManager.INTAKE);
			am.setState(AgitatorManager.FEED);
			fm.setState(FlywheelManager.SHOOT);

			im.update();
			am.update();
			fm.update();
			
			if (!InputManager.shoot())
				canStopShooting = true;
			
			if(InputManager.cancel() || InputManager.shoot() && canStopShooting) {
				setState(INTAKE);
			}
			
			break;

		case CLIMB:
			im.setState(IntakeManager.STOP);
			am.setState(AgitatorManager.STARVE);
			fm.setState(FlywheelManager.STARVE);
			
			im.update();
			am.update();
			fm.update();
			break;
		
		}
	}
}
