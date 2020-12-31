package me.iTzYeXx.SSPortal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RunnableSS {

    private Main plugin;
    private final Player player;
    private int taskID;
    private int taskID2;
    private int delaySound;
    private boolean soundEnabled;
    private boolean messagesEnabled;
    private int timeRemaining;
    private int i = 0;

    public RunnableSS(Main plugin, Player player, Integer delaySound, boolean soundEnabled, boolean messagesEnabled, int timeRemaining){
        this.plugin = plugin;
        this.player = player;
        this.delaySound = delaySound;
        this.soundEnabled = true;
        this.messagesEnabled = true;
        this.timeRemaining = timeRemaining;
        this.i = timeRemaining;
        freezePlayer();
        timer();
    }

    private void freezePlayer(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                if (plugin.isSSRequest(player)) {
                    if (plugin.isFroozed(player)) {
                        if (soundEnabled) {
                            Sound sound = Sound.valueOf(plugin.getConfig().getString("REMINDER.SOUND.SOUND"));
                            if (sound == null) {
                                sound = Sound.LAVA_POP;
                            }
                            player.playSound(player.getLocation(), sound, 2.0F, 1F);
                        }
                        if (messagesEnabled) {
                            String message = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("REMINDER.CHAT.MESSAGE")
                                    .replaceAll("%seconds%", plugin.secondsToTime(i)));
                            player.sendMessage(message);
                        }
                    } else {
                        Bukkit.getScheduler().cancelTask(taskID);
                    }
                }else{
                    Bukkit.getScheduler().cancelTask(taskID);
                }
            }
        }, 20L * delaySound, 20L * delaySound);
    }

    private void timer(){
        taskID2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                if (plugin.isSSRequest(player)){
                    if(i <= 0){
                        for(Player p : Bukkit.getOnlinePlayers()){
                            if(p.hasPermission("ss.use")){
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SS_TIMEOUT").replaceAll("%player%", player.getName())));
                            }
                        }
                        plugin.unSSRequest(player);
                        player.closeInventory();
                        Bukkit.getScheduler().cancelTask(taskID2);
                    }
                    i--;
                }else{
                    Bukkit.getScheduler().cancelTask(taskID2);
                }
            }
        }, 0L, 20L);
    }
}
