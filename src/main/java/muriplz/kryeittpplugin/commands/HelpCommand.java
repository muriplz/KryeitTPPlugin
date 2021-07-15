package muriplz.kryeittpplugin.commands;

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

import java.util.List;

public class HelpCommand implements CommandExecutor {
    public void setTextComponent(String command, TextComponent textComponent) {
        String commandString = "/posthelp " + command;
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandString));
        String hoverText = "Click to get more information about " + ChatColor.GOLD + "/" + command + ChatColor.WHITE + ".";
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
    }

    public void sendMessage(Player player, String message) {
        PostAPI.sendMessage(player, message);
    }

    private final KryeitTPPlugin plugin;

    public HelpCommand(KryeitTPPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(plugin.name + "You can't execute this command from console.");
            return false;
        } else {
            Player player = (Player) sender;

            // /posthelp
            if (args.length == 0) {


                sendMessage(player, "&aList of commands: &f( + info on /posthelp <Command> )");
                sendMessage(player, "- &6/nearestpost&f: tells you where the nearest post is.");
                sendMessage(player, "- &6/setpost&f: sets the nearest post as your home post.");
                sendMessage(player, "- &6/homepost&f: teleports you to your home post.");
                sendMessage(player, "- &6/visit <PostName/Player>&f: teleports you to another post.");
                sendMessage(player, "- &6/invite <Player>&f: invite a player to your home post.");
                sendMessage(player, "- &6/postlist&f: shows you all the named posts.");
                if (plugin.getConfig().getBoolean("random-post")) {
                    sendMessage(player, "- &6/randompost&f: teleports to a random post.");
                }
                if (player.hasPermission("telepost.namepost") || player.hasPermission("telepost.unnamepost") || player.hasPermission("telepost.buildpost")) {
                    sendMessage(player, "&aAdmin commands: ");
                    if (player.hasPermission("telepost.namepost")) {
                        sendMessage(player, "- &6/namepost <Name>&f: names the nearest post.");
                    }
                    if (player.hasPermission("telepost.unnamepost")) {
                        sendMessage(player, "- &6/unnamepost <Name>&f: unnames a post.");
                    }
                    //                 if(player.hasPermission("telepost.buildpost")){
                    //                     PostAPI.sendMessage(player,"- &6/buildpost (y)&f: builds the nearest post.");
                    //                 }
                }

                return true;
            } else if (args.length == 1) {
                //              if(args[0].equals("buildpost") && player.hasPermission("telepost.buildpost")) {
                //                  PostAPI.sendMessage(player,"&a/BuildPost guide: ");
                //                  PostAPI.sendMessage(player,"- &6/buildpost&f: builds the nearest post.");
                //                  PostAPI.sendMessage(player,"- &6/buildpost (y)&f: builds the nearest post at a certain height.");
                //                  PostAPI.sendMessage(player,"- &6/buildpost (x) (z)&f: builds a post on that location but on the ground level.");
                //                  PostAPI.sendMessage(player,"- &6/buildpost (x) (y) (z)&f: builds a post on that location.");
                //                  PostAPI.sendMessage(player,"NOTE: I do not recommend building posts with (x) and (z) provided by you, because the teleport system won't work.");
                if (args[0].equals("aliases") || args[0].equals("alias")) {
                    sendMessage(player, "&aAll aliases for your commands: ");
                    sendMessage(player, "- &6/h&f: alias for /homepost.");
                    sendMessage(player, "- &6/v&f: alias for /visit.");
                    sendMessage(player, "- &6/plist&f: alias for /postlist.");

                } else if (args[0].equals("nearestpost")) {
                    sendMessage(player, "&a/NearestPost guide: ");
                    sendMessage(player, "- There is a post every &6" + plugin.getConfig().getInt("distance-between-posts") + "&f blocks.");
                    sendMessage(player, "- You can only use teleports inside posts.");
                    sendMessage(player, "- Use F3 to see your own coordinates.");
                    sendMessage(player, "- You can also use &6/nearestpost on/off&f.");
                } else if (args[0].equals("setpost")) {
                    sendMessage(player, "&a/SetPost guide: ");
                    sendMessage(player, "- This command will set a home for you on the nearest post.");
                    sendMessage(player, "- You can visit your home post using &6/homepost&f.");
                    sendMessage(player, "- You can only have 1 home post at a time.");
                } else if (args[0].equals("homepost")) {
                    sendMessage(player, "&a/HomePost guide: ");
                    sendMessage(player, "- This command will teleport to your home post.");
                    sendMessage(player, "- You can set your home post using &6/setpost&f.");
                    if (!player.hasPermission("telepost.homepost")) {
                        sendMessage(player, "- You can only use this command if you are inside a post.");
                    }
                    sendMessage(player, "- You can only have 1 home post at a time.");
                } else if (args[0].equals("visit")) {
                    sendMessage(player, "&a/Visit guide: ");
                    sendMessage(player, "- This command will teleport to your destination.");
                    sendMessage(player, "- You can be invited to a post with &6/invite&f.");
                    sendMessage(player, "- You can visit &6Named Posts&f.");
                    if (!player.hasPermission("telepost.visit")) {
                        sendMessage(player, "- You can only use this command if you are inside a post.");
                    }
                } else if (args[0].equals("postlist")) {
                    sendMessage(player, "&a/PostList guide: ");
                    sendMessage(player, "- This command will show you all named posts.");
                    sendMessage(player, "- You can click the names to teleport to the destination.");
                    sendMessage(player, "- You also have to be inside a post to teleport.");
                    sendMessage(player, "- You can use &6/postlist&f anywhere.");
                } else if (args[0].equals("invite")) {
                    sendMessage(player, "&a/Invite guide: ");
                    sendMessage(player, "- You can invite a player to your home post.");
                    sendMessage(player, "- He will have 5 minutes to teleport as much as he wants.");
                    sendMessage(player, "- This command can be used anywhere.");
                    sendMessage(player, "- You will get notified if an invite expires.");
                } else if (args[0].equals("namepost") && player.hasPermission("telepost.namepost")) {
                    sendMessage(player, "&a/NamePost guide: ");
                    sendMessage(player, "- You can set a name for the nearest post.");
                    sendMessage(player, "- The named post will be accesible for everyone.");
                    if (plugin.getConfig().getBoolean("multiple-names-per-post")) {
                        sendMessage(player, "- You can give the same post different names.");
                    } else {
                        sendMessage(player, "- You can only set one name per post.");
                    }
                    sendMessage(player, "- This command is only for Admins.");
                } else if (args[0].equals("unnamepost") && player.hasPermission("telepost.unnamepost")) {
                    sendMessage(player, "&a/UnnamePost guide: ");
                    sendMessage(player, "- You can delete the name of any named post.");
                    sendMessage(player, "- Comming soon: /unnamepost unnames the nearest post.");
                    sendMessage(player, "- This command is only for Admins.");
                } else if (args[0].equals("randompost")) {
                    List<Location> allPosts =  PostAPI.getAllPostLocations(plugin);
                    List<Location> allNamedAndHomed = PostAPI.getAllNamedAndHomed();
                    allNamedAndHomed = PostAPI.removeLocDuplicates(allNamedAndHomed);
                    int availablePosts = allPosts.size()-allNamedAndHomed.size();
                    sendMessage(player, "&a/RandomPost guide: ");
                    sendMessage(player, "- There are &6"+availablePosts+ "&f available posts to randomly teleport.");
                    sendMessage(player, "- Total post amount: &6"+allPosts.size()+"&f.");
                    sendMessage(player, "- You can use the command once every 3 days.");
                    sendMessage(player, "- You have to be standing on a post to use it.");
                } else {
                    sendMessage(player, "Use /posthelp or /posthelp <Command>");
                    return false;
                }

                return true;
            }
        }
    return false;
    }
}
