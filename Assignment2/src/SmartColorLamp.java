import java.util.ArrayList;
/**
 * The `SmartColorLamp` class is a subclass of `SmartLamp` that represents a smart lamp capable of displaying different colors of light. It extends the functionality of the `SmartLamp` class by allowing users to set the color of the lamp using color codes or kelvin values.
 * The class provides methods to set and get the color code of the lamp, as well as to add a new `SmartColorLamp` device, set the color code of an existing device, and set the color and brightness of an existing device.
 */
public class SmartColorLamp extends SmartLamp{
    private int colorCode;
    private String mode;
    /**
     * Constructs a new `SmartColorLamp` object with the given name.
     * @param name The name of the smart color lamp.
     */
    public SmartColorLamp(String name) {
        super(name);
        this.setMode("white");
    }
    /**
     * Constructs a new `SmartColorLamp` object with the given name and status.
     * @param name The name of the smart color lamp.
     * @param status The status of the smart color lamp.
     * @throws ErroneousError if an error occurs during construction.
     */
    public SmartColorLamp(String name, String status) throws ErroneousError {
        super(name, status);
        this.setMode("white");
    }
    /**
     * Constructs a new `SmartColorLamp` object with the given name, status, kelvin or color code, and brightness.
     * @param name The name of the smart color lamp.
     * @param status The status of the smart color lamp.
     * @param kelvinOrColorCode The kelvin or color code of the smart color lamp.
     * @param brightness The brightness of the smart color lamp.
     * @throws WrongValueError if the kelvin or color code is not in the correct format or range.
     * @throws ErroneousError if an error occurs during construction.
     */
    public SmartColorLamp(String name, String status, String kelvinOrColorCode, int brightness) throws WrongValueError, ErroneousError {
        super(name, status);
        if (kelvinOrColorCode.startsWith("0x")){
            this.setColorCode(kelvinOrColorCode);
            this.setBrightness(brightness);
            this.setMode("color");
        } else {
            int kelvin = Integer.parseInt(kelvinOrColorCode);
            this.setKelvin(kelvin);
            this.setBrightness(brightness);
            this.setMode("white");
        }
    }
    /**
     * Gets the color code of the smart color lamp.
     * @return The color code of the smart color lamp.
     */
    public int getColorCode() {
        return colorCode;}
    /**
     * Sets the color code of the smart color lamp.
     * @param colorCode The color code to be set.
     * @throws WrongValueError if the color code is not in the correct format or range.
     */
    public void setColorCode(String colorCode) throws WrongValueError {
        int intColorCode = Integer.decode(colorCode);
        if (0x000000<=intColorCode && intColorCode<=0xFFFFFF){
            this.colorCode = intColorCode;
        } else {
            throw new WrongValueError("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");}
    }
    /**
     * Gets the color mode of the smart color lamp.
     * @return The mode of the smart color lamp.
     */
    public String getMode() {
        return mode;
    }
    /**
     * Sets the mode of the smart color lamp.
     * @param mode The color code to be set.
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
    /**
     * Adds a new SmartColorLamp device based on the command line arguments.
     *
     * @param commandLineAddColorLamp ArrayList of String containing the command line arguments.
     *                               The expected format is:
     *                               - 3 arguments: name
     *                               - 4 arguments: name, status
     *                               - 6 arguments: name, status, kelvinOrColorCode, brightness
     * @throws DuplicateError     if a device with the same name already exists
     * @throws WrongValueError    if the command line arguments have wrong values
     * @throws ErroneousError     if the command line arguments are erroneous
     * @throws NumberFormatException if the conversion of kelvinOrColorCode to int fails
     */
    public static void smartColorLampAdder(ArrayList<String> commandLineAddColorLamp){
        try {
            int numberOfProperties = commandLineAddColorLamp.size();
            if (!(numberOfProperties==3 || numberOfProperties==4 || numberOfProperties==6)){
                throw new ErroneousError();
            }
            SmartColorLamp testingDeviceToCheckCommands = new SmartColorLamp("tester");
            String name = commandLineAddColorLamp.get(2);
            switch (numberOfProperties) {
                case (3):
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();}
                    allSmartDevices.add(new SmartColorLamp(name));
                    namesOfAllDevices.add(name);
                    break;
                case (4):
                    String status = commandLineAddColorLamp.get(3);
                    testingDeviceToCheckCommands.setStatus(status);
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();}
                    allSmartDevices.add(new SmartColorLamp(name, status));
                    namesOfAllDevices.add(name);
                    break;
                case (6):
                    status = commandLineAddColorLamp.get(3);
                    testingDeviceToCheckCommands.setStatus(status);
                    String kelvinOrColorCode = commandLineAddColorLamp.get(4);
                    int intColorCode = Integer.decode(kelvinOrColorCode);
                    int brightness = Integer.parseInt(commandLineAddColorLamp.get(5));
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();}
                    if (kelvinOrColorCode.startsWith("0x")){
                        testingDeviceToCheckCommands.setColorCode(kelvinOrColorCode);
                        testingDeviceToCheckCommands.setBrightness(brightness);
                    } else {
                        testingDeviceToCheckCommands.setKelvin(Integer.parseInt(kelvinOrColorCode));
                        testingDeviceToCheckCommands.setBrightness(brightness);
                    }
                    SmartColorLamp newColorLamp = new SmartColorLamp(name, status, kelvinOrColorCode, brightness);
                    allSmartDevices.add(newColorLamp);
                    namesOfAllDevices.add(name);
                    break;}
        } catch (DuplicateError e) {
            FileOutput.writeToFile("ERROR: There is already a smart device with same name!");
        } catch (WrongValueError e) {
            FileOutput.writeToFile(e.getMessage());
        } catch (NumberFormatException | ErroneousError e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Sets the color code of an existing SmartColorLamp device based on the command line arguments.
     *
     * @param commandLineSetColorCode ArrayList of String containing the command line arguments.
     *                               The expected format is:
     *                               - 3 arguments: name, colorCode
     * @throws WrongValueError  if the command line arguments have wrong values
     * @throws ClassCastException if the specified device is not a SmartColorLamp device
     * @throws Exception        if an error occurs during the execution
     */
    public static void setLampColorCode(ArrayList<String> commandLineSetColorCode){
        try{
            if (!(commandLineSetColorCode.size()==3)){
                throw new ErroneousError();
            }
            String name = commandLineSetColorCode.get(1);
            String colorCode = commandLineSetColorCode.get(2);
            if (namesOfAllDevices.contains(name)) {
                SmartColorLamp lampToBeUpdated = (SmartColorLamp) deviceGetter(name);
                lampToBeUpdated.setColorCode(colorCode);
                lampToBeUpdated.setMode("color");
            } else {
                FileOutput.writeToFile("ERROR: There is not such a device!");
            }
        } catch (WrongValueError e){
            FileOutput.writeToFile(e.getMessage());
        } catch (ClassCastException e){
            FileOutput.writeToFile("ERROR: This device is not a smart color lamp!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Sets the color and brightness of an existing SmartColorLamp device based on the command line arguments.
     *
     * @param commandLineSetColor ArrayList of String containing the command line arguments.
     *                            The expected format is:
     *                            - 4 arguments: name, colorCode, brightness
     * @throws WrongValueError  if the command line arguments have wrong values
     * @throws ClassCastException if the specified device is not a SmartColorLamp device
     * @throws Exception        if an error occurs during the execution
     */
    public static void setColor(ArrayList<String> commandLineSetColor){
        try{
            if (!(commandLineSetColor.size()==4)){
                throw new ErroneousError();
            }
            String name = commandLineSetColor.get(1);
            String colorCode = commandLineSetColor.get(2);
            int brightness = Integer.parseInt(commandLineSetColor.get(3));
            if (namesOfAllDevices.contains(name)) {
                SmartColorLamp lampToBeUpdated = (SmartColorLamp) deviceGetter(name);
                int intColorCode = Integer.decode(colorCode);
                if (0<=brightness && brightness<=100){
                    lampToBeUpdated.setColorCode(colorCode);
                    lampToBeUpdated.setBrightness(brightness);
                    lampToBeUpdated.setMode("color");
                } else if (0x000000<=intColorCode && intColorCode<=0xFFFFFF) {
                    lampToBeUpdated.setBrightness(brightness);
                    lampToBeUpdated.setColorCode(colorCode);
                    lampToBeUpdated.setMode("color");
                } else {
                    lampToBeUpdated.setColorCode(colorCode);
                }
            } else {
                FileOutput.writeToFile("ERROR: There is not such a device!");
            }
        } catch (WrongValueError e){
            FileOutput.writeToFile(e.getMessage());
        } catch (ClassCastException e){
            FileOutput.writeToFile("ERROR: This device is not a smart color lamp!");
        } catch (Exception e){
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Reports the status of the SmartColorLamp device.
     *
     * @throws WrongValueError  if the command line arguments have wrong values
     * @throws ClassCastException if the specified device is not a SmartColorLamp device
     * @throws Exception        if an error occurs during the execution
     */
    @Override
    public void report(){
        String stringColor = null;
        if (getMode() == "color"){
            stringColor = (Integer.toHexString(getColorCode()));
            stringColor = String.format("0x%6s", stringColor.toUpperCase()).replace(' ', '0');
        } else if (getMode() == "white") {
            stringColor = String.format("%dK",getKelvin());}
        FileOutput.writeToFile("Smart Color Lamp "+getName()+" is "+getStatus().toLowerCase()+" and its color" +
                " value is "+stringColor+" with "+getBrightness()+"%" + " brightness, and its time to switch its status " +
                "is "+ getSwitchTime()+".");
    }

}
