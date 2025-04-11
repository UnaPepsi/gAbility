package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CloseCall extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.CLOSE_CALL);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        item.setAmount(item.getAmount()-1);
        if (player.getHealth() < 4*2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, (int) (getAbility().getDuration() * 20), 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) (getAbility().getDuration() * 20), 4));
            player.sendMessage(Chat.translate(GAbility.getPrefix() + getAbility().getUsedMessage()));
        }else{
            player.sendMessage(Chat.translate(GAbility.getPrefix() + getAbility().getErrorMessage()));
        }
    }
}