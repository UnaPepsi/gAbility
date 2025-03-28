package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GuardianAngelListener implements Listener {
    @EventHandler
    void onDeath(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getFinalDamage() > player.getHealth() && PlayerInfo.getPlayersWithGuardianAngel().contains(player.getUniqueId())){
            event.setDamage(0);
            player.setHealth(player.getAttribute(Attribute.MAX_HEALTH).getBaseValue());
            PlayerInfo.getPlayersWithGuardianAngel().remove(player.getUniqueId());
            player.sendMessage(Chat.translate(GAbility.getPrefix()+ Ability.fromAbilityType(AbilityType.GUARDIAN_ANGEL).getMessageTargets()));
        }
    }
}
