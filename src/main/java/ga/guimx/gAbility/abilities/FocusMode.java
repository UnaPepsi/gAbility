package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FocusMode extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.FOCUS_MODE);}

    @Override
    public boolean checks(Player player){
        if (!super.checks(player)) return false;
        if (!PlayerInfo.getLastPlayersAttacked().containsKey(player.getUniqueId()) ||
                System.currentTimeMillis() - PlayerInfo.getLastPlayersAttacked().get(player.getUniqueId()).getWhen() > 15*1000
        ){
            player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getErrorMessage()));
            return false;
        }
        return true;
    }
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        item.setAmount(item.getAmount()-1);
        Player playerToFocus = PlayerInfo.getLastPlayersAttacked().get(player.getUniqueId()).getAttacker();
        PlayerInfo.getPlayersWithFocusMode().put(player.getUniqueId(),playerToFocus.getUniqueId());
        player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getUsedMessage()
                .replace("%player%",player.getName())));
        playerToFocus.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getMessageTargets()
                .replace("%player%", player.getName())));
        Bukkit.getScheduler().runTaskLater(GAbility.getInstance(), () -> PlayerInfo.getPlayersWithFocusMode().remove(player.getUniqueId()), getAbility().getDuration()*20);
    }
}