package ga.guimx.gAbility.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.command.CommandSender;

@Command(name="gability info",aliases = {"ability info"})
public class PluginInfoCommand {

    @Execute
    void executePluginInfo(@Context CommandSender sender) {
        sender.sendMessage(Chat.translate("&6gAbility\n&f- Version %s\n&f- Made by guimx :)", GAbility.getInstance().getPluginMeta().getVersion()));
    }
}
