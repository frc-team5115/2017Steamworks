package com.team5115.systems;

import com.team5115.Constants;
import com.team5115.PID;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel {
	/**
	 * This code is for the flywheel
	 */
	public boolean inuse;

	public int direction;
	Victor flywheel;
	Encoder wheel;
	
	public Flywheel() {
		flywheel = new Victor(Constants.FLYWHEEL_VICTOR);
		wheel = new Encoder(Constants.ENCODER_CHANNEL_1, Constants.ENCODER_CHANNEL_2, true, Encoder.EncodingType.k4X);
		wheel.setDistancePerPulse(.05);
	}
	
	public void spinUp(double speed) {
		flywheel.set(-speed);
		inuse = true;
		SmartDashboard.putBoolean("Is Idle", inuse);
	}
	
	public void idle() {
		flywheel.set(0);
		inuse = false;
		SmartDashboard.putBoolean("Is Idle", inuse);
	}
	
	public void back() {
		flywheel.set(0/*0.3*/);
	}
	
	public double getFlywheelRPM() {
		double rps = wheel.getRate();
		return rps * 60;
	}
}