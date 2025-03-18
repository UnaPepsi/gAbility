package ga.guimx.gAbility.abilities;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Strength extends BaseAbility{

    @Override
    protected void abilityLogic(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,5*20,1));
        player.sendMessage("gave s2 for 5s");
    }
}