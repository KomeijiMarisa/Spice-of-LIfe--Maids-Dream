package com.mastermarisa.solmaiddream.render;

import net.irisshaders.iris.api.v0.IrisApi;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShaderStateHelper {
    public static boolean shaderEnabled(){
        return IrisApi.getInstance().isShaderPackInUse();
    }
}
