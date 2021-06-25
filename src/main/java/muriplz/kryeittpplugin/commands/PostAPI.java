package muriplz.kryeittpplugin.commands;

import muriplz.kryeittpplugin.KryeitTPPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

public class PostAPI {
    public KryeitTPPlugin plugin;
    public PostAPI(KryeitTPPlugin plugin) {
        this.plugin = plugin;
    }
    public static int getNearPost(int gap, int player, int origin) {
        // Subtracting origin of posts to get correct calculation
        player -= origin;

        // Setting the post variable
        float post = player;

        // Getting the closest multiple of gap to post
        post = post / gap;
        post = Math.round(post);
        post = post * gap;

        // Adding origin of posts to finish calculation
        post += origin;

        // Returning the number
        return (int) post;
    }

    public static void playSoundAfterTp(Player player,Location location){
        player.playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,1f,1f);
    }

    public static void sendMessage(Player player,String message){
        // Sending the message with & instead of §
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    public static void loadAllChunksToBuildThePost(Block block, int width){
        // Getting block x and z coords
        int x = block.getX();
        int z = block.getZ();

        // Loading the chunks that are in the area of the base
        for (int i=x-width;i<=x+width;i++) {
            for (int j=z-width;j<=z+width;j++) {
                // Getting the chunk the block is in
                Chunk chunk = Objects.requireNonNull(Bukkit.getWorld("world")).getChunkAt(i,j);

                // Checking if the chunk is unloaded and if it isn't load it
                if (!chunk.isLoaded()) {
                    chunk.load();
                }
            }
        }
    }
    public static void launch(Player player,Location location){



    }
    public static void unloadAllChunksToBuildThePost(Block block,int width){
        // Getting block x and z coords
        int x = block.getX();
        int z = block.getZ();

        // Unloading the chunks that are in the area of the base
        for (int i=x-width;i<=x+width;i++) {
            for (int j = z - width; j <= z + width; j++) {
                // Getting the chunk the block is in
                Chunk chunk = Objects.requireNonNull(Bukkit.getWorld("world")).getChunkAt(i, j);

                // Checking if the chunk is loaded and if it is unload it
                if (chunk.isLoaded()) {
                    chunk.unload();
                }
            }
        }
    }
    public static boolean isPlayerOnPost(Player player, int originX, int originZ, int width, int gap) {
        // Getting the coords of nearest post to the player
        int postX = PostAPI.getNearPost(gap,player.getLocation().getBlockX(),originX);
        int postZ = PostAPI.getNearPost(gap,player.getLocation().getBlockZ(),originZ);

        // Getting player x and z coords
        int playerX = player.getLocation().getBlockX();
        int playerZ = player.getLocation().getBlockZ();

        // Returning true if on post else false
        return playerX < postX - width || playerX > postX + width && playerZ < postZ - width || playerZ > postZ + width;
    }
    public static void launchOnTP(Player player, String message, Location postLocation){
        double initialYDirection = player.getVelocity().getY();
        do {
            if (player.getVelocity().getBlockY() < initialYDirection*2) {
                player.getVelocity().setY(initialYDirection*2);
            }
        } while (player.getLocation().getY() < 255);
        player.teleport(postLocation);
        PostAPI.sendMessage(player,message);
        player.playSound(postLocation, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,1f,1f);
    }
    public static int getFirstSolidBlockHeight(int X, int Z){
        // Setting the highest height the post can be at
        int height = 251;

        // Looping down and searching for a solid block or water or lava
        while (true){
            Location l = new Location(Bukkit.getWorld("world"), X, height, Z);
            if(l.getBlock().getType().isSolid() || l.getBlock().getType().equals(Material.WATER) || l.getBlock().getType().equals(Material.LAVA)){
                break;
            }
            height--;
        }
        return height;
    }
}