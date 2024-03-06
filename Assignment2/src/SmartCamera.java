import java.util.ArrayList;
/**
 * The SmartCamera class represents a smart camera device that extends the DevicesCalculatingTimeInterval class.
 * It stores information about the device's name, megabytes, storage, and status. It provides methods to set and
 * retrieve these properties, as well as methods to calculate storage usage and generate a report.
 */
public class SmartCamera extends DevicesCalculatingTimeInterval{
    private double megabytes;
    private double storage;
    /**
     * Constructs a SmartCamera object with the given name.
     *
     * @param name The name of the smart camera.
     */
    public SmartCamera(String name) {
        super(name);}
    /**
     * Constructs a SmartCamera object with the given name and megabytes.
     *
     * @param name      The name of the smart camera.
     * @param megabytes The initial amount of megabytes used by the smart camera.
     * @throws WrongValueError If the megabytes value is not a positive number.
     */
    public SmartCamera(String name, double megabytes) throws WrongValueError {
        super(name);
        setMegabytes(megabytes);}
    /**
     * Constructs a SmartCamera object with the given name, status, and megabytes.
     *
     * @param name      The name of the smart camera.
     * @param status    The status of the smart camera.
     * @param megabytes The initial amount of megabytes used by the smart camera.
     * @throws WrongValueError If the megabytes value is not a positive number.
     * @throws ErroneousError If an erroneous error occurs.
     */
    public SmartCamera(String name, String status, double megabytes) throws WrongValueError, ErroneousError {
        super(name, status);
        setMegabytes(megabytes);}
    /**
     * Retrieves the amount of megabytes used by the smart camera.
     *
     * @return The amount of megabytes used by the smart camera.
     */
    public double getMegabytes(){
        return this.megabytes;}
    /**
     * Sets the amount of megabytes used by the smart camera.
     *
     * @param megabytes The amount of megabytes used by the smart camera.
     * @throws WrongValueError If the megabytes value is not a positive number.
     */
    public void setMegabytes(double megabytes) throws WrongValueError {
        if (megabytes>0){
            this.megabytes = megabytes;
        } else{
            throw new WrongValueError("ERROR: Megabyte value must be a positive number!");}
    }
    /**
     * Retrieves the storage used by the smart camera.
     *
     * @return The storage used by the smart camera.
     */
    public double getStorage(){
        return this.storage;}
    /**
     * Sets the storage used by the smart camera.
     *
     * @param storage The storage used by the smart camera.
     */
    public void setStorage(double storage){
        this.storage = storage;}
    /**
     * Adds a smart camera to the system based on the command line input.
     *
     * @param commandLineAddCamera An ArrayList of Strings representing the command line input for adding a smart camera.
     *                            The ArrayList must contain 4 or 5 elements, which represent the properties of the smart camera
     *                            in the following order: device type, device name, storage capacity in megabytes,
     *                            status (optional).
     * @throws ErroneousError If the number of properties in the command line input is not 4 or 5.
     * @throws DuplicateError If a smart device with the same name already exists in the system.
     */

    public static void smartCameraAdder(ArrayList<String> commandLineAddCamera){
        try{
            int numberOfProperties = commandLineAddCamera.size();
            if (!(numberOfProperties==4 || numberOfProperties==5)){
                throw new ErroneousError();
            }
            SmartCamera testingDeviceToCheckCommands = new SmartCamera("tester");
            String name = commandLineAddCamera.get(2);
            double megabyte = Double.parseDouble(commandLineAddCamera.get(3));
            switch (numberOfProperties){
                case (4):
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();}
                    allSmartDevices.add(new SmartCamera(name,megabyte));
                    namesOfAllDevices.add(name);
                    break;
                case (5):
                    String status = commandLineAddCamera.get(4);
                    testingDeviceToCheckCommands.setStatus(status);
                    if (namesOfAllDevices.contains(name)){
                        throw new DuplicateError();}
                    testingDeviceToCheckCommands.setMegabytes(megabyte);
                    allSmartDevices.add(new SmartCamera(name,status,megabyte));
                    namesOfAllDevices.add(name);
                    ((SmartCamera) deviceGetter(name)).setTimeDeviceBeenSwitchedOn(Time.time);
                    break;
            }
        } catch (WrongValueError e){
                FileOutput.writeToFile(e.getMessage());
        } catch (DuplicateError e) {
                FileOutput.writeToFile("ERROR: There is already a smart device with same name!");
        } catch (Exception e) {
            FileOutput.writeToFile("ERROR: Erroneous command!");
        }
    }
    /**
     * Calculates the storage usage of the smart camera based on the time elapsed since its last status change.
     * The calculated storage usage is updated in the smart camera object.
     */
    public void calculateStorageUsage(){
        double timeElapsedHours = (double) calculateTimeElapsed().toMinutes();
        double storageUsed = timeElapsedHours * getMegabytes();
        setStorage(storageUsed);
        setTimeDeviceBeenSwitchedOn(null);
    }
    /**
     * Generates a report for the smart camera, including its name, status, storage usage, and time to switch status.
     * The report is written to a file using FileOutput.writeToFile() method.
     */
    @Override
    public void report(){
        FileOutput.writeToFile("Smart Camera "+getName()+" is "+getStatus().toLowerCase() +" and used " +
                String.format("%.2f",getStorage()).replace(".",",") +" MB of storage so far (excluding "
                + "current status), and its time to switch its status is "+Time.timeStringReturner(getSwitchTime())+".");}
}
