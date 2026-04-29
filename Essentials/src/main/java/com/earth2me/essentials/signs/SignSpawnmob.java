package com.earth2me.essentials.signs;

import com.earth2me.essentials.SpawnMob;
import com.earth2me.essentials.User;
import net.ess3.api.IEssentials;

import java.util.List;

public class SignSpawnmob extends EssentialsSign {
    public SignSpawnmob() {
        super("Spawnmob");
    }

    @Override
    protected boolean onSignCreate(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        validateInteger(sign, 1);
        return true;
    }

    @Override
    protected boolean onSignInteract(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        try {
            final List<String> mobParts = SpawnMob.mobParts(sign.getLine(2));
            final List<String> mobData = SpawnMob.mobData(sign.getLine(2));
            SpawnMob.spawnmob(ess, ess.getServer(), player.getSource(), player, mobParts, mobData, Integer.parseInt(sign.getLine(1)));
        } catch (final Exception ex) {
            throw new SignException(ex.getMessage(), ex);
        }
        return true;
    }
}
