package ga.guimx.gAbility.abilities;

import com.google.common.util.concurrent.AtomicDouble;
import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Copycat extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.COPYCAT);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... args) {
        Player victim = (Player) args[0];
        player.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getUsedMessage()
                .replace("%player%",victim.getName())));
        victim.sendMessage(Chat.translate(GAbility.getPrefix()+getAbility().getMessageTargets()
                .replace("%player%",player.getName())));
        ga.guimx.gAbility.utils.Copycat copycat = new ga.guimx.gAbility.utils.Copycat(player,victim,new ArrayList<>(),new ArrayList<>());
        PlayerInfo.getPlayersWithCopycat().add(copycat);
        List<ItemDisplay> displayItems = new ArrayList<>();
        AtomicDouble v = new AtomicDouble(0);
        Task.runTimer(task -> {
            if (!PlayerInfo.getPlayersWithCopycat().contains(copycat)){
                displayItems.forEach(Entity::remove);
                displayItems.clear();
                task.cancel();
                return;
            }
            v.set(v.get()+50);
            Stream.concat(copycat.blocksToBreak().stream(), copycat.blocksToInteract().stream()).forEach(loc -> {
                if (displayItems.stream().anyMatch(d -> d.getLocation().equals(loc.toCenterLocation()))) return;
                displayItems.add(loc.getWorld().spawn(loc.toCenterLocation(), ItemDisplay.class, itemDisplay -> {
                    itemDisplay.setItemStack(new ItemStack(Material.EMERALD_BLOCK));
                    Transformation transformation = itemDisplay.getTransformation();
                    transformation.getScale().set(0.2);
                    itemDisplay.setTransformation(transformation);
                }));
            });
            displayItems.forEach(itemDisplay -> {
                itemDisplay.setInterpolationDuration(5);
                itemDisplay.setInterpolationDelay(-1);
                float angleY = (float) Math.toRadians(v.get());
                float angleX = (float) Math.toRadians(v.get() / 2);
                Transformation transformation = itemDisplay.getTransformation();
                AxisAngle4f rotationY = new AxisAngle4f(angleY, 0, 1, 0);
                AxisAngle4f rotationX = new AxisAngle4f(angleX, 1, 0, 0);
                transformation.getLeftRotation()
                        .set(new Quaternionf(rotationY).mul(new Quaternionf(rotationX)));
                itemDisplay.setTransformation(transformation);
            });
        },0,5);
        Task.runLater(
                () -> PlayerInfo.getPlayersWithCopycat().remove(copycat),
                getAbility().getDuration()*20);
        item.setAmount(item.getAmount()-1);
    }
}