import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * Represents a SmartPlug device that extends DevicesCalculatingTimeInterval class.
 * It provides functionalities for setting and getting ampere, energy consumption,
 * switching on and off the device, and adding, plugging in and out SmartPlug devices.
 */
public class SmartPlug extends DevicesCalculatingTimeInterval {

    private double ampere = 0;
    private final int voltage = 220;
    private double energy = 0;
    /**
     * Creates a SmartPlug device with the given name.
     *
     * @param name The name of the SmartPlug device.
     */
    public SmartPlug(String name){
        super(name);}
    /**
     * Creates a SmartPlug device with the given name and status.
     *
     * @param name The name of the SmartPlug device.
     * @param status The status of the SmartPlug device.
     * @throws ErroneousError If an erroneous error occurs.
     */
    public SmartPlug(String name, String status) throws ErroneousError {
        super(name,status);}
    /**
     * Creates a SmartPlug device with the given name, status, and ampere value.
     *
     * @param name The name of the SmartPlug device.
     * @param status The status of the SmartPlug device.
     * @param ampere The ampere value of the SmartPlug device.
     * @throws WrongValueError If the ampere value is not a positive number.
     * @throws ErroneousError If an erroneous error occurs.
     */
    public SmartPlug(String name, String status, double ampere) throws WrongValueError, ErroneousError {
        super(name,status);
        setAmpere(ampere);}
    /**
     * Gets the ampere value of the SmartPlug device.
     *
     * @return The ampere value of the SmartPlug device.
     */
    public double getAmpere(){
        return this.ampere;}
    /**
     * Sets the ampere value of the SmartPlug device.
     *
     * @param ampere The ampere value of the SmartPlug device.
     * @throws WrongValueError If the ampere value is not a positive number.
     */
    public void setAmpere(double ampere) throws WrongValueError {
        if (ampere>0){
            this.ampere = ampere;}
        else{
            throw new WrongValueError("ERROR: Ampere value must be a positive number!");}
    }
    /**
     * Gets the energy consumption of the SmartPlug device.
     *
     * @return The energy consumption of the SmartPlug device.
     */
    public double getEnergy(){
        return this.energy;}
    /**
     * Sets the energy consumption of the SmartPlug device.
     *
     * @param energy The energy consumption of the SmartPlug device.
     */
    public void setEnergy(double energy){
        this.energy += energy;
    }
    /**
     * Sets the time when the SmartPlug device has been switched on.
     * The time will only be set if the device is on and ampere value is not zero.
     *
     * @param newTime The new time to be set.
     */
    @Override
    public void setTimeDeviceBeenSwitchedOn(LocalDateTime newTime){
        if ((this.getStatus().equals("On")) && (this.getAmpere()!=0)){
            super.setTimeDeviceBeenSwitchedOn(newTime);
        } else if (newTime == null) {
            super.setTimeDeviceBeenSwitchedOn(null);
        }
    }
    /**
     * Adds a smart plug device based on the command line arguments passed in.
     *
     * @param commandLineAddPlug An ArrayList of Strings containing the command line arguments for adding a smart plug.
     *                           The size of the ArrayList should be either 3, 4, or 5.
     *                           Index 0 should contain the command "AddPlug".
     *                           Index 1 should contain the name of the smart plug device.
     *                           Index 2 should contain the status (optional).
     *                           Index 3 should contain the ampere value (optional).
     * @throws ErroneousError If the number of properties in the command line arguments is not 3, 4, or 5.
     * @throws DuplicateError If a smart device with the same name already exists.
     * @throws WrongValueError If the ampere value is not a valid positive number.
     */
    public static void smartPlugAdder(ArrayList<String> commandLineAddPlug){
        try {
            int numberOfProperties = commandLineAddPlug.size();
            if (!(numberOfProperties==3 || numberOfProperties==4 || numberOfProperties==5)){
                throw new ErroneousError();
            }
            SmartPlug testingDeviceToCheckCommands = new SmartPlug("tester");
            String name = commandLineAddPlug.get(2);
            switch (numberOfProperties) {
                case (3):
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();
                    }
                    allSmartDevices.add(new SmartPlug(name));
                    namesOfAllDevices.add(name);
                    break;
                case (4):
                    String status = commandLineAddPlug.get(3);
                    testingDeviceToCheckCommands.setStatus(status);
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();
                    }
                    allSmartDevices.add(new SmartPlug(name, status));
                    namesOfAllDevices.add(name);
                    ((SmartPlug) deviceGetter(name)).setTimeDeviceBeenSwitchedOn(Time.time);
                    break;
                case (5):
                    status = commandLineAddPlug.get(3);
                    double ampere = Double.parseDouble(commandLineAddPlug.get(4));
                    testingDeviceToCheckCommands.setStatus(status);
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();
                    }
                    allSmartDevices.add(new SmartPlug(name, status, ampere));
                    namesOfAllDevices.add(name);
                    ((SmartPlug) deviceGetter(name)).setTimeDeviceBeenSwitchedOn(Time.time);
                    break;
        }} catch (WrongValueError e){
            FileOutput.writeToFile(e.getMessage());
        } catch (DuplicateError e) {
            FileOutput.writeToFile("ERROR: There is already a smart device with same name!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }

    /**
     * Performs the "PlugIn" or "PlugOut" action on a smart plug device based on the command line arguments passed in.
     *
     * @param commandLinePlugInOut An ArrayList of Strings containing the command line arguments for plugging in or out a smart plug.
     *                             The size of the ArrayList should be either 2 or 3.
     *                             Index 0 should contain the command "PlugIn" or "PlugOut".
     *                             Index 1 should contain the name of the smart plug device.
     *                             Index 2 should contain the ampere value (optional, only for "PlugIn" command).
     * @throws ErroneousError If the command is "PlugIn" and the number of properties in the command line arguments is not 3,
     *                        or if the command is "PlugOut" and the number of properties in the command line arguments is not 2.
     * @throws WrongValueError If the ampere value is not a valid positive number.
     * @throws ClassCastException If the specified device is not a smart plug.
     */
    public static void plugInOrOut(ArrayList<String> commandLinePlugInOut)  {
        int numberOfProperties = commandLinePlugInOut.size();
        try{
            if (commandLinePlugInOut.get(1)=="PlugIn" && !(numberOfProperties==3)){
                throw new ErroneousError();
            } else if (commandLinePlugInOut.get(1)=="PlugOut" && !(numberOfProperties==2)) {
                throw new ErroneousError();
            }
            String plugName = commandLinePlugInOut.get(1);
            if (namesOfAllDevices.contains(plugName)){
                SmartPlug plugToBeUpdated = (SmartPlug) deviceGetter(plugName);
                if (plugToBeUpdated.getAmpere() == 0){
                    if (commandLinePlugInOut.get(0).equals("PlugIn")){
                        int ampereValue = Integer.parseInt(commandLinePlugInOut.get(2));
                        ((SmartPlug) deviceGetter(plugName)).setAmpere(ampereValue);
                        ((SmartPlug) deviceGetter(plugName)).calculateEnergyConsumption();
                        ((SmartPlug) deviceGetter(plugName)).setTimeDeviceBeenSwitchedOn(Time.time);
                    } else if (commandLinePlugInOut.get(0).equals("PlugOut")) {
                        FileOutput.writeToFile("ERROR: This plug has no item to plug out from that plug!");
                    }
                } else {
                    if (commandLinePlugInOut.get(0).equals("PlugIn")){
                        FileOutput.writeToFile("ERROR: There is already an item plugged in to that plug!");
                    } else if (commandLinePlugInOut.get(0).equals("PlugOut")){
                        ((SmartPlug) deviceGetter(plugName)).ampere = 0;
                        ((SmartPlug) deviceGetter(plugName)).calculateEnergyConsumption();;}
                }
            } else{
                FileOutput.writeToFile("ERROR: There is not such a device!");
            }
        } catch (WrongValueError e){
            FileOutput.writeToFile("ERROR: Ampere value must be a positive number!");
        } catch (ClassCastException e){
            FileOutput.writeToFile("ERROR: This device is not a smart plug!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Calculates the energy consumption of the smart plug device based on the time elapsed since it was last switched on,
     * and updates the energy consumption and switch time accordingly.
     */
    public void calculateEnergyConsumption(){
        double timeElapsedHours = (double) calculateTimeElapsed().toMinutes()/60;
        double energyConsumed = voltage * timeElapsedHours * getAmpere();
        setEnergy(energyConsumed);
        setTimeDeviceBeenSwitchedOn(null);
    }
    /**
     * Generates a report for the smart plug device, including its name, status, energy consumption,
     * time to switch its status, and any additional plugged-in device (if any).
     */
    @Override
    public void report(){
        FileOutput.writeToFile("Smart Plug "+ getName()+" is "+getStatus().toLowerCase()+ " and consumed " +
                String.format("%.2f", getEnergy()).replace(".",",") +"W so far (excluding current " +
                "device), and its time to switch its status is "+ Time.timeStringReturner(getSwitchTime())+".");
    }
}
