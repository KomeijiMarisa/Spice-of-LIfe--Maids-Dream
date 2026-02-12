package com.mastermarisa.solmaiddream.utils;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;

import java.util.ArrayList;
import java.util.List;

public class MaidTracker {
    private static final List<EntityMaid> maids = new ArrayList<>();

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            maids.add((EntityMaid) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            EntityMaid maid = (EntityMaid) event.getEntity();
            maids.remove(maid);
        }
    }

    public static List<EntityMaid> getMaids(){
        return maids;
    }
}
