package muriplz.kryeittpplugin.commands;

import io.github.niestrat99.advancedteleport.api.ATPlayer;
import muriplz.kryeittpplugin.KryeitTPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;


public class HomePostCommand implements CommandExecutor{

    private final KryeitTPPlugin plugin;

    public HomePostCommand(KryeitTPPlugin plugin) {
        this.plugin = plugin;
    }

    //  This commands aims to be /HomePost in-game
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if( ! ( sender instanceof Player )) {
            Bukkit.getConsoleSender().sendMessage(plugin.name+"You cant execute this command from console.");
            return false;
        }else {
            Player player = (Player) sender;
            if(!player.getWorld().getName().equals("world")){
                PostAPI.sendMessage(player,"&cYou have to be in the Overworld to use this command.");
                return false;
            }
            //get distance between posts and width from config.yml
            int gap = plugin.getConfig().getInt("distance-between-posts");
            int width = (plugin.getConfig().getInt("post-width")-1)/2;

            // for the X axis
            int originX = plugin.getConfig().getInt("post-x-location");
            int postX = PostAPI.getNearPost(gap,player.getLocation().getBlockX(),originX);

            // for the Z axis
            int originZ = plugin.getConfig().getInt("post-z-location");
            int postZ = PostAPI.getNearPost(gap,player.getLocation().getBlockZ(),originZ);

            // If the player is not inside a post and does not have telepost.homepost permission, he won't be teleported
            if(postX>=0&&!player.hasPermission("telepost.homepost")){
                if(player.getLocation().getBlockX()<postX-width||player.getLocation().getBlockX()>postX+width){
                    PostAPI.sendMessage(player,"&cYou have to be inside a post to use this command, try /nearestpost.");
                    return false;
                }
            }
            if(postX<0&&!player.hasPermission("telepost.homepost")){
                if(player.getLocation().getBlockX()>postX+width||player.getLocation().getBlockX()<postX-width){
                    PostAPI.sendMessage(player,"&cYou have to be inside a post to use this command, try /nearestpost.");
                    return false;
                }
            }
            if(postZ>=0&&!player.hasPermission("telepost.homepost")){
                if(player.getLocation().getBlockZ()<postZ-width||player.getLocation().getBlockZ()>postZ+width){
                    PostAPI.sendMessage(player,"&cYou have to be inside a post to use this command, try /nearestpost.");
                    return false;
                }
            }
            if(postZ<0&&!player.hasPermission("telepost.homepost")){
                if(player.getLocation().getBlockZ()>postZ+width||player.getLocation().getBlockZ()<postZ-width){
                    PostAPI.sendMessage(player,"&cYou have to be inside a post to use this command, try /nearestpost.");
                    return false;
                }
            }
            ATPlayer atPlayer = ATPlayer.getPlayer(player);
            if(atPlayer.hasHome("home")) {
                Location location = atPlayer.getHome("home").getLocation();
                if(location.getBlockX()==postX&&location.getBlockZ()==postZ){
                    PostAPI.sendMessage(player,"&cYou are already at your home post.");
                    return false;
                }
                World world = player.getWorld();
                if(plugin.getConfig().getBoolean("launch-feature")){
                    player.setVelocity(new Vector(0,4,0));
                    Bukkit.getScheduler().runTaskLater(plugin, () -> player.setVelocity(new Vector (0,2.5,0)), 25L);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        Location newlocation = new Location(world, location.getBlockX() + 0.5, 260, location.getBlockZ() + 0.5,player.getLocation().getYaw(),player.getLocation().getPitch());
                        player.teleport(newlocation);
                        player.playSound(newlocation, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,1f,1f);
                        PostAPI.sendMessage(player, "&7Welcome to your post.");
                    }, 40L);
                }else{
                    // Teleport player to his home without launch feature
                    Location newlocation = new Location(world, location.getBlockX() + 0.5, 260, location.getBlockZ() + 0.5,player.getLocation().getYaw(),player.getLocation().getPitch());
                    player.teleport(newlocation);
                    player.playSound(newlocation, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,1f,1f);
                    PostAPI.sendMessage(player,"&7Welcome to your post.");
                }
                return true;
            }else{
                // Player does not have a homepost
                PostAPI.sendMessage(player,"&aPlease, set a post with /SetPost first.");
            }
            return true;
        }
    }

}
