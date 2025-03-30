package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Berserk extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.BERSERK);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, (int) (getAbility().getDuration()*20),1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) (getAbility().getDuration()*20),2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) (getAbility().getDuration()*20),2));
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        PlayerInfo.getPlayersWithBerserk().add(player.getUniqueId());
        item.setAmount(item.getAmount()-1);
        Task.runLater(() -> PlayerInfo.getPlayersWithBerserk().remove(player.getUniqueId()), getAbility().getDuration()*20);
    }
}