package de.undefinedhuman.core.utils;

import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.log.Log;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tools {

    public static boolean isMac = System.getProperty("os.name").contains("OS X");
    public static boolean isWindows = System.getProperty("os.name").contains("Windows");
    public static boolean isLinux = System.getProperty("os.name").contains("Linux");

    private static Random random = new Random();

    public static SettingsObject loadSettings(FileReader reader) {
        SettingsObject settingsObject = new SettingsObject();
        while(reader.nextLine() != null) {
            String key = reader.getNextString();
            if(key.startsWith("}")) return settingsObject;
            else if(key.startsWith("{")) settingsObject.addSetting(loadKey(key), loadSettings(reader));
            else settingsObject.addSetting(key, reader.getRemainingRawData());
        }
        return settingsObject;
    }

    private static String loadKey(String key) {
        String[] values = key.split(":");
        if(values.length < 2)
            Log.instance.crash("Can't find key in string: " + key);
        return values[1];
    }

    public static void saveSettings(FileWriter writer, List<Setting> settings) {
        for(Setting setting : settings) {
            setting.save(writer);
            writer.nextLine();
        }
    }

    public static String convertArrayToString(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array)
            builder.append(s).append(Variables.SEPARATOR);
        return builder.toString();
    }

    public static String convertArrayToString(Vector2f[] array) {
        StringBuilder builder = new StringBuilder();
        for (Vector2f current : array)
            builder.append(current.x).append(Variables.SEPARATOR).append(current.y).append(Variables.SEPARATOR);
        return builder.toString();
    }

    public static void addSettings(JPanel panel, ArrayList<Setting> settings) {
        int currentOffset = 0;
        for (Setting setting : settings) {
            setting.addMenuComponents(panel, new Vector2f(0, currentOffset));
            currentOffset += setting.offset;
        }
        resetPanel(panel);
    }

    public static void removeSettings(JPanel panel) {
        panel.removeAll();
        resetPanel(panel);
    }

    public static void resetPanel(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    public static String appendSToString(int length) {
        return length > 1 ? "s" : "";
    }

    public static BufferedImage scaleNearest(BufferedImage before, double scale) {
        return scale(before, scale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    }

    private static BufferedImage scale(final BufferedImage before, double scale, int type) {
        int w = before.getWidth(), h = before.getHeight(), w2 = (int) (w * scale), h2 = (int) (h * scale);
        BufferedImage after = new BufferedImage(w2, h2, before.getType());
        AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(scaleInstance, type);
        scaleOp.filter(before, after);
        return after;
    }

    public static boolean isDigit(String text) {
        try { Integer.parseInt(text); return true; } catch (NumberFormatException ex) { return false; }
    }


    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) return original;
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }

    public static boolean contains(byte worldBlockID, byte[] blockIDs) {
        for (byte id : blockIDs) if (worldBlockID == id) return false;
        return true;
    }

    public static byte getMostFrequentElement(byte[] array) {
        Arrays.sort(array);
        byte maxID = array[0], currentID = array[0], maxAmount = 1, currentAmount = 1;
        for (int i = 1; i < array.length; i++) {
            if (currentID == array[i]) currentAmount++;
            else {
                if (currentAmount > maxAmount) {
                    maxAmount = currentAmount;
                    maxID = currentID;
                }
                currentAmount = 1;
            }
        }
        return maxID;
    }

    public static String appendSToString(Object[] objects) {
        return objects.length > 1 ? "s" : "";
    }

}
