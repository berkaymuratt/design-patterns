import java.util.ArrayList;

enum FileElementType {
    LINUX,
    BSD,
    NT
}

// The exception for checking the type of the file when a
// directory is trying to add a file that types are not matched
class InvalidFileElementType extends Exception {
    public InvalidFileElementType() {
        super("File type and FileSystem type is not matching..!");
    }
}

// Component in Composite Pattern
abstract public class FileElement {

    private final FileElementType type;
    protected String name;

    public FileElement(FileElementType type, String name) {
        this.type = type;
        this.name = name;
    }

    public FileElementType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    // Composite Pattern Methods
    public abstract void Display(int indent); // indent value for extra blanks to print a tree structure
    public abstract void Add(FileElement fileElement) throws InvalidFileElementType;
    public abstract boolean Remove(FileElement fileElement);
    public abstract FileElement GetChild(int index);
}

// Leaf in Composite Pattern
// Product 1 in Abstract Factory
abstract class File extends FileElement {

    private String content;

    public File(FileElementType type, String name) {
        super(type, name);
        this.content = "-";
    }

    @Override
    public void Display(int indent) {

        String s = "";

        for(int i=0; i<indent; i++) {
            s+="-";
        }
        s += "  " + name;
        s += " content => " + content;
        System.out.println(s);
    }

    @Override
    public void Add(FileElement fileElement) {
        // Empty
    }

    @Override
    public boolean Remove(FileElement fileElement) {
        // Empty
        return false;
    }

    @Override
    public FileElement GetChild(int index) {
        return null;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

// Subclasses of File
class LinuxFile extends File {
    public LinuxFile(String name) {
        super(FileElementType.LINUX, name);
    }
}

class BSDFile extends File {
    public BSDFile(String name) {
        super(FileElementType.BSD, name);
    }
}

class NTFile extends File {
    public NTFile(String name) {
        super(FileElementType.NT, name);
    }
}


// Composite in Composite Pattern
// Abstract Class in Template Method()
// Product 2 in Abstract Factory
abstract class Directory extends FileElement {

    private final ArrayList<FileElement> fileElements;

    public Directory(FileElementType type, String name) {
        super(type, name);
        this.fileElements = new ArrayList<>();
    }

    // First display the name of itself then
    // call Display() method for each fileElement in the list
    @Override
    public void Display(int indent) {
        String s = "";

        for(int i=0; i<indent; i++) {
            s += "-";
        }
        s += "+ " + name;
        System.out.println(s);

        for(FileElement fileElement : fileElements) {
            fileElement.Display(indent+2);
        }
    }

    // Primitive Operation
    abstract protected boolean checkType(FileElement fileElement);

    // Template Method
    @Override
    public void Add(FileElement fileElement) throws InvalidFileElementType {
        // First checkType the fileElement by comparing the file element types of the directory and the file
        boolean isOk = checkType(fileElement);

        // If file element types are not matched, throw InvalidFileElementType()
        if(!isOk) {
            throw new InvalidFileElementType();
        }

        // If there is no problem with the type of the file,
        // add the file to fileElements of the directory
        fileElements.add(fileElement);
    }

    // If fileElement in this directory remove, if its not search for the rest of the list
    // If its deleted return true
    @Override
    public boolean Remove(FileElement fileElement) {
        boolean isDeleted = this.fileElements.remove(fileElement);

        if(!isDeleted) {
            for(FileElement fileElement1 : fileElements) {
                isDeleted = fileElement1.Remove(fileElement);

                if(isDeleted) {
                    break;
                }
            }
        }

        return isDeleted;
    }

    @Override
    public FileElement GetChild(int index) {
        return this.fileElements.get(index);
    }
}

// Subclasses of Directory
class LinuxDirectory extends Directory {
    public LinuxDirectory(String name) {
        super(FileElementType.LINUX, name);
    }

    // Override the checkType() method to check the fileElement's type is Linux type or not
    @Override
    protected boolean checkType(FileElement fileElement) {
        return fileElement.getType() == FileElementType.LINUX;
    }
}

class BSDDirectory extends Directory {
    public BSDDirectory(String name) {
        super(FileElementType.BSD, name);
    }

    // Override the checkType() method for check the fileElement's type is BSD type or not
    @Override
    protected boolean checkType(FileElement fileElement) {
        return fileElement.getType() == FileElementType.BSD;
    }
}

class NTDirectory extends Directory {

    public NTDirectory(String name) {
        super(FileElementType.NT, name);
    }

    // Override the checkType() method for check the fileElement's type is NT type or not
    @Override
    protected boolean checkType(FileElement fileElement) {
        return fileElement.getType() == FileElementType.NT;
    }
}