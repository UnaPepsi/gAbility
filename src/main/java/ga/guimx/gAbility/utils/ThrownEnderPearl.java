package ga.guimx.gAbility.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ThrownEnderPearl {
    @Getter
    private Location location;
    @Getter
    private Long when;
    public ThrownEnderPearl(Location location, long when){
        this.location = location;
        this.when = when;
    }
}
