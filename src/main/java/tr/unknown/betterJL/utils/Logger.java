package tr.unknown.betterJL.utils;

import tr.unknown.betterJL.Main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

public class Logger {
    private static final String LOG_DIRECTORY = Main.instance.getConfig().getString("Server-Logs.file-path");
    private static final String LATEST_LOG_NAME = Main.instance.getConfig().getString("Server-Logs.latest-log-name");

    public static void initialize(){
        File logDir = new File(Main.instance.getDataFolder(), LOG_DIRECTORY);
        if(!logDir.exists()){
            logDir.mkdirs();
        }

        File latestLog = new File(logDir, LATEST_LOG_NAME);
        if(latestLog.exists()){
            rotateLog(latestLog);
        }

    }

    private static void rotateLog(File logFile){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dateFormat.format(new Date());

            String archiveFileName = timestamp + ".log.gz";
            File archiveFile = new File(logFile.getParentFile(), archiveFileName);

            compressFile(logFile, archiveFile);

            if(!logFile.delete()){
                Main.instance.getLogger().warning("After moving to the archive, the old record file may not be deleted.");
            }
        }catch (IOException e){
            Main.instance.getLogger().warning("Error archiving log file: " + e.getMessage());
        }
    }

    private static void compressFile(File inputFile, File outputFile) throws IOException{
        try(FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            GZIPOutputStream gzos = new GZIPOutputStream(fos)){

            byte[] buffer = new byte[1024];
            int len;
            while((len = fis.read(buffer)) != -1){
                gzos.write(buffer, 0, len);
            }
        }
    }

    public static void writeToLog(String message){
        if(Main.instance.getConfig().getBoolean("Server-Logs.enabled")) {
            File logDir = new File(Main.instance.getDataFolder(), LOG_DIRECTORY);
            if(!logDir.exists()){
                logDir.mkdirs();
            }

            File logFile = new File(logDir, LATEST_LOG_NAME);

            try{
                if(!logFile.exists()){
                    logFile.createNewFile();
                }

                try(FileWriter writer = new FileWriter(logFile, true)){
                    writer.write(message + System.lineSeparator());
                }
            }catch (IOException e){
                Main.instance.getLogger().warning("Error writing log file: " + e.getMessage());
            }
        }
    }
}
