package me.iTzYeXx.SSPortal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Menu implements Listener {

    private Main plugin;
    private Inventory inv;
    public Menu(Main plugin) {
        this.plugin = plugin;
    }


    public void openSSMenu(Player player){
        inv = Bukkit.createInventory(null, plugin.getConfig().getInt("MENU.SLOTS"),
                ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MENU.TITLE")));
        for(String key : plugin.getConfig().getConfigurationSection("MENU.ITEMS").getKeys(false)){
            ItemStack item = new ItemStack(plugin.getConfig().getInt("MENU.ITEMS." + key + ".ID"), 1);
            item.setDurability((short) plugin.getConfig().getInt("MENU.ITEMS." + key + ".DATA"));
            ArrayList<String> loreList = new ArrayList<String>();
            for(String lore : plugin.getConfig().getStringList("MENU.ITEMS." + key + ".LORE")){
                loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MENU.ITEMS." + key + ".DISPLAYNAME")));
            meta.setLore(loreList);
            item.setItemMeta(meta);
            inv.setItem(plugin.getConfig().getInt("MENU.ITEMS." + key + ".SLOT"), item);
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void click(InventoryClickEvent e){
        String equalsTitle = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MENU.TITLE")));
        String titleConf = ChatColor.stripColor(equalsTitle);
        Player playerFrozed = (Player) e.getWhoClicked();
        if(titleConf.equals(ChatColor.stripColor(e.getInventory().getName()))){
            if(plugin.getConfig().getInt("MENU.ITEMS.SS.SLOT") == e.getSlot()){
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.hasPermission("ss.use")){
                        if(p.hasPermission("ss.use")){
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SS_NO_ADMIT").replaceAll("%player%" ,playerFrozed.getName())));
                        }
                    }
                }
                plugin.unSSRequest(playerFrozed);
                playerFrozed.closeInventory();
            }else if(plugin.getConfig().getInt("MENU.ITEMS.ADMIT.SLOT") == e.getSlot()){
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.hasPermission("ss.use")){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("SS_ADMIT").replaceAll("%player%",playerFrozed.getName())));
                    }
                }
                plugin.unSSRequest(playerFrozed);
                playerFrozed.closeInventory();
            }
        }
    }
}
