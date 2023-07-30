package com.pokereturn.pixelmonmdk.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.pokereturn.pixelmonmdk.PixelmonMDK;
import com.pokereturn.pixelmonmdk.util.Adapters;

import java.io.*;

public class LanguageConfig
{
    public String prefix;
    public String splitter;

    public LanguageConfig()
    {
        this.prefix = "&6[&bPixelmonMDK&6]";
        this.splitter = " &e» ";
    }

    public static void writeConfig()
    {
        File dir = PixelmonMDK.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        LanguageConfig config = new LanguageConfig();
        try {
            File file = new File(dir, "language.json");
            if (file.exists())
                return;
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            String json = gson.toJson(config);
            writer.write(json);
            writer.close();
        } catch (IOException e)
        {
            PixelmonMDK.log.warn(e);
        }
    }

    public static LanguageConfig getConfig()
    {
        File dir = PixelmonMDK.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        File file = new File(dir, "language.json");
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            PixelmonMDK.log.error("Something went wrong attempting to read the Config");
            return null;
        }

        return gson.fromJson(reader, LanguageConfig.class);
    }
}
