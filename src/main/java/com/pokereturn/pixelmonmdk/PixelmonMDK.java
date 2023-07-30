package com.pokereturn.pixelmonmdk;

import com.pokereturn.pixelmonmdk.cache.PlayerCache;
import com.pokereturn.pixelmonmdk.command.Command;
import com.pokereturn.pixelmonmdk.config.Config;
import com.pokereturn.pixelmonmdk.config.LanguageConfig;
import com.pokereturn.pixelmonmdk.listeners.PlayerListener;
import com.pokereturn.pixelmonmdk.wrapper.PermissionWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pixelmonmdk")
public class PixelmonMDK {

    public static PixelmonMDK instance;

    public static final String MOD_NAME = "PixelmonMDK";
    public static final String VERSION = "1.0.0";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2023";
    public static final Logger log = LogManager.getLogger(MOD_NAME);
    private static MinecraftServer server;
    public static File configDir;
    public static File storageDir;
    public static File playerStorageDir;

    public static PermissionWrapper permissionWrapper;

    public static PlayerCache playerCache;

    public static LanguageConfig languageConfig;

    public static Config config;

    public PixelmonMDK() {
        instance = this;
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        PixelmonMDK.configDir = configDir;
    }

    public static File getPlayerStorageDir() {
        return playerStorageDir;
    }

    public static void setPlayerStorageDir(File playerStorageDir) {
        PixelmonMDK.playerStorageDir = playerStorageDir;
    }

    public static MinecraftServer getServer() {
        return server;
    }



    private void setup(final FMLCommonSetupEvent event) {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
        initDirs();
    }

    @SubscribeEvent
    public void onCommandRegistry(RegisterCommandsEvent event)
    {
        permissionWrapper = new PermissionWrapper();

        event.getDispatcher().register(Command.getCommand());
    }


    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event) {
        log.warn("Finalising set up");
        server = ServerLifecycleHooks.getCurrentServer();
        initConfig();
        registerListeners();
        playerCache = new PlayerCache();
        log.warn("Finalised set up");
    }

    public void registerListeners()
    {
        log.warn("Registering listeners");
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
    }

    public void initDirs() {
        log.log(Level.WARN, "Setting up Storage Paths and Directories for %name%".replace("%name%", MOD_NAME));
        setConfigDir(new File(FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()) + "/%name%/".replace("%name%", MOD_NAME)));
        getConfigDir().mkdir();
        storageDir = new File(configDir + "/%name%/Storage/".replace("%name%", MOD_NAME));
        storageDir.mkdirs();
        playerStorageDir = new File(storageDir + "/PlayerData/");
        playerStorageDir.mkdirs();
    }

    public void loadConfigs() {
        log.log(Level.WARN, "Loading and Reading Config Data for %name%".replace("%name%", MOD_NAME));
        LanguageConfig.writeConfig();
        languageConfig = LanguageConfig.getConfig();

        Config.writeConfig();
        config = Config.getConfig();

    }

    public void initConfig() {
        loadConfigs();
    }

    public void save()
    {
        if (config != null)
            Config.saveConfig(config);
        else log.error("Failed to save..");
    }


    public void reload()
    {
        log.warn("Reload Requested... Just a moment");
        initDirs();
        initConfig();

    }

}
