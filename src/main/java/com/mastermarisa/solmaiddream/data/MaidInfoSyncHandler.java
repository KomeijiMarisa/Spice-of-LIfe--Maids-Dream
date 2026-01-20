package com.mastermarisa.solmaiddream.data;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashSet;

@ParametersAreNonnullByDefault
public class MaidInfoSyncHandler implements AttachmentSyncHandler<MaidInfo> {
    @Override
    public void write(RegistryFriendlyByteBuf registryFriendlyByteBuf, MaidInfo info, boolean b) {
        registryFriendlyByteBuf.writeLong(info.existTime);
        registryFriendlyByteBuf.writeInt(info.achievedWishCount);
        registryFriendlyByteBuf.writeInt(info.maxWishBuffCount);
    }

    @Override
    public @Nullable MaidInfo read(IAttachmentHolder iAttachmentHolder, RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable MaidInfo info) {
        if (info == null){
            info = new MaidInfo();
        }
        info.existTime = registryFriendlyByteBuf.readLong();
        info.achievedWishCount = registryFriendlyByteBuf.readInt();
        info.maxWishBuffCount = registryFriendlyByteBuf.readInt();

        return info;
    }
}
