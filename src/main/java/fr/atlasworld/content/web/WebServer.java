package fr.atlasworld.content.web;

import fr.atlasworld.content.config.ConfigManager;
import fr.atlasworld.content.datagen.AssetsManager;
import io.javalin.Javalin;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class WebServer {
    public static final WebServer server = new WebServer();

    private final Javalin webserver;
    private boolean isActive;

    public WebServer() {
        this.webserver = Javalin.create();
        this.isActive = false;
    }

    public void startServer() {
        webserver.get("/pack", ctx -> {
            ctx.contentType("application/zip");
            ctx.header("Content-Disposition", "attachment; filename=\"pack.zip\"");

            // Write the file to the response
            OutputStream outputStream = ctx.outputStream();
            FileInputStream inputStream = new FileInputStream(AssetsManager.texturePack);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            inputStream.close();
        });
        this.isActive = true;
        webserver.start(ConfigManager.get().getInt("WebServerPort"));
    }

    public void stopServer() {
        webserver.stop();
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }
}
