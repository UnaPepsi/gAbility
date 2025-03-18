package ga.guimx.gAbility;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import ga.guimx.gAbility.commands.Cooldown;
import ga.guimx.gAbility.listeners.AbilityUsageListener;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityArgument;
import ga.guimx.gAbility.utils.CommandInvalidUsage;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class GAbility extends JavaPlugin {

    @Getter
    public static GAbility instance;
    private LiteCommands<CommandSender> liteCommands;
    @Override
    public void onEnable() {
        instance = this;
        liteCommands = LiteBukkitFactory.builder("gability",this)
                        .commands(new Cooldown())
                        .argument(Ability.class, new AbilityArgument())
                        .invalidUsage(new CommandInvalidUsage())
                        .build();
        getServer().getPluginManager().registerEvents(new AbilityUsageListener(),this);
        Bukkit.getConsoleSender().sendMessage("gAbility enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("gAbility disabled");
    }
}
