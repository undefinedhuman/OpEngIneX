package de.undefinedhuman.base.utils;

import de.undefinedhuman.base.file.FileWriter;
import de.undefinedhuman.base.settings.SettingsObject;
import org.joml.Vector2f;
import org.joml.Vector4f;

import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Tools {

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
        if(values == null || values.length < 2)
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

    public static String convertArrayToString(Vector2[] array) {
        StringBuilder builder = new StringBuilder();
        for (Vector2 current : array)
            builder.append(current.x).append(Variables.SEPARATOR).append(current.y).append(Variables.SEPARATOR);
        return builder.toString();
    }

    public static void addSettings(JPanel panel, ArrayList<Setting> settings) {
        int currentOffset = 0;
        for (Setting setting : settings) {
            setting.addMenuComponents(panel, new Vector2(0, currentOffset));
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

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static boolean isInRange(int val, int min, int max) {
        return val >= min && val <= max;
    }

    public static boolean isInRange(float val, float min, float max) {
        return val >= min && val <= max;
    }

    public static int isEqual(int i, int j) {
        return i == j ? 1 : 0;
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

    public static int getWorldPositionX(float x, float width) {
        return (int) ((width + x) % width);
    }

    public static Vector4f calculateBounds(Vector2f position, Vector2f offset, Vector2f size) {
        return new Vector4f(position.x + offset.x, position.y + offset.y, position.x + offset.x + size.x, position.y + offset.y + size.y);
    }

    public static boolean isDigit(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
