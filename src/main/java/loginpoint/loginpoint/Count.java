package loginpoint.loginpoint;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.broadcastMessage;
import static org.bukkit.Bukkit.getServer;

public class Count {

    public static HashMap<Player, String> set = new HashMap<>();
    public static HashMap<Player, Integer> get = new HashMap<>();

    public static HashMap<Integer, String> rewards = new HashMap<>();
    public static HashMap<String, List<String>> commands = new HashMap<>();
    public static HashMap<String, List<ItemStack>> items = new HashMap<>();
    public static HashMap<String, String> uuid = new HashMap<>();

    public static HashMap<String, Boolean> Server = new HashMap<>();

    public void start(Player p) {
        BukkitRunnable task = new BukkitRunnable() {
            int i = 0;

            public void run() {
                if (!p.isOnline()) {
                    cancel();
                    new GetPoint().add(p, i);
                }
                if (Server.containsKey("Down")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        new GetPoint().add(p, i);
                    }
                    return;
                }
                if (set.containsKey(p)) {
                    if (set.get(p).equalsIgnoreCase("delete")) {
                        i = 0;
                        set.remove(p);
                    } else if (set.get(p).equalsIgnoreCase("get")) {
                        set.remove(p);
                        get.put(p, i);
                    }
                }
                i++;
            }
        };
        task.runTaskTimer(LoginPoint.getPlugin(LoginPoint.class), 0L, 20L);
    }

    public void setRewards(Integer i, String s, List<String> cmd, List<ItemStack> is, String u) {
        rewards.put(i, s);
        commands.put(s, cmd);
        items.put(s, is);
        uuid.put(s, u);
    }

    public void Settings(Player p, String s) {
        set.put(p, s);
    }

    public void check(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/PlayerPoints.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            on(p);
            BukkitRunnable task = new BukkitRunnable() {
                int i = 2;

                public void run() {
                    if (i == 0) {
                        Integer a = 0;
                        if (file.exists()) {
                            a = data.getInt("Points." + p.getUniqueId());
                        }
                        Integer n = off(p) + a;
                        File file = new File("plugins/LoginPoint/PlayerData/" + p.getUniqueId() + ".yml");
                        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                        for (Integer i : rewards.keySet()) {
                            if (i <= n) {
                                if (file.exists()) {
                                    if (data.contains("Keys." + uuid.get(rewards.get(i)))) {
                                    } else {
                                        p.sendMessage(rewards.get(i) + "をクリアしました。");
                                        data.set("Keys." + uuid.get(rewards.get(i)), rewards.get(i));
                                        try {
                                            data.save(file);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    p.sendMessage(rewards.get(i) + "をクリアしました。");
                                    data.set("Keys." + uuid.get(rewards.get(i)), rewards.get(i));
                                    try {
                                        data.save(file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    i--;
                }
            };
            task.runTaskTimer(LoginPoint.getPlugin(LoginPoint.class), 0L, 20L);
        });
    }

    public void addCommands(Player p, String i) {
        File file = new File("plugins/LoginPoint/PlayerPoints.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            try {
                data.load(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
        if (data.contains("Rewards." + i + ".Commands")) {
            for (String s : data.getStringList("Rewards." + i + ".Commands")) {
                getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("/",""));
            }
        }
    }

    public void addItems(Player p, String i) {
        File file = new File("plugins/LoginPoint/PlayerPoints.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            try {
                data.load(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
        if (data.contains("Rewards." + i + ".Items")) {
            if (data.contains("Rewards." + i + ".Items.1")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.1"));
            if (data.contains("Rewards." + i + ".Items.2")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.2"));
            if (data.contains("Rewards." + i + ".Items.3")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.3"));
            if (data.contains("Rewards." + i + ".Items.4")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.4"));
            if (data.contains("Rewards." + i + ".Items.5")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.5"));
            if (data.contains("Rewards." + i + ".Items.6")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.6"));
            if (data.contains("Rewards." + i + ".Items.7")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.7"));
            if (data.contains("Rewards." + i + ".Items.8")) p.getInventory().addItem(data.getItemStack("Rewards." + i + ".Items.8"));
        }
    }

    public void on(Player p) {
        set.put(p, "get");
    }

    public int off(Player p) {
        if (get.get(p) == null) {
            return 0;
        } else {
            return get.get(p);
        }
    }

    public void ServerDown() {
        Server.put("Down", true);
    }
}