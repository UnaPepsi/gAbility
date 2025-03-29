package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class PortableBardListener implements Listener {
    @EventHandler
    void onEntityDamage(EntityDamageEvent event){
        String data = event.getEntity().getPersistentDataContainer().get(GAbility.getKey(), PersistentDataType.STRING);
        if (!"portable_bard".equals(data)) return;
        LivingEntity entity = (LivingEntity) event.getEntity();
        event.setDamage(0);
        if (entity.getHealth() <= 1) {
        entity.remove();
        entity.getWorld().spawnParticle(Particle.DUST,
                entity.getEyeLocation(),
                100,
                Math.random()-0.5*3,Math.random()-0.5*3,Math.random()-0.5*3,
                new Particle.DustOptions(Color.WHITE,2)
            );
        }
        entity.setHealth(entity.getHealth()-1);
        entity.setCustomName(Chat.translate(Ability.fromAbilityType(AbilityType.PORTABLE_BARD).getName()+" &c♥"+(int)entity.getHealth()));
    }
}
