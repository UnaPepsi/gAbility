package ga.guimx.gAbility.config;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class PluginConfig {
    @Getter
    private final static PluginConfig instance = new PluginConfig();
    private File file;
    private YamlConfiguration config;
    @Getter
    private static HashMap<String,String> messages = new HashMap<>();
    @Getter
    private static HashMap<AbilityType, Ability> abilities = new HashMap<>(); //eh...
    @Getter
    private static long globalCooldown;
    private PluginConfig(){}
    public void load(){
        file = new File(GAbility.getInstance().getDataFolder(),"config.yml");
        if (!file.exists()){
            GAbility.getInstance().saveResource("config.yml",false);
        }
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try{
            config.load(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        loadMessages();
        loadAbilities();
    }
    public void reload(){
        file = new File(GAbility.getInstance().getDataFolder(),"config.yml");
        if (!file.exists()){
            GAbility.getInstance().saveResource("config.yml",false);
            config = new YamlConfiguration();
            config.options().parseComments(true);
        }
        try{
            config = YamlConfiguration.loadConfiguration(file);
            loadMessages();
            loadAbilities();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadMessages(){
        GAbility.setPrefix(config.getString("messages.prefix"));
        messages.put("plugin_enabled",config.getString("messages.plugin_enabled"));
        messages.put("plugin_disabled",config.getString("messages.plugin_disabled"));
        messages.put("plugin_reloaded",config.getString("messages.plugin_reloaded"));
        messages.put("received_given_ability",config.getString("messages.received_given_ability"));
        messages.put("given_ability",config.getString("messages.given_ability"));
        messages.put("global_item_cooldown",config.getString("messages.global_item_cooldown"));
        messages.put("item_cooldown",config.getString("messages.item_cooldown"));
    }
    private void loadAbilities(){
        globalCooldown = config.getLong("abilities.global.cooldown");
        abilities.put(AbilityType.STRENGTH,new Ability(
                AbilityType.STRENGTH,
                Material.valueOf(config.getString("abilities.strength.material_type")),
                config.getString("abilities.strength.name"),
                config.getStringList("abilities.strength.lore"),
                config.getLong("abilities.strength.cooldown"),
                config.getLong("abilities.strength.duration"),
                config.getString("abilities.strength.used_message"),
                config.getBoolean("abilities.strength.enchanted")
                ));
        abilities.put(AbilityType.RESISTANCE,new Ability(
                AbilityType.RESISTANCE,
                Material.valueOf(config.getString("abilities.resistance.material_type")),
                config.getString("abilities.resistance.name"),
                config.getStringList("abilities.resistance.lore"),
                config.getLong("abilities.resistance.cooldown"),
                config.getLong("abilities.resistance.duration"),
                config.getString("abilities.resistance.used_message"),
                config.getBoolean("abilities.resistance.enchanted")
                ));
        abilities.put(AbilityType.REGENERATION,new Ability(
                AbilityType.REGENERATION,
                Material.valueOf(config.getString("abilities.regeneration.material_type")),
                config.getString("abilities.regeneration.name"),
                config.getStringList("abilities.regeneration.lore"),
                config.getLong("abilities.regeneration.cooldown"),
                config.getLong("abilities.regeneration.duration"),
                config.getString("abilities.regeneration.used_message"),
                config.getBoolean("abilities.regeneration.enchanted")
        ));
        abilities.put(AbilityType.SWITCHER,new Ability(
                AbilityType.SWITCHER,
                Material.valueOf(config.getString("abilities.switcher.material_type")),
                config.getString("abilities.switcher.name"),
                config.getStringList("abilities.switcher.lore"),
                config.getLong("abilities.switcher.cooldown"),
                config.getString("abilities.switcher.used_message"),
                config.getBoolean("abilities.switcher.enchanted"),
                config.getString("abilities.switcher.target_message")
        ));
    }
}
