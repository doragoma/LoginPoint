package loginpoint.loginpoint;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class LoginPoint extends JavaPlugin {

    private static LoginPoint instance;

    public static LoginPoint getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        File file = new File("plugins/LoginPoint/config.yml");
        if (!file.exists()) {
            saveResource("config.yml",false);
        }
        getCommand("loginpoint").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new Reward(), this);
        getServer().getPluginManager().registerEvents(new GetPoint(), this);
    }

    @Override
    public void onDisable() {
        new Count().ServerDown();
    }
}