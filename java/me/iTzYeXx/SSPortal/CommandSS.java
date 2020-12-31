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
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEl usuario no esta conectado."));
                    }else{
                        if(plugin.isFroozed(target)){
                            plugin.unFrooze(target);
                            target.closeInventory();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eEl jugador &d" + target.getName() + " &eha sido unfrozeado."));
                        }else{
                            plugin.frooze(target);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eEl jugador &d" + target.getName() + " &eha sido frozeado."));
                        }
                    }
                }else{
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUso /ss <nick>"));
                }
            }else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo tienes permisos para hacer esto."));
            }
        }else{
            Bukkit.getConsoleSender().sendMessage("No puedes congelar a un usuario con la consola.");
        }
        return false;
    }

}
