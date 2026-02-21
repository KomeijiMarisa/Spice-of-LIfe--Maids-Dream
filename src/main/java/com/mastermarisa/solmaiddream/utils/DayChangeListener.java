package com.mastermarisa.solmaiddream.utils;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.api.event.DayChangedEvent;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID)
public class DayChangeListener {
    private static long lastKnownDay = -1;

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event){
        MinecraftServer server = event.getServer();

        for (var level : server.getAllLevels()) {
            long worldTime = level.getDayTime();
            long currentDay = 0;
            if(worldTime >= 0){
                currentDay = worldTime/ 24000;
            }

            if (lastKnownDay != -1 && currentDay != lastKnownDay) {
                DayChangedEvent dayChangedEvent = new DayChangedEvent(currentDay);
                NeoForge.EVENT_BUS.post(dayChangedEvent);
            }
            lastKnownDay = currentDay;
            break;
        }
    }

    public static long getLastKnownDay() { return lastKnownDay; }
}
