package com.mastermarisa.solmaiddream.utils;

import com.mastermarisa.solmaiddream.event.DayChangedEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class DayChangeListener {
    private static long lastKnownDay = -1;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event){
        MinecraftServer server = event.getServer();
        if (server == null) return;

        for (var level : server.getAllLevels()) {
            long worldTime = level.getDayTime();
            long currentDay = 0;
            if(worldTime >= 0){
                currentDay = worldTime/ 24000;
            }

            if (lastKnownDay != -1 && currentDay != lastKnownDay) {
                DayChangedEvent dayChangedEvent = new DayChangedEvent(currentDay);
                EVENT_BUS.post(dayChangedEvent);
            }
            lastKnownDay = currentDay;
            break;
        }
    }

    public static long getLastKnownDay() { return lastKnownDay; }
}
