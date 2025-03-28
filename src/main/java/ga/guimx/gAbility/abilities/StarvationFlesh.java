package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StarvationFlesh extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.STARVATION_FLESH);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... args) {
        Player victim = (Player) args[0];
        player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getUsedMessage()
                .replace("%player%",victim.getName())));
        victim.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getMessageTargets()
                .replace("%player%",player.getName())));
        victim.setFoodLevel(0);
        item.setAmount(item.getAmount()-1);
    }
}