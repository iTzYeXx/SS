package me.iTzYeXx.SSPortal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSS implements CommandExecutor {


    private Main plugin;
    public CommandSS(Main plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(p.hasPermission("ss.use")){
                if(args.length >= 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target == null){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TARGET_NOT_FOUND").replaceAll("%player%", args[0])));
                    }else{
                        if(plugin.isFroozed(target)){
                            plugin.unFrooze(target);
                            target.closeInventory();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TARGET_UNFREEZE").replaceAll("%player%", args[0])));
                        }else{
                            plugin.frooze(target);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TARGET_FREEZE").replaceAll("%player%", args[0])));
                        }
                    }
                }else{
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SS_USE")));
                }
            }else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NO_HAVE_PERMISSION")));
            }
        }else Bukkit.getConsoleSender().sendMessage("You cannot do this from the console.");
        return false;
    }

}
