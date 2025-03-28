package ga.guimx.gAbility;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.message.LiteMessages;
import ga.guimx.gAbility.commands.*;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.listeners.*;
import ga.guimx.gAbility.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Random;

public final class GAbility extends JavaPlugin {

    @Getter
    private static GAbility instance;
    @Getter
    @Setter
    private static String prefix;
    @Getter
    private static NamespacedKey key;
    @Getter
    private static final Random random = new Random();
    private LiteCommands<CommandSender> liteCommands;
    @Override
    public void onEnable() {
        instance = this;
        key = new NamespacedKey(instance,"ability");
        PluginConfig.getInstance().load();
        liteCommands = LiteBukkitFactory.builder("gability",this)
                        .commands(new CooldownCommand(),new ReloadConfigCommand(),new GiveAbilityCommand(),new PluginInfoCommand(),new TestCommand())
                        .argument(Ability.class, new AbilityArgument())
                        .invalidUsage(new CommandInvalidUsage())
                        .message(LiteMessages.MISSING_PERMISSIONS, permissions -> Chat.translate(prefix+PluginConfig.getMessages().get("no_permissions")
                                .replace("%missing_permissions%",permissions.asJoinedText())))
                        .build();
        enableListeners();
        Bukkit.getConsoleSender().sendMessage(Chat.translate(prefix+PluginConfig.getMessages().get("plugin_enabled")));
        checkForUpdates();
    }

    void enableListeners(){
        getServer().getPluginManager().registerEvents(new AbilityUsageListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(),this);
        getServer().getPluginManager().registerEvents(new ProjectileAbilityUsageListener(),this);
        getServer().getPluginManager().registerEvents(new AntitrapBoneListener(),this);
        getServer().getPluginManager().registerEvents(new NinjaStarListener(),this);
        getServer().getPluginManager().registerEvents(new ComboAbilityListener(),this);
        getServer().getPluginManager().registerEvents(new ConfuserListener(),this);
        getServer().getPluginManager().registerEvents(new FocusModeListener(),this);
        getServer().getPluginManager().registerEvents(new ZeusHammerListener(),this);
        getServer().getPluginManager().registerEvents(new AntitrapBeaconListener(),this);
        getServer().getPluginManager().registerEvents(new RiskyModeListener(),this);
        getServer().getPluginManager().registerEvents(new GuardianAngelListener(),this);
        getServer().getPluginManager().registerEvents(new PortableBardListener(),this);
        getServer().getPluginManager().registerEvents(new StarvationFleshListener(),this);
    }
    void checkForUpdates(){
        try{
            String newPossibleVersion = PluginUpdates.getLatestVersion();
            if (!newPossibleVersion.equals(getPluginMeta().getVersion())){
                Bukkit.getConsoleSender().sendMessage(Chat.translate(prefix+PluginConfig.getMessages().get("new_plugin_version_available")
                        .replace("%current_version%",getPluginMeta().getVersion())
                        .replace("%new_version%",newPossibleVersion)
                        .replace("%repository%","https://github.com/UnaPepsi/gAbility/releases")));
            }
        }catch (IOException e) {
            GAbility.getInstance().getLogger().warning("gAbility couldn't get the latest version of the plugin");
        }
    }

    @Override
    public void onDisable() {
        if (liteCommands != null) {
            liteCommands.unregister();
        }
        Bukkit.getConsoleSender().sendMessage(Chat.translate(prefix+PluginConfig.getMessages().get("plugin_disabled")));
    }
}
