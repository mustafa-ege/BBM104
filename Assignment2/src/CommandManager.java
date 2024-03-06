import java.time.format.DateTimeParseException;
import java.util.ArrayList;
/**
 * The `CommandManager` class is responsible for managing commands passed as input in the form of
 * an `ArrayList` of `ArrayList` of `String` objects.
 * It processes each command sequentially and performs the corresponding actions based on the command type.
 */
public class CommandManager {
    /**
     * This method manages the commands passed as input and performs the corresponding actions based on the command type.
     * It takes an `ArrayList` of `ArrayList` of `String` objects as input, where each inner `ArrayList` represents
     * a command line with its arguments.
     *
     * @param commandInput An `ArrayList` of `ArrayList` of `String` objects representing the commands to be processed.
     */
    public static void manageCommands(ArrayList<ArrayList<String>> commandInput){
        try{
            int commandOrder =0;
            for (ArrayList<String> commandLine : commandInput){
                String commandType = commandLine.get(0);
                FileOutput.commandWriter(commandLine);
                if (commandOrder++ == 0){
                    if (commandType.equals("SetInitialTime") && (commandLine.size()==2)){
                        try{
                            Time.setTime(commandLine.get(1));
                            Time.timeHasSet();
                            continue;
                        } catch (DateTimeParseException e){
                            FileOutput.writeToFile("ERROR: Format of the initial date is wrong! Program is going to terminate!");
                        } catch (Exception e){
                            FileOutput.writeToFile("ERROR: First command must be set initial time! Program is going to terminate!");}
                            System.exit(0);
                    } else {
                        throw new InvalidFirstCommandError();
                    }
                }
                switch (commandType){
                    case ("SetTime"):
                        try{
                            if (!(commandLine.size()==2)){
                                throw new ErroneousError();
                            }
                            String time = commandLine.get(1);
                            Time.setTime(time);
                        } catch (DateTimeParseException e){
                            FileOutput.writeToFile("ERROR: Time format is not correct!");
                        } catch (ErroneousError e){
                            FileOutput.writeToFile("ERROR: Erroneous command!");
                        }
                        break;
                    case ("SkipMinutes"):
                        Time.skipMinutes(commandLine);
                        break;
                    case ("Add"):
                        SmartDevice.deviceAdder(commandLine);
                        break;
                    case ("Remove"):
                        SmartDevice.remove(commandLine);
                        break;
                    case ("PlugIn"):
                    case("PlugOut"):
                        SmartPlug.plugInOrOut(commandLine);
                        break;
                    case ("Switch"):
                        SmartDevice.switchDevice(commandLine);
                        break;
                    case ("SetSwitchTime"):
                        SmartDevice.setSwitchTimeForDevice(commandLine);
                        break;
                    case ("ChangeName"):
                        SmartDevice.changeName(commandLine);
                        break;
                    case ("Nop"):
                        SmartDevice.nop(commandLine);
                        break;
                    case ("SetKelvin"):
                        SmartLamp.setLampKelvin(commandLine);
                        break;
                    case ("SetBrightness"):
                        SmartLamp.setLampBrightness(commandLine);
                        break;
                    case ("SetColorCode"):
                        SmartColorLamp.setLampColorCode(commandLine);
                        break;
                    case ("SetWhite"):
                        SmartLamp.setWhite(commandLine);
                        break;
                    case ("SetColor"):
                        SmartColorLamp.setColor(commandLine);
                        break;
                    case ("ZReport"):
                        SmartDevice.zReport();
                        break;
                    default:
                        FileOutput.writeToFile("ERROR: Erroneous command!");
                }
            }
            if (!commandInput.get(commandInput.size()-1).get(0).equals("ZReport")){
                FileOutput.writeToFile("ZReport:");
                SmartDevice.zReport();
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (InvalidFirstCommandError e){
            FileOutput.writeToFile("ERROR: First command must be set initial time! Program is going to terminate!");
            System.exit(0);
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }

    }
}
