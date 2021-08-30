package loginpoint.loginpoint;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static org.bukkit.Bukkit.broadcastMessage;

public class Ranking {

    public void set(Player p, Integer i) {
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/Ranking.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            data.set("ranking." + p.getUniqueId(), i);
            try {
                data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void start(Player p, Integer i) {
        makeDisplay(p, i);
    }

    private void makeDisplay(Player pp, Integer a) {
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/Ranking.yml");
            FileConfiguration fdata = YamlConfiguration.loadConfiguration(file);
            if (!fdata.contains("ranking."))
                return;
            HashMap<String, Integer> data = new HashMap<>();
            for (String uuid : fdata.getConfigurationSection("ranking.").getKeys(false)) {
                int count = fdata.getInt("ranking." + uuid);
                data.put(uuid, Integer.valueOf(count));
            }
            int[] points = new int[data.size()];
            int i = 0;
            for (Iterator<Integer> iterator = data.values().iterator(); iterator.hasNext(); ) {
                int p = iterator.next().intValue();
                points[i] = p;
                i++;
            }
            int how = 0;
            Map<Integer, Integer> rankingMap = getRankingMap(points);
            for (Integer point : rankingMap.keySet()) {
                for (String uuid : data.keySet()) {
                    if ((data.get(uuid)).equals(point)) {
                        if (how == a) return;
                        int d = point / 86400;
                        int h = (point - (d * 86400)) / 3600;
                        int m = (point - (d * 86400) - (h * 3600)) / 60;
                        int s = point - (d * 86400) - (h * 3600) - (m * 60);
                        DecimalFormat df = new DecimalFormat("00");
                        String result = "" + df.format(d) + "日 " + df.format(h) + "時間 " + df.format(m) + "分 " + df.format(s) + "秒";
                        String name = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                        pp.sendMessage("§e" + (how + 1) + "位 §a" + name + " §7§l▶ §b" + result);
                        how++;
                    }
                }
            }
        });
    }

    public static Map<Integer, Integer> getRankingMap(int[] points) {
        int len = points.length;
        int[] temp = new int[len];
        for (int i = 0; i < len; i++)
            temp[i] = points[i];
        Arrays.sort(temp);
        int[] pointsDesc = new int[len];
        for (int j = 0; j < len; j++)
            pointsDesc[j] = temp[len - 1 - j];
        Map<Integer, Integer> map = new LinkedHashMap<>();
        int rank = 1;
        map.put(Integer.valueOf(pointsDesc[0]), Integer.valueOf(rank));
        for (int k = 1; k < len; k++) {
            if (pointsDesc[k] != pointsDesc[k - 1])
                rank = k + 1;
            map.put(Integer.valueOf(pointsDesc[k]), Integer.valueOf(rank));
        }
        return map;
    }
}
