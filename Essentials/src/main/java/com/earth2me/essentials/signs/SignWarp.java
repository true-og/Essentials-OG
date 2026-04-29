package com.earth2me.essentials.signs;

import com.earth2me.essentials.User;
import net.ess3.api.IEssentials;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.concurrent.CompletableFuture;

import static com.earth2me.essentials.I18n.tl;

public class SignWarp extends EssentialsSign {
    public SignWarp() {
        super("Warp");
    }

    @Override
    protected boolean onSignCreate(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        final String warpName = sign.getLine(1);

        if (warpName.isEmpty()) {
            sign.setLine(1, "§c<Warp name>");
            throw new SignException(tl("invalidSignLine", 1));
        } else {
            try {
                ess.getWarps().getWarp(warpName);
            } catch (final Exception ex) {
                throw new SignException(ex.getMessage(), ex);
            }
            final String group = sign.getLine(2);
            if ("Everyone".equalsIgnoreCase(group) || "Everybody".equalsIgnoreCase(group)) {
                sign.setLine(2, "§2Everyone");
            }
            return true;
        }
    }

    @Override
    protected boolean onSignInteract(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        final String warpName = sign.getLine(1);
        final String group = sign.getLine(2);

        if (!group.isEmpty()) {
            if (!"§2Everyone".equals(group) && !player.inGroup(group)) {
                throw new SignException(tl("warpUsePermission"));
            }
        } else {
            if (ess.getSettings().getPerWarpPermission() && !player.isAuthorized("essentials.warps." + warpName)) {
                throw new SignException(tl("warpUsePermission"));
            }
        }

        final CompletableFuture<Boolean> future = new CompletableFuture<>();
        player.getAsyncTeleport().warp(player, warpName, TeleportCause.PLUGIN, future);
        future.exceptionally(e -> {
            ess.showError(player.getSource(), e, "\\ sign: " + signName);
            return false;
        });
        return true;
    }
}
