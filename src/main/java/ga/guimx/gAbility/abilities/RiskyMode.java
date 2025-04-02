package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class RiskyMode extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.RISKY_MODE);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        item.setAmount(item.getAmount()-1);
        double damageBoost = Math.min(35*Math.pow(((20-player.getHealth())/17),2),35);
        PlayerInfo.getPlayersWithRiskyMode().put(player.getUniqueId(),damageBoost);
        player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getUsedMessage()
                .replace("%boost%",String.format(Locale.US,"%.2f",damageBoost))));
        player.getLocation().getNearbyPlayers(10, p -> !p.getUniqueId().equals(player.getUniqueId())).forEach(p -> {
            p.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getMessageTargets()
                    .replace("%player%",player.getName())));
        });
        Task.runLater(() -> PlayerInfo.getPlayersWithRiskyMode().remove(player.getUniqueId()), getAbility().getDuration()*20);
    }
}