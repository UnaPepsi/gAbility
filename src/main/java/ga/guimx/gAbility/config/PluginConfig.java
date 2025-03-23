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
        abilities.put(AbilityType.NINJA_STAR,new Ability.Builder(
                AbilityType.NINJA_STAR,
                Material.valueOf(config.getString("abilities.ninja_star.material_type")),
                config.getString("abilities.ninja_star.name"),
                config.getStringList("abilities.ninja_star.lore"),
                config.getLong("abilities.ninja_star.cooldown"),
                config.getBoolean("abilities.ninja_star.enchanted"),
                config.getString("abilities.ninja_star.used_message"))
                .duration(config.getLong("abilities.ninja_star.duration"))
                .errorMessage(config.getString("abilities.ninja_star.error_message"))
                .messageTargets(config.getString("abilities.ninja_star.target_message"))
                .build()
        );
        abilities.put(AbilityType.COMBO,new Ability.Builder(
                AbilityType.COMBO,
                Material.valueOf(config.getString("abilities.combo.material_type")),
                config.getString("abilities.combo.name"),
                config.getStringList("abilities.combo.lore"),
                config.getLong("abilities.combo.cooldown"),
                config.getBoolean("abilities.combo.enchanted"),
                config.getString("abilities.combo.used_message"))
                .duration(config.getLong("abilities.combo.duration"))
                .build()
        );
        abilities.put(AbilityType.CONFUSER,new Ability.Builder(
                AbilityType.CONFUSER,
                Material.valueOf(config.getString("abilities.confuser.material_type")),
                config.getString("abilities.confuser.name"),
                config.getStringList("abilities.confuser.lore"),
                config.getLong("abilities.confuser.cooldown"),
                config.getBoolean("abilities.confuser.enchanted"),
                config.getString("abilities.confuser.used_message"))
                .duration(config.getLong("abilities.confuser.duration"))
                .errorMessage(config.getString("abilities.confuser.error_message"))
                .messageTargets(config.getString("abilities.confuser.target_message"))
                .build()
        );
        abilities.put(AbilityType.FOCUS_MODE,new Ability.Builder(
                AbilityType.FOCUS_MODE,
                Material.valueOf(config.getString("abilities.focus_mode.material_type")),
                config.getString("abilities.focus_mode.name"),
                config.getStringList("abilities.focus_mode.lore"),
                config.getLong("abilities.focus_mode.cooldown"),
                config.getBoolean("abilities.focus_mode.enchanted"),
                config.getString("abilities.focus_mode.used_message"))
                .duration(config.getLong("abilities.focus_mode.duration"))
                .errorMessage(config.getString("abilities.focus_mode.error_message"))
                .messageTargets(config.getString("abilities.focus_mode.target_message"))
                .build()
        );
        abilities.put(AbilityType.ZEUS_HAMMER,new Ability.Builder(
                AbilityType.ZEUS_HAMMER,
                Material.valueOf(config.getString("abilities.zeus_hammer.material_type")),
                config.getString("abilities.zeus_hammer.name"),
                config.getStringList("abilities.zeus_hammer.lore"),
                config.getLong("abilities.zeus_hammer.cooldown"),
                config.getBoolean("abilities.zeus_hammer.enchanted"),
                config.getString("abilities.zeus_hammer.used_message"))
                .duration(config.getLong("abilities.zeus_hammer.duration"))
                .build()
        );
        abilities.put(AbilityType.ANTITRAP_BEACON,new Ability.Builder(
                AbilityType.ANTITRAP_BEACON,
                Material.valueOf(config.getString("abilities.antitrap_beacon.material_type")),
                config.getString("abilities.antitrap_beacon.name"),
                config.getStringList("abilities.antitrap_beacon.lore"),
                config.getLong("abilities.antitrap_beacon.cooldown"),
                config.getBoolean("abilities.antitrap_beacon.enchanted"),
                config.getString("abilities.antitrap_beacon.used_message"))
                .duration(config.getLong("abilities.antitrap_beacon.duration"))
                .messageTargets(config.getString("abilities.antitrap_beacon.target_message"))
                .build()
        );
    }
}
