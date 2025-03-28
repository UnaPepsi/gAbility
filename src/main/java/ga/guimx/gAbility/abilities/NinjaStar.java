package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NinjaStar extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.NINJA_STAR);}
    @Override
    public boolean checks(Player player){
        if (!super.checks(player)) return false;
        if (!PlayerInfo.getLastPlayersHit().containsKey(player.getUniqueId()) ||
            System.currentTimeMillis() - PlayerInfo.getLastPlayersHit().get(player.getUniqueId()).getWhen() > getAbility().getDuration()*1000
        ){
            player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getErrorMessage()));
            return false;
        }
        return true;
    }
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        Player playerToTeleport = PlayerInfo.getLastPlayersHit().get(player.getUniqueId()).getAttacker();
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()
                .replace("%player%", playerToTeleport.getName())));
        item.setAmount(item.getAmount()-1);
        playerToTeleport.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getMessageTargets()
                .replace("%player%",player.getName())));;
        Task.runLater(() -> player.teleport(playerToTeleport),3*20);
    }
}