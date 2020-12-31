package me.iTzYeXx.SSPortal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

public class Events implements Listener {

    private Main plugin;
    public Events(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(plugin.isFroozed(e.getPlayer())) {
            if(e.getFrom().getX() != e.getTo().getX()){
                if(e.getFrom().getZ() != e.getTo().getZ()){
                    e.setTo(e.getFrom());
                }
            }
        }
    }

    @EventHandler
    public void closeInv(final InventoryCloseEvent e){
        if(e.getPlayer() instanceof Player){
            if(plugin.isSSRequest((Player) e.getPlayer())){
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        Menu menu = new Menu(plugin);
                        menu.openSSMenu((Player) e.getPlayer());
                    }
                },5L);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e instanceof  Player){
            if(plugin.isFroozed(((Player) e).getPlayer())){
                e.setCancelled(true);
            }
        }
    }

}
