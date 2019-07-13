/*
 * Part of the Ore Veins Mod by alcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.lastsupper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import com.alcatrazescapee.lastsupper.Config;

public final class Helpers
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static Item catalyst = null;
    private static List<Ingredient> ingredients = null;
    private static boolean isLoaded = false;

    public static void reloadConfig()
    {
        ingredients = Config.COMMON.ingredients.get().stream().map(value -> {
            if (value.startsWith("#"))
            {
                Tag<Item> tag = ItemTags.getCollection().get(new ResourceLocation(value.substring(1)));
                if (tag != null)
                {
                    return Ingredient.fromTag(tag);
                }
                LOGGER.warn("Unknown tag: {} specified in last supper ingredients", value);
                return null;
            }
            else
            {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(value));
                if (item != null)
                {
                    return Ingredient.fromItems(item);
                }
                LOGGER.warn("Unknown item: {} specified in last supper ingredients", value);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        catalyst = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Config.COMMON.catalystItem.get()));
        if (catalyst == null)
        {
            LOGGER.info("Unknown item: {}. Defaulting to minecraft:diamond", Config.COMMON.catalystItem.get());
            catalyst = Items.DIAMOND;
        }

        isLoaded = true;
    }

    @Nonnull
    public static Item getCatalystItem()
    {
        if (!isLoaded)
        {
            reloadConfig();
        }
        return catalyst;
    }

    @Nonnull
    public static List<Ingredient> getIngredients()
    {
        if (!isLoaded)
        {
            reloadConfig();
        }
        // Copies the ingredients since they are removed during use
        return new ArrayList<>(ingredients);
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T notNull()
    {
        return null;
    }
}
