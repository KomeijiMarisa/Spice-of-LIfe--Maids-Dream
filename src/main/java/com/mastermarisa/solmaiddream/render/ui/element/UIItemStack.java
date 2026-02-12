package com.mastermarisa.solmaiddream.render.ui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.awt.Rectangle;
import java.util.List;

public class UIItemStack extends UIElement {
    public static final int size = 16;
    public ItemStack itemStack;

    public UIItemStack(ItemStack itemStack) {
        super(new Rectangle(16, 16));
        this.itemStack = itemStack;
    }

    protected void render(GuiGraphics graphics) {
        super.render(graphics);
        graphics.renderItem(this.itemStack, this.frame.x + (this.frame.width - 16) / 2, this.frame.y + (this.frame.height - 16) / 2);
    }

    public boolean hasTooltip() {
        return true;
    }

    protected void tryRenderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        if (!hasTooltip()) return;
        boolean hover = frame.contains(mouseX,mouseY);
        if (hover){
            Player currentPlayer = mc.player;
            List<Component> tooltip = this.itemStack.getTooltipLines(
                    currentPlayer,
                    mc.options.advancedItemTooltips ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL
            );
            this.renderTooltip(graphics, this.itemStack, tooltip, mouseX, mouseY);
        }
    }
}
