package com.mastermarisa.solmaiddream.render;

import net.irisshaders.iris.api.v0.IrisApi;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;

@OnlyIn(Dist.CLIENT)
public class ShaderStateHelper {
    public static boolean shaderEnabled(){
        return IrisApi.getInstance().isShaderPackInUse();
    }
}
