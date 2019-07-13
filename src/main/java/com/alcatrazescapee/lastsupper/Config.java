/*
 * Part of the Ore Veins Mod by alcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.lastsupper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Config
{
    public static final CommonConfig COMMON;

    static final ForgeConfigSpec COMMON_SPEC;

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

        CommonConfig(ForgeConfigSpec.Builder builder)
        {
            builder.push("general");

            catalystItem = builder
                    .comment("The item used as a catalyst to create the last supper")
                    .define("catalystItem", "minecraft:diamond");

            ingredients = builder
                    .comment("The items used as ingredients for the last supper")
                    .defineList("ingredients", getDefaultIngredients(), obj -> obj instanceof String);

            builder.pop();
        }

        private List<? extends String> getDefaultIngredients()
        {
            return new ArrayList<>(Arrays.asList(
                    "minecraft:chorus_fruit",
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
            ));
        }
    }
}
