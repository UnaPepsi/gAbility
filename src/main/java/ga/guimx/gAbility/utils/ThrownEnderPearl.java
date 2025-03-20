package ga.guimx.gAbility.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ThrownEnderPearl {
    @Getter
    private Player player;
    @Getter
    private Location location;
    @Getter
    private Long when;
    public ThrownEnderPearl(Player player, Location location, long when){
        this.player = player;
        this.location = location;
        this.when = when;
    }
}
