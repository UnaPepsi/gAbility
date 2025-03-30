package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.*;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class AbilityUsageListener implements Listener {
    public AbilityUsageListener(){}
    @EventHandler
    void playerInteract(PlayerInteractEvent event){
        if (event.getItem() == null){return;}
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item.getType().toString().endsWith("POTION") &&
                PlayerInfo.getPlayersWithBerserk().contains(player.getUniqueId()) &&
                event.getAction().isRightClick())
        {
            event.setCancelled(true);
            player.sendMessage(Chat.translate(GAbility.getPrefix()+Ability.fromAbilityType(AbilityType.BERSERK).getErrorMessage()));
            return;
        }
        PersistentDataContainerView container = item.getPersistentDataContainer();
        String itemType = container.get(GAbility.getKey(), PersistentDataType.STRING);
        if (itemType == null) return;
        if (item.getType().toString().endsWith("SPAWN_EGG")) event.setCancelled(true);
        if (event.getAction().isRightClick()) {
            switch (itemType) {
                case "strength" -> new Strength().handle(player, item);
                case "resistance" -> new Resistance().handle(player, item);
                case "regeneration" -> new Regeneration().handle(player, item);
                case "shrinker" -> new Shrinker().handle(player, item);
                case "time_warp" -> new TimeWarp().handle(player, item);
                case "reach" -> new Reach().handle(player, item);
                case "ninja_star" -> new NinjaStar().handle(player, item);
                case "combo" -> new Combo().handle(player, item);
                case "focus_mode" -> new FocusMode().handle(player, item);
                case "zeus_hammer" -> new ZeusHammer().handle(player, item);
                case "risky_mode" -> new RiskyMode().handle(player,item);
                case "guardian_angel" -> new GuardianAngel().handle(player,item);
                case "portable_bard" -> new PortableBard().handle(player,item,event.getClickedBlock() != null ? event.getClickedBlock().getLocation().add(0,1,0) : player.getLocation());
                case "berserk" -> new Berserk().handle(player,item);
                case "medkit" -> new Medkit().handle(player,item);
            }
        }else if (event.getAction().isLeftClick()){
            switch (itemType) {
                case "strength" -> new Strength().checks(player);
                case "resistance" -> new Resistance().checks(player);
                case "regeneration" -> new Regeneration().checks(player);
                case "switcher" -> new Switcher().checks(player);
                case "shrinker" -> new Shrinker().checks(player);
                case "time_warp" -> new TimeWarp().checks(player);
                case "reach" -> new Reach().checks(player);
                case "antitrap_bone" -> new AntitrapBone().checks(player);
                case "ninja_star" -> new NinjaStar().checks(player);
                case "combo" -> new Combo().checks(player);
                case "confuser" -> new Confuser().checks(player);
                case "focus_mode" -> new FocusMode().checks(player);
                case "zeus_hammer" -> new ZeusHammer().checks(player);
                case "antitrap_beacon" -> new AntitrapBeacon().checks(player);
                case "risky_mode" -> new RiskyMode().checks(player);
                case "guardian_angel" -> new GuardianAngel().checks(player);
                case "rage_ball" -> new RageBall().checks(player);
                case "portable_bard" -> new PortableBard().checks(player);
                case "starvation_flesh" -> new StarvationFlesh().checks(player);
                case "berserk" -> new Berserk().checks(player);
                case "medkit" -> new Medkit().checks(player);
            }
        }
    }

    @EventHandler
    void blockPlace(BlockPlaceEvent event){
        //to avoid some ability items being placed on the ground
        if (event.getItemInHand().getPersistentDataContainer().has(GAbility.getKey(),PersistentDataType.STRING) &&
            !"antitrap_beacon".equals(event.getItemInHand().getPersistentDataContainer().get(GAbility.getKey(),PersistentDataType.STRING))) event.setCancelled(true);
    }

    //to try and avoid false staff bans or false reports
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player attacker) ||
            !(event.getEntity() instanceof Player victim) ||
            Ability.fromAbilityType(AbilityType.REACH).getMessageTargets().isEmpty() ||
            !PlayerInfo.getPlayersWithReach().contains(attacker.getUniqueId())
        ){
            return;
        }
        victim.sendMessage(Chat.translate(GAbility.getPrefix()+ Ability.fromAbilityType(AbilityType.REACH).getMessageTargets()
                .replace("%player%", attacker.getName())));
    }
}
