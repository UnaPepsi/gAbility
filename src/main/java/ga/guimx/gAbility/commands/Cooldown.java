package ga.guimx.gAbility.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.PlayerCooldown;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Command(name = "ability cooldown",aliases = {"gability cooldown"})
@Permission("gability.cooldown")
public class Cooldown {
    @Execute(name="set")
    void setCooldown(@Context CommandSender commandSender, @Arg Player player, @Arg Ability ability, @Arg long cooldown){
        //commandSender.sendMessage("ability: "+ability.getName()+ability.getLore()+ability.getCooldown());
        if (PlayerCooldown.getAbilityCooldowns().containsKey(player.getUniqueId())) {
            PlayerCooldown.getAbilityCooldowns().get(player.getUniqueId()).put(ability, cooldown);
        }else{
            PlayerCooldown.getAbilityCooldowns().put(player.getUniqueId(),new HashMap<>(){{put(ability,cooldown);}});
        }
        commandSender.sendMessage("done");
    }
}