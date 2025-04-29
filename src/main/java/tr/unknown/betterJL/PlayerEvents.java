package tr.unknown.betterJL;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import tr.unknown.betterJL.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerEvents implements Listener {

    private Main plugin;
    private DiscordWebhookSender discordWebhookSender;

    public PlayerEvents(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);

        String joinMessage = plugin.getConfig().getString("Formats.joinMessage");
        String firstJoinMessage = plugin.getConfig().getString("Formats.firstJoinMessage");
        String joinSound = plugin.getConfig().getString("Join-Sound.sound");

        String webhookUrl = plugin.getConfig().getString("Discord-Webhook.webhookURL");
        discordWebhookSender = new DiscordWebhookSender(webhookUrl);

        Player player = e.getPlayer();

        if(plugin.getConfig().getBoolean("Join-Leave-Logs") == true){
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String logMessage = "Player has joined the game. | " + player.getName() + " | " + currentTime;
            Logger.writeToLog(logMessage);
        }

        if (plugin.getConfig().getBoolean("Discord-Webhook.enabled") == true){
            discordWebhookSender.sendEmbedMessage(plugin.getConfig().getString("Discord-Webhook.joinMessages.title"), plugin.getConfig().getString("Discord-Webhook.joinMessages.description").replaceAll("%username%", player.getName()), plugin.getConfig().getString("Discord-Webhook.joinMessages.footer"));
        }

        if (plugin.getConfig().getBoolean("Join-Sound.enabled") == true){
            player.playSound(player.getLocation(), Sound.valueOf(joinSound), 1, 1);
        }

        if (plugin.getConfig().getBoolean("JL-Messages-Enabled") == true){
            if(!(player.hasPermission("betterjoinleave.silentjoin") && plugin.getConfig().getBoolean("Silent-Join-For-Admins") == true)){
                if (!(player.hasPlayedBefore())){
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', firstJoinMessage).replace("%player_name%", player.getName()));
                }else{
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', joinMessage).replace("%player_name%", player.getName()));
                }
            }
        }

        String joinMessageAB = plugin.getConfig().getString("Action-Bar.joinMessage");
        String firstJoinMessageAB = plugin.getConfig().getString("Action-Bar.firstJoinMessage");

        if (plugin.getConfig().getBoolean("Action-Bar.enabled") == true){
            if (!(player.hasPermission("betterjoinleave.silentjoin") && plugin.getConfig().getBoolean("Silent-Join-For-Admins") == true)){
                for (Player players : Bukkit.getOnlinePlayers()){
                    if (!(player.hasPlayedBefore())){
                        plugin.sendActionBar(players, ChatColor.translateAlternateColorCodes('&', firstJoinMessageAB).replace("%player_name%", player.getName()));
                    }else{
                        plugin.sendActionBar(players, ChatColor.translateAlternateColorCodes('&', joinMessageAB).replace("%player_name%", player.getName()));
                    }
                }
            }
        }

        if (plugin.getConfig().getBoolean("Join-Command.enabled") == true){
            for (String cmd : plugin.getConfig().getStringList("Join-Command.commands")){
                player.performCommand(cmd);
            }
        }

        if (plugin.getConfig().getBoolean("Send-Message-To-Player.enabled") == true){
            for (String line : plugin.getConfig().getStringList("Send-Message-To-Player.welcomeText")){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace("%player_name%", player.getName()));
            }
        }

        if (plugin.getConfig().getBoolean("Firework-On-Join") == true){

            Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            fireworkMeta.addEffect(FireworkEffect.builder()
                    .withColor(org.bukkit.Color.RED)
                    .withFade(org.bukkit.Color.YELLOW)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .withFlicker()
                    .build());
            fireworkMeta.setPower(1);
            firework.setFireworkMeta(fireworkMeta);
        }

        if (plugin.getConfig().getBoolean("Send-Title-To-Player.enabled") == true){

            String title = plugin.getConfig().getString("Send-Title-To-Player.titleText").replace("%player_name%", player.getName());
            String subtitle = plugin.getConfig().getString("Send-Title-To-Player.subtitleText").replace("%player_name%", player.getName());

            player.sendTitle(ChatColor.translateAlternateColorCodes('&', title), ChatColor.translateAlternateColorCodes('&', subtitle), 10, 70, 20);

        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        e.setQuitMessage(null);

        String leaveMessage = plugin.getConfig().getString("Formats.leaveMessage");

        String webhookUrl = plugin.getConfig().getString("Discord-Webhook.webhookURL");
        discordWebhookSender = new DiscordWebhookSender(webhookUrl);

        Player player = e.getPlayer();

        if(plugin.getConfig().getBoolean("Join-Leave-Logs") == true){
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String logMessage = "Player has logged out. | " + player.getName() + " | " + currentTime;
            Logger.writeToLog(logMessage);
        }

        if (plugin.getConfig().getBoolean("Discord-Webhook.enabled") == true){
            discordWebhookSender.sendEmbedMessage(plugin.getConfig().getString("Discord-Webhook.leaveMessages.title"), plugin.getConfig().getString("Discord-Webhook.leaveMessages.description").replaceAll("%username%", player.getName()), plugin.getConfig().getString("Discord-Webhook.leaveMessages.footer"));
        }

        if (plugin.getConfig().getBoolean("JL-Messages-Enabled") == true){
            if(!(player.hasPermission("betterjoinleave.silentjoin"))){
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage).replace("%player_name%", player.getName()));
            }
        }

        String leaveMessageAB = plugin.getConfig().getString("Action-Bar.leaveMessage");

        if (plugin.getConfig().getBoolean("Action-Bar.enabled") == true){
            if (!(player.hasPermission("betterjoinleave.silentjoin"))){
                for (Player players : Bukkit.getOnlinePlayers()){
                    plugin.sendActionBar(players, ChatColor.translateAlternateColorCodes('&', leaveMessageAB).replace("%player_name%", player.getName()));
                }
            }
        }

    }

}
