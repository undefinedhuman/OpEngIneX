package de.undefinedhuman.base.file;

import de.undefinedhuman.base.utils.Base64Coder;
import de.undefinedhuman.base.utils.Variables;
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

    private void writeData(Object o) {
        data.append(base ? Base64Coder.encodeString(String.valueOf(o)) : o).append(separator);
    }

    public void writeInt(int i) {
        writeData(i);
    }

    public void writeFloat(float f) {
        writeData(f);
    }

    public void writeLong(long l) {
        writeData(l);
    }

    public void writeString(double d) {
        writeData(d);
    }

    public void writeBoolean(boolean b) {
        writeData(b ? 1 : 0);
    }

    public void writeVector2(Vector2f vector) {
        writeData(vector.x);
        writeData(vector.y);
    }

    public void writeVector3(Vector3f vector) {
        writeData(vector.x);
        writeData(vector.y);
        writeData(vector.z);
    }

    public String getData() {
        return data.toString();
    }

}
