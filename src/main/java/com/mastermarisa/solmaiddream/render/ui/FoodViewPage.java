package com.mastermarisa.solmaiddream.render.ui;

import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.render.ui.element.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FoodViewPage extends Page{
    private static final int itemsPerRow = 6;
    public static final int itemsPerPage = 42;
    private static final int spacingX = 3;
    private static final int spacingY = 1;

    private final UIContainerVertical pageContainer;

    public FoodViewPage(Rectangle frame, String header, List<ItemStack> foods){
        super(frame,header);
        List<UIItemStack> UIFoods = foods.stream().map(UIItemStack::new).toList();
        int width = itemsPerRow * 16 + (itemsPerRow - 1) * spacingX;

        List<UIElement> rows = new ArrayList<>();
        for (int i = 0;i < (UIFoods.size() / itemsPerRow) + 1;i++){
            if (i * itemsPerRow == UIFoods.size()) break;

            if ((i + 1) * itemsPerRow <= UIFoods.size()){
                rows.add(UIContainerHorizontal.wrap(new Rectangle(width,16),UIFoods.subList(i * itemsPerRow,(i + 1) * itemsPerRow),spacingX,0,UIContainerHorizontal.ElementAlignment.LEFT));
            } else {
                rows.add(UIContainerHorizontal.wrap(new Rectangle(width,16),UIFoods.subList(i * itemsPerRow,UIFoods.size()),spacingX,0,UIContainerHorizontal.ElementAlignment.LEFT));
            }
            rows.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));
        }

        pageContainer = UIContainerVertical.wrap(rows,spacingY,0,UIContainerVertical.ElementAlignment.UP);

        this.children = List.of(bg,title,underLine,pageContainer);
    }

    public void onResize(){
        super.onResize();

        pageContainer.setCenterX(FoodListScreen.getScreenCenterX());
        pageContainer.setMinY(underLine.getMaxY() + 1);
    }

    public void onSwitchedTo(){
        onResize();
    }
}
