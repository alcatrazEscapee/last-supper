/*
 * Part of the Last Supper Mod by alcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.lastsupper.items;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

import com.alcatrazescapee.lastsupper.util.Helpers;

import static com.alcatrazescapee.lastsupper.LastSupper.MOD_ID;

public class ModItems
{
    @ObjectHolder(MOD_ID + ":last_supper")
    public static final LastSupperItem LAST_SUPPER = Helpers.notNull();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new LastSupperItem().setRegistryName("last_supper"));
    }

    private ModItems() {}
}
