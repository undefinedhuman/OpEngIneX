package de.undefinedhuman.log;

import java.util.ArrayList;

public class Log {

    public static Log instance;

    private static ArrayList<String> logMessages;

    public Log() {
        if(instance == null) instance = this;
        logMessages = new ArrayList<>();
    }

}
