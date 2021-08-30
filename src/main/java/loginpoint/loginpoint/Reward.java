package loginpoint.loginpoint;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Reward implements Listener {
    public static HashMap<Player, Integer> limit = new HashMap<>();

    public static HashMap<Player, List<ItemStack>> items = new HashMap<>();
    public static HashMap<Player, List<String>> commands = new HashMap<>();
    public static HashMap<Player, String> day = new HashMap<>();
    public static HashMap<Player, String> name = new HashMap<>();

    public static HashMap<Player, Integer> namel = new HashMap<>();

    public static HashMap<Player, String> cmdc = new HashMap<>();

    public void onMenu(Player p) {
        List<String> sl1 = new ArrayList<>();
        sl1.add("§7ランキングの確認ができます");
        sl1.add("§r");
        sl1.add("§7コマンド:");
        sl1.add("§r  §e/login ranking");
        sl1.add("§r");
        sl1.add("§7必須権限:");
        sl1.add("§r  §cなし");
        sl1.add("§r");
        sl1.add("§c§l▶ §eクリックで実行");

        List<String> sl2 = new ArrayList<>();
        sl2.add("§7プレイヤーのログイン日数の確認ができます");
        sl2.add("§r");
        sl2.add("§7コマンド:");
        sl2.add("§r  §e/login show <プレイヤー>");
        sl2.add("§r");
        sl2.add("§7必須権限:");
        sl2.add("§r  §cなし");
        sl2.add("§r");
        sl2.add("§c§l▶ §eクリックで実行");

        List<String> sl3 = new ArrayList<>();
        sl3.add("§7ゲーム内でconfigの設定が可能です");
        sl3.add("§r");
        sl3.add("§7コマンド:");
        sl3.add("§r  §cなし");
        sl3.add("§r");
        sl3.add("§7必須権限:");
        sl3.add("§r  §eloginpoint.azistaff");
        sl3.add("§r");
        sl3.add("§c§l▶ §eクリックで実行");

        List<String> sl4 = new ArrayList<>();
        sl4.add("§7アイテムの報酬の追加などの設定が可能です");
        sl4.add("§r");
        sl4.add("§7コマンド:");
        sl4.add("§r  §cなし");
        sl4.add("§r");
        sl4.add("§7必須権限:");
        sl4.add("§r  §eloginpoint.azistaff");
        sl4.add("§r");
        sl4.add("§c§l▶ §eクリックで実行");

        List<String> sl5 = new ArrayList<>();
        sl5.add("§7プレイヤーのログイン日数の追加ができます");
        sl5.add("§r");
        sl5.add("§7コマンド:");
        sl5.add("§r  §e/login add show <プレイヤー> <数字>");
        sl5.add("§r");
        sl5.add("§7必須権限:");
        sl5.add("§r  §eloginpoint.azistaff");
        sl5.add("§r");
        sl5.add("§c§l▶ §eクリックで実行");

        List<String> sl6 = new ArrayList<>();
        sl6.add("§7プレイヤーのログイン日数の削除ができます");
        sl6.add("§r");
        sl6.add("§7コマンド:");
        sl6.add("§r  §e/login delete <プレイヤー> <数字>");
        sl6.add("§r");
        sl6.add("§7必須権限:");
        sl6.add("§r  §eloginpoint.azistaff");
        sl6.add("§r");
        sl6.add("§c§l▶ §eクリックで実行");

        List<String> sl7 = new ArrayList<>();
        sl7.add("§7プレイヤーのログイン日数の削減ができます");
        sl7.add("§r");
        sl7.add("§7コマンド:");
        sl7.add("§r  §e/login remove <プレイヤー> <数字>");
        sl7.add("§r");
        sl7.add("§7必須権限:");
        sl7.add("§r  §eloginpoint.azistaff");
        sl7.add("§r");
        sl7.add("§c§l▶ §eクリックで実行");

        List<String> sl8 = new ArrayList<>();
        sl8.add("§7プレイヤーのログイン日数の固定ができます");
        sl8.add("§r");
        sl8.add("§7コマンド:");
        sl8.add("§r  §e/login set <プレイヤー> <数字>");
        sl8.add("§r");
        sl8.add("§7必須権限:");
        sl8.add("§r  §eloginpoint.azistaff");
        sl8.add("§r");
        sl8.add("§c§l▶ §eクリックで実行");

        List<String> sl9 = new ArrayList<>();
        sl9.add("§7ファイルデータを保存することができます");
        sl9.add("§r");
        sl9.add("§7コマンド:");
        sl9.add("§r  §cなし");
        sl9.add("§r");
        sl9.add("§7必須権限:");
        sl9.add("§r  §eloginpoint.azistaff");
        sl9.add("§r");
        sl9.add("§c§l▶ §eクリックで実行");

        List<String> sl10 = new ArrayList<>();
        sl10.add("§7ファイルのデータを削除します。");
        sl10.add("§r");
        sl10.add("§7コマンド:");
        sl10.add("§r  §cなし");
        sl10.add("§r");
        sl10.add("§7必須権限:");
        sl10.add("§r  §eloginpoint.azistaff");
        sl10.add("§r");
        sl10.add("§c§l▶ §eクリックで実行");

        List<String> sl11 = new ArrayList<>();
        sl11.add("§7ファイルデータをリロードします");
        sl11.add("§r");
        sl11.add("§7コマンド:");
        sl11.add("§r  §e/login reload");
        sl11.add("§r");
        sl11.add("§7必須権限:");
        sl11.add("§r  §eloginpoint.azistaff");
        sl11.add("§r");
        sl11.add("§c§l▶ §eクリックで実行");
        Inventory inv = Bukkit.createInventory(null, 45, "§e§lログインポイントの報酬");
        inv.setItem(10, createItem(Material.GOLD_INGOT, 1, (short) 99, "&a&lランキング &f&l(Leaderboard)", sl1, false));
        inv.setItem(11, createItem(Material.WATCH, 1, (short) 99, "&a&lプレイヤーチェック &f&l(Check)", sl2, false));
        inv.setItem(12, createItem(Material.PAPER, 1, (short) 99, "&a&lconfigの設定 &f&l(Config)", sl3, false));
        inv.setItem(13, createItem(Material.CHEST, 1, (short) 99, "&a&l報酬アイテム &f&l(Rewards)", sl4, false));

        inv.setItem(28, createItem(Material.STAINED_CLAY, 1, (short) 13, "&a&lポイント追加 &f&l(Add)", sl5, false));
        inv.setItem(29, createItem(Material.STAINED_CLAY, 1, (short) 4, "&c&lポイント削除 &f&l(Delete)", sl6, false));
        inv.setItem(30, createItem(Material.STAINED_CLAY, 1, (short) 14, "&4&lポイント削減 &f&l(Remove)", sl7, false));
        inv.setItem(31, createItem(Material.STAINED_CLAY, 1, (short) 6, "&b&lポイント固定 &f&l(Set)", sl8, false));

        inv.setItem(15, createItem(Material.EMERALD_BLOCK, 1, (short) 99, "&a&lデータ保存 &f&l(Save)", sl9, false));
        inv.setItem(24, createItem(Material.TNT, 1, (short) 99, "&c&lデータ削除 &f&l(Delete)", sl10, false));
        inv.setItem(33, createItem(Material.REDSTONE_BLOCK, 1, (short) 99, "&4&lデータ再読み込み &f&l(Reload)", sl11, false));

        //  0  1  2  3  4  5  6  7  8
        //  9 10 11 12 13 14 15 16 17
        // 18 19 20 21 22 23 24 25 26
        // 27 28 29 30 31 32 33 34 35
        // 36 37 38 39 40 41 42 43 44
        p.openInventory(inv);
    }

    public void onConfig(Player p) {
        List<String> sl1 = new ArrayList<>();
        sl1.add("§7ランキングのコマンドを使った際の最大表示数の設定");
        sl1.add("§r");
        sl1.add("§7デフォルト:");
        sl1.add("§r  §e10人");
        sl1.add("§r");
        sl1.add("§7現在の設定:");
        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
            File file = new File("plugins/LoginPoint/config.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            try {
                data.load(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
            sl1.add("§r  §e" + data.get("System.Ranking") + "人");
        });
        sl1.add("§r");
        sl1.add("§c§l▶ §eクリックで変更");

        Inventory inv = Bukkit.createInventory(null, 27, "§e§lコンフィグの設定変更");
        inv.setItem(13, createItem(Material.GOLD_INGOT, 1, (short) 99, "&a&l最大数 &f&l(Limit)", sl1, false));
        inv.setItem(26, createItem(Material.ARROW, 1, (short) 99, "&c&l前に戻る", null, false));

        //  0  1  2  3  4  5  6  7  8
        //  9 10 11 12 13 14 15 16 17
        // 18 19 20 21 22 23 24 25 26
        p.openInventory(inv);
    }

    public void onReward(Player p) {
            Inventory inv = Bukkit.createInventory(null, 54, "§e§l報酬の設定");
            inv.setItem(47, createItem(Material.WORKBENCH, 1, (short) 99, "&a報酬の追加", null, false));
            inv.setItem(49, createItem(Material.ARROW, 1, (short) 99, "&c&l前に戻る", null, false));
            inv.setItem(51, createItem(Material.FLINT_AND_STEEL, 1, (short) 99, "&a報酬の削除", null, false));
            File file = new File("plugins/LoginPoint/Rewards.yml");
            if (!file.exists()) {
                inv.setItem(22, createItem(Material.BARRIER, 1, (short) 99, "&4&l✖ &b&l何も設定されていません。 &4&l✖", null, false));
                p.openInventory(inv);
                return;
            }
            p.openInventory(inv);
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            BukkitRunnable task = new BukkitRunnable() {
                int i = 0;

                public void run() {
                    Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
                        try {
                            data.load(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InvalidConfigurationException e) {
                            e.printStackTrace();
                        }
                        if (!data.contains("Rewards." + i)) {
                            if (!data.contains("Rewards." + (i + 1))) {
                                if (!data.contains("Rewards." + (i + 2))) {
                                    cancel();
                                    return;
                                }
                            }
                        }
                        if (data.getString("Rewards." + i + ".Name") != null) {
                            List<String> sl = new ArrayList<>();
                            sl.add("§7" + (i + 1) + "番目の報酬");
                            sl.add("§r");
                            sl.add("§b報酬名: §e" + data.getString("Rewards." + i + ".Name"));
                            sl.add("§b日時: §e" + data.getString("Rewards." + i + ".Time"));
                            sl.add("§r");
                            sl.add("§bコマンド:");
                            if (data.contains("Rewards." + i + ".Commands")) {
                                for (String s : data.getStringList("Rewards." + i + ".Commands")) {
                                    sl.add("§r  §7- §e" + s);
                                }
                            }
                            sl.add("§r");
                            sl.add("§bアイテム:");
                            if (data.contains("Rewards." + i + ".Items")) {
                                if (data.contains("Rewards." + i + ".Items.1"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.1").getItemMeta().getDisplayName());
                                if (data.contains("Rewards." + i + ".Items.2"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.2").getItemMeta().getDisplayName());
                                if (data.contains("Rewards." + i + ".Items.3"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.3").getItemMeta().getDisplayName());
                                if (data.contains("Rewards." + i + ".Items.4"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.4").getItemMeta().getDisplayName());
                                if (data.contains("Rewards." + i + ".Items.5"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.5").getItemMeta().getDisplayName());
                                if (data.contains("Rewards." + i + ".Items.6"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.6").getItemMeta().getDisplayName());
                                if (data.contains("Rewards." + i + ".Items.7"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.7").getItemMeta().getDisplayName());
                                if (data.contains("Rewards." + i + ".Items.8"))
                                    sl.add("§r  §7- §e" + data.getItemStack("Rewards." + i + ".Items.8").getItemMeta().getDisplayName());
                            }
                            sl.add("§r");
                            inv.setItem(i, createItem(Material.PAPER, 1, (short) 99, "&4&l⚔ &b&l" + (1 + i) + "番目の報酬", sl, false));
                        } else {
                            inv.setItem(i, createItem(Material.BARRIER, 1, (short) 99, "&4&l⚔ &b&lnull", null, false));
                        }
                    });
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    i++;
                }
                //  0  1  2  3  4  5  6  7  8
                //  9 10 11 12 13 14 15 16 17
                // 18 19 20 21 22 23 24 25 26
                // 27 28 29 30 31 32 33 34 35
                // 36 37 38 39 40 41 42 43 44
                // 45 46 47 48 49 50 51 52 53
            };
            task.runTaskTimer(LoginPoint.getPlugin(LoginPoint.class), 0L, 5L);
    }

    public void createReward(Player p) {
        List<String> sl1 = new ArrayList<>();
        sl1.add("§7報酬をクリアまでの時間を設定できます。");
        sl1.add("§r");
        sl1.add("§b現在の時間:");
        if (day.containsKey(p)) {
            sl1.add("§r  §e" + day.get(p));
        } else {
            sl1.add("§r  §e0日 0時 0分 0秒");
        }
        sl1.add("§r");

        List<String> sl2 = new ArrayList<>();
        sl2.add("§7クリアした際に入手するアイテムを設定できます。");
        sl2.add("§r");
        sl2.add("§b現在設定されているアイテム:");
        if (items.containsKey(p)) {
            for (ItemStack s : items.get(p)) {
                sl2.add("§r  §7- §b" + s.getItemMeta().getDisplayName() + " §fx" + s.getAmount());
            }
        } else {
            sl2.add("§r  §7- §cなし");
        }
        sl2.add("§r");

        List<String> sl3 = new ArrayList<>();
        sl3.add("§7クリアした際に発動するコマンドを設定できます。");
        sl3.add("§r");
        sl3.add("§b現在設定されているコマンド:");
        if (commands.containsKey(p)) {
            for (String s : commands.get(p)) {
                sl3.add("§r  §7- §b" + s);
            }
        } else {
            sl3.add("§r  §7- §cなし");
        }
        sl3.add("§r");

        List<String> sl4 = new ArrayList<>();
        sl4.add("§7報酬の名前の設定をできます。");
        sl4.add("§r");
        sl4.add("§b現在の報酬名:");
        if (name.containsKey(p)) {
            sl4.add("§r  §e" + name.get(p));
        } else {
            sl4.add("§r  §cなし");
        }
        sl4.add("§r");

        List<String> sl5 = new ArrayList<>();
        sl5.add("§r");
        sl5.add("§7報酬の設定を保存して自動的に追加されます。");
        sl5.add("§r");

        List<String> sl6 = new ArrayList<>();
        sl6.add("§r");
        sl6.add("§7こちらの報酬を削除＆リセットします。");
        sl6.add("§r");
        Inventory inv = Bukkit.createInventory(null, 27, "§e§l報酬の作成");
        inv.setItem(10, createItem(Material.WATCH, 1, (short) 99, "&a報酬のクリア時間", sl1, false));
        inv.setItem(11, createItem(Material.CHEST, 1, (short) 99, "&b&l報酬クリア時のアイテム", sl2, false));
        inv.setItem(12, createItem(Material.COMMAND, 1, (short) 99, "&b&l報酬のクリア時のコマンド", sl3, false));
        inv.setItem(13, createItem(Material.ANVIL, 1, (short) 99, "&b&l報酬の名前設定", sl4, false));

        inv.setItem(15, createItem(Material.EMERALD_BLOCK, 1, (short) 99, "&2&l● &a&l報酬の保存 &2&l●", sl5, false));
        inv.setItem(16, createItem(Material.REDSTONE_BLOCK, 1, (short) 99, "&4&l● &c&l作成をキャンセル &4&l●", sl6, false));
        //  0  1  2  3  4  5  6  7  8
        //  9 10 11 12 13 14 15 16 17
        // 18 19 20 21 22 23 24 25 26
        p.openInventory(inv);
    }

    public void setTime(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "§e§l報酬の時間設定");
        inv.setItem(4, createItem(Material.STAINED_GLASS, 1, (short) 5, "&b現在の時間: &f0日 0時 0分 0秒", null, false));
        inv.setItem(26, createItem(Material.EMERALD_BLOCK, 1, (short) 99, "&a保存", null, false));

        inv.setItem(20, createItem(Material.STAINED_GLASS_PANE, 1, (short) 14, "&cリセット &f&l(Reset)", null, false));
        inv.setItem(21, createItem(Material.STAINED_GLASS_PANE, 1, (short) 4, "&e+1日 &f&l(Day)", null, false));
        inv.setItem(22, createItem(Material.STAINED_GLASS_PANE, 1, (short) 4, "&e+1時 &f&l(Hour)", null, false));
        inv.setItem(23, createItem(Material.STAINED_GLASS_PANE, 1, (short) 4, "&e+1分 &f&l(Minute)", null, false));
        inv.setItem(24, createItem(Material.STAINED_GLASS_PANE, 1, (short) 4, "&e+1秒 &f&l(Second)", null, false));
        //  0  1  2  3  4  5  6  7  8
        //  9 10 11 12 13 14 15 16 17
        // 18 19 20 21 22 23 24 25 26
        p.openInventory(inv);
    }

    public void setItems(Player p) {
        Inventory inv = Bukkit.createInventory(null, 18, "§e§l報酬のアイテム設定");
        if (items.containsKey(p)) {
            Integer i = 0;
            for (ItemStack s : items.get(p)) {
                inv.setItem(i, s);
                i++;
            }
        }
        inv.setItem(9, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        inv.setItem(10, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        inv.setItem(11, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        inv.setItem(12, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        inv.setItem(13, createItem(Material.EMERALD_BLOCK, 1, (short) 99, "&a保存", null, false));
        inv.setItem(14, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        inv.setItem(15, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        inv.setItem(16, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        inv.setItem(17, createItem(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r", null, false));
        //  0  1  2  3  4  5  6  7  8
        //  9 10 11 12 13 14 15 16 17
        p.openInventory(inv);
    }

    public void setCommands(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§e§l報酬のコマンド設定");

        if (commands.containsKey(p)) {
            Integer i = 0;
            for (String s : commands.get(p)) {
                List<String> sl = new ArrayList<>();
                sl.add("§f§l" + s);
                sl.add("§r");
                sl.add("§6§l● §c§lクリックでこのコマンドを削除");
                inv.setItem(i, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", sl, false));
                i++;
            }
        }
        if (inv.getItem(0) == null)
            inv.setItem(0, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        if (inv.getItem(1) == null)
            inv.setItem(1, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        if (inv.getItem(2) == null)
            inv.setItem(2, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        if (inv.getItem(3) == null)
            inv.setItem(3, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        if (inv.getItem(4) == null)
            inv.setItem(4, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        if (inv.getItem(5) == null)
            inv.setItem(5, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        if (inv.getItem(6) == null)
            inv.setItem(6, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        if (inv.getItem(7) == null)
            inv.setItem(7, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
        inv.setItem(8, createItem(Material.EMERALD_BLOCK, 1, (short) 99, "&a保存", null, false));
        //  0  1  2  3  4  5  6  7  8
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("§e§l報酬のアイテム設定")) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().hasItemMeta()) {
                    if (e.getCurrentItem().getType().toString().indexOf("GLASS") != -1) {
                        e.setCancelled(true);
                    }
                }
            }
            Integer a = e.getRawSlot();
            Inventory inv = e.getInventory();
            if (a == 13) {
                e.setCancelled(true);
                List<ItemStack> is = new ArrayList<>();
                if (inv.getItem(0) != null) is.add(inv.getItem(0));
                if (inv.getItem(1) != null) is.add(inv.getItem(1));
                if (inv.getItem(2) != null) is.add(inv.getItem(2));
                if (inv.getItem(3) != null) is.add(inv.getItem(3));
                if (inv.getItem(4) != null) is.add(inv.getItem(4));
                if (inv.getItem(5) != null) is.add(inv.getItem(5));
                if (inv.getItem(6) != null) is.add(inv.getItem(6));
                if (inv.getItem(7) != null) is.add(inv.getItem(7));
                if (inv.getItem(8) != null) is.add(inv.getItem(8));
                items.put(p, is);
                createReward(p);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§e§l報酬のコマンド設定")) {
            e.setCancelled(true);
            Integer a = e.getRawSlot();
            Inventory inv = e.getInventory();

            if (a >= 0) {
                if (a <= 7) {
                    if (inv.getItem(a).getItemMeta().hasLore()) {
                        inv.setItem(a, createItem(Material.COMMAND, 1, (short) 99, "&a&lコマンド:", null, false));
                        return;
                    }
                    cmdc.put(p, p.getUniqueId() + " " + a);
                    p.closeInventory();
                    p.sendMessage("");
                    p.sendMessage("チャットにて設定するコマンドを / なしで入力してください");
                    p.sendMessage("例：give %player% diamond");
                    p.sendMessage("");
                    return;
                }
            }
            if (a == 8) {
                e.setCancelled(true);
                List<String> is = new ArrayList<>();
                if (inv.getItem(0).getItemMeta().hasLore())
                    is.add(inv.getItem(0).getItemMeta().getLore().get(0).replace("§f§l", ""));
                if (inv.getItem(1).getItemMeta().hasLore())
                    is.add(inv.getItem(1).getItemMeta().getLore().get(0).replace("§f§l", ""));
                if (inv.getItem(2).getItemMeta().hasLore())
                    is.add(inv.getItem(2).getItemMeta().getLore().get(0).replace("§f§l", ""));
                if (inv.getItem(3).getItemMeta().hasLore())
                    is.add(inv.getItem(3).getItemMeta().getLore().get(0).replace("§f§l", ""));
                if (inv.getItem(4).getItemMeta().hasLore())
                    is.add(inv.getItem(4).getItemMeta().getLore().get(0).replace("§f§l", ""));
                if (inv.getItem(5).getItemMeta().hasLore())
                    is.add(inv.getItem(5).getItemMeta().getLore().get(0).replace("§f§l", ""));
                if (inv.getItem(6).getItemMeta().hasLore())
                    is.add(inv.getItem(6).getItemMeta().getLore().get(0).replace("§f§l", ""));
                if (inv.getItem(7).getItemMeta().hasLore())
                    is.add(inv.getItem(7).getItemMeta().getLore().get(0).replace("§f§l", ""));
                commands.put(p, is);
                createReward(p);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§e§l報酬の設定")) {
            e.setCancelled(true);
            Integer i = e.getRawSlot();
            Inventory inv = e.getInventory();
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().hasItemMeta()) {
                    if (e.getCurrentItem().getItemMeta().hasEnchants()) {
                        Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
                            File file = new File("plugins/LoginPoint/Rewards.yml");
                            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                            data.set("Rewards." + i, null);
                            try {
                                data.save(file);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        });
                        p.closeInventory();
                        p.sendMessage("削除しました。");
                        return;
                    }
                }
            }
            if (i == 47) {
                createReward(p);
            } else if (i == 49) {
                onMenu(p);
            } else if (i == 51) {
                BukkitRunnable task = new BukkitRunnable() {
                    int i = 0;

                    public void run() {
                        if (inv.getItem(i) == null) {
                            cancel();
                            return;
                        }
                        ItemStack is = inv.getItem(i);
                        ItemMeta im = is.getItemMeta();
                        im.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        is.setItemMeta(im);
                        inv.setItem(i, is);
                        i++;
                    }
                };
                task.runTaskTimer(LoginPoint.getPlugin(LoginPoint.class), 0L, 0L);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§e§l報酬の作成")) {
            e.setCancelled(true);
            Integer i = e.getRawSlot();
            if (i == 10) {
                setTime(p);
            } else if (i == 11) {
                setItems(p);
            } else if (i == 12) {
                setCommands(p);
            } else if (i == 13) {
                p.sendMessage("");
                p.sendMessage("チャットにて報酬の名前を設定できます。");
                p.sendMessage("キャンセルする場合は cancel と入力してください");
                p.sendMessage("");
                namel.put(p, 1);
                p.closeInventory();
            } else if (i == 15) {
                if (!day.containsKey(p)) {
                    p.sendMessage("日時が設定されていません。");
                    return;
                }
                if (!name.containsKey(p)) {
                    p.sendMessage("報酬名が設定されておりません。");
                    return;
                }
                    String[] zikan = ChatColor.stripColor(day.get(p)).replace("日", "").replace("時", "")
                            .replace("分", "").replace("秒", "").split(" ");

                    String uuid = UUID.randomUUID().toString();

                    Integer dai = Integer.valueOf(zikan[0]) * 86400;
                    Integer hor = Integer.valueOf(zikan[1]) * 3600;
                    Integer min = Integer.valueOf(zikan[2]) * 60;
                    Integer sec = Integer.valueOf(zikan[3]) * 60;

                    new Count().setRewards((dai + hor + min + sec), name.get(p), commands.get(p), items.get(p), uuid);
                    Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
                        Integer a = 0;
                    File file = new File("plugins/LoginPoint/Rewards.yml");
                    FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                    if (!file.exists()) {
                        a = 0;
                        data.set("int", "1");
                    } else {
                        a = data.getInt("int");
                        data.set("int", a + 1);
                    }
                    data.set("Rewards." + a + ".Name", name.get(p));
                    data.set("Rewards." + a + ".Time", day.get(p));
                    data.set("Rewards." + a + ".Key", uuid);
                    if (commands.containsKey(p)) {
                        data.set("Rewards." + a + ".Commands", commands.get(p));
                    }
                    if (items.containsKey(p)) {
                        Integer n = 1;
                        for (ItemStack is : items.get(p)) {
                            data.set("Rewards." + a + ".Items." + n, is);
                            n++;
                        }
                    }
                    try {
                        data.save(file);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                p.closeInventory();
                p.sendMessage("新しく報酬の作成を保存することに成功しました！");
                if (limit.containsKey(p)) limit.remove(p);
                if (items.containsKey(p)) items.remove(p);
                if (commands.containsKey(p)) commands.remove(p);
                if (day.containsKey(p)) day.remove(p);
                if (name.containsKey(p)) name.remove(p);
                if (namel.containsKey(p)) namel.remove(p);
                if (cmdc.containsKey(p)) cmdc.remove(p);
            } else if (i == 16) {
                if (limit.containsKey(p)) limit.remove(p);
                if (items.containsKey(p)) items.remove(p);
                if (commands.containsKey(p)) commands.remove(p);
                if (day.containsKey(p)) day.remove(p);
                if (name.containsKey(p)) name.remove(p);
                if (namel.containsKey(p)) namel.remove(p);
                if (cmdc.containsKey(p)) cmdc.remove(p);
                p.closeInventory();
            } else {
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§e§l報酬の時間設定")) {
            Inventory inv = e.getInventory();
            Integer i = e.getRawSlot();
            e.setCancelled(true);
            if (i == 20) {
                inv.setItem(4, createItem(Material.STAINED_GLASS, 1, (short) 5, "&b現在の時間: &f0日 0時 0分 0秒", null, false));
            } else if (i == 21) {
                String[] time = inv.getItem(4).getItemMeta().getDisplayName().replace("§b現在の時間: §f", "").split(" ");
                String a = time[0].replace("日", "");
                Integer n = Integer.valueOf(a) + 1;
                inv.setItem(4, createItem(Material.STAINED_GLASS, 1, (short) 5, "&b現在の時間: &f" +
                        n + "日 " + time[1] + " " + time[2] + " " + time[3] + "", null, false));
            } else if (i == 22) {
                String[] time = inv.getItem(4).getItemMeta().getDisplayName().replace("§b現在の時間: §f", "").split(" ");
                String a = time[1].replace("時", "");
                Integer n = Integer.valueOf(a) + 1;
                if (n == 24) {
                    return;
                }
                inv.setItem(4, createItem(Material.STAINED_GLASS, 1, (short) 5, "&b現在の時間: &f" +
                        time[0] + " " + n + "時 " + time[2] + " " + time[3] + "", null, false));
            } else if (i == 23) {
                String[] time = inv.getItem(4).getItemMeta().getDisplayName().replace("§b現在の時間: §f", "").split(" ");
                String a = time[2].replace("分", "");
                Integer n = Integer.valueOf(a) + 1;
                if (n == 60) {
                    return;
                }
                inv.setItem(4, createItem(Material.STAINED_GLASS, 1, (short) 5, "&b現在の時間: &f" +
                        time[0] + " " + time[1] + " " + n + "分 " + time[3] + "", null, false));
            } else if (i == 24) {
                String[] time = inv.getItem(4).getItemMeta().getDisplayName().replace("§b現在の時間: §f", "").split(" ");
                String a = time[3].replace("秒", "");
                Integer n = Integer.valueOf(a) + 1;
                if (n == 60) {
                    return;
                }
                inv.setItem(4, createItem(Material.STAINED_GLASS, 1, (short) 5, "&b現在の時間: &f" +
                        time[0] + " " + time[1] + " " + time[2] + " " + n + "秒", null, false));
            } else if (i == 26) {
                day.put(p, inv.getItem(4).getItemMeta().getDisplayName().replace("§b現在の時間: §f", ""));
                createReward(p);
            }

        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§e§lコンフィグの設定変更")) {
            e.setCancelled(true);
            Integer i = e.getRawSlot();
            if (i == 13) {
                p.sendMessage("チャット欄にて数字を入力してください。");
                limit.put(p, null);
                p.closeInventory();
            } else if (i == 26) {
                onMenu(p);
            } else {
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§e§lログインポイントの報酬")) {
            e.setCancelled(true);
            Integer i = e.getRawSlot();
            if (i == 10) {
                p.closeInventory();
                new Ranking().start(p, 10);
            } else if (i == 11) {
                p.sendMessage("使い方: /loginpoint show <プレイヤー> <数字>");
                p.closeInventory();
            } else if (i == 12) {
                onConfig(p);
            } else if (i == 13) {
                onReward(p);
            } else if (i == 28) {
                p.sendMessage("使い方: /loginpoint add <プレイヤー> <数字>");
                p.closeInventory();
            } else if (i == 29) {
                p.sendMessage("使い方: /loginpoint delete <プレイヤー> <数字>");
                p.closeInventory();
            } else if (i == 30) {
                p.sendMessage("使い方: /loginpoint remove <プレイヤー> <数字>");
                p.closeInventory();
            } else if (i == 31) {
                p.sendMessage("使い方: /loginpoint set <プレイヤー> <数字>");
                p.closeInventory();
            } else if (i == 15) {
                p.closeInventory();
                File file = new File("plugins/LoginPoint/PlayerPoints.yml");
                if (!file.exists()) {
                    p.sendMessage("保存をするファイルがありませんでした。");
                    return;
                }
                FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                p.sendMessage("保存中...");
                try {
                    p.sendMessage("保存に成功しました。");
                    data.save(file);
                } catch (IOException ioException) {
                    p.sendMessage("保存に失敗しました。");
                    ioException.printStackTrace();
                }
            } else if (i == 24) {
                p.closeInventory();
                p.sendMessage("申し訳ございません、現在管理者によりファイルの削除ができない設定になっております。");
            } else if (i == 33) {
                p.performCommand("loginpoint reload");
            } else {
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onchat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (limit.containsKey(p)) {
            if (e.getMessage().equalsIgnoreCase("cancel")) {
                limit.remove(p);
                return;
            }
            if (isInt(e.getMessage())) {
                Bukkit.getScheduler().runTaskAsynchronously(LoginPoint.getPlugin(LoginPoint.class), () -> {
                    File file = new File("plugins/LoginPoint/config.yml");
                    FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                    p.sendMessage("リミット数を" + e.getMessage() + "に変更しました。");
                    onConfig(p);
                    data.set("System.Ranking", e.getMessage());
                    limit.remove(p);
                    e.setCancelled(true);
                    try {
                        data.save(file);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            } else {
                p.sendMessage("数字を入力してください。");
                e.setCancelled(true);
            }
        }
        if (namel.containsKey(p)) {
            if (e.getMessage().equalsIgnoreCase("cancel")) {
                namel.remove(p);
                e.setCancelled(true);
                createReward(p);
            } else {
                e.setCancelled(true);
                namel.remove(p);
                name.put(p, e.getMessage());
                p.sendMessage("登録しました！");
                createReward(p);
            }
        }
        if (cmdc.containsKey(p)) {
            e.setCancelled(true);
            if (commands.size() == 8) {
                p.sendMessage("既にコマンドの数が最大です。");
                setCommands(p);
                return;
            }
            String cmds = "";
            cmdc.remove(p);
            if (e.getMessage().startsWith("/")) {
                cmds = e.getMessage();
            } else {
                cmds = "/" + e.getMessage();
            }
            List<String> sl = new ArrayList<>();
            sl.add(cmds);
            if (commands.size() == 0) {
                commands.put(p, sl);
                setCommands(p);
                return;
            }
            for (String s : commands.get(p)) {
                sl.add(s);
            }
            commands.put(p, sl);
            setCommands(p);
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public ItemStack createItem(Material Type, Integer Amount, short Color, String DisplayName, List<String> Lore, Boolean Glow) {
        ItemStack is = null;
        if (Color == 99) {
            is = new ItemStack(Type, Amount);
        } else {
            is = new ItemStack(Type, Amount, Color);
        }
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(DisplayName.replace("&", "§"));
        if (Lore != null) {
            List<String> sl = new ArrayList<>();
            for (String s : Lore) {
                sl.add(s.replace("&", "§"));
            }
            im.setLore(Lore);
        }
        if (Glow == true) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_DESTROYS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }
}