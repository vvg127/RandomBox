package org.plugin.randombox;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomBox extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new BoxGet(),this);
        getServer().getPluginManager().registerEvents(new BoxUse(),this);
        getServer().getPluginManager().registerEvents(new SpecialItem(this),this);
        Thunder.startTask();
        LeggingsTask.startTask();
        ChestTask.startTask();
        getCommand("giveitems").setExecutor(new DevTool());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
