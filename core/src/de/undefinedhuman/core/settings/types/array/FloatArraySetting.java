package de.undefinedhuman.core.settings.types.array;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.utils.Tools;

public class FloatArraySetting extends ArraySetting {

    public FloatArraySetting(String key, float[] value) {
        super(SettingType.FloatArray, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        LineSplitter splitter = (LineSplitter) value;
        setValue(splitter.getNextFloatArray());
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeFloatArray(getFloatArray());
    }

    @Override
    public String generateString() {
        return Tools.convertArrayToString(getFloatArray());
    }

    @Override
    public void keyEvent() {
        setValue(Tools.convertStringToFloatArray(valueField.getText().split(";")));
    }

}
