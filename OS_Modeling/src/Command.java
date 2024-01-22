// Command (Command Pattern)
public interface Command {
    void Execute();
}

// Concrete Command (Command Pattern)
class ShutDownCommand implements Command {

    private final OperatingSystem operatingSystem;

    public ShutDownCommand(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    // call reset() method of operating system in Execute()
    @Override
    public void Execute() {
        operatingSystem.reset();
    }
}

// Invoker (Command Pattern)
class User {
    private final Command shutdown;

    public User(Command shutdown) {
        this.shutdown = shutdown;
    }

    // Execute the shutdown command
    public void shutdownOS() {
        shutdown.Execute();
    }
}