package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TimeWarp extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.TIME_WARP);}
    @Override
    public boolean checks(Player player){
        if (!super.checks(player)) return false;
        if (!PlayerInfo.getLastThrownEnderPearl().containsKey(player.getUniqueId()) ||
            System.currentTimeMillis() - PlayerInfo.getLastThrownEnderPearl().get(player.getUniqueId()).when() > getAbility().getDuration()*1000
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
        Location location = PlayerInfo.getLastThrownEnderPearl().get(player.getUniqueId()).location().clone();
        Task.runLater(() -> player.teleport(location),3*20);
    }
}