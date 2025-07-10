package dk.dev.customersystem.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleAppRunner implements CommandLineRunner {
    private final ConsoleInterface console;
    @Autowired
    public ConsoleAppRunner(ConsoleInterface console) {
        this.console = console;
    }

    @Override
    public void run(String... args) throws Exception {
        console.start();
    }
}
