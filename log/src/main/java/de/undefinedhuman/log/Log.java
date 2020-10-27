package de.undefinedhuman.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Log {

    public static final String LOG_PATH = "../openginex/log/";
    public static final String LOG_MESSAGE_FORMAT = "[%NAME%]";
    public static final String CONSOLE_MESSAGE_FORMAT = "[%NAME% - %TIME%] %%";

    public static Log instance;
    private static List<String> logMessages;

    private String fileName;
    private File file;

    public Log() {
        logMessages = new ArrayList<>();
        if (instance == null) instance = this;
    }

    public void init() {
        fileName = getTime() + ".txt";
        load();
    }

    public void delete() {
        save();
    }

    public void save() {
        if(file == null || !file.exists()) return;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(file.getPath())));
        } catch (Exception ex) {
            Log.instance.crash("Can't create file writer \n" + ex.getMessage());
        }
        info("Log file successfully saved!");
        for (String message : logMessages) writer.writeString(message).nextLine();
        logMessages.clear();
        writer.close();
    }

    public void load() {
        checkLogs();
        file = new File(LOG_PATH + fileName);
        if (file.exists()) info("Log file successfully created!");
    }

    public void crash() {
        save();
        System.exit(0);
    }

    public void crash(String errorMessage) {
        error(errorMessage);
        save();
        System.exit(1);
    }

    public static void log(Object msg) {
        createMessage("Log", msg);
    }

    public static void error(Object msg) {
        createMessage("Error", msg);
    }

    public static void info(Object msg) {
        createMessage("Info", msg);
    }

    public static void info(Object... values) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < values.length; i++) s.append(values[i]).append(i < values.length - 1 ? ", " : "");
        createMessage("Info", s.toString());
    }

    private static void createMessage(String prefix, Object msg) {
        String message = generateMessage(prefix, String.valueOf(msg));
        Log.instance.displayMessage("[" + prefix + "] " + msg);
        System.out.println(message);
        logMessages.add(message);
    }

    private static String generateMessage(String prefix, String msg) {
        return "[" + getTime() + "] [" + prefix + "] " + msg;
    }

    private void checkLogs() {
        ArrayList<File> filesToRemove = new ArrayList<>();
        File dir = new File(LOG_PATH);
        if (!dir.exists() || !dir.isDirectory()) return;
        File[] files = dir.listFiles();
        if(files == null || files.length == 0) return;
        for (File file : files)
            if ((new Date().getTime() - file.lastModified()) > 172800000) filesToRemove.add(file);
        int dFiles = 0;
        for (File file : filesToRemove) if (file.delete()) dFiles++;
        info("Deleted " + dFiles + " log files!");
        filesToRemove.clear();
    }

    public void displayMessage(String msg) {}

    public static String getTime() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy - HH-mm-ss");
        Calendar cal = Calendar.getInstance();
        return df.format(cal.getTime());
    }

}
