package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

abstract class BaseAbility {
    protected abstract Ability getAbility();
    public void handle(Player player, @Nullable ItemStack item, Object... extraArgs){
        if (checks(player)) {
            abilityLogic(player, item, extraArgs);
            putOnCooldown(player);
        }
    }
    public boolean checks(Player player){
        if (isOnGlobalAbilityCooldown(player)){
            long cooldown = (PlayerInfo.getGlobalAbilityCooldowns().get(player.getUniqueId())-System.currentTimeMillis())/1000;
            long minutes = cooldown / 60;
            long seconds = cooldown % 60;
            player.sendMessage(Chat.translate(GAbility.getPrefix()+ PluginConfig.getMessages().get("global_item_cooldown")
                    .replace("%minutes%",String.format("%02d",minutes)).replace("%seconds%",String.format("%02d",seconds))));
            return false;
        }
        if (isOnAbilityCooldown(player)){
            long cooldown = (PlayerInfo.getAbilityCooldowns().get(player.getUniqueId()).get(getAbility())-System.currentTimeMillis())/1000;
            long minutes = cooldown / 60;
            long seconds = cooldown % 60;
            player.sendMessage(Chat.translate(GAbility.getPrefix()+ PluginConfig.getMessages().get("item_cooldown")
                    .replace("%ability%", getAbility().getName())
                    .replace("%minutes%",String.format("%02d",minutes)).replace("%seconds%",String.format("%02d",seconds))));
            return false;
        }
        return true;
    }
    protected boolean isOnGlobalAbilityCooldown(Player player){
        return System.currentTimeMillis() < PlayerInfo.getGlobalAbilityCooldowns().getOrDefault(player.getUniqueId(),0L);
    }
    protected boolean isOnAbilityCooldown(Player player){
        return System.currentTimeMillis() < PlayerInfo.getAbilityCooldowns().getOrDefault(player.getUniqueId(),new HashMap<>()).getOrDefault(getAbility(),0L);
    }
    protected abstract void abilityLogic(Player player, @Nullable ItemStack item, Object... extraArgs);
    public void putOnCooldown(Player player){
        if (!PlayerInfo.getAbilityCooldowns().containsKey(player.getUniqueId())){
            PlayerInfo.getAbilityCooldowns().put(player.getUniqueId(),new HashMap<>(){{put(getAbility(),System.currentTimeMillis() + getAbility().getCooldown()*1000);}});
        }else{
            PlayerInfo.getAbilityCooldowns().get(player.getUniqueId()).put(getAbility(), System.currentTimeMillis() + getAbility().getCooldown()*1000);
        }
        PlayerInfo.getGlobalAbilityCooldowns().put(player.getUniqueId(),System.currentTimeMillis()+PluginConfig.getGlobalCooldown()*1000);
    }
}