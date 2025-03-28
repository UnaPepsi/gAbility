package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GuardianAngel extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.GUARDIAN_ANGEL);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... unused) {
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        item.setAmount(item.getAmount()-1);
        PlayerInfo.getPlayersWithGuardianAngel().add(player.getUniqueId());
        Task.runLater(() -> {
                if (PlayerInfo.getPlayersWithGuardianAngel().contains(player.getUniqueId())){
                    player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getErrorMessage()));
                    PlayerInfo.getPlayersWithGuardianAngel().remove(player.getUniqueId());
                }
        },getAbility().getDuration()*20);
    }
}