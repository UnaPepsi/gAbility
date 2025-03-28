package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ZeusHammer extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.ZEUS_HAMMER);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
        PlayerInfo.getPlayersWithZeusHammer().add(player.getUniqueId());
        Task.runLater(() -> PlayerInfo.getPlayersWithZeusHammer().remove(player.getUniqueId()), getAbility().getDuration()*20);
    }
}