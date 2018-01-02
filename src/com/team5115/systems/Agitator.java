package com.team5115.systems;

import com.team5115.Constants;
import com.team5115.robot.Roobit;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class Agitator {
	
	public boolean inuse;
	
	Victor agitator; 
	Victor feeder;
	
	public Agitator() {
		agitator = new Victor(Constants.AGITATOR_VICTOR);
		feeder = new Victor(Constants.FEEDER_VICTOR);
	}
	
	public void feed() {
		agitator.set(1);
		feeder.set(-0.4);
	}
	
	public void starve() {
		agitator.set(0/*-0.5*/);
		feeder.set(0);
	}

	public void stop() {
		agitator.set(0);
		feeder.set(0);
	}
	

}