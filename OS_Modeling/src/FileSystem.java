import java.util.ArrayList;

// Client in Abstract Factory
// Client in Composite Pattern
abstract public class FileSystem {
    private String name;
    private final ArrayList<FileElement> fileElements;
    private final FileElementFactory fileElementFactory;

    public FileSystem(FileElementFactory fileElementFactory, String name) {
        this.fileElementFactory = fileElementFactory;
        this.fileElements = new ArrayList<>();
        this.name = name;
    }

    public void displayFiles() {
        for (FileElement fileElement : fileElements) {
            System.out.println("----------------------");
            fileElement.Display(0);
            System.out.println("----------------------");
        }
    }

    public void Add(FileElement fileElement) {
        this.fileElements.add(fileElement);
    }

    public void Remove(FileElement fileElement) {
        this.fileElements.remove(fileElement);
    }

    public FileElement Get(int index) {
        return this.fileElements.get(index);
    }

    public File createFile(String name) {
        return fileElementFactory.createFile(name);
    }

    public Directory createDirectory(String name) {
        return fileElementFactory.createDirectory(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// Adaptee1 in Adapter Pattern (TranslatorDPToLinux)
class LinuxFileSystem extends FileSystem {

    public LinuxFileSystem() {
        super(LinuxFileElementFactory.getInstance(), "Linux File System");
    }

    // The print statement of LinuxFileSystem
    public int uprintf(String str, File handle) {
        if(handle==null) {
            return 0;
        }
        // Some special operations for linux...

        handle.setContent(str);
        return 1;
    }
}


// Adaptee2 in Adapter Pattern (TranslatorDPToNT)
class BSDFileSystem extends FileSystem {
    public BSDFileSystem() {
        super(BSDFileElementFactory.getInstance(), "BSD File System");
    }

    // The print statement of BSDFileSystem
    public int uprintf(String str, File file) {
        if(file==null) {
            return 0;
        }
        // Some special operations for BSD...

        file.setContent(str);
        return 1;
    }
}

// Adaptee3 in Adapter Pattern (TranslatorDPToNT)
class NTFileSystem extends FileSystem {

    public NTFileSystem() {
        super(NTFileElementFactory.getInstance(),"NT File System");
    }

    // The print statement of NTFileSystem
    public void printf(byte[] charArray, File file) {
        String content = new String(charArray);
        file.setContent(content);
    }
}