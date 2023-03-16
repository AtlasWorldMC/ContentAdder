package fr.atlasworld.content.datagen;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.item.CustomItem;
import fr.atlasworld.content.api.utils.Identifier;
import fr.atlasworld.content.registering.Registry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AssetsManager {
    private static final Path texturePackFolderPath = Paths.get(ContentAdder.plugin.getDataFolder().getPath(), "temp");
    public static void generateTexturePack() {
        try {
            ContentAdder.logger.info("Preparing texture pack generation..");
            prepareTexturePack();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File[] pluginFiles = new File(System.getProperty("user.dir") + "/plugins").listFiles((dir, name) -> name.endsWith(".jar"));
        ContentAdder.logger.info(pluginFiles.length + " plugins found!");

        int count = 1;
        for (File pluginFile : pluginFiles) {
            if (!hasAssetsFolder(pluginFile)) {
                ContentAdder.logger.info(pluginFile.getName().substring(0, pluginFile.getName().lastIndexOf('.')) +
                        " skipped, no assets found! (" + count + "/" + pluginFiles.length + ")");
            } else {
                long methodStartedTime = System.currentTimeMillis();
                copyPluginAssets(pluginFile, Path.of(texturePackFolderPath.toString(), "pack", "assets"));
                ContentAdder.logger.info(pluginFile.getName().substring(0, pluginFile.getName().lastIndexOf('.')) +
                        " assets loaded in " + (System.currentTimeMillis() - methodStartedTime) + "ms (" + count + "/" + pluginFiles.length + ")");
            }
            count++;
        }

        ContentAdder.logger.info("Successfully loaded assets!");
    }

    private static void prepareTexturePack() throws IOException {
        if (!Files.exists(texturePackFolderPath)) {
            Files.createDirectories(texturePackFolderPath);

        }

        //Gen Texture pack tree
        Path minecraftRootPath = Paths.get(texturePackFolderPath.toString(), "pack", "assets");
        Files.createDirectories(minecraftRootPath);
    }

    private static boolean hasAssetsFolder(File file) {
        try (ZipFile zipFile = new ZipFile(file)) {
            for (ZipEntry entry : Collections.list(zipFile.entries())) {
                if (entry.getName().equals("assets/") && entry.isDirectory()) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read ZIP file: " + file.getAbsolutePath(), e);
        }
    }

    private static void copyPluginAssets(File file, Path dest) {
        try (ZipFile zipFile = new ZipFile(file)) {
            String assetsPath = "assets/";
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                // Check if entry is inside the assets folder and is not a directory
                if (entry.getName().startsWith(assetsPath) && !entry.isDirectory()) {
                    String relativePath = entry.getName().substring(assetsPath.length());
                    File outputFile = new File(dest.toString(), relativePath);

                    if (!outputFile.getParentFile().exists()) {
                        outputFile.getParentFile().mkdirs();
                    }

                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         OutputStream outputStream = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }

                // Check if entry is inside the assets folder and is a directory
                if (entry.getName().startsWith(assetsPath) && entry.isDirectory()) {
                    String relativePath = entry.getName().substring(assetsPath.length());
                    File outputDir = new File(dest.toString(), relativePath);

                    if (!outputDir.exists()) {
                        outputDir.mkdirs();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void editMinecraftItemModel(CustomItem item, Path textureRootPath) {
        Identifier itemIdentifier = Registry.ITEM.getIdentifier(item);
        File modelFile = Path.of(textureRootPath.toString(), item.getParent().getKey().getNamespace(), "models",
                "item", item.getParent().getKey().getKey() + ".json").toFile();
        if (!modelFile.exists()) throw new RuntimeException("Unable to find " + itemIdentifier + " parent item model file!");


    }

    public static void modifyItemModelFiles() {
        Registry.ITEM.getEntries().forEach(item -> editMinecraftItemModel(item, Path.of(texturePackFolderPath.toString(), "pack", "assets")));
        ContentAdder.logger.info("Successfully modified item model files!");
    }

    private static void cleanTexturePack() {

    }
}
