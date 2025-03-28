package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Combo extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.COMBO);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
        PlayerInfo.getComboHitCounter().put(player.getUniqueId(),0);
        Task.runLater(() -> {
            int duration = PlayerInfo.getComboHitCounter().get(player.getUniqueId()) / 2 * 20;
            player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, duration,1));
            PlayerInfo.getComboHitCounter().remove(player.getUniqueId());
        }, getAbility().getDuration() * 20);
    }
}