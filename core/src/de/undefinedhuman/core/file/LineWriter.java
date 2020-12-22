package de.undefinedhuman.core.file;

import de.undefinedhuman.core.utils.Base64Coder;
import de.undefinedhuman.core.utils.Variables;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class LineWriter {

    private StringBuilder data;

    private boolean base;
    private String separator;

    public LineWriter(boolean base) {
        this(base, Variables.SEPARATOR);
    }

    public LineWriter(boolean base, String separator) {
        data = new StringBuilder();
        this.base = base;
        this.separator = separator;
    }

    public void writeString(String s) {
        writeData(s);
    }

    private LineWriter writeData(Object o) {
        data.append(base ? Base64Coder.encodeString(String.valueOf(o)) : o).append(separator);
        return this;
    }

    public LineWriter writeInt(int i) {
        return writeData(i);
    }

    public LineWriter writeFloat(float f) {
        return writeData(f);
    }

    public LineWriter writeLong(long l) {
        return writeData(l);
    }

    public LineWriter writeString(double d) {
        return writeData(d);
    }

    public LineWriter writeBoolean(boolean b) {
        return writeData(FileUtils.booleanToInt(b));
    }

    public LineWriter writeVector2(Vector2f vector) {
        writeData(vector.x);
        writeData(vector.y);
        return this;
    }

    public LineWriter writeVector3(Vector3f vector) {
        writeData(vector.x);
        writeData(vector.y);
        writeData(vector.z);
        return this;
    }

    public String getData() {
        return data.toString();
    }

}
