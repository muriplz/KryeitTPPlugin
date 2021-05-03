package muriplz.kryeittpplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class VisitTab implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length==1){
            List<String> commands = new ArrayList<>();
            List<String> completions = new ArrayList<>();
            commands.add("Pangea");
            commands.add("Fossil");
            commands.add("Agua");
            commands.add("Magma");
            commands.add("Trident");
            commands.add("Seahorse");
            commands.add("Rock");
            commands.add("Extremadura");
            commands.add("Bee");
            commands.add("Gaja");
            Bukkit.getOnlinePlayers().forEach(p -> commands.add(p.getName()));
            //Sort the list and show it to the player
            int i=0;
            while(i< commands.size()){
                if(commands.get(i).toLowerCase().contains(args[0])||commands.get(i).contains(args[0])){
                    completions.add(commands.get(i));
                }
                i++;
            }
            return completions;
        }
        return null;
}}
