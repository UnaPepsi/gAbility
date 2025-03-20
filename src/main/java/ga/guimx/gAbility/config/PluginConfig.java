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
        messages.put("new_plugin_version_available",config.getString("messages.new_plugin_version_available"));
        messages.put("no_permissions",config.getString("messages.no_permissions"));
        messages.put("received_given_ability",config.getString("messages.received_given_ability"));
        messages.put("given_ability",config.getString("messages.given_ability"));
        messages.put("global_item_cooldown",config.getString("messages.global_item_cooldown"));
        messages.put("item_cooldown",config.getString("messages.item_cooldown"));
    }
    private void loadAbilities(){
        globalCooldown = config.getLong("abilities.global.cooldown");
        abilities.put(AbilityType.STRENGTH, new Ability.Builder(
                AbilityType.STRENGTH,
                Material.valueOf(config.getString("abilities.strength.material_type")),
                config.getString("abilities.strength.name"),
                config.getStringList("abilities.strength.lore"),
                config.getLong("abilities.strength.cooldown"),
                config.getBoolean("abilities.strength.enchanted"),
                config.getString("abilities.strength.used_message")
                )
                .duration(config.getLong("abilities.strength.duration"))
                .build());
        abilities.put(AbilityType.RESISTANCE, new Ability.Builder(
                AbilityType.RESISTANCE,
                Material.valueOf(config.getString("abilities.resistance.material_type")),
                config.getString("abilities.resistance.name"),
                config.getStringList("abilities.resistance.lore"),
                config.getLong("abilities.resistance.cooldown"),
                config.getBoolean("abilities.resistance.enchanted"),
                config.getString("abilities.resistance.used_message")
        )
                .duration(config.getLong("abilities.strength.duration"))
                .build());
        abilities.put(AbilityType.REGENERATION, new Ability.Builder(
                AbilityType.REGENERATION,
                Material.valueOf(config.getString("abilities.regeneration.material_type")),
                config.getString("abilities.regeneration.name"),
                config.getStringList("abilities.regeneration.lore"),
                config.getLong("abilities.regeneration.cooldown"),
                config.getBoolean("abilities.regeneration.enchanted"),
                config.getString("abilities.regeneration.used_message")
        )
                .duration(config.getLong("abilities.strength.duration"))
                .build());
        abilities.put(AbilityType.SWITCHER, new Ability.Builder(
                AbilityType.SWITCHER,
                Material.valueOf(config.getString("abilities.switcher.material_type")),
                config.getString("abilities.switcher.name"),
                config.getStringList("abilities.switcher.lore"),
                config.getLong("abilities.switcher.cooldown"),
                config.getBoolean("abilities.switcher.enchanted"),
                config.getString("abilities.switcher.used_message")
                )
                .messageTargets(config.getString("abilities.switcher.target_message"))
                .build()
        );
        abilities.put(AbilityType.SHRINKER,new Ability.Builder(
                AbilityType.SHRINKER,
                Material.valueOf(config.getString("abilities.shrinker.material_type")),
                config.getString("abilities.shrinker.name"),
                config.getStringList("abilities.shrinker.lore"),
                config.getLong("abilities.shrinker.cooldown"),
                config.getBoolean("abilities.shrinker.enchanted"),
                config.getString("abilities.shrinker.used_message")
                )
                .duration(config.getLong("abilities.shrinker.duration"))
                .build()
        );
        abilities.put(AbilityType.TIME_WARP,new Ability.Builder(
                AbilityType.TIME_WARP,
                Material.valueOf(config.getString("abilities.time_warp.material_type")),
                config.getString("abilities.time_warp.name"),
                config.getStringList("abilities.time_warp.lore"),
                config.getLong("abilities.time_warp.cooldown"),
                config.getBoolean("abilities.time_warp.enchanted"),
                config.getString("abilities.time_warp.used_message"))
                .duration(config.getLong("abilities.time_warp.duration"))
                .errorMessage(config.getString("abilities.time_warp.error_message"))
                .build());
        abilities.put(AbilityType.REACH,new Ability.Builder(
                AbilityType.REACH,
                Material.valueOf(config.getString("abilities.reach.material_type")),
                config.getString("abilities.reach.name"),
                config.getStringList("abilities.reach.lore"),
                config.getLong("abilities.reach.cooldown"),
                config.getBoolean("abilities.reach.enchanted"),
                config.getString("abilities.reach.used_message"))
                .duration(config.getLong("abilities.reach.duration"))
                .messageTargets(config.getString("abilities.reach.target_message"))
                .build());
        abilities.put(AbilityType.ANTITRAP_BONE,new Ability.Builder(
                AbilityType.ANTITRAP_BONE,
                Material.valueOf(config.getString("abilities.antitrap_bone.material_type")),
                config.getString("abilities.antitrap_bone.name"),
                config.getStringList("abilities.antitrap_bone.lore"),
                config.getLong("abilities.antitrap_bone.cooldown"),
                config.getBoolean("abilities.antitrap_bone.enchanted"),
                config.getString("abilities.antitrap_bone.used_message"))
                .duration(config.getLong("abilities.antitrap_bone.duration"))
                .errorMessage(config.getString("abilities.antitrap_bone.error_message"))
                .messageTargets(config.getString("abilities.antitrap_bone.target_message"))
                .build());
    }
}
