package de.undefinedhuman.core.file;

import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.utils.Variables;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {

    private LineSplitter splitter;
    private BufferedReader reader;
    private FsFile file;
    private boolean base;
    private String separator;

    public FileReader(FsFile file, boolean base) {
        this(file, base, Variables.SEPARATOR);
    }

    public FileReader(FsFile file, boolean base, String separator) {
        this.reader = FileUtils.getBufferedReader(file);
        this.file = file;
        this.base = base;
        this.separator = separator;
    }

    public String nextLine() {
        String line = null;
        try { line = reader.readLine(); } catch (IOException ex) { Log.error(ex.getMessage()); }
        if (line == null) return null;
        splitter = new LineSplitter(line, base, separator);
        return line;
    }

    public String getNextString() {
        return splitter.getNextString();
    }
    public int getNextInt() {
        return splitter.getNextInt();
    }
    public float getNextFloat() {
        return splitter.getNextFloat();
    }
    public long getNextLong() {
        return splitter.getNextLong();
    }
    public Vector2f getNextVector2() {
        return splitter.getNextVector2();
    }
    public Vector3f getNextVector3() {
        return splitter.getNextVector3();
    }
    public double getNextDouble() {
        return splitter.getNextDouble();
    }
    public boolean getNextBoolean() {
        return splitter.getNextBoolean();
    }

    public int[] getNextIntegerArray() {
        int[] array = new int[getNextInt()];
        for(int i = 0; i < array.length; i++) array[i] = getNextInt();
        return array;
    }

    public float[] getNextFloatArray() {
        float[] array = new float[getNextInt()];
        for(int i = 0; i < array.length; i++) array[i] = getNextFloat();
        return array;
    }

    public String getData() {
        StringBuilder builder = new StringBuilder();
        while (!isEndOfLine()) builder.append(getNextString()).append(Variables.SEPARATOR);
        return builder.toString();
    }

    public LineSplitter getRemainingRawData() { return new LineSplitter(splitter.getRemainingRawData(), base, separator); }

    public boolean isEndOfLine() {
        return !splitter.hasMoreValues();
    }

    public FsFile getParentDirectory() {
        return new FsFile(file.getFile().getParentFile().getPath(), true);
    }

    public void close() {
        FileUtils.closeReader(reader);
    }

}
