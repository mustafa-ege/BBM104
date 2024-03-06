import java.time.Duration;
import java.time.LocalDateTime;
/**
 * The DevicesCalculatingTimeInterval class is an abstract class that represents a smart device capable of calculating
 * the time elapsed since it has been switched on. It extends the SmartDevice class.
 */
public abstract class DevicesCalculatingTimeInterval extends SmartDevice {

    private LocalDateTime timeDeviceBeenSwitchedOn;
    /**
     * Gets the time when the device has been switched on.
     *
     * @return The LocalDateTime representing the time when the device has been switched on.
     */
    public LocalDateTime getTimeDeviceBeenSwitchedOn(){
        return this.timeDeviceBeenSwitchedOn;}
    /**
     * Sets the time when the device has been switched on.
     *
     * @param newTime The new time value to set.
     */

    public void setTimeDeviceBeenSwitchedOn(LocalDateTime newTime){
        if ((this.getStatus().equals("On"))){
            this.timeDeviceBeenSwitchedOn = newTime;
        } else if (newTime == null) {
            this.timeDeviceBeenSwitchedOn = null;
        }
    }
    /**
     * Constructs a new DevicesCalculatingTimeInterval instance with the specified name.
     *
     * @param name The name of the device.
     */
    public DevicesCalculatingTimeInterval(String name) {
        super(name);}
    /**
     * Constructs a new DevicesCalculatingTimeInterval instance with the specified name and status.
     *
     * @param name   The name of the device.
     * @param status The initial status of the device.
     * @throws ErroneousError If an erroneous status is provided.
     */

    public DevicesCalculatingTimeInterval(String name, String status) throws ErroneousError {
        super(name, status);}
    /**
     * Calculates the time elapsed since the device has been switched on.
     *
     * @return The Duration representing the time elapsed since the device has been switched on.
     */
    public Duration calculateTimeElapsed(){
        try{
            LocalDateTime startTime = getTimeDeviceBeenSwitchedOn();
            Duration timeElapsed = Duration.between(startTime,Time.time);
            return timeElapsed;
        } catch (NullPointerException e){
            return Duration.ofSeconds(0);
        }
    }
}
