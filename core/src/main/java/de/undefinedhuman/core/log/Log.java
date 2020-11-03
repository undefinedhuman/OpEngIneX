package de.undefinedhuman.core.log;

import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.utils.Variables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Log {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(Variables.LOG_DATE_FORMAT);

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
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(file.getPath()));
        } catch (IOException ex) {
            System.out.println("Can't save log file as FileWriter can't be created: \n" + ex.getMessage());
            crash();
        }
        if(fileWriter == null) return;
        BufferedWriter writer = new BufferedWriter(fileWriter);
        info("Log file successfully saved!");
        for (String message : logMessages)
            writeValue(writer, message).nextLine(writer);
        logMessages.clear();
        try { writer.close(); } catch (IOException ex) { System.out.println("BufferedWriter can't be closed: \n" + ex.getMessage()); }
    }

    public void load() {
        checkLogs();
        file = new File(Paths.LOG_PATH + fileName);
        if (file.exists()) info("Log file successfully created!");
    }

    public void crash() {
        close();
        save();
        System.exit(0);
    }

    public void crash(String errorMessage) {
        error(errorMessage);
        crash();
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
        String logMessage = Variables.LOG_MESSAGE_FORMAT.replaceAll("%prefix%", prefix).replaceAll("%time%", getTime()).replaceAll("%message%", String.valueOf(msg)).replaceAll("%name%", Variables.NAME).replaceAll("%version%", Variables.VERSION);
        Log.instance.displayMessage(logMessage);
        System.out.println(logMessage);
        logMessages.add(logMessage);
    }

    private void checkLogs() {
        ArrayList<File> filesToRemove = new ArrayList<>();
        File dir = new File(Paths.LOG_PATH);
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

    public static String getTime() {
        return DATE_FORMAT.format(Calendar.getInstance().getTime());
    }

    public Log writeValue(BufferedWriter writer, Object message) {
        try { writer.write(String.valueOf(message));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            crash();
        }
        return this;
    }

    private Log nextLine(BufferedWriter writer) {
        try { writer.newLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            crash();
        }
        return this;
    }

    public void displayMessage(String msg) {}
    public void close() {}

}
