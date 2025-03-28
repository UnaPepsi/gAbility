package ga.guimx.gAbility.utils;

import ga.guimx.gAbility.GAbility;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class Task {
    public static void runLater(Runnable runnable, long timeToWait){
        Bukkit.getScheduler().runTaskLater(GAbility.getInstance(),runnable,timeToWait);
    }
    public static void runTimer(Consumer<? super BukkitTask> bukkitTask, long delay, long period){
        Bukkit.getScheduler().runTaskTimer(GAbility.getInstance(),bukkitTask,delay,period);
    }
}
