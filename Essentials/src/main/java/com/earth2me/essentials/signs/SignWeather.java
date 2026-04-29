package com.earth2me.essentials.signs;

import com.earth2me.essentials.User;
import net.ess3.api.IEssentials;

import static com.earth2me.essentials.I18n.tl;

public class SignWeather extends EssentialsSign {
    public SignWeather() {
        super("Weather");
    }

    @Override
    protected boolean onSignCreate(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        if (sign.getLine(1).isEmpty() && sign.getLine(2).isEmpty() && sign.getLine(3).isEmpty()) {
            return true;
        }

        final String timeString = sign.getLine(1);
        if ("Sun".equalsIgnoreCase(timeString)) {
            sign.setLine(1, "§2Sun");
            return true;
        }
        if ("Storm".equalsIgnoreCase(timeString)) {
            sign.setLine(1, "§2Storm");
            return true;
        }
        sign.setLine(1, "§c<sun|storm>");
        throw new SignException(tl("onlySunStorm"));
    }

    @Override
    protected boolean onSignInteract(final ISign sign, final User player, final String username, final IEssentials ess) throws SignException {
        if (sign.getLine(1).isEmpty() && sign.getLine(2).isEmpty() && sign.getLine(3).isEmpty()) {
            if (player.getWorld().hasStorm()) {
                player.sendMessage(tl("weatherSignStorm"));
            } else {
                player.sendMessage(tl("weatherSignSun"));
            }
            return true;
        }

        final String weatherString = sign.getLine(1);
        if ("§2Sun".equalsIgnoreCase(weatherString)) {
            player.getWorld().setStorm(false);
            return true;
        }
        if ("§2Storm".equalsIgnoreCase(weatherString)) {
            player.getWorld().setStorm(true);
            return true;
        }
        throw new SignException(tl("onlySunStorm"));
    }
}
