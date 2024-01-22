// Abstract Factory (Abstract Factory Pattern)
public interface FileElementFactory {
    File createFile(String name);
    Directory createDirectory(String name);
}

// Concrete Factory 1 (Abstract Factory Pattern)
// It is used to create LinuxFile and create LinuxDirectory
// Singleton Pattern
class LinuxFileElementFactory implements FileElementFactory {

    private static LinuxFileElementFactory instance;

    private LinuxFileElementFactory() {}

    public static LinuxFileElementFactory getInstance() {
        if(instance == null) {
            instance = new LinuxFileElementFactory();
        }

        return instance;
    }

    @Override
    public File createFile(String name) {
        return new LinuxFile(name + ".lnx");
    }

    @Override
    public Directory createDirectory(String name) {
        return new LinuxDirectory(name + ".lnxd");
    }
}

// Concrete Factory 2
// It is used to create BSDFile and create BSDDirectory
// Singleton Pattern
class BSDFileElementFactory implements FileElementFactory {

    private static BSDFileElementFactory instance;

    private BSDFileElementFactory() {}

    public static BSDFileElementFactory getInstance() {
        if(instance == null) {
            instance = new BSDFileElementFactory();
        }
        return instance;
    }

    @Override
    public File createFile(String name) {
        return new BSDFile(name + ".bsd");
    }

    @Override
    public Directory createDirectory(String name) {
        return new BSDDirectory(name + ".bsdir");
    }
}

// Concrete Factory 3
// It is used to create NTFile and create NTDirectory
// Singleton Pattern
class NTFileElementFactory implements FileElementFactory {

    private static NTFileElementFactory instance;

    private NTFileElementFactory() {}

    public static NTFileElementFactory getInstance() {
        if(instance == null) {
            instance = new NTFileElementFactory();
        }

        return instance;
    }

    @Override
    public File createFile(String name) {
        return new NTFile(name + ".nt");
    }

    @Override
    public Directory createDirectory(String name) {
        return new NTDirectory(name + ".ntdir");
    }
}