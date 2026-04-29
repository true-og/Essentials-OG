package com.earth2me.essentials.api;

import net.ess3.api.IUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

public interface IAsyncTeleport {

    void now(Location loc, boolean cooldown, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void now(Player entity, boolean cooldown, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void nowUnsafe(Location loc, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void teleport(Location loc, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void teleport(Player entity, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void teleportPlayer(IUser otherUser, Location loc, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void teleportPlayer(IUser otherUser, Player entity, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void respawn(PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void warp(IUser otherUser, String warp, PlayerTeleportEvent.TeleportCause cause, CompletableFuture<Boolean> future);

    void back(CompletableFuture<Boolean> future);

    void back(IUser teleporter, CompletableFuture<Boolean> future);
}
