package loginpoint.loginpoint;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GetPoint implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new Count().start(p);
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/PlayerPoints.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            if (!file.exists()) {
                data.set("Points." + p.getUniqueId(), 0);
                try {
                    data.save(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return;
            }
            if (!data.contains("Points." + p.getUniqueId())) {
                data.set("Points." + p.getUniqueId(), 0);
                try {
                    data.save(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                new Ranking().set(p, data.getInt("Points." + p.getUniqueId()));
            }
        });
    }

    public void set(Player p, Integer i) {
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/PlayerPoints.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            data.set("Points." + p.getUniqueId(), i);
            try {
                data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void add(Player p, Integer i) {
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/PlayerPoints.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            Integer time = data.getInt("Points." + p.getUniqueId());
            data.set("Points." + p.getUniqueId(), time + i);
            try {
                data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void remove(Player p, Integer i) {
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/PlayerPoints.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            Integer time = data.getInt("Points." + p.getUniqueId());
            if (time < i) {
                delete(p);
            } else {
                data.set("Points." + p.getUniqueId(), time + i);
            }
            try {
                data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void delete(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            new Count().Settings(p, "delete");
            File file = new File("plugins/LoginPoint/PlayerPoints.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            data.set("Points." + p.getUniqueId(), 0);
            try {
                data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
