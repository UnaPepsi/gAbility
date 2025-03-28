package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class Switcher extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.SWITCHER);}
    @Override
    public void handle(Player player, @Nullable ItemStack item, Object... extraArgs){
        abilityLogic(player, item, extraArgs);
    }
    @Override
    protected void abilityLogic(Player player, @Nullable ItemStack nullItem, Object... extraArgs) {
        Player targetPlayer = (Player) extraArgs[0];
        Location origialLocation = player.getLocation().clone();
        player.teleport(targetPlayer.getLocation());
        targetPlayer.teleport(origialLocation);
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage().replace("%target%",targetPlayer.getName())));
        targetPlayer.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getMessageTargets().replace("%player%", player.getName())));
    }
}