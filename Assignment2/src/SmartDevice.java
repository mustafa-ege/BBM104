import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * The abstract class SmartDevice represents a generic smart device with common functionalities.
 * It includes methods for setting and getting the name, status, and switch time of a smart device,
 * as well as abstract method for generating a report. The class also includes static methods for adding,
 * removing, and changing the name of a smart device, as well as for switching a smart device on/off and
 * setting the switch time for a smart device.
 */

public abstract class SmartDevice {
    /**
     * An ArrayList of all smart devices created, to keep track of all the objects.
     */
    public static ArrayList<SmartDevice> allSmartDevices = new ArrayList<>();
    /**
     * An ArrayList of names of all smart devices created, to keep track of the names.
     */
    public static ArrayList<String> namesOfAllDevices = new ArrayList<>();
    /**
     * An ArrayList of smart devices that have switch times set, to keep track of the devices with switch times.
     */
    public static ArrayList<SmartDevice> devicesWithSwitchTimes = new ArrayList<>();

    private String name;
    private String status = "Off";
    private LocalDateTime switchTime = null;
    /**
     * Constructor for SmartDevice class with only name parameter.
     *
     * @param name the name of the smart device
     */
    public SmartDevice(String name){
        setName(name);
    }
    /**
     * Constructor for SmartDevice class with name and status parameters.
     *
     * @param name the name of the smart device
     * @param status the status of the smart device (On/Off)
     * @throws ErroneousError if an erroneous status value is provided
     */
    public SmartDevice(String name, String status) throws ErroneousError {
        setName(name);
        setStatus(status);}
    /**
     * Get the name of the smart device.
     *
     * @return the name of the smart device
     */

    public String getName(){
        return name;}
    /**
     * Set the name of the smart device.
     *
     * @param name the name of the smart device
     */
    public void setName(String name) {
        this.name = name;}
    /**
     * Get the status of the smart device (On/Off).
     *
     * @return the status of the smart device
     */
    public String getStatus(){
        return this.status;}

