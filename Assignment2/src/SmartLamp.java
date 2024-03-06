import java.util.ArrayList;
/**
 * The SmartLamp class represents a smart lamp device that extends the SmartDevice class.
 * It allows users to control the color temperature (kelvin) and brightness of the lamp.
 */
public class SmartLamp extends SmartDevice {
    /**
     * The color temperature of the lamp in kelvin. Default value is 4000K.
     */
    private int kelvin = 4000 ;
    /**
     * The brightness level of the lamp. Default value is 100%.
     */
    private int brightness = 100 ;
    /**
     * Constructor with name parameter.
     * @param name The name of the smart lamp.
     */
    public SmartLamp(String name) {
        super(name);}
    /**
     * Constructor with name and status parameters.
     * @param name The name of the smart lamp.
     * @param status The status of the smart lamp.
     * @throws ErroneousError if an error occurs.
     */
    public SmartLamp(String name, String status) throws ErroneousError {
        super(name, status);}

    /**
     * Constructor with name, status, kelvin, and brightness parameters.
     * @param name The name of the smart lamp.
     * @param status The status of the smart lamp.
     * @param kelvin The color temperature of the smart lamp in kelvin.
     * @param brightness The brightness level of the smart lamp.
     * @throws WrongValueError if the kelvin or brightness values are not valid.
     * @throws ErroneousError if an error occurs.
     */
    public SmartLamp(String name, String status, int kelvin, int brightness) throws WrongValueError, ErroneousError {
        super(name, status);
        setKelvin(kelvin);
        setBrightness(brightness);}

    /**
     * Gets the color temperature of the smart lamp in kelvin.
     * @return The color temperature of the smart lamp in kelvin.
     */
    public int getKelvin() {
        return kelvin;}
    /**
     * Sets the color temperature of the smart lamp in kelvin.
     * @param kelvin The color temperature to set in kelvin.
     * @throws WrongValueError if the kelvin value is not valid.
     */
    public void setKelvin(int kelvin) throws WrongValueError {
        if (2000<=kelvin && kelvin<=6500){
            this.kelvin = kelvin;}
        else {
            throw new WrongValueError("ERROR: Kelvin value must be in range of 2000K-6500K!");}
    }
    /**
     * Gets the brightness level of the smart lamp.
     * @return The brightness level of the smart lamp.
     */
    public int getBrightness() {
        return brightness;}
    /**
     * Sets the brightness level of the smart lamp.
     * @param brightness The brightness level to set.
     * @throws WrongValueError if the brightness value is not valid.
     */
    public void setBrightness(int brightness) throws WrongValueError {
        if (0<=brightness && brightness<=100) {
            this.brightness = brightness;
        } else{
            throw new WrongValueError("ERROR: Brightness must be in range of 0%-100%!");}
    }
    /**
     * Adds a new smart lamp device based on the command line arguments.
     * @param commandLineAddLamp The command line arguments for adding a new smart lamp device.
     */
    public static void smartLampAdder(ArrayList<String> commandLineAddLamp){
        try {
            int numberOfProperties = commandLineAddLamp.size();
            if (!(numberOfProperties==3 || numberOfProperties==4 || numberOfProperties==6)){
                throw new ErroneousError();
            }
            SmartLamp testingDeviceToCheckCommands = new SmartLamp("tester");
            String name = commandLineAddLamp.get(2);
            switch (numberOfProperties) {
                case (3):
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();
                    }
                    allSmartDevices.add(new SmartLamp(name));
                    namesOfAllDevices.add(name);
                    break;
                case (4):
                    String status = commandLineAddLamp.get(3);
                    testingDeviceToCheckCommands.setStatus(status);
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();
                    }
                    allSmartDevices.add(new SmartLamp(name, status));
                    namesOfAllDevices.add(name);
                    break;
                case (6):
                    status = commandLineAddLamp.get(3);
                    testingDeviceToCheckCommands.setStatus(status);
                    int kelvin = Integer.parseInt(commandLineAddLamp.get(4));
                    int brightness = Integer.parseInt(commandLineAddLamp.get(5));
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();
                    }
                    testingDeviceToCheckCommands.setKelvin(kelvin);
                    testingDeviceToCheckCommands.setBrightness(brightness);
                    SmartLamp newLamp = new SmartLamp(name, status,kelvin,brightness);
                    allSmartDevices.add(newLamp);
                    namesOfAllDevices.add(name);
                    break;
            }
        }  catch (WrongValueError e) {
            FileOutput.writeToFile(e.getMessage());
        } catch (DuplicateError e) {
            FileOutput.writeToFile("ERROR: There is already a smart device with same name!");
        } catch (Exception e) {
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Sets the kelvin value of a SmartLamp object based on the command line input.
     *
     * @param commandLineSetKelvin An ArrayList of Strings representing the command line arguments.
     *                            It should contain 3 elements: the command, the name of the SmartLamp, and the kelvin value.
     * @throws ErroneousError If the size of the command line input is not equal to 3.
     * @throws WrongValueError If the kelvin value provided is not a valid integer.
     * @throws ClassCastException If the device with the given name is not a SmartLamp object.
     */
    public static void setLampKelvin(ArrayList<String> commandLineSetKelvin){
        try{
            if (!(commandLineSetKelvin.size()==3)){
                throw new ErroneousError();
            }
            String name = commandLineSetKelvin.get(1);
            String kelvin = commandLineSetKelvin.get(2);
            if (namesOfAllDevices.contains(name)) {
                SmartLamp lampToBeUpdated = (SmartLamp) deviceGetter(name);
                lampToBeUpdated.setKelvin(Integer.parseInt(kelvin));
                if (lampToBeUpdated instanceof SmartColorLamp){
                    ((SmartColorLamp) lampToBeUpdated).setMode("white");
                }
            } else {
                FileOutput.writeToFile("ERROR: There is not such a device");
            }
        } catch (WrongValueError e){
            FileOutput.writeToFile(e.getMessage());
        } catch (ClassCastException e){
            FileOutput.writeToFile("ERROR: This device is not a smart lamp!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }

    /**
     * Sets the brightness value of a SmartLamp object based on the command line input.
     *
     * @param commandLineSetBrightness An ArrayList of Strings representing the command line arguments.
     *                                It should contain 3 elements: the command, the name of the SmartLamp, and the brightness value.
     * @throws ErroneousError If the size of the command line input is not equal to 3.
     * @throws WrongValueError If the brightness value provided is not a valid integer.
     * @throws ClassCastException If the device with the given name is not a SmartLamp object.
     */
    public static void setLampBrightness(ArrayList<String> commandLineSetBrightness){
        try{
            if (!(commandLineSetBrightness.size()==3)){
                throw new ErroneousError();
            }
            String name = commandLineSetBrightness.get(1);
            String brightness = commandLineSetBrightness.get(2);
            if (namesOfAllDevices.contains(name)) {
                SmartLamp lampToBeUpdated = (SmartLamp) deviceGetter(name);
                lampToBeUpdated.setBrightness(Integer.parseInt(brightness));
            } else {
                FileOutput.writeToFile("ERROR: There is not such a device!");
            }
        } catch (WrongValueError e){
            FileOutput.writeToFile(e.getMessage());
        } catch (ClassCastException e){
            FileOutput.writeToFile("ERROR: This device is not a smart lamp!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Sets the kelvin and brightness values of a SmartLamp object based on the command line input.
     *
     * @param commandLineSetWhite An ArrayList of Strings representing the command line arguments.
     *                           It should contain 4 elements: the command, the name of the SmartLamp, the kelvin value, and the brightness value.
     * @throws ErroneousError If the size of the command line input is not equal to 4.
     * @throws WrongValueError If the kelvin or brightness value provided is not a valid integer.
     * @throws ClassCastException If the device with the given name is not a SmartLamp object.
     */
    public static void setWhite(ArrayList<String> commandLineSetWhite){
        try{
            if (!(commandLineSetWhite.size()==4)){
                throw new ErroneousError();
            }
            String name = commandLineSetWhite.get(1);
            int kelvin = Integer.parseInt(commandLineSetWhite.get(2));
            int brightness = Integer.parseInt(commandLineSetWhite.get(3));
            if (namesOfAllDevices.contains(name)) {
                SmartLamp lampToBeUpdated = (SmartLamp) deviceGetter(name);
                if (0<=brightness && brightness<=100) {
                    lampToBeUpdated.setKelvin(kelvin);
                    lampToBeUpdated.setBrightness(brightness);
                    if (lampToBeUpdated instanceof SmartColorLamp){
                        ((SmartColorLamp) lampToBeUpdated).setMode("white");
                    }
                } else if (2000<=kelvin && kelvin<=6500) {
                    lampToBeUpdated.setBrightness(brightness);
                    lampToBeUpdated.setKelvin(kelvin);
                    if (lampToBeUpdated instanceof SmartColorLamp){
                        ((SmartColorLamp) lampToBeUpdated).setMode("white");
                    }
                } else {
                    lampToBeUpdated.setKelvin(kelvin);
                }
            } else {
                FileOutput.writeToFile("ERROR: There is not such a device!");
            }
        } catch (WrongValueError e){
            FileOutput.writeToFile(e.getMessage());
        } catch (ClassCastException e){
            FileOutput.writeToFile("ERROR: This device is not a smart lamp!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Reports the current status, kelvin value, brightness value, and switch time of a SmartLamp object.
     */
    @Override
    public void report(){
        FileOutput.writeToFile("Smart Lamp "+ getName()+" is "+getStatus().toLowerCase()+" and its kelvin" +
                " value is "+getKelvin()+"K with "+getBrightness()+"% brightness, and its time to switch its " +
                "status is "+Time.timeStringReturner(getSwitchTime())+".");
    }
}
