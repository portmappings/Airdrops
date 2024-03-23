package me.portmapping.airdrops.util.chat;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static String getStringFromList(List<String> source) {
        if (source.size() == 0) {
            return "Empty";
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String piece : source) {
            builder.append(piece.replace(",", "#%&$"));
            if (i < source.size() - 1) {
                builder.append(", ");
            }
            i++;
        }
        return builder.toString();
    }

    public static List<String> getListFromString(String source) {
        if (source.equals("Empty")) return new ArrayList<>();

        return Arrays.stream(source.split(", ")).map(s -> s.replace("#%&$", ",")).collect(Collectors.toList());
    }

    public static String convertFirstUpperCase(String source) {
        return source.substring(0, 1).toUpperCase() + source.substring(1);
    }

    public static String buildMessage(String[] args, int start) {
        if (start >= args.length) {
            return "";
        }
        return ChatColor.stripColor(String.join(" ", Arrays.copyOfRange(args, start, args.length)));
    }
}
