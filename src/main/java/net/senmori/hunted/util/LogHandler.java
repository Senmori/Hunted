package net.senmori.hunted.util;

import java.util.logging.Level;

import net.senmori.hunted.Hunted;

public class LogHandler {
	public static void log(Level level, String message) {
		Hunted.log.log(level, message);
	}

	public static void all(String message) {
		log(Level.ALL, message);
	}

	public static void debug(String message) {
		if (Hunted.getInstance().getConfigManager().debug) {
			log(Level.CONFIG, message);
		}
	}

	public static void dbWarning(String message) {
		log(Level.WARNING, "[DB] " + message);
	}

	public static void warning(String message) {
		log(Level.WARNING, message);
	}

	public static void severe(String message) {
		log(Level.SEVERE, message);
	}

	public static void info(String message) {
		log(Level.INFO, message);
	}

	public static void fine(String message) {
		log(Level.FINE, message);
	}
}