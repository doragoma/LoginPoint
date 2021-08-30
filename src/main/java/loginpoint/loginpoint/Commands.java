package loginpoint.loginpoint;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.broadcastMessage;

public class Commands implements CommandExecutor {
    public static HashMap<Player, Integer> cooldown = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("コンソールではコマンドを打てません。");
            return true;
        }
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;
        if (cmd.getName().equalsIgnoreCase("loginpoint")) {
            if (cooldown.containsKey(p)) {
                p.sendMessage("クールダウン中です。");
                return true;
            }
            if (args.length == 0) {
                p.sendMessage("あなたの取得できる報酬を確認中... (2秒後に表示されます)");
                File file = new File("plugins/LoginPoint/Rewards.yml");
                if (!file.exists()) {
                    p.sendMessage("現在報酬がありません。");
                    return true;
                }
                new Count().check(p);
                return true;
            }
            //startCooldown(p);
            if (args[0].equalsIgnoreCase("help")) {
                p.sendMessage("§r ");
                p.sendMessage("§r ");
                p.sendMessage("§b§lログインポイントの使い方");
                p.sendMessage("§e /login §7- §f自分の日数を使い様々な恩賞を得られます");
                p.sendMessage("§e /loginpoint show §7- §f自分のログイン時間の確認");
                p.sendMessage("§e /loginpoint show <プレイヤー> §7- §f他人のログイン時間の確認");
                p.sendMessage("§e /loginpoint ranking §7- §fTOP10を見る");
                p.sendMessage("§e /loginpoint ranking <数字> §7- §f自分の指定したTOP数を見る");
                if (p.hasPermission("loginpoint.azistaff")) {
                    p.sendMessage("§r ");
                    p.sendMessage("§c§l運営のコマンドについて");
                    p.sendMessage("§e /loginpoint add <プレイヤー> <1日/時/分/秒> §7- §f他人のログイン時間を変更");
                    p.sendMessage("§e /loginpoint set <プレイヤー> <1日/時/分/秒> §7- §f他人のログイン時間を追加");
                    p.sendMessage("§e /loginpoint remove <プレイヤー> <1日/時/分/秒> §7- §f他人のログイン時間を削減");
                    p.sendMessage("§e /loginpoint delete <プレイヤー> §7- §f他人のログインデータを削除");
                    p.sendMessage("§e /loginpoint menu §7- §f報酬の設定");
                }
                p.sendMessage("§r ");
                p.sendMessage("§r ");
            }
            if (args[0].equalsIgnoreCase("show")) {
                if (args.length == 1) {
                    new Count().on(p);
                    Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
                        p.sendMessage("データロードのため2秒後に表示されます。");
                        BukkitRunnable task = new BukkitRunnable() {
                            int i = 0;

                            public void run() {
                                if (i == 2) {
                                    File file = new File("plugins/LoginPoint/PlayerPoints.yml");
                                    FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                                    Integer a = 0;
                                    if ((data.contains("Points." + p.getUniqueId()))) {
                                        a = new Count().off(p) + data.getInt("Points." + p.getUniqueId());
                                    } else {
                                        a = new Count().off(p);
                                    }
                                    int d = a / 86400;
                                    int h = (a - (d * 86400)) / 3600;
                                    int m = (a - (d * 86400) - (h * 3600)) / 60;
                                    int s = a - (d * 86400) - (h * 3600) - (m * 60);
                                    DecimalFormat df = new DecimalFormat("00");
                                    String result = "" + df.format(d) + "日 " + df.format(h) + "時間 " + df.format(m) + "分 " + df.format(s) + "秒";
                                    p.sendMessage("あなたのポイント数: " + result);
                                    cancel();
                                }
                                i++;
                            }
                        };
                        task.runTaskTimer(LoginPoint.getPlugin(LoginPoint.class), 0L, 20L);
                    });
                } else {
                    for (Player t : Bukkit.getOnlinePlayers()) {
                        if (t.getDisplayName().equalsIgnoreCase(args[1])) {
                            new Count().on(t);
                            p.sendMessage("２秒後に表示されます。");
                            BukkitRunnable task = new BukkitRunnable() {
                                int i = 0;

                                public void run() {
                                    if (i == 2) {
                                        File file = new File("plugins/LoginPoint/PlayerPoints.yml");
                                        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                                        Integer a = new Count().off(t) + data.getInt("Points." + t.getUniqueId());
                                        p.sendMessage(t.getDisplayName() + "のポイント数: " + a);
                                        cancel();
                                    }
                                    i++;
                                }
                            };
                            task.runTaskTimer(LoginPoint.getPlugin(LoginPoint.class), 0L, 20L);
                            return true;
                        }
                    }
                    p.sendMessage("プレイヤーが見つかりませんでした。");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("ranking")) {
                if (args.length == 1) {
                    new Ranking().start(p, 10);
                } else {
                    if (isInt(args[1])) {
                        new Ranking().start(p, Integer.valueOf(args[1]));
                    } else {
                        p.sendMessage("数字を入力してください。");
                    }
                }
                return true;
            }
            if (!p.hasPermission("loginpoint.azistaff")) {
                p.sendMessage("あなたは権限を持っていないもしくな不明なコマンドです。");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length == 1) {
                    p.sendMessage("プレイヤーを選択してください。");
                    return true;
                }
                if (args.length == 2) {
                    p.sendMessage("日時を入力してください 書き方は 数字の横に 日、時、分、秒 (例 1年: 365日)");
                    return true;
                }
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (t.getDisplayName().equalsIgnoreCase(args[1])) {
                        String time = args[2];
                        time = time.replace("日", "").replace("時", "")
                                .replace("分", "").replace("秒", "");
                        if (isInt(time)) {
                            String type = args[2].replace(time, "");
                            List<String> types = new ArrayList<>();
                            types.add("日");
                            types.add("時");
                            types.add("分");
                            types.add("秒");
                            if (types.contains(type)) {
                                new GetPoint().add(p, getSecond(Integer.valueOf(time), type));
                            } else {
                                p.sendMessage("以下の物を数字の後に書いてください 日、時、分、秒");
                            }
                        } else {
                            p.sendMessage("数字を入力してください。");
                        }
                        return true;
                    }
                }
                p.sendMessage("プレイヤーが見つかりませんでした。");
                return true;
            }
            if (args[0].equalsIgnoreCase("menu")) {
                new Reward().onMenu(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 1) {
                    p.sendMessage("プレイヤーを選択してください。");
                    return true;
                }
                if (args.length == 2) {
                    p.sendMessage("日時を入力してください 書き方は 数字の横に 日、時、分、秒 (例 1年: 365日)");
                    return true;
                }
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (t.getDisplayName().equalsIgnoreCase(args[1])) {
                        String time = args[2];
                        time = time.replace("日", "").replace("時", "")
                                .replace("分", "").replace("秒", "");
                        if (isInt(time)) {
                            String type = args[2].replace(time, "");
                            List<String> types = new ArrayList<>();
                            types.add("日");
                            types.add("時");
                            types.add("分");
                            types.add("秒");
                            if (types.contains(type)) {
                                new GetPoint().set(p, getSecond(Integer.valueOf(time), type));
                            } else {
                                p.sendMessage("以下の物を数字の後に書いてください 日、時、分、秒");
                            }
                        } else {
                            p.sendMessage("数字を入力してください。");
                        }
                        return true;
                    }
                }
                p.sendMessage("プレイヤーが見つかりませんでした。");
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 1) {
                    p.sendMessage("プレイヤーを選択してください。");
                    return true;
                }
                if (args.length == 2) {
                    p.sendMessage("日時を入力してください 書き方は 数字の横に 日、時、分、秒 (例 1年: 365日)");
                    return true;
                }
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (t.getDisplayName().equalsIgnoreCase(args[1])) {
                        String time = args[2];
                        time = time.replace("日", "").replace("時", "")
                                .replace("分", "").replace("秒", "");
                        if (isInt(time)) {
                            String type = args[2].replace(time, "");
                            List<String> types = new ArrayList<>();
                            types.add("日");
                            types.add("時");
                            types.add("分");
                            types.add("秒");
                            if (types.contains(type)) {
                                new GetPoint().remove(p, getSecond(Integer.valueOf(time), type));
                            } else {
                                p.sendMessage("以下の物を数字の後に書いてください 日、時、分、秒");
                            }
                        } else {
                            p.sendMessage("数字を入力してください。");
                        }
                        return true;
                    }
                }
                p.sendMessage("プレイヤーが見つかりませんでした。");
                return true;
            }
            if (args[0].equalsIgnoreCase("delete")) {
                if (args.length == 1) {
                    p.sendMessage("プレイヤーを選択してください。");
                    return true;
                }
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (t.getDisplayName().equalsIgnoreCase(args[1])) {
                        new GetPoint().delete(p);
                        return true;
                    }
                }
                p.sendMessage("プレイヤーが見つかりませんでした。");
                return true;
            }
        }
        return false;
    }

    public void startCooldown(Player p) {
        cooldown.put(p, 1);
        BukkitRunnable task = new BukkitRunnable() {
            int i = 7;

            public void run() {
                if (i == 0) {
                    if (cooldown.containsKey(p)) cooldown.remove(p);
                    cancel();
                }
                if (!p.isOnline()) {
                    if (cooldown.containsKey(p)) cooldown.remove(p);
                    cancel();
                }
                i--;
            }
        };
        task.runTaskTimer(LoginPoint.getInstance(), 0L, 20L);
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static int getSecond(Integer i, String s) {
        Integer a = -1;
        if (s.equalsIgnoreCase("日")) {
            a = i * 86400;
        } else if (s.equalsIgnoreCase("時")) {
            a = i * 3600;
        } else if (s.equalsIgnoreCase("分")) {
            a = i * 60;
        } else if (s.equalsIgnoreCase("秒")) {
            a = i;
        }
        return a;
    }
}