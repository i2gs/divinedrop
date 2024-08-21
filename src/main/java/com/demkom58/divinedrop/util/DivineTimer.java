package com.demkom58.divinedrop.util;

import com.demkom58.divinedrop.DivineDrop;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import space.arim.morepaperlib.scheduling.ScheduledTask;

import java.time.Duration;

public class DivineTimer {
    private final JavaPlugin plugin;
    private final Runnable handler;

    private ScheduledTask handleTimer;

    public DivineTimer(@NotNull final JavaPlugin plugin,
                       @NotNull final Runnable handler) {
        this.plugin = plugin;
        this.handler = handler;
    }

    public boolean start() {
        if (handleTimer != null)
            return false;

        handleTimer = DivineDrop.getMorePaperLib().scheduling().asyncScheduler().runAtFixedRate(handler, Duration.ofMillis(1), Duration.ofSeconds(1));

        return true;
    }

    public boolean stop() {
        if (handleTimer == null)
            return false;

        handleTimer.cancel();
        handleTimer = null;

        return true;
    }


}
