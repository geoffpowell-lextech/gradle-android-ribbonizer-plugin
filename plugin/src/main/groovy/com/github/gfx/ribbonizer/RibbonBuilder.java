package com.github.gfx.ribbonizer;

import com.android.build.gradle.api.ApplicationVariant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Consumer;

public class RibbonBuilder implements FilterBuilder {

    @Override
    public Consumer<BufferedImage> apply(ApplicationVariant variant, File iconFile) {
        return new RibbonFilter(variant.getBuildType().getName());
    }
}
