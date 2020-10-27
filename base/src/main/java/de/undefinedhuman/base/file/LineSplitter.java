package de.undefinedhuman.base.file;

import de.undefinedhuman.base.utils.Base64Coder;
import de.undefinedhuman.base.utils.Variables;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class LineSplitter {

    private int pointer = 0;
    private String[] data;

    private boolean base;
    private String separator;

    public LineSplitter(String string, boolean base) {
        this(string, base, Variables.SEPARATOR);
    }

    public LineSplitter(String string, boolean base, String separator) {
        this.data = string.split(separator);
        this.separator = separator;
        this.base = base;
    }

    public String getNextString() {
        String s = getNextData();
        return s != null ? s : "";
    }

    public boolean hasMoreValues() {
        return this.pointer < this.data.length;
    }

    public int getNextInt() {
        String s = getNextData();
        return s != null ? Integer.parseInt(s) : 0;
    }

    public long getNextLong() {
        String s = getNextData();
        return s != null ? Long.parseLong(s) : 0;
    }

    public double getNextDouble() {
        String s = getNextData();
        return s != null ? Double.parseDouble(s) : 0;
    }

    public boolean getNextBoolean() {
        String s = getNextData();
        return s != null && FileUtils.readBoolean(s);
    }

    public Vector2f getNextVector2() {
        return new Vector2f(getNextFloat(), getNextFloat());
    }

    public float getNextFloat() {
        String s = getNextData();
        return s != null ? Float.parseFloat(s) : 0;
    }

    public Vector3f getNextVector3() {
        return new Vector3f(getNextFloat(), getNextFloat(), getNextFloat());
    }

    public String getData() {
        StringBuilder builder = new StringBuilder();
        while (hasMoreValues()) builder.append(getNextData()).append(separator);
        return builder.toString();
    }

    public LineSplitter getRawDataAsLineSplitter() {
        StringBuilder builder = new StringBuilder();
        for (String data : this.data)
            builder.append(data).append(separator);
        return new LineSplitter(builder.toString(), true, separator);
    }

    public String getRemainingRawData() {
        StringBuilder builder = new StringBuilder();
        while(hasMoreValues()) builder.append(this.data[pointer++]).append(separator);
        return builder.toString();
    }

    private String getNextData() {
        if (hasMoreValues()) {
            String s = this.data[this.pointer++];
            return (base ? Base64Coder.decodeString(s) : s);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < data.length; i++)
            builder.append((base ? Base64Coder.decodeString(data[i]) : data[i])).append(i != data.length - 1 ? ", " : "");
        return builder.toString();
    }

}
