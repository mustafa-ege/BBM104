import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * The Time class represents a time management utility that allows setting and skipping time.
 */
public class Time {
    public static LocalDateTime time = LocalDateTime.of(0,1,1,0,0,0);
    /**
     * Sets the time to the specified value.
     *
     * @param timeToBeSet The time to be set, in the format "yyyy-MM-dd_HH:mm:ss".
     */
    public static void setTime(String timeToBeSet) {
        LocalDateTime newTime = timeObjectReturner(timeToBeSet);
        if (newTime.isBefore(time)){
            FileOutput.writeToFile("ERROR: Time cannot be reversed!");
        } else if (newTime.isEqual(time)) {
            FileOutput.writeToFile("ERROR: There is nothing to change!");
        } else {
            time = newTime;
            SmartDevice.deviceSwitcherWithTime();
        }
    }
    /**
     * Skips the specified number of minutes in the current time.
     *
     * @param commandLineSkipMinutes The command line arguments for skipping minutes.
     */
    public static void skipMinutes(ArrayList<String> commandLineSkipMinutes) {
        try{
            int numberOfCommands = commandLineSkipMinutes.size();
            if (numberOfCommands!=2){
                throw new ErroneousError();
            }
            String stringMinutes = commandLineSkipMinutes.get(1);
            int intMinute = Integer.parseInt(stringMinutes);
            if (intMinute<0){
                throw new WrongValueError("ERROR: Time cannot be reversed!");
            } else if (intMinute==0) {
                throw new WrongValueError("ERROR: There is nothing to skip!");
            }
            time = time.plusMinutes(intMinute);
            SmartDevice.deviceSwitcherWithTime();
        } catch (WrongValueError e){
            FileOutput.writeToFile(e.getMessage());
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }

    /**
     * Parses a time string to a LocalDateTime object.
     *
     * @param timeToBeSet The time string to be parsed, in the format "yyyy-MM-dd_HH:mm:ss".
     * @return The LocalDateTime object representing the parsed time.
     */
    public static LocalDateTime timeObjectReturner(String timeToBeSet){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        LocalDateTime newTime = LocalDateTime.parse(timeToBeSet, formatter);
        return newTime;}
    /**
     * Returns a time string in the format "yyyy-MM-dd_HH:mm:ss" from a LocalDateTime object.
     *
     * @param time The LocalDateTime object to be formatted.
     * @return The formatted time string.
     */
    public static String timeStringReturner(LocalDateTime time){
        try{
            String timeString = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(time);
            return timeString;
        } catch (NullPointerException e){
            return null;}
    }

    /**
     * Writes a success message with the current time to the output file.
     */
    public static void timeHasSet(){
        FileOutput.writeToFile("SUCCESS: Time has been set to " + timeStringReturner(time)+"!");}

    /**
     * Writes a success message with the current time to the output file.
     */
    public static void timeIs(){
        FileOutput.writeToFile("Time is:\t" + timeStringReturner(time));}

}
