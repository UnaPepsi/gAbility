package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.Collection;

public class RageBall extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.RAGE_BALL);}
    @Override
    public void handle(Player player, @Nullable ItemStack item, Object... extraArgs){
        abilityLogic(player, item, extraArgs);
    }
    @Override
    protected void abilityLogic(Player player, @Nullable ItemStack nullItem, Object... extraArgs) {
        Collection<Player> playersCaught = (Collection<Player>) extraArgs[0];
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, (int) (getAbility().getDuration()*20),1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, (int) (getAbility().getDuration()*20),2));
        player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getUsedMessage()));
        playersCaught.forEach(p -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (int) (getAbility().getDuration()*20),1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (getAbility().getDuration()*20),1));
            p.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getMessageTargets()
                    .replace("%player%",player.getName())));
        });
    }
}