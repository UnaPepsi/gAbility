package ga.guimx.gAbility.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.command.CommandSender;

@Command(name = "gability reload",aliases = {"ability reload"})
@Permission("gability.admin")
public class ReloadConfigCommand {

    @Execute
    void executeReloadConfig(@Context CommandSender sender) {
        PluginConfig.getInstance().reload();
        sender.sendMessage(Chat.translate(GAbility.getPrefix()+PluginConfig.getMessages().get("plugin_reloaded")));
    }

}