    /**
     * Set the status of the smart device (On/Off).
     *
     * @param status the status of the smart device (On/Off)
     * @throws ErroneousError if an erroneous status value is provided
     */
    public void setStatus(String status) throws ErroneousError {
        if (status.equals("Off")){
            this.status = status;
        } else if (status.equals("On")) {
            this.status = status;
        } else{
            throw new ErroneousError();
        }
    }
    /**
     * Get the switch time of the smart device.
     *
     * @return the switch time of the smart device
     */
    public LocalDateTime getSwitchTime(){
        return this.switchTime;}
    /**
     * Set the switch time of the smart device.
     *
     * @param time the switch time of the smart device
     */
    public void setSwitchTime(LocalDateTime time){
        this.switchTime = time;}
    /**
     * Abstract method for generating a report about the smart device.
     * Subclasses of SmartDevice should implement this method.
     */
    abstract void report();
    /**
     * Retrieves a SmartDevice object from a list of all smart devices based on the provided name.
     *
     * @param name The name of the smart device to retrieve.
     * @return The SmartDevice object with the given name, or null if not found.
     */
    public static SmartDevice deviceGetter(String name){
        if (namesOfAllDevices.contains(name)){
            return allSmartDevices.get(namesOfAllDevices.indexOf(name));
        }
        return null;
    }
    /**
     * Adds a new smart device to the list of all smart devices based on the provided device properties.
     *
     * @param deviceProperties An ArrayList of String containing the properties of the smart device.
     */
    public static void deviceAdder(ArrayList<String> deviceProperties){
        try{
            switch (deviceProperties.get(1)){
                case ("SmartPlug"):
                    SmartPlug.smartPlugAdder(deviceProperties);
                    break;
                case ("SmartCamera"):
                    SmartCamera.smartCameraAdder(deviceProperties);
                    break;
                case ("SmartLamp"):
                    SmartLamp.smartLampAdder(deviceProperties);
                    break;
                case ("SmartColorLamp"):
                    SmartColorLamp.smartColorLampAdder(deviceProperties);
                    break;
            }
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * This method changes the name of a smart device.
     *
     * @param commandLineChangeName An ArrayList of String containing the command line arguments.
     *                             The first element is the command name "change-name",
     *                             the second element is the old name of the smart device,
     *                             and the third element is the new name to be set.
     * @throws ErroneousError If the number of command line arguments is not exactly 3.
     */
    public static void changeName(ArrayList<String> commandLineChangeName){
        try{
            if (!(commandLineChangeName.size()==3)){
                throw new ErroneousError();
            }
            String oldName =commandLineChangeName.get(1);
            String newName =commandLineChangeName.get(2);
            if (oldName.equals(newName)){
                FileOutput.writeToFile("ERROR: Both of the names are the same, nothing changed!");
            } else if (!namesOfAllDevices.contains(oldName)) {
                FileOutput.writeToFile("ERROR: There is not such a device!");
            } else if (namesOfAllDevices.contains(newName)) {
                FileOutput.writeToFile("ERROR: There is already a smart device with same name!");
            } else {
                deviceGetter(oldName).setName(newName);
                namesOfAllDevices.set(namesOfAllDevices.indexOf(oldName), newName);
            }
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * This method removes a smart device from the list of all smart devices.
     *
     * @param commandLineRemove An ArrayList of String containing the command line arguments.
     *                          The first element is the command name "remove",
     *                          and the second element is the name of the smart device to be removed.
     * @throws ErroneousError If the number of command line arguments is not exactly 2.
     */
    public static void remove(ArrayList<String> commandLineRemove) {
        try{
            if (!(commandLineRemove.size()==2)){
                throw new ErroneousError();
            }
            String name = commandLineRemove.get(1);
            if (namesOfAllDevices.contains(name)) {
                SmartDevice smartDeviceToBeRemoved = deviceGetter(name);
                smartDeviceToBeRemoved.setStatus("Off");
                if (smartDeviceToBeRemoved instanceof SmartPlug) {
                    ((SmartPlug) smartDeviceToBeRemoved).calculateEnergyConsumption();
                } else if (smartDeviceToBeRemoved instanceof SmartCamera) {
                    ((SmartCamera) smartDeviceToBeRemoved).calculateStorageUsage();
                }
                FileOutput.writeToFile("SUCCESS: Information about removed smart device is as follows:");
                smartDeviceToBeRemoved.report();
                allSmartDevices.remove(smartDeviceToBeRemoved);
                namesOfAllDevices.remove(name);
            } else{
                FileOutput.writeToFile("ERROR: There is not such a device!");
            }
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * This method switches the status (On/Off) of a smart device.
     *
     * @param commandLineSwitch An ArrayList of String containing the command line arguments.
     *                          The first element is the command name "switch",
     *                          the second element is the name of the smart device,
     *                          and the third element is the status to be set ("On" or "Off").
     * @throws ErroneousError If the number of command line arguments is not exactly 3.
     */
    public static void switchDevice(ArrayList<String> commandLineSwitch){
        try{
            if (!(commandLineSwitch.size()==3)){
                throw new ErroneousError();
            }
            String deviceName = commandLineSwitch.get(1);
            String status = commandLineSwitch.get(2);
            SmartDevice smartDeviceObject = (deviceGetter(deviceName));
            if (smartDeviceObject == null){
                FileOutput.writeToFile("ERROR: There is not such a device!");
            } else if (smartDeviceObject.getStatus().equals(status)) {
                FileOutput.writeToFile("ERROR: This device is already switched "+ smartDeviceObject.getStatus().toLowerCase()+ "!");
            } else {
                smartDeviceObject.setStatus(status);
                if (smartDeviceObject instanceof SmartPlug){
                    ((SmartPlug) smartDeviceObject).calculateEnergyConsumption();
                    ((SmartPlug) smartDeviceObject).setTimeDeviceBeenSwitchedOn(Time.time);
                } else if (smartDeviceObject instanceof SmartCamera){
                    ((SmartCamera) smartDeviceObject).calculateStorageUsage();
                    ((SmartCamera) smartDeviceObject).setTimeDeviceBeenSwitchedOn(Time.time);
                }
            }
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * This method sets the switch time for a smart device.
     *
     * @param commandLineSetSwitchTime An ArrayList of String containing the command line arguments.
     *                                 The first element is the command name "set-switch-time",
     *                                 the second element is the name of the smart device,
     *                                 and the third element is the switch time in string format.
     * @throws ErroneousError If the number of command line arguments is not exactly 3,
     *                        or if the switch time is in the past.
     * @throws NullPointerException If the specified smart device does not exist.
     */
    public static void setSwitchTimeForDevice(ArrayList<String> commandLineSetSwitchTime){
        try{
            if (!(commandLineSetSwitchTime.size()==3)){
                throw new ErroneousError();
            }
            String deviceName = commandLineSetSwitchTime.get(1);
            String timeString = commandLineSetSwitchTime.get(2);
            LocalDateTime switchTime = Time.timeObjectReturner(timeString);
            SmartDevice smartDeviceWithSwitchTime = deviceGetter(deviceName);
            if (switchTime.isAfter(Time.time) || switchTime.isEqual(Time.time)){
                smartDeviceWithSwitchTime.setSwitchTime(switchTime);
                if (!devicesWithSwitchTimes.contains(smartDeviceWithSwitchTime)){
                    SmartDevice.devicesWithSwitchTimes.add(smartDeviceWithSwitchTime);
                }
                SmartDevice.deviceSwitcherWithTime();
            } else {
                FileOutput.writeToFile("ERROR: Switch time cannot be in the past!");
            }
        } catch (NullPointerException e){
            FileOutput.writeToFile("ERROR: There is not such a device!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * This method switches the status (On/Off) of smart devices that have a switch time set.
     * If the switch time is before or equal to the current time, the status of the smart device
     * will be changed accordingly.
     */
    public static void deviceSwitcherWithTime(){
        try{
            if (devicesWithSwitchTimes != null){
                for (SmartDevice smartDevice : devicesWithSwitchTimes){
                    if (smartDevice.getSwitchTime().isBefore(Time.time) || smartDevice.getSwitchTime().isEqual(Time.time)){
                        if (smartDevice.getStatus().equals("Off")){
                            smartDevice.setStatus("On");
                            if (smartDevice instanceof DevicesCalculatingTimeInterval){
                                ((DevicesCalculatingTimeInterval) smartDevice).setTimeDeviceBeenSwitchedOn(Time.time);
                            }
                        } else if (smartDevice.getStatus().equals("On")) {
                            smartDevice.setStatus("Off");
                        }
                        if (smartDevice instanceof SmartPlug){
                            ((SmartPlug) smartDevice).calculateEnergyConsumption();
                        } else if (smartDevice instanceof SmartCamera) {
                            ((SmartCamera) smartDevice).calculateStorageUsage();
                        }
                    }
                }
            }
            switchTimeCleaner();
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * This method removes the switch time of smart devices that have a switch time set
     * and the switch time is before or equal to the current time.
     * Also rearranges the list that keeps all the devices.
     */
    public static void switchTimeCleaner(){
        try{
            if (devicesWithSwitchTimes != null){
                ArrayList<SmartDevice> devicesToBeRemoved = new ArrayList<>();
                for (SmartDevice smartDevice : devicesWithSwitchTimes){
                    if (smartDevice.getSwitchTime().isBefore(Time.time) || smartDevice.getSwitchTime().isEqual(Time.time)){
                        devicesToBeRemoved.add(smartDevice);}
                }
                if (devicesToBeRemoved != null){
                    int index=0;
                    for (int i=0  ; i<devicesToBeRemoved.size() ; i++){
                        SmartDevice smartDevice = devicesToBeRemoved.get(i);
                        devicesWithSwitchTimes.remove(smartDevice);
                        if (devicesToBeRemoved.size()>1 && i<devicesToBeRemoved.size()-1) {
                            SmartDevice nextSmartDevice = devicesToBeRemoved.get(i+1);
                            if (smartDevice.getSwitchTime().isEqual(nextSmartDevice.getSwitchTime())){
                                allSmartDevices.remove(smartDevice);
                                allSmartDevices.add(index++, smartDevice);
                                namesOfAllDevices.remove(smartDevice.getName());
                                namesOfAllDevices.add(index, smartDevice.getName());
                                smartDevice.setSwitchTime(null);
                            } else {
                                allSmartDevices.remove(smartDevice);
                                allSmartDevices.add(index, smartDevice);
                                namesOfAllDevices.remove(smartDevice.getName());
                                namesOfAllDevices.add(index, smartDevice.getName());
                                smartDevice.setSwitchTime(null);
                                index =0;}
                        } else {
                            allSmartDevices.remove(smartDevice);
                            allSmartDevices.add(index, smartDevice);
                            namesOfAllDevices.remove(smartDevice.getName());
                            namesOfAllDevices.add(index, smartDevice.getName());
                            smartDevice.setSwitchTime(null);
                            index = 0;
                        }
                    }
                }
            }
        } catch (NullPointerException ignored){
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Sorts the list of SmartDevice objects based on their switch time in ascending order.
     *
     * @param list The ArrayList of SmartDevice objects to be sorted.
     */
    public static void sort(ArrayList<SmartDevice> list) {
        list.sort((o1, o2) -> o1.getSwitchTime().compareTo(o2.getSwitchTime()));}
    /**
     * Executes a no-operation (nop) command to switch to the next scheduled switch time for devices.
     *
     * @param commandLineNop An ArrayList of String containing the command line input for nop command.
     * @throws ErroneousError If the command line input for nop command is invalid.
     */
    public static void nop(ArrayList<String> commandLineNop){
        try{
            if (!(commandLineNop.size()==1)){
                throw new ErroneousError();}
            sort(devicesWithSwitchTimes);
            String nextTime = Time.timeStringReturner(devicesWithSwitchTimes.get(0).getSwitchTime());
            Time.setTime(nextTime);
            switchTimeCleaner();
        } catch (ErroneousError e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: There is nothing to switch!");
        }
    }
    /**
     * Generates a z-report containing the status or information of all smart devices.
     *
     * @throws Exception If an error occurs while generating the z-report.
     */
    public static void zReport(){
        Time.timeIs();
        try{
            try{
                sort(devicesWithSwitchTimes);
                for (SmartDevice devicesWithSwitchTime : devicesWithSwitchTimes){
                    devicesWithSwitchTime.report();}
            } catch (NullPointerException e) {}
            for (SmartDevice smartDevice : allSmartDevices){
                if (!devicesWithSwitchTimes.contains(smartDevice)){
                    smartDevice.report();
                }
            }
        }catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
}
