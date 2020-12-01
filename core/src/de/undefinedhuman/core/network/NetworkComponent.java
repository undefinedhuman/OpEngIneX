package de.undefinedhuman.core.network;

import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.file.LineWriter;

public interface NetworkComponent {

    void send(LineWriter writer);
    void read(LineSplitter splitter);

}
