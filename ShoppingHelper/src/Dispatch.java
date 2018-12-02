/*
 * File: Dispatch.java
 * Author: David Green DGreen@uab.edu
 * Assignment:  ScheduleBuilder - EE333 Fall 2018
 * Vers: 1.1.0 11/24/2018 dgg - refactor, test file statuses
 * Vers: 1.0.0 11/19/2018 dgg - initial coding
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardCopyOption.*;

/**
 * Dispatch various external programs to perform desired behaviors
 * @author David Green DGreen@uab.edu
 */
public class Dispatch {
    
    String fullPath;
    
    /**
     * Set the path the path for the data files
     * @param path path to courses
     * @param course specific course
     * @param semester specific semester
     */
    public void setFullPath(String path, String course, String semester) {
        fullPath = path + "/" + course + "/" + semester;
    }
    
    /**
     * Dispatch the spreadsheet to allow schedule creation.Presently, don't hang 
     * program but merely delay 10 seconds to allow the spreadsheet to show.
     * Assumes fullPath has been set prior to execution.
     * @return String representation of result
     */
    public String createEditSchedule() {
        if (! readyForCreateEdit() ) {
            return "";
        }
        try {
            String spreadSheetName = "schedule.numbers";        
            File schedule          = new File(fullPath + "/" + spreadSheetName);
            
            if (! schedule.exists()) {
                Files.copy((new File("resources/schedule-template.numbers")).toPath(), 
                            new File(fullPath + "/" + spreadSheetName).toPath(), 
                           COPY_ATTRIBUTES);
                schedule = new File(fullPath + "/" + spreadSheetName);
            }
            
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open " + spreadSheetName, (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Edit the template behavior -- invoke editor assigned to .md suffix
     * Assumes fullPath has been set prior to execution.
     * @return String representation of result
     */
    public String editTemplate() {
        if (! readyForEditTemplate() ) {
            return "";
        }
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open schedule.md", (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Merge the CSV from the spreadsheet into the Template replacing any exising
     * old schedule
     * Assumes fullPath has been set prior to execution.
     * @return String representation of result     * @return
     */
    public String mergeSchedule() {
        
        if (! readyForMerge() ) {
            return "";
        }

        boolean finished = false;

        try {
// ensure the schedule.old has been removed
            File file = new File(fullPath + "/schedule.old");
            file.delete();           // quietly continues even if file not there            
            
        //    Runtime rt = Runtime.getRuntime();
        //    Process p = rt.exec("/bin/sh -c \"/usr/bin/touch x.txt\"; sleep 100 ", (String[]) null, new File(fullPath) );
          
            ProcessBuilder pb = new ProcessBuilder("./insert-schedule.pl");
            pb.directory(new File(fullPath));
            File log = new File(fullPath + "/merge.log");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        
            Process p = pb.start();
            finished = p.waitFor(2, TimeUnit.SECONDS);
                        
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

        if (! finished)
            return "Merge failed";
        else
            return "Merge complete";
    }
    
    /**
     * Allow touching up file after merge (presently only beautification of .md)
     * using the editor assigned to .md suffix.
     * Assumes fullPath has been set prior to execution.
     * @return String representation of result
     */
    public String touchUpResult() {
        if (! readyForMerge() ) {
            return "";
        }
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open schedule.md", (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * View result in production view (using Marked which also reads the .md file)
     * Assumes fullPath has been set prior to execution.
     * @return String representation of result
     */
    public String viewResult() {
        if (! readyForView() ) {
            return "";
        }
        try {           
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec( "open schedule.md -a Marked.app", (String[]) null, new File(fullPath) );

            Thread.sleep(10000);
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Export the schedule to HTML using multi-markdown script mmd
     * Assumes fullPath has been set prior to execution.
     * @return String representation of result
     */
    public String exportResult() {

        if (! readyForExport() ) {
            return "";
        }
        
        boolean finished = false;

        try {        
            ProcessBuilder pb = new ProcessBuilder("/Users/dgreen/bin/mmd", "schedule.md");
            pb.directory(new File(fullPath));
            File log = new File(fullPath + "/merge.log");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));

            Map<String, String> env = pb.environment();
            
            Process p = pb.start();
            finished = p.waitFor(2, TimeUnit.SECONDS);

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Dispatch.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

        if (! finished)
            return "Export failed";
        else
            return "Export complete";
    }
    
    /**
     * Determine if Create/Edit action is allowable
     * @return true if yes
     */
    public boolean readyForCreateEdit() {
        return true;                        // always ready to do this
    }
    
    /**
     * Determine if Edit action is allowable
     * @return true if yes
     */
    public boolean readyForEditTemplate() {
        return scheduleMdExists();
    }

    /**
     * Determine if Merge action is allowable
     * @return true if yes
     */
    public boolean readyForMerge() {
        return scheduleMdExists() && scheduleCSVExists();
    }

    /**
     * Determine if Touch Up action is allowable
     * @return true if yes
     */
    public boolean readyForTouchUp() {
        return isScheduleMdNewerScheduleCSV();
    }

    /**
     * Determine if View action is allowable
     * @return true if yes
     */
    public boolean readyForView() {
        return isScheduleMdNewerScheduleCSV();
    }

    /**
     * Determine if Create/Edit action is allowable
     * @return true if yes
     */
    public boolean readyForExport() {
        return isScheduleMdNewerScheduleCSVScheduleHtml();
    }
    
    private boolean scheduleMdExists() {
        File scheduleMd = new File( fullPath + "/schedule.md");
        return scheduleMd.exists();
    }
    
    private boolean scheduleCSVExists() {
        File scheduleCSV = new File( fullPath + "/schedule.csv");
        return scheduleCSV.exists();
    }

    private boolean isScheduleMdNewerScheduleCSV() {
        boolean result = false;
        
        File scheduleMd  = new File( fullPath + "/schedule.md");
        File scheduleCSV = new File( fullPath + "/schedule.csv");
        
        if ( scheduleMd.exists() && scheduleCSV.exists() ) {
            result = scheduleMd.lastModified() > scheduleCSV.lastModified();
        }    
        return result;   
    }

    private boolean isScheduleMdNewerScheduleCSVScheduleHtml() {
        boolean result = false;
        
        File scheduleMd   = new File(fullPath + "/schedule.md");
        File scheduleCSV  = new File(fullPath + "/schedule.csv");
        File scheduleHtml = new File(fullPath + "/schedule.html");
        
        if ( scheduleMd.exists() && scheduleCSV.exists() ) {
            if ( scheduleMd.lastModified() > scheduleCSV.lastModified() ) {
                result = scheduleMd.lastModified() > scheduleHtml.lastModified();
            }
        }    
        return result;   
    }

}
