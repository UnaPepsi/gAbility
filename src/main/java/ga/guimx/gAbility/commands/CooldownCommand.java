package ga.guimx.gAbility.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.literal.Literal;
import dev.rollczi.litecommands.annotations.permission.Permission;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Command(name = "gability cooldown",aliases = {"ability cooldown"})
public class CooldownCommand {

    @Execute
    @Permission("gability.cooldown")
    void setCooldown(@Context CommandSender commandSender, @Arg Player player, @Arg Ability ability, @Arg long seconds){
        //commandSender.sendMessage("ability: "+ability.getName()+ability.getLore()+ability.getCooldown());
        if (PlayerInfo.getAbilityCooldowns().containsKey(player.getUniqueId())) {
            PlayerInfo.getAbilityCooldowns().get(player.getUniqueId()).put(ability, System.currentTimeMillis()+seconds*1000);
        }else{
            PlayerInfo.getAbilityCooldowns().put(player.getUniqueId(),new HashMap<>(){{put(ability,System.currentTimeMillis()+seconds*1000);}});
        }
        commandSender.sendMessage("done");
    }
    @Execute
    @Permission("gability.cooldown")
    void setGlobalCooldown(@Context CommandSender sender, @Arg Player player, @Literal("global") String literal, @Arg long seconds){
        PlayerInfo.getGlobalAbilityCooldowns().put(player.getUniqueId(),System.currentTimeMillis()+seconds*1000);
        sender.sendMessage("done");
    }
    @Execute
    @Permission("gability.cooldown")
    void setAllCooldown(@Context CommandSender sender, @Arg Player player, @Literal("all") String literal, @Arg long seconds){
        for (AbilityType abilityType : AbilityType.values()){
            Ability ability = Ability.fromAbilityType(abilityType);
            if (PlayerInfo.getAbilityCooldowns().containsKey(player.getUniqueId())) {
                PlayerInfo.getAbilityCooldowns().get(player.getUniqueId()).put(ability, System.currentTimeMillis()+seconds*1000);
            }else{
                PlayerInfo.getAbilityCooldowns().put(player.getUniqueId(),new HashMap<>(){{put(ability,System.currentTimeMillis()+seconds*1000);}});
            }
        }
        sender.sendMessage("done");
    }
}