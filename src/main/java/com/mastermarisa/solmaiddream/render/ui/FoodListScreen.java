package com.mastermarisa.solmaiddream.render.ui;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.config.ModConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FoodListScreen extends Screen {
    private Player player;
    private EntityMaid maid;
    private FoodList foodList;
    private List<ItemStack> stacks;
    private double Yoffset;
    private int itemPerLine;
    private static final int itemSize = 16;
    private static final int startY = 40;
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Font font;

    static {
        font = mc.font;
    }

    public FoodListScreen(Player player,EntityMaid maid){
        super(Component.empty());
        this.player = player;
        this.maid = maid;
        this.foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
        this.stacks = new ArrayList<>();
        for(Item item : foodList.getFoodItems()){
            stacks.add(new ItemStack(item));
        }
        this.Yoffset = 0;
        this.itemPerLine = ModServerConfig.ITEMS_PER_LINE.get();
    }

    public static void open(Player player,EntityMaid maid) {
        Minecraft.getInstance().setScreen(new FoodListScreen(player,maid));
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.drawString(font,Component.translatable("screen.solmaid.title"),(mc.getWindow().getGuiScaledWidth() - font.width(Component.translatable("screen.solmaid.title").getString())) / 2,startY - 20 + (int)Yoffset, Color.WHITE.getRGB());
        for(int i = 0;i < this.stacks.size();i++){
            int j = (i / itemPerLine);
            ItemStack stack = this.stacks.get(i);
            int separation = itemSize + 4;
            int size = itemSize;
            int X = (int)((i - j * itemPerLine + itemPerLine / 2d) * separation - itemPerLine * separation + mc.getWindow().getGuiScaledWidth() / 2d);
            int Y = startY + j * separation + (int)Yoffset;
            graphics.renderItem(stack,X,Y);
            if(X < mouseX && mouseX < X + size && Y < mouseY && mouseY < Y + size){
                List<Component> tooltip = stack.getTooltipLines(Item.TooltipContext.of(mc.level), mc.player, mc.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
                graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY, stack);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.Yoffset += scrollY * 4d;
        if(this.Yoffset > 0){
            Yoffset = 0;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
