package ga.guimx.gAbility.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.literal.Literal;
import dev.rollczi.litecommands.annotations.permission.Permission;
import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

@Command(name = "gability give",aliases = {"ability give"})
public class GiveAbilityCommand {

    @Execute
    @Permission("gability.give")
    void giveAbility(@Context CommandSender sender, @Arg Player player, @Arg Ability ability, @Arg Optional<Integer> amount) {
        int chosenAmount = Math.max(Math.min(amount.orElse(1),99),1);
        ItemStack item = new ItemStack(ability.getMaterial(),chosenAmount);
        item.setLore(ability.getLore().stream().map(Chat::translate).toList());
        if (ability.isEnchanted()){
            item.addUnsafeEnchantment(Enchantment.LURE,1);
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(GAbility.getInstance(),"ability"), PersistentDataType.STRING,ability.getAbilityType().name().toLowerCase());
        itemMeta.setDisplayName(Chat.translate(ability.getName()));
        itemMeta.setMaxStackSize(chosenAmount);
        item.setItemMeta(itemMeta);
        item.setAmount(chosenAmount);
        player.give(item);
        player.sendMessage(Chat.translate(GAbility.getPrefix()+PluginConfig.getMessages().get("received_given_ability").replace("%amount%",chosenAmount+"").replace("%ability%", ability.getName())));
        sender.sendMessage(Chat.translate(GAbility.getPrefix()+PluginConfig.getMessages().get("given_ability").replace("%player%",player.getName()).replace("%amount%",chosenAmount+"").replace("%ability%", ability.getName())));
    }
    @Execute
    @Permission("gability.give")
    void giveAllAbilities(@Context CommandSender sender, @Arg Player player, @Literal("all") String literal, @Arg Optional<Integer> amount){
        int chosenAmount = Math.max(Math.min(amount.orElse(1),99),1);
        for (AbilityType abilityType : AbilityType.values()){
            Ability ability = Ability.fromAbilityType(abilityType);
            ItemStack item = new ItemStack(ability.getMaterial());
            item.setLore(ability.getLore().stream().map(Chat::translate).toList());
            if (ability.isEnchanted()){
                item.addUnsafeEnchantment(Enchantment.LURE,1);
            }
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(GAbility.getInstance(),"ability"), PersistentDataType.STRING,ability.getAbilityType().name().toLowerCase());
            itemMeta.setDisplayName(Chat.translate(ability.getName()));
            itemMeta.setMaxStackSize(chosenAmount);
            item.setItemMeta(itemMeta);
            item.setAmount(chosenAmount);
            player.give(item);
            player.sendMessage(Chat.translate(GAbility.getPrefix()+PluginConfig.getMessages().get("received_given_ability").replace("%amount%",chosenAmount+"").replace("%ability%", ability.getName())));
            sender.sendMessage(Chat.translate(GAbility.getPrefix()+PluginConfig.getMessages().get("given_ability").replace("%player%",player.getName()).replace("%amount%",chosenAmount+"").replace("%ability%", ability.getName())));
        }
    }

}
