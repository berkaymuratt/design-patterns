import java.util.ArrayList;
import java.util.Scanner;

// Driver Class
// Client (Command Pattern)
class MainSystem {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        OperatingSystem os;

        System.out.println("Choose Operation System: ");
        System.out.println("1: Linux OS");
        System.out.println("2: BSD OS");
        System.out.println("3: NT OS");
        System.out.println("------------------------------------------");

        System.out.print(">: ");
        int osNumber = scanner.nextInt();

        switch (osNumber) {
            case 1:
                os = Linux_OS.getInstance();
                break;
            case 2:
                os = BSD_OS.getInstance();
                break;
            case 3:
                os = NT_OS.getInstance();
                break;
            default:
                System.out.println("Invalid Option");
                return;
        }

        while(true) {
            System.out.println(); // Blank
            System.out.println("1: Create Files");
            System.out.println("2: Display All Files");
            System.out.println("3: Print to File");
            System.out.println("4: Send Data to Network Port");
            System.out.println("5: Shutdown");
            System.out.println("------------------------------------");

            System.out.print(">: ");
            int opNumber = scanner.nextInt();

            switch (opNumber) {
                case 1:
                    createExampleFiles(os);
                    break;
                case 2:
                    displayAllFiles(os);
                    break;
                case 3:
                    printToFileOp(os);
                    break;
                case 4:
                    sendData(os);
                    break;
                case 5:
                    shutdownOP(os);
                    return;
                default:
                    System.out.println("Invalid Operation Number...");
            }
        }
    }

    // create example files in the operating system's file system
    public static void createExampleFiles(OperatingSystem os) {

        FileSystem fileSystem = os.getFileSystem();

        FileElement file1 = fileSystem.createFile("File1");
        FileElement file2 = fileSystem.createFile("File2");
        FileElement file3 = fileSystem.createFile("File3");
        FileElement file4 = fileSystem.createFile("File4");

        FileElement dir1 = fileSystem.createDirectory("Directory1");
        FileElement dir2 = fileSystem.createDirectory("Directory2");
        FileElement dir3 = fileSystem.createDirectory("Directory3");

        try {
            dir1.Add(file1);
            dir1.Add(dir2);
            dir2.Add(file2);

            dir3.Add(file4);

            os.getFileSystem().Add(dir1);
            os.getFileSystem().Add(file3);
            os.getFileSystem().Add(dir3);

            System.out.println("File are created...");
        } catch (InvalidFileElementType e) {
            java.lang.System.out.println("ERROR: " + e.getMessage());
        }
    }

    // display fileElements in the operating system's file system
    public static void displayAllFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        fileSystem.displayFiles();
    }

    // create a different fileElement and add it to the operating system's file system
    // then use the print statement of translator of the operating system (DP Language)
    // to change the content of the file
    public static void printToFileOp(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();

        File file = fileSystem.createFile("my-file");
        fileSystem.Add(file);

        DPLanguage dpLanguage = os.getTranslator();

        System.out.println("File Name: " + file.getName());

        System.out.println("Current Content: " + file.getContent());
        dpLanguage.fprintf(file, "-new content-");

        System.out.println("New Content: " + file.getContent());
    }

    // Network Port object is created as IODevice and added to operating system as device.
    // Create 2 different Application objects as Observer then Attach them to NetworkPort as observers
    // change the data value of the Network Port object
    public static void sendData(OperatingSystem os) {
        IODevice networkPort = new NetworkPort();
        os.addDevice(networkPort);

        Observer appA = new Application("Application A");
        Observer appB = new Application("Application B");
        networkPort.Attach(appA);
        networkPort.Attach(appB);

        System.out.println("------------------------------");
        networkPort.setData("value");
    }

    // Create a ShutDownCommand as Command. Create a User object as invoker.
    // Set ShutDownCommand as command of User. Then call shutdownOS() from the invoker (User)
    public static void shutdownOP(OperatingSystem os) {
        Command command = new ShutDownCommand(os);
        User user = new User(command);
        user.shutdownOS();
    }
}

// Singleton for all subclasses
abstract public class OperatingSystem {
    private String name;
    private FileSystem fileSystem;
    private final ArrayList<Device> devices;
    protected DPLanguage translator;

    public OperatingSystem(String name, FileSystem fileSystem) {
        this.name = name;
        this.fileSystem = fileSystem;
        this.devices = new ArrayList<>();

        this.devices.add(new CPU());
        this.devices.add(new HardDisk());
        this.devices.add(new NetworkPort());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void reset() {
        for(Device device : devices) {
            device.resetDevice();
        }
    }

    public DPLanguage getTranslator() {
        return translator;
    }
}

class Linux_OS extends OperatingSystem {

    private static Linux_OS linux_os;

    private Linux_OS() {
        super("Linux Operating System", new LinuxFileSystem());
        super.translator = new TranslatorDPToLinux(this);
    }

    public static Linux_OS getInstance() {
        if(linux_os == null) {
            linux_os = new Linux_OS();
        }
        return linux_os;
    }
}

class BSD_OS extends OperatingSystem {

    private static BSD_OS bsd_os;

    private BSD_OS() {
        super("BSD Operating System", new BSDFileSystem());
        super.translator = new TranslatorDPToBSD(this);
    }

    public static BSD_OS getInstance() {
        if(bsd_os == null) {
            bsd_os = new BSD_OS();
        }
        return bsd_os;
    }
}

class NT_OS extends OperatingSystem {

    private static NT_OS nt_os;

    private NT_OS() {
        super("NT Operating System", new NTFileSystem());
        super.translator = new TranslatorDPToNT(this);
    }

    public static NT_OS getInstance() {
        if(nt_os == null) {
            nt_os = new NT_OS();
        }
        return nt_os;
    }
}