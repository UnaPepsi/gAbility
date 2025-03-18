package ga.guimx.gAbility.utils;

import org.bukkit.ChatColor;

public class Chat {
    public static String translate(String string, Object... args){
        return ChatColor.translateAlternateColorCodes('&',String.format(string,args));
    }
}
