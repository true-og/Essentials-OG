package com.earth2me.essentials.api;

import net.ess3.api.IUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @deprecated This API is not asynchronous and is no longer maintained. Use {@link com.earth2me.essentials.api.IAsyncTeleport IAsyncTeleport}.
 */
public interface ITeleport {
    @Deprecated
    void now(Location loc, boolean cooldown, PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void now(Player entity, boolean cooldown, PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void teleport(Location loc) throws Exception;

    @Deprecated
    void teleport(Location loc, PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void teleport(Player entity, PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void teleportPlayer(IUser otherUser, Location loc, PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void teleportPlayer(IUser otherUser, Player entity, PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void respawn(PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void warp(IUser otherUser, String warp, PlayerTeleportEvent.TeleportCause cause) throws Exception;

    @Deprecated
    void back() throws Exception;

    @Deprecated
    void back(IUser teleporter) throws Exception;
}
