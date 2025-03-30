package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Medkit extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.MEDKIT);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (int) (getAbility().getDuration()*20),4));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) (getAbility().getDuration()*20),2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) (getAbility().getDuration()*20),2));
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
    }
}