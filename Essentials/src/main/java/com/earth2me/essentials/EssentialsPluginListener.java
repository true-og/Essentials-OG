package com.earth2me.essentials;

import net.ess3.api.IEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class EssentialsPluginListener implements Listener, IConf {
    private final transient IEssentials ess;

    public EssentialsPluginListener(final IEssentials ess) {
        this.ess = ess;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(final PluginEnableEvent event) {
        if (event.getPlugin().getName().equals("EssentialsChat")) {
            ess.getSettings().setEssentialsChatActive(true);
        }
        ess.getPermissionsHandler().setUseSuperperms(ess.getSettings().useBukkitPermissions());
        ess.getPermissionsHandler().checkPermissions();
        ess.getAlternativeCommandsHandler().addPlugin(event.getPlugin());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(final PluginDisableEvent event) {
        if (event.getPlugin().getName().equals("EssentialsChat")) {
            ess.getSettings().setEssentialsChatActive(false);
        }
        ess.getPermissionsHandler().checkPermissions();
        ess.getAlternativeCommandsHandler().removePlugin(event.getPlugin());
    }

    @Override
    public void reloadConfig() {
    }
}
