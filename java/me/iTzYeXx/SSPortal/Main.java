package me.iTzYeXx.SSPortal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {

    private ArrayList<UUID> players;
    private ArrayList<UUID> ssrequest;
    private int delayTimer;
    private int timeRemaining;
    private boolean soundEnabled;
    private boolean messageEnabled;

    public void onEnable(){
        registerConfig();
        players = new ArrayList<UUID>();
        ssrequest = new ArrayList<UUID>();
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l------------------------"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l  SS PORTALCRAFT ACTIVADO"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l------------------------"));
        this.delayTimer = getConfig().getInt("REMINDER.TIMER");
        this.timeRemaining = getConfig().getInt("REMINDER.TIMER_ADMIT_LIMIT");
        this.soundEnabled = getConfig().getBoolean("REMINDER.SOUND.ENABLED");
        this.messageEnabled = getConfig().getBoolean("REMINDER.CHAT.ENABLED");
        getServer().getPluginManager().registerEvents(new Events(this), this);;
        getServer().getPluginManager().registerEvents(new Menu(this), this);
        getCommand("ss").setExecutor(new CommandSS(this));
    }

    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l------------------------"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l  SS PORTALCRAFT DESACTIVADO"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l------------------------"));
    }

    public void registerConfig(){
        File configFile = new File(getDataFolder(), "config.yml");
        if(!configFile.exists()){
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public boolean isFroozed(Player player){
        if(this.players.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public void frooze(final Player player){
        this.players.add(player.getUniqueId());
        ssRequest(player);
        final Menu menu = new Menu(this);
        new RunnableSS(this, player, delayTimer, soundEnabled, messageEnabled, timeRemaining);
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
                menu.openSSMenu(player);
            }
        }, 2L);
    }

    public void unFrooze(Player player){
        UUID uuidPlayer = player.getUniqueId();
        players.remove(uuidPlayer);
        unSSRequest(player);
    }

    public boolean ssRequest(Player player){
        this.ssrequest.add(player.getUniqueId());
        return false;
    }

    public void unSSRequest(Player player){
        UUID uuidPlayer = player.getUniqueId();
        ssrequest.remove(uuidPlayer);
    }

    public boolean isSSRequest(Player player){
        if(this.ssrequest.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public String secondsToTime(int sec){
            int seconds = sec % 60;
            int minutes = sec / 60;
            return minutes + " m " + seconds + " segs.";
    }

}
