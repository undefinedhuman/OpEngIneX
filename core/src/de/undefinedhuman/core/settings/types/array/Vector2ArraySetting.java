package de.undefinedhuman.core.settings.types.array;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.utils.Tools;
import org.joml.Vector2f;

public class Vector2ArraySetting extends ArraySetting {

    public Vector2ArraySetting(String key, Vector2f[] value) {
        super(SettingType.Vector2Array, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        LineSplitter splitter = (LineSplitter) value;
        Vector2f[] vectors = new Vector2f[splitter.getNextInt()];
        for(int i = 0; i < vectors.length; i++) vectors[i] = splitter.getNextVector2();
        setValue(vectors);
    }

    @Override
    public void save(FileWriter writer) {
        super.save(writer);
        writer.writeInt(getVector2Array().length);
        for(Vector2f vector : getVector2Array()) writer.writeVector2(vector);
    }

    @Override
    public String generateString() {
        return Tools.convertArrayToString(getVector2Array());
    }

    @Override
    public void keyEvent() {
        String[] array = valueField.getText().split(";");
        Vector2f[] vectorArray = new Vector2f[array.length/2];
        for(int i = 0; i < vectorArray.length; i++) vectorArray[i] = new Vector2f(Float.parseFloat(array[i*2]), Float.parseFloat(array[i*2+1]));
        setValue(vectorArray);
    }

}
