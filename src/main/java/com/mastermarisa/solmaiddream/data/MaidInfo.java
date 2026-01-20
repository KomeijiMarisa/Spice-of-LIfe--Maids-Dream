package com.mastermarisa.solmaiddream.data;

import com.github.tartaricacid.touhoulittlemaid.data.PowerAttachment;
import com.mastermarisa.solmaiddream.utils.ModUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class MaidInfo implements INBTSerializable<CompoundTag> {
    public long existTime;
    public int achievedWishCount;
    public int maxWishBuffCount;

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putLong("existTime",existTime);
        tag.putInt("achievedWishCount",achievedWishCount);
        tag.putInt("maxWishBuffCount",maxWishBuffCount);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        existTime = tag.getLong("existTime");
        achievedWishCount = tag.getInt("achievedWishCount");
        maxWishBuffCount = tag.getInt("maxWishBuffCount");
    }

    public static final AttachmentType<MaidInfo> TYPE = AttachmentType.serializable(MaidInfo::new).sync(new MaidInfoSyncHandler()).build();
}
