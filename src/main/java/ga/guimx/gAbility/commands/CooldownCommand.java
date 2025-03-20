package ga.guimx.gAbility.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.literal.Literal;
import dev.rollczi.litecommands.annotations.permission.Permission;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Command(name = "ability cooldown",aliases = {"gability cooldown"})
@Permission("gability.cooldown")
public class CooldownCommand {
    @Execute(name="set")
    void setCooldown(@Context CommandSender commandSender, @Arg Ability ability, @Arg Player player, @Arg long cooldown){
        //commandSender.sendMessage("ability: "+ability.getName()+ability.getLore()+ability.getCooldown());
        if (PlayerInfo.getAbilityCooldowns().containsKey(player.getUniqueId())) {
            PlayerInfo.getAbilityCooldowns().get(player.getUniqueId()).put(ability, System.currentTimeMillis()+cooldown*1000);
        }else{
            PlayerInfo.getAbilityCooldowns().put(player.getUniqueId(),new HashMap<>(){{put(ability,System.currentTimeMillis()+cooldown*1000);}});
        }
        commandSender.sendMessage("done");
    }
    @Execute(name="set")
    void setGlobalCooldown(@Context CommandSender sender, @Literal("global") String literal, @Arg Player player, @Arg long cooldown){
        PlayerInfo.getGlobalAbilityCooldowns().put(player.getUniqueId(),System.currentTimeMillis()+cooldown*1000);
        sender.sendMessage("done");
    }
    //TODO: set global cd and "all" cd
}