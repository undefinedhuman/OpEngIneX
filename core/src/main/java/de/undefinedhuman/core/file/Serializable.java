package de.undefinedhuman.core.file;

public interface Serializable {
    void load(FileReader reader);
    void save(FileWriter writer);
}
