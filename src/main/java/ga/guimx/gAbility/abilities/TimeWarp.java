package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TimeWarp extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.TIME_WARP);}
    @Override
    public boolean checks(Player player){
        if (!super.checks(player)) return false;
        if (!PlayerInfo.getLastThrownEnderPearl().containsKey(player.getUniqueId()) ||
            System.currentTimeMillis() - PlayerInfo.getLastThrownEnderPearl().get(player.getUniqueId()).getWhen() > getAbility().getDuration()*1000
        ){
            player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getErrorMessage()));
            return false;
        }
        return true;
    }
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
        Location location = PlayerInfo.getLastThrownEnderPearl().get(player.getUniqueId()).getLocation().clone();
        Bukkit.getScheduler().runTaskLater(GAbility.getInstance(), () -> player.teleport(location),3*20);
    }
}