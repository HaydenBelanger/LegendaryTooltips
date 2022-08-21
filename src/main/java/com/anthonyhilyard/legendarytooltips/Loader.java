package com.anthonyhilyard.legendarytooltips;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Loader.MODID)
public class Loader
{
	public static final String MODID = "legendarytooltips";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public Loader()
	{
		if (FMLEnvironment.dist == Dist.CLIENT && Minecraft.getInstance() != null && !ModLoader.isDataGenRunning())
		{
			new LegendaryTooltips();
			MinecraftForge.EVENT_BUS.register(LegendaryTooltips.class);

			// Check for legacy config files to convert now.
			LegacyConfigConverter.convert();

			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LegendaryTooltipsConfig.SPEC);

			if (Minecraft.getInstance().getResourceManager() instanceof ReloadableResourceManager resourceManager)
			{
				resourceManager.registerReloadListener(FrameResourceParser.INSTANCE);
			}
		}

		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "ANY", (remote, isServer) -> true));
	}

}