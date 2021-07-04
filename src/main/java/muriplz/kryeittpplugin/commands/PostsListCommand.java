package muriplz.kryeittpplugin.commands;

import io.github.niestrat99.advancedteleport.api.Warp;
import muriplz.kryeittpplugin.KryeitTPPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PostsListCommand implements CommandExecutor {
    private final KryeitTPPlugin plugin;

    public PostsListCommand(KryeitTPPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.name+"You can't execute this command from console.");
            return false;
        }else{
            // Get player
            Player player = (Player) sender;

            // Get all warps (named posts)
            HashMap<String, Warp> warps = Warp.getWarps();

            // Get all names of named posts and made it into a List
            Set<String> warpNames = warps.keySet();

            // If there are no named posts (Warps) then just return false
            if(warpNames.isEmpty()) {
                PostAPI.sendMessage(player,"&cThere are no named posts.");
                return false;
            }

            // Place all the warpNames into an arraylist
            List<String> allWarpNames = new ArrayList<>(warpNames);

            // Initialize all the TextComponents
            TextComponent messagePosts = new TextComponent();
            TextComponent message;

            // Header
            if(Warp.getWarps().size()<9){
                messagePosts.addExtra(ChatColor.GOLD+"Named posts: ");
            }else{
                PostAPI.sendMessage(player, "&6-----------------------------------------------------");
                PostAPI.sendMessage(player, "                                 &6Named posts ");
                PostAPI.sendMessage(player, "&6-----------------------------------------------------");
            }


            // Sort all warp names
            java.util.Collections.sort(allWarpNames);

            // Initialize the strings
            String commandString;
            String hoverText;

            // Add to messagePosts all components to teleport to every warp
            for (String warpName : allWarpNames) {
                message = new TextComponent(" " +ChatColor.WHITE+ warpName);
                commandString = "/v " + warpName;
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandString ));
                Location loc = Warp.getWarps().get(warpName).getLocation();
                hoverText = "Click to teleport to " + warpName + " post.\nThis post is at "+ChatColor.GOLD+"("+loc.getBlockX()+" , "+loc.getBlockZ()+")"+ChatColor.WHITE+".";
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
                messagePosts.addExtra(message);
            }
            // Send the message with the Text components to the player
            player.spigot().sendMessage(messagePosts);
            if(Warp.getWarps().size()>=9){
                PostAPI.sendMessage(player, "&6-----------------------------------------------------");
            }
            return true;
        }
    }
}
