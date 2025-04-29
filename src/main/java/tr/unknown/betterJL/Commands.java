package tr.unknown.betterJL;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private Main plugin;

    public Commands(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Messages.onlyConsole")));
            return true;
        }

        Player player = (Player) sender;

        if (!(player.hasPermission("betterjoinleave.admin"))){

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Messages.noPermission")));
        }else{
            if (args.length == 1){
                switch (args[0].toLowerCase()){
                    case "help":
                        for (String help : plugin.getConfig().getStringList("Messages.helpCommand")){
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                        }
                        break;
                    case "reload":
                        plugin.reloadConfig();
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Messages.pluginReloaded")));
                        break;
                }
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix") + plugin.getConfig().getString("Messages.wrongUsage")));
            }
        }

        return true;
    }
}
