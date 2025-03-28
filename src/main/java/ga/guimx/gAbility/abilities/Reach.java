package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Reach extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.REACH);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        double baseEntityValue = player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).getBaseValue();
        double baseBlockValue = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getBaseValue();
        player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(baseEntityValue*2);
        player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).setBaseValue(baseBlockValue*2);
        PlayerInfo.getPlayersWithReach().add(player.getUniqueId());
        Task.runLater( () -> {
            player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(baseEntityValue);
            player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).setBaseValue(baseBlockValue);
            PlayerInfo.getPlayersWithReach().remove(player.getUniqueId());
        }, getAbility().getDuration()*20);
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
    }
}