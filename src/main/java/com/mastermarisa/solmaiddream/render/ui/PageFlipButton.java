package com.mastermarisa.solmaiddream.render.ui;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PageFlipButton extends Button {
    private static final ResourceLocation texture = SOLMaidDream.resourceLocation("textures/gui/food_book.png");
    public static final int width = 23;
    public static final int height = 13;
    public final Pageable pageable;
    public final int relativeDirection;

    public PageFlipButton(int x,int y,Pageable pageable,int relativeDirection){
        super(x, y, 17, 10, CommonComponents.EMPTY, (button) -> pageable.switchToPage(pageable.getCurrentPageNumber() + relativeDirection), DEFAULT_NARRATION);
        this.pageable = pageable;
        this.relativeDirection = relativeDirection;
    }

    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int x = 0;
        int y = 0;
        if (this.visible) {
            if (relativeDirection > 0){
                if (isHovered()){
                    x = 26;
                    y = 194;
                } else {
                    x = 3;
                    y = 194;
                }
            } else {
                if (isHovered()){
                    x = 26;
                    y = 207;
                } else {
                    x = 3;
                    y = 207;
                }
            }
        }

        graphics.blit(texture, this.getX(), this.getY(), x,y,17,10,400,256);
    }

    public void updateState() {
        this.visible = this.pageable.isWithinRange(this.pageable.getCurrentPageNumber() + relativeDirection);
    }

    interface Pageable {
        void switchToPage(int pageNumber);

        int getCurrentPageNumber();

        boolean isWithinRange(int pageNumber);
    }
}
