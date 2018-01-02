package com.team5115;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Preferences;
public class Prefs {
	
	static double centerFirstLegDef = 2;
	static double centerSecondLegDef = 4.5;
	static double boilerFirstLegDef = 5.6;
	static double boilerTurnDef = 60;
	static double boilerSecondLegDef = 3.25;
	static double boilerThirdLegDef = 3.25;
	static double loadingFirstLegDef = 6.0;
	static double loadingTurnDef = 60;
	static double loadingSecondLegDef = 4.25;
	static double fuelFirstLegDef = 8.8;
	static double fuelSecondLegDef = 11.1;
	static double fuelTurnDef = 35;
	static double fuelThirdLegDef = 10;

	public static Preferences prefs = Preferences.getInstance();
	
	public static double getCenterFirstLeg() {
		double input = prefs.getDouble("CenterFirstLeg", centerFirstLegDef);
		return Math.abs(input) < 15 ? input : centerFirstLegDef;
	}
	
	public static double getCenterSecondLeg() {
		double input =  prefs.getDouble("CenterSecondLeg", centerSecondLegDef);
		return Math.abs(input) < 15 ? input : centerSecondLegDef;
	}
	
	public static double getBoilerFirstLeg() {
		double input =  prefs.getDouble("BoilerFirstLeg", boilerFirstLegDef);
		return Math.abs(input) < 15 ? input : boilerFirstLegDef;
	}
	
	public static double getBoilerTurn() {
		double input =  prefs.getDouble("BoilerTurn", boilerTurnDef);
		return Math.abs(input) < 180 ? input : boilerTurnDef;
	}
	
	public static double getBoilerSecondLeg() {
		double input =  prefs.getDouble("BoilerSecondLeg", boilerSecondLegDef);
		return Math.abs(input) < 15 ? input : boilerSecondLegDef;
	}
	
	public static double getBoilerThirdLeg() {
		double input =  prefs.getDouble("BoilerThirdLeg", boilerThirdLegDef);
		return Math.abs(input) < 15 ? input : boilerThirdLegDef;
	}
	
	public static double getLoadingFirstLeg() {
		double input =  prefs.getDouble("LoadingFirstLeg", loadingFirstLegDef);
		return Math.abs(input) < 15 ? input : loadingFirstLegDef;
	}
		
	public static double getLoadingTurn() {
		double input =  prefs.getDouble("LoadingSideTurn", loadingTurnDef);
		return Math.abs(input) < 180 ? input : loadingTurnDef;
	}
	
	public static double getLoadingSecondLeg() {
		double input =  prefs.getDouble("LoadingSideSecondLeg", loadingSecondLegDef);
		return Math.abs(input) < 15 ? input : loadingSecondLegDef;
	}
	
	public static double getFuelFirstLeg() {
		double input =  prefs.getDouble("LoadingFirstLeg", loadingFirstLegDef);
		return Math.abs(input) < 15 ? input : loadingFirstLegDef;
	}
	
	public static double getFuelTurn() {
		double input =  prefs.getDouble("LoadingSideTurn", loadingTurnDef);
		return Math.abs(input) < 180 ? input : loadingTurnDef;
	}
	
	public static double getFuelSecondLeg() {
		double input =  prefs.getDouble("LoadingSideSecondLeg", loadingSecondLegDef);
		return Math.abs(input) < 15 ? input : loadingSecondLegDef;
	}
	
	public static double getFuelThirdLeg() {
		double input =  prefs.getDouble("fuelthirdleg", fuelThirdLegDef);
		return Math.abs(input) < 15 ? input : fuelThirdLegDef;
	}

}