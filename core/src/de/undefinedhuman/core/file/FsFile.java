package de.undefinedhuman.core.file;

import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.log.Log;

import java.io.File;
import java.io.IOException;

public class FsFile {

    private String name, path;
    private File file;
    private boolean isDirectory;

    public FsFile(File file, String fileName, boolean isDirectory) {
        this(file.getPath() + fileName, isDirectory);
    }

    public FsFile(String path, String fileName, boolean isDirectory) {
        this(path + fileName, isDirectory);
    }

    public FsFile(String fileName, boolean isDirectory) {
        this(fileName, isDirectory, true);
    }

    public FsFile(String path, String fileName, boolean isDirectory, boolean create) {
        this(path + fileName, isDirectory, create);
    }

    public FsFile(String fileName, boolean isDirectory, boolean create) {
        this.path = fileName;
        this.isDirectory = isDirectory;
        String[] dirs = this.path.split(Variables.FILE_SEPARATOR);
        this.name = dirs[dirs.length - 1];

        file = new File(this.path);
        if(create) createFile(isDirectory);
    }

    public FsFile createFile(boolean isDirectory) {
        try {
            createNewFile(isDirectory);
        } catch (IOException ex) {
            Log.instance.crash(ex.getMessage());
        }
        return this;
    }

    private void createNewFile(boolean isDirectory) throws IOException {
        if (file.exists()) return;
        if (!file.getParentFile().exists()) if (!file.getParentFile().mkdirs()) Log.instance.crash();
        if (isDirectory) if (file.mkdir()) Log.info("Successfully created dir: " + file.getPath());
        else Log.instance.crash();
        if (!isDirectory) if (file.createNewFile()) Log.info("Successfully created " + file.getName());
        else Log.instance.crash();
    }

    public boolean exists() {
        return file.exists();
    }

    public FsFile(FsFile file, String fileName, boolean isDirectory) {
        this(file.getPath() + Variables.FILE_SEPARATOR + fileName, isDirectory);
    }

    public String getPath() {
        return this.path;
    }

    public FileReader getFileReader(boolean base) {
        return new FileReader(this, base);
    }

    public FileReader getFileReader(boolean base, String separator) {
        return new FileReader(this, base, separator);
    }

    public FileWriter getFileWriter(boolean base) {
        return new FileWriter(this, base);
    }

    public FileWriter getFileWriter(boolean base, String separator) {
        return new FileWriter(this, base, separator);
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public File[] list() {
        if(!file.isDirectory()) return new File[0];
        return file.listFiles();
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isEmpty() {
        return file.length() == 0;
    }

}
