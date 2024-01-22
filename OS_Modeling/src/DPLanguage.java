import java.nio.charset.StandardCharsets;

// Target in Adapter Pattern
public interface DPLanguage {
    void fprintf(File handle, String str);
}

// Adapter (1) in Adapter Pattern
// It is used to translate DPLanguage print statement to LinuxFileSystem print statement
class TranslatorDPToLinux implements DPLanguage {

    private final LinuxFileSystem adaptee; // Linux or BSD File System

    public TranslatorDPToLinux(Linux_OS linux_os) {
        this.adaptee = (LinuxFileSystem) linux_os.getFileSystem();
    }

    @Override
    public void fprintf(File handle, String str) {
        adaptee.uprintf(str, handle);
    }
}

// Adapter (2) in Adapter Pattern
// It is used to translate DPLanguage print statement to BSDFileSystem print statement
class TranslatorDPToBSD implements DPLanguage {

    private final BSDFileSystem adaptee;

    public TranslatorDPToBSD(BSD_OS bsd_os) {
        this.adaptee = (BSDFileSystem) bsd_os.getFileSystem();
    }

    @Override
    public void fprintf(File handle, String str) {
        adaptee.uprintf(str, handle);
    }
}

// Adapter (3) in Adapter Pattern
// It is used to translate DPLanguage print statement to NTFileSystem print statement
class TranslatorDPToNT implements DPLanguage {

    private final NTFileSystem adaptee; // NT System

    public TranslatorDPToNT(NT_OS nt_os) {
        this.adaptee = (NTFileSystem) nt_os.getFileSystem();
    }

    @Override
    public void fprintf(File handle, String str) {
        byte[] charArray = str.getBytes(StandardCharsets.UTF_8);
        adaptee.printf(charArray, handle);
    }
}