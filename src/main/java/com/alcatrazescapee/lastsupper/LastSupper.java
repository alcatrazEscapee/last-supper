package com.alcatrazescapee.lastsupper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.alcatrazescapee.lastsupper.capability.IPlayerData;
import com.alcatrazescapee.lastsupper.capability.PlayerData;
import com.alcatrazescapee.lastsupper.items.ModItems;
import com.alcatrazescapee.lastsupper.util.Helpers;

import static com.alcatrazescapee.lastsupper.LastSupper.MOD_ID;

@Mod(value = MOD_ID)
public class LastSupper
{
    public static final String MOD_ID = "lastsupper";

    private static final Logger LOGGER = LogManager.getLogger();

    public LastSupper()
    {
        LOGGER.debug("Hello debug logging!");

        // Setup config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        // Event subscribers
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        FMLJavaModLoadingContext.get().getModEventBus().register(new ModItems());
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event)
    {
        // Init capability for tracking player
        CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerData.Storage(), PlayerData.Provider::new);
    }

    @SubscribeEvent
    public void onConfigReload(ModConfig.ConfigReloading event)
    {
        // Reload cached ingredients + catalyst item
        Helpers.reloadConfig();
    }
}
