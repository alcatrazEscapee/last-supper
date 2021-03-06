/*
 * Part of the Last Supper Mod by AlcatrazEscapee
 * Work under Copyright. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.lastsupper;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

import com.alcatrazescapee.lastsupper.util.Helpers;

public final class Config
{
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static
    {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static final class CommonConfig
    {
        public final ForgeConfigSpec.ConfigValue<String> catalystItem;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> ingredients;
        public final ForgeConfigSpec.BooleanValue enableInfiniteSaturation;

        CommonConfig(ForgeConfigSpec.Builder builder)
        {
            builder.push("general");

            catalystItem = builder
                    .comment("The item used as a catalyst to create the last supper")
                    .define("catalystItem", "minecraft:diamond");

            ingredients = builder
                    .comment("The items used as ingredients for the last supper")
                    .defineList("ingredients", getDefaultIngredients(), obj -> obj instanceof String);

            enableInfiniteSaturation = builder
                    .comment("If true, the last supper will also provide infinite saturation as well as hunger")
                    .define("enableInfiniteSaturation", false);

            builder.pop();
        }

        private List<? extends String> getDefaultIngredients()
        {
            return Helpers.listOf("minecraft:chorus_fruit",
                    "minecraft:golden_apple",
                    "minecraft:rabbit_stew",
                    "minecraft:mushroom_stew",
                    "minecraft:beetroot_soup",
                    "minecraft:suspicious_stew",
                    "minecraft:cooked_porkchop",
                    "minecraft:cooked_beef",
                    "minecraft:cooked_mutton",
                    "minecraft:cooked_chicken",
                    "minecraft:cooked_salmon",
                    "minecraft:cooked_cod",
                    "minecraft:cooked_rabbit",
                    "minecraft:spider_eye",
                    "minecraft:bread",
                    "minecraft:baked_potato",
                    "minecraft:golden_carrot",
                    "minecraft:dried_kelp",
                    "minecraft:melon_slice",
                    "minecraft:pumpkin_pie",
                    "minecraft:cookie",
                    "minecraft:cake",
                    "minecraft:milk_bucket",
                    "minecraft:pufferfish",
                    "minecraft:sweet_berries",
                    "minecraft:dried_kelp"
            );
        }
    }
}
