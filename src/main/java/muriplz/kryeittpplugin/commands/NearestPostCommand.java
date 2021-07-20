package muriplz.kryeittpplugin.commands;

import muriplz.kryeittpplugin.KryeitTPPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class NearestPostCommand implements CommandExecutor{

    private final KryeitTPPlugin plugin;

    public NearestPostCommand(KryeitTPPlugin plugin) {
        this.plugin = plugin;
    }

    //  This commands aims to be /NearestPost in-game
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if( ! ( sender instanceof Player )) {
            Bukkit.getConsoleSender().sendMessage(plugin.name+"You can't execute this command from console.");
            return false;
        }else {
            Player player = (Player) sender;

            // Player has to be in the Overworld
            if(!player.getWorld().getName().equals("world")){
                PostAPI.sendMessage(player,"&cYou have to be in the Overworld to use this command.");
                return false;
            }

            Location nearestPost = PostAPI.getNearPostLocation(player,plugin);
            // For the X axis
            int postX = nearestPost.getBlockX();

            // For the Z axis
            int postZ = nearestPost.getBlockZ();

            if(args.length==0) {

                String postName = PostAPI.NearestPostName(player,plugin);
                if(postName!=null){
                    PostAPI.sendMessage(player,"&fThe nearest post is on: &6(" + postX + " , " + postZ + ")&f, it's &6"+postName+"&f.");
                }else{
                    PostAPI.sendMessage(player,"&fThe nearest post is on: &6(" + postX + " , " + postZ + ")&f.");
                }

            }else if (args.length==1) {
                if(args[0].equals("on")) {
                    if (!plugin.showNearest.contains(player.getUniqueId())){
                        String postName = PostAPI.NearestPostName(player,plugin);
                        if(postName!=null){
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',"The nearest post is on: &6(" + postX + " , " + postZ + ")&f, it's &6"+postName+"&f.")));
                        }else{
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',"The nearest post is on: &6(" + postX + " , " + postZ + ")&f.")));
                        }
                        plugin.showNearest.add(player.getUniqueId());
                    }else{
                        PostAPI.sendMessage(player,"&cYou already have the option enabled.");
                    }
                }else if (args[0].equals("off")){
                    if (plugin.showNearest.contains(player.getUniqueId())){
                        plugin.counterNearest.remove(plugin.showNearest.indexOf(player.getUniqueId()));
                        plugin.showNearest.remove(player.getUniqueId());
                    }else{
                        PostAPI.sendMessage(player,"&cYou don't have the option enabled.");
                    }

                }
            }
            return true;
        }
    }

}