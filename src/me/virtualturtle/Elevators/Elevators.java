package me.virtualturtle.Elevators;

import org.bukkit.plugin.java.JavaPlugin;

public class Elevators extends JavaPlugin {
private ElevatorListener listener; 
	
	public Elevators() {
		listener = new ElevatorListener();
	}
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(listener, this);
	}
}
