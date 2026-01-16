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
public class FoodListSyncHandler implements AttachmentSyncHandler<FoodList> {
    @Override
    public void write(RegistryFriendlyByteBuf registryFriendlyByteBuf, FoodList foodList, boolean b) {
        registryFriendlyByteBuf.writeCollection(foodList.getFoods(), FriendlyByteBuf::writeUtf);
        registryFriendlyByteBuf.writeCollection(foodList.getCurrentWishes(), FriendlyByteBuf::writeUtf);
        registryFriendlyByteBuf.writeInt(foodList.getWishesAchieved());
        registryFriendlyByteBuf.writeInt(foodList.getWishesCycleCount());
        registryFriendlyByteBuf.writeInt(foodList.getWishesAchievedInCycle());
    }

    @Override
    public @Nullable FoodList read(IAttachmentHolder iAttachmentHolder, RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable FoodList foodList) {
        if(foodList == null){
            foodList = new FoodList();
        }
        foodList.setFoods(registryFriendlyByteBuf.readCollection(HashSet::new, FriendlyByteBuf::readUtf));
        foodList.setCurrentWishes(registryFriendlyByteBuf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf));
        foodList.setWishesAchieved(registryFriendlyByteBuf.readInt());
        foodList.setWishesCycleCount(registryFriendlyByteBuf.readInt());
        foodList.setWishesAchievedInCycle(registryFriendlyByteBuf.readInt());
        return foodList;
    }
}
