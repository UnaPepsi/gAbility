package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.Task;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class PortableBard extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.PORTABLE_BARD);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... extraArgs) {
        item.setAmount(item.getAmount()-1);
        player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getUsedMessage()));
        Location loc = (Location) extraArgs[0];
        LivingEntity entitySpawned = (LivingEntity) loc.getWorld().spawn(loc,getAbility().getEntityType().getEntityClass(), entity -> {
            LivingEntity livingEntity = (LivingEntity) entity;
            try {
                livingEntity.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
                livingEntity.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
                livingEntity.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
                livingEntity.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
            } catch(NullPointerException ignored){}
            livingEntity.setAI(false);
            livingEntity.setCollidable(false);
            livingEntity.setCustomName(Chat.translate(getAbility().getName()+" &c♥20"));
            livingEntity.getPersistentDataContainer().set(GAbility.getKey(), PersistentDataType.STRING,"portable_bard");
            livingEntity.setCustomNameVisible(true);
            livingEntity.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20);
            livingEntity.setHealth(20);
        });
        HashMap<PotionEffectType,short[]> potionEffectsAmplifiers = new HashMap<>(){{
            put(PotionEffectType.STRENGTH, new short[]{0, 1});
            put(PotionEffectType.RESISTANCE, new short[]{0, 2});
            put(PotionEffectType.REGENERATION, new short[]{0, 2});
            put(PotionEffectType.SPEED, new short[]{1, 2});
        }};
        Task.runTimer(runnable -> {
            if (entitySpawned.isDead()){
                runnable.cancel();
                return;
            }
            double radius = getAbility().getRadius();
            for (int angle = 0; angle <= 360; angle+=5){
                player.spawnParticle(Particle.DUST,
                        entitySpawned.getX()+Math.cos(Math.toRadians(angle))*radius,
                        entitySpawned.getY(),
                        entitySpawned.getZ()+Math.sin(Math.toRadians(angle))*radius,5, new Particle.DustOptions(Color.YELLOW,10));
            }
            Material[] materials = {Material.BLAZE_POWDER,Material.IRON_INGOT,Material.GHAST_TEAR,Material.SUGAR};
            entitySpawned.getEquipment().setItemInMainHand(new ItemStack(materials[GAbility.getRandom().nextInt(0,4)]));

            try {
                if (entitySpawned.getLocation().distance(player.getLocation()) > getAbility().getRadius()) {
                    return;
                }
            } catch(IllegalArgumentException e){
                return;
            }

            //int rand = -1;
            int rand;
            if (Math.random() < 0.3) {
                rand = GAbility.getRandom().nextInt(0, 4);
            } else {
                rand = -1;
            }
            potionEffectsAmplifiers.keySet().forEach(potion -> {
                int amplifier = rand != -1 && potionEffectsAmplifiers.keySet().stream().toList().get(rand) == potion ? 1 : 0;
                player.addPotionEffect(new PotionEffect(potion,5*20,potionEffectsAmplifiers.get(potion)[amplifier]));
            });
        },0,5*20);
        Task.runLater(() -> {
            if (!entitySpawned.isDead()) {
                entitySpawned.getWorld().spawnParticle(Particle.DUST,
                        entitySpawned.getEyeLocation(),
                        100,
                        Math.random()-0.5*3,Math.random()-0.5*3,Math.random()-0.5*3,
                        new Particle.DustOptions(Color.WHITE,2)
                );
                entitySpawned.remove();
            }
        }, getAbility().getDuration()*20);
    }
}