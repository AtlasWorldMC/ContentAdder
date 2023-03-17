package fr.atlasworld.content.web;

import fr.atlasworld.content.ContentAdder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebUtils {
    public static String getIp() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                String ip = in.readLine();
                return ip;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        ContentAdder.logger.error("Unable to close connection: " + e);
                    }
                }
            }
        } catch (Exception e) {
            ContentAdder.logger.error("Unable to retrieve server public ip! (Does the server have an internet connection?): " + e);
            return "127.0.0.1";
        }
    }
}
