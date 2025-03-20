package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.AntitrapBone;
import ga.guimx.gAbility.abilities.Confuser;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class ConfuserListener implements Listener {
    private HashMap<UUID,Integer> hitsRemaining = new HashMap<>(); //attacker:hits
    private HashMap<UUID,UUID> playerCurrentTarget = new HashMap<>(); //attacker:victim
    private HashMap<UUID,Integer> playersWithMathQuestion = new HashMap<>(); //victim:math_question_answer
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player victim) ||
            !(event.getDamager() instanceof Player attacker) ||
            event.getDamageSource().isIndirect() //for example, a player shooting with a bow on their off-hand
        ){
            return;
        }
        ItemStack item = attacker.getInventory().getItemInMainHand();
        String itemType = item.getPersistentDataContainer().get(new NamespacedKey(GAbility.getInstance(),"ability"), PersistentDataType.STRING);
        if (itemType == null || !itemType.equals("confuser")) return;
        Confuser confuser = new Confuser();
        if (!confuser.checks(attacker)) return;
        if (PlayerInfo.getPlayersWithConfuser().contains(victim.getUniqueId())){
            attacker.sendMessage(Chat.translate(GAbility.getPrefix()+ Ability.fromAbilityType(AbilityType.CONFUSER).getErrorMessage()
                    .replace("%player%",victim.getName())));
            return;
        }
        if (playerCurrentTarget.getOrDefault(attacker.getUniqueId(),victim.getUniqueId()).equals(victim.getUniqueId())){
            hitsRemaining.put(attacker.getUniqueId(),hitsRemaining.getOrDefault(attacker.getUniqueId(),0)+1);
        //if player switches targets, the counter resets
        }else{
            hitsRemaining.put(attacker.getUniqueId(),1);
        }
        playerCurrentTarget.put(attacker.getUniqueId(),victim.getUniqueId());

        if (hitsRemaining.get(attacker.getUniqueId()) >= 3) {
            confuser.handle(attacker, item, victim);
            hitsRemaining.remove(attacker.getUniqueId());
            playerCurrentTarget.remove(attacker.getUniqueId());

            if (Math.random() < 0.2){
                //to try and avoid players being hit into the air, making it way harder to move their camera
                //but what if they were already in the air? ¯\_(ツ)_/¯
                victim.setVelocity(new Vector(0,0,0));

                int num1 = (int) (Math.random()*10);
                int num2 = (int) (Math.random()*10);
                playersWithMathQuestion.put(victim.getUniqueId(),num1+num2);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,Integer.MAX_VALUE,1));
                victim.sendTitle(String.format("%d+%d",num1,num2),null,0,Integer.MAX_VALUE,0);
            }
        }
    }

    @EventHandler
    void onMovement(PlayerMoveEvent event){
        //let the player move their camera
        Location from = event.getFrom();
        Location to = event.getTo();
        if ((from.getX() != to.getX() ||
            from.getY() != to.getY() ||
            from.getZ() != to.getZ()) &&
            playersWithMathQuestion.containsKey(event.getPlayer().getUniqueId())
        ) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onChat(AsyncChatEvent event){
        Player player = event.getPlayer();
        if (!playersWithMathQuestion.containsKey(player.getUniqueId())) return;
        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
        int response;
        try{
            response = Integer.parseInt(serializer.serialize(event.originalMessage()));
            if (playersWithMathQuestion.get(player.getUniqueId()) == response){
                playersWithMathQuestion.remove(player.getUniqueId());
                player.clearTitle();

                //messing with potions can only be done synchronous
                //player.removePotionEffect(PotionEffectType.BLINDNESS);
                Bukkit.getScheduler().runTask(GAbility.getInstance(),() -> player.removePotionEffect(PotionEffectType.BLINDNESS));
            }
        } catch (NumberFormatException ignored) {}
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (PlayerInfo.getPlayersWithConfuser().contains(player.getUniqueId())){
            event.setCancelled(true);
            player.sendMessage(Chat.translate(GAbility.getPrefix() + Ability.fromAbilityType(AbilityType.CONFUSER).getMessageTargets()));
            player.getWorld().spawn(event.getBlockPlaced().getLocation(), Pig.class, pig -> {
               pig.setAI(false);
                Bukkit.getScheduler().runTaskTimer(GAbility.getInstance(), c -> {
                    if (pig.isDead()){
                        c.cancel();
                        return;
                    }
                    if (pig.getBoundingBox().overlaps(player.getBoundingBox())){
                        player.damage(2,pig);
                    }
                },0,1);
                Bukkit.getScheduler().runTaskLater(GAbility.getInstance(), pig::remove,Ability.fromAbilityType(AbilityType.CONFUSER).getDuration()*20);
            });
        }
    }
}
