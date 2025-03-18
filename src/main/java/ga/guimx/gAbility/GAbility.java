package ga.guimx.gAbility;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import ga.guimx.gAbility.commands.CooldownCommand;
import ga.guimx.gAbility.commands.GiveAbilityCommand;
import ga.guimx.gAbility.commands.ReloadConfigCommand;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.listeners.AbilityUsageListener;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityArgument;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.CommandInvalidUsage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class GAbility extends JavaPlugin {

    @Getter
    public static GAbility instance;
    @Getter
    @Setter
    private static String prefix;
    private LiteCommands<CommandSender> liteCommands;
    @Override
    public void onEnable() {
        instance = this;
        PluginConfig.getInstance().load();
        liteCommands = LiteBukkitFactory.builder("gability",this)
                        .commands(new CooldownCommand(),new ReloadConfigCommand(),new GiveAbilityCommand())
                        .argument(Ability.class, new AbilityArgument())
                        .invalidUsage(new CommandInvalidUsage())
                        .build();
        getServer().getPluginManager().registerEvents(new AbilityUsageListener(),this);
        Bukkit.getConsoleSender().sendMessage(Chat.translate(prefix+PluginConfig.getMessages().get("plugin_enabled")));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Chat.translate(prefix+PluginConfig.getMessages().get("plugin_disabled")));
    }
}
