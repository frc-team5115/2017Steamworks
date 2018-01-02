package com.team5115.systems;

import com.team5115.Constants;
import com.team5115.robot.Roobit;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
	
	public boolean inuse;

	public int direction;
	
	Spark intake;
	double intakeSpeed;
	
	public Intake() {
		intake = new Spark(Constants.INTAKE_VICTOR);
		intakeSpeed = Roobit.drivetrain.averageSpeed() / 10;
	}
	
	public void intake() {
		if (intakeSpeed <= 0) {
			intake.set(0.4);
		}
		else  if (intakeSpeed < 0.4) {  
			intake.set(intakeSpeed * 0.5 + 0.5);
		} else {
			intake.set(1);
		}
	}
	
	public void back() {
		intake.set(-0.2);
	}

	public void stop() {
		intake.set(0);
	}
	

}