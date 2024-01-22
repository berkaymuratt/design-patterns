import java.util.ArrayList;

// Abstract Class (Template Method Pattern)
abstract public class Device {
    private String name;

    public Device(String name) {
        this.name = name;
    }

    // Template Method
    public void resetDevice() {
        System.out.println("---------------------------------------");
        reset();
        System.out.println(this.name + " has been reset.");
        System.out.println("---------------------------------------");
    }

    // Primitive Operation
    protected abstract void reset();
}

// Concrete Class 1 (Template Method)
class CPU extends Device {

    public CPU() {
        super("CPU");
    }

    // Override the reset() method for reset CPU
    @Override
    public void reset() {
        System.out.println("All Processes are terminated...");
    }
}

// Concrete Class 2 (Template Method)
class HardDisk extends  Device {

    private final String bufferData;

    public HardDisk() {
        super("Hard Disk");
        this.bufferData = "buffer-data";
    }

    // Override the reset() method for reset HardDisk
    @Override
    public void reset() {
        System.out.println("The data in buffer: " + bufferData);
    }
}


// Subject (Observer Pattern)
abstract class IODevice extends Device {

    protected String data; // subjectState
    private final ArrayList<Observer> observers;

    public IODevice() {
        super("I/O Device");
        this.observers = new ArrayList<>();
    }

    public void Attach(Observer observer) {
        this.observers.add(observer);
    }

    public void Detach(Observer observer) {
        this.observers.remove(observer);
    }

    public void Notify() {
        for(Observer observer : this.observers) {
            observer.Update(this.data);
        }
    }

    public String getData() {
        return data;
    }

    abstract public void setData(String data);
}

// ConcreteSubject (Observer Pattern)
// Concrete Class 3 (Template Method)
class NetworkPort extends IODevice {

    // Override the reset() method for reset NetworkPort
    @Override
    public void reset() {
        System.out.println("The data in network port: " + super.data);
    }

    // When the object's state is changed, call Notify() to notify observers
    @Override
    public void setData(String data) {
        System.out.println("Network Port Data (old): " + super.data);
        super.data = data;
        System.out.println("Network Port Data (new): " + super.data);
        Notify();
    }
}

// Observer
interface Observer {
    void Update(String newData);
}

// ConcreteObserver
class Application implements Observer {

    private static boolean isDataConsumed; // for checking the data is consumed or not
    private String name; // name of the Application
    private String data; // observerState

    public Application(String name) {
        this.name = name;
    }

    @Override
    public void Update(String newData) {
        this.data = newData;
        System.out.println(this.name + "'s data is updated: " + this.data);

        // Data is not consumed, consume data
        // By this way only one of the application can consume the data
        if(!isDataConsumed) {
            consumeData();
        }
    }

    private void consumeData() {
        System.out.println("(Consumed " + this.data + " from " + this.name + ")");
        isDataConsumed = true;
    }
}