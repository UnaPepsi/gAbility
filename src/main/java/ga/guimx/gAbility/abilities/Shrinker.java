package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Shrinker extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.SHRINKER);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        double baseSize = player.getAttribute(Attribute.SCALE).getBaseValue();
        player.getAttribute(Attribute.SCALE).setBaseValue(baseSize/2);
        PlayerInfo.getPlayersShrunk().add(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(GAbility.getInstance(), () -> {
            player.getAttribute(Attribute.SCALE).setBaseValue(baseSize);
            PlayerInfo.getPlayersShrunk().remove(player.getUniqueId());
        }, getAbility().getDuration()*20);
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
    }
}