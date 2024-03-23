package me.portmapping.airdrops.util.general;

import lombok.experimental.UtilityClass;
import me.portmapping.airdrops.Airdrops;
import me.portmapping.airdrops.util.chat.CC;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

@UtilityClass
public class ServerUtil {

    public final String SERVER_VERSION =
            Bukkit.getServer()
                    .getClass().getPackage()
                    .getName().split("\\.")[3]
                    .substring(1);

    public final int SERVER_VERSION_INT = Integer.parseInt(
            SERVER_VERSION
                    .replace("1_", "")
                    .replaceAll("_R\\d", ""));


    public String getIP() {
        String ipAddress;

        try {
            URL url = new URL("http://checkip.amazonaws.com/%22");
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = in.readLine()) != null) {
                builder.append(line);
            }

            ipAddress = builder.toString();
        }
        catch (UnknownHostException ex) {
            ipAddress = "NONE";
            CC.log("Problem on the page with the IPS.");
        }
        catch (IOException ex) {
            ipAddress = "NONE";
            CC.log("Error in check your host IP.");
        }
        return ipAddress + ":" + Airdrops.getInstance().getServer().getPort();
    }

}
