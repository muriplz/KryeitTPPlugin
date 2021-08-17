package muriplz.kryeittpplugin.Listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import muriplz.kryeittpplugin.KryeitTPPlugin;
import muriplz.kryeittpplugin.commands.PostAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onPlayerMovePAPI implements Listener {
    @EventHandler
    public void OnMovePAPI(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        int i = KryeitTPPlugin.getInstance().counter;

        if(i>50){
            // for the X axis
            int originX = KryeitTPPlugin.getInstance().getConfig().getInt("post-x-location");
            int postX = PostAPI.getNearPost(player.getLocation().getBlockX(),originX);
            // for the Z axis
            int originZ = KryeitTPPlugin.getInstance().getConfig().getInt("post-z-location");
            int postZ = PostAPI.getNearPost(player.getLocation().getBlockZ(),originZ);

            String postName = PostAPI.NearestPostName(player);
            PlaceholderAPI.setPlaceholders(player,"("+postX+" , "+postZ+")");
            KryeitTPPlugin.getInstance().counter = 0;
        }
        KryeitTPPlugin.getInstance().counter += 1;
    }
}
