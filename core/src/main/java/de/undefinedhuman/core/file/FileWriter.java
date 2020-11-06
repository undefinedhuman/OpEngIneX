package de.undefinedhuman.core.file;

import de.undefinedhuman.core.utils.Base64Coder;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.log.Log;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {

    private FsFile file;
    private String separator;
    private BufferedWriter writer;
    private boolean base;

    public FileWriter(FsFile file, boolean base) {
        this(file, base, Variables.SEPARATOR);
    }

    public FileWriter(FsFile file, boolean base, String separator) {
        this.separator = separator;
        this.base = base;
        this.file = file;
        writer = FileUtils.getBufferedWriter(file);
    }

    public FileWriter writeString(String s) {
        return writeValue(s);
    }

    public FileWriter writeInt(int i) {
        return writeValue(i);
    }

    public FileWriter writeFloat(float f) {
        return writeValue(f);
    }

    public FileWriter writeLong(long l) {
        return writeValue(l);
    }

    public FileWriter writeBoolean(boolean b) {
        return writeValue(FileUtils.booleanToInt(b));
    }

    public FileWriter writeDouble(double d) {
        return writeValue(d);
    }

    public FileWriter writeVector2(Vector2f vector) {
        return writeFloat(vector.x).writeFloat(vector.y);
    }

    public FileWriter writeVector3(Vector3f vector) {
        return writeFloat(vector.x).writeFloat(vector.y).writeFloat(vector.z);
    }

    public FileWriter writeStringArray(String[] array) {
        writeInt(array.length);
        for(String s : array)
            writeString(s);
        return this;
    }

    public FileWriter writeFloatArray(float[] array) {
        writeInt(array.length);
        for(float f : array)
            writeFloat(f);
        return this;
    }

    public FileWriter writeIntegerArray(int[] array) {
        writeInt(array.length);
        for (int i : array)
            writeInt(i);
        return this;
    }

    public FsFile getParentDirectory() {
        return new FsFile(file.getFile().getParentFile().getPath(), true);
    }

    public FileWriter writeValue(Object v) {
        try { writer.write(base ? Base64Coder.encodeString(String.valueOf(v)) + this.separator : v + this.separator);
        } catch (IOException ex) { Log.instance.crash(ex.getMessage()); }
        return this;
    }

    public FileWriter nextLine() {
        try { writer.newLine();
        } catch (IOException ex) { Log.instance.crash(ex.getMessage()); }
        return this;
    }

    public void close() {
        FileUtils.closeWriter(writer);
    }

}
