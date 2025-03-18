package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Resistance extends BaseAbility{

    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        Ability ability = PluginConfig.getAbilities().get(AbilityType.RESISTANCE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) (ability.getDuration()*20),1));
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ ability.getUsedMessage()));
        item.setAmount(item.getAmount()-1);
    }
}