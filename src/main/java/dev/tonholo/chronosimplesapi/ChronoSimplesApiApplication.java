package dev.tonholo.chronosimplesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class ChronoSimplesApiApplication {

    public static void main(String[] args) {
//        ReactorDebugAgent.init();
//        ReactorDebugAgent.processExistingClasses();

        BlockHound.install();

        SpringApplication.run(ChronoSimplesApiApplication.class, args);
    }

}
