package me.virtualturtle.Elevators;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ElevatorListener implements Listener {
	
	private Block FindNextElevator(Block block, BlockFace face) {
		while (block != null && block.getLocation().getY() > 0 && block.getLocation().getY() < block.getLocation().getWorld().getMaxHeight() ) {
			block = block.getRelative(face);
			if(block.getType() == Material.IRON_PLATE) {
				Block down = block.getRelative(BlockFace.DOWN);
				if (	down.getType() == Material.IRON_BLOCK &&
						down.getRelative(BlockFace.NORTH).getType() == Material.REDSTONE_BLOCK &&
						down.getRelative(BlockFace.EAST).getType() == Material.REDSTONE_BLOCK &&
						down.getRelative(BlockFace.SOUTH).getType() == Material.REDSTONE_BLOCK &&
						down.getRelative(BlockFace.WEST).getType() == Material.REDSTONE_BLOCK) {
					//We found an elevator!
					
					return block;
				}
			}
		}
		return null;
	}
	
	private void Teleport(Player player, Location loc) {
		player.teleport(loc.add(0.5, 0, 0.5));
		for (int i = 0; i < 10; i++) {
			player.getWorld().playEffect(player.getEyeLocation(), Effect.SMOKE, i, 2);
		}
		player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJump(PlayerMoveEvent  event) {
		if (event.getTo().getY() - event.getFrom().getY() > 0.4) {
			Block curr = event.getFrom().getBlock();
			Block next = FindNextElevator(curr.getRelative(BlockFace.UP, 3), BlockFace.UP);
			if (next != null && next.getY() - curr.getY() < 20) {
				Teleport(event.getPlayer(), next.getLocation());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerSneak(PlayerToggleSneakEvent  event) {
		Player player = event.getPlayer();
		if (player.isSneaking()) 
		{
			Block curr = player.getLocation().getBlock();
			Block next = FindNextElevator(curr.getRelative(BlockFace.DOWN, 3), BlockFace.DOWN);
			if (next != null && curr.getY() - next.getY() < 20) {
				Teleport(player, next.getLocation());
			}
		}
	}
}
