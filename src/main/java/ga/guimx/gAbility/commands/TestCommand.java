package ga.guimx.gAbility.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Player;

@Command(name = "gability test",aliases = "ability test")
public class TestCommand {

    @Execute(name="fakeblock")
    void executeTest(@Context Player player, @Arg Material blockType, @Arg int distance) {
        Location loc = player.getLocation().clone();
        for (int x = -distance/2; x < distance/2; x++){
            player.sendMessage("x");
            for (int y = -distance/2; y < distance/2; y++){
                player.sendMessage("y");
                for (int z = -distance/2; z < distance/2; z++){
                    player.sendMessage("z");
                    Location tempLoc = loc.clone().add(x,y,z);
                    if (player.getWorld().getBlockAt(tempLoc).getType() == Material.STONE) {
                        player.sendBlockChange(tempLoc, Bukkit.createBlockData(blockType));
                        player.sendMessage(String.format("%d,%d,%d", x, y, z));
                    }
                }
            }
        }
    }

}
