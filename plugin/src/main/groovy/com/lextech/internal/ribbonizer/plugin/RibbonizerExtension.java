package com.lextech.internal.ribbonizer.plugin;

import com.android.build.gradle.api.ApplicationVariant;
import com.lextech.internal.ribbonizer.FilterBuilder;
import com.lextech.internal.ribbonizer.RibbonBuilder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

class RibbonizerExtension {

    public static String NAME = "ribbonizer";

    List<FilterBuilder> filterBuilders = new ArrayList<>();

    public RibbonizerExtension() {
    }

    public List<FilterBuilder> getFilterBuilders() {
        return filterBuilders;
    }

    public void setFilterBuilders(Collection<FilterBuilder> filterBuilders) {
        this.filterBuilders = new ArrayList<>(filterBuilders);
    }

    public void builder(FilterBuilder filterBuilder)
            throws IllegalAccessException, InstantiationException {
        this.filterBuilders.clear();
        this.filterBuilders.add(filterBuilder);
    }

    // utilities
    public Consumer<BufferedImage> ribbonFilter(ApplicationVariant variant, File iconFile) {
        return new RibbonBuilder().apply(variant, iconFile);
    }

}
