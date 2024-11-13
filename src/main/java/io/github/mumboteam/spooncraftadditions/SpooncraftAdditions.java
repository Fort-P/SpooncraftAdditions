package io.github.mumboteam.spooncraftadditions;

import eu.pb4.playerdata.api.PlayerDataApi;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import io.github.mumboteam.spooncraftadditions.command.BlameGarethCommand;
import io.github.mumboteam.spooncraftadditions.command.IsItGarethsFaultCommand;
import io.github.mumboteam.spooncraftadditions.component.ModComponents;
import io.github.mumboteam.spooncraftadditions.loot.ModLootTables;
import io.github.mumboteam.spooncraftadditions.registry.ModBlockEntityTypes;
import io.github.mumboteam.spooncraftadditions.registry.ModBlocks;
import io.github.mumboteam.spooncraftadditions.registry.ModItems;
import io.github.mumboteam.spooncraftadditions.reward.Rewards;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpooncraftAdditions implements ModInitializer {
    public static final String ID = "spooncraftadditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModBlockEntityTypes.initialize();
        ModComponents.initialize();
        ModLootTables.initialize();
        PolymerResourcePackUtils.addModAssets(ID);
        PlayerDataApi.register(Rewards.CLAIMED_REWARDS_STORAGE);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            BlameGarethCommand.register(dispatcher, "blamegareth", true);
            BlameGarethCommand.register(dispatcher, "forgivegareth", false);
            IsItGarethsFaultCommand.register(dispatcher);
        });

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.of(SpooncraftAdditions.ID, "reward");
            }

            @Override
            public void reload(ResourceManager manager) {
                Rewards.reload(manager);
            }
        });
    }
}