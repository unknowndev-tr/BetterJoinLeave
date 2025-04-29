package tr.unknown.betterJL;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tr.unknown.betterJL.utils.Logger;

public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Logger.initialize();

        getCommand("betterjl").setExecutor(new Commands(this));
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   ___      _   _              _ _    "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b  | _ ) ___| |_| |_ ___ _ _ _ | | |   "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3  | _ \\/ -_)  _|  _/ -_) '_| || | |__ "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b  |___/\\___|\\__|\\__\\___|_|  \\__/|____|"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "                                     "));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', " &8● &7Author: &fUnknownDev"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', " &8● &7Version: &f1.1.0"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', " &8● &7Discord: &9Unknown (@unknownstoretr)"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));

    }

    public void sendActionBar(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

}
