package com.earth2me.essentials.signs;

import com.earth2me.essentials.User;
import net.ess3.api.IEssentials;

import static com.earth2me.essentials.I18n.tl;

public class SignHeal extends EssentialsSign {
    public SignHeal() {
        super("Heal");
    }

    @Override
    protected boolean onSignCreate(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        return true;
    }

    @Override
    protected boolean onSignInteract(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        if (player.getBase().getHealth() == 0) {
            throw new SignException(tl("healDead"));
        }
        player.getBase().setHealth(20);
        player.getBase().setFoodLevel(20);
        player.getBase().setFireTicks(0);
        player.sendMessage(tl("youAreHealed"));
        return true;
    }
}
