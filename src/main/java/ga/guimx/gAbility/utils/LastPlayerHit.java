package ga.guimx.gAbility.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LastPlayerHit {
    @Getter
    private Player attacker;
    @Getter
    private Long when;
    public LastPlayerHit(Player attacker, long when){
        this.attacker = attacker;
        this.when = when;
    }
}
