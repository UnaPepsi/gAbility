package ga.guimx.gAbility.config;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

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
        config.getConfigurationSection("abilities").getKeys(false).stream().filter(key -> !"global".equals(key)).forEach(key -> {
                String path = "abilities."+key+".";
                abilities.put(AbilityType.fromString(key),Ability.builder()
                        .abilityType(AbilityType.fromString(key))
                        .material(Material.valueOf(config.getString(path+"material_type")))
                        .entityType(config.getString(path+"entity_type") != null ? EntityType.valueOf(config.getString(path+"entity_type")) : null)
                        .name(config.getString(path+"name"))
                        .lore(config.getStringList(path+"lore"))
                        .cooldown(config.getLong(path+"cooldown"))
                        .enchanted(config.getBoolean(path+"enchanted"))
                        .usedMessage(config.getString(path+"used_message"))
                        .duration(config.getLong(path+"duration"))
                        .messageTargets(config.getString(path+"target_message"))
                        .errorMessage(config.getString(path+"error_message"))
                        .build()
                );
            }
        );
    }
}
