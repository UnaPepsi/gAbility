package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shrinker extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.SHRINKER);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        double baseSize = player.getAttribute(Attribute.SCALE).getBaseValue();
        player.getAttribute(Attribute.SCALE).setBaseValue(baseSize/2);
        PlayerInfo.getPlayersShrunk().add(player.getUniqueId());
        Task.runLater(() -> {
            player.getAttribute(Attribute.SCALE).setBaseValue(baseSize);
            PlayerInfo.getPlayersShrunk().remove(player.getUniqueId());
        }, getAbility().getDuration()*20);
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
    }
}