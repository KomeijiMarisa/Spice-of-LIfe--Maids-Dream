package com.mastermarisa.solmaiddream.data;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.capability.MaidInfoCapability;

public class MaidInfo {
    public long existTime;
    /**
     * 完成的愿望数量
     */
    public int achievedWishCount;
    public int maxWishBuffCount;

    public MaidInfo() {
        this.existTime = 0;
        this.achievedWishCount = 0;
        this.maxWishBuffCount = 0;
    }

    public void markDirty(EntityMaid maid) {
        MaidInfoCapability.sync(maid);
    }
}
