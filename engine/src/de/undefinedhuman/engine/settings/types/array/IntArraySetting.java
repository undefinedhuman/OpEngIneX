package de.undefinedhuman.engine.settings.types.array;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.engine.settings.SettingType;
import de.undefinedhuman.core.utils.Tools;

public class IntArraySetting extends ArraySetting {

    public IntArraySetting(String key, int[] value) {
        super(SettingType.IntArray, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter splitter)) return;
        setValue(splitter.getNextIntegerArray());
    }

    @Override
    public void save(FileWriter writer) {
        super.save(writer);
        writer.writeIntegerArray(getIntegerArray());
    }

    @Override
    public String generateString() {
        return Tools.convertArrayToString(getIntegerArray());
    }

    @Override
    public void keyEvent() {
        setValue(Tools.convertStringToIntArray(valueField.getText().split(";")));
    }

}
