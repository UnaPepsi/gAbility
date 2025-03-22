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

public class AntitrapBone extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.ANTITRAP_BONE);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... args) {
        //another (and much easier way) of making this item would be to just set their block reach to 0
        //but it may not be clear to the player why they aren't able to place blocks
        //when their chat is spammed every time they try and place a block it becomes more obvious why
        //rather than just 1 message when activated which can easily go unnoticed
        Player victim = (Player) args[0];
        player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getUsedMessage()
                .replace("%player%",victim.getName())));
        victim.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getMessageTargets()
                .replace("%player%",player.getName())));
        PlayerInfo.getPlayersWithAntitrapBone().add(victim.getUniqueId());
        Bukkit.getScheduler().runTaskLater(GAbility.getInstance(),
                () -> PlayerInfo.getPlayersWithAntitrapBone().remove(victim.getUniqueId()),
                getAbility().getDuration()*20);
        item.setAmount(item.getAmount()-1);
    }
}