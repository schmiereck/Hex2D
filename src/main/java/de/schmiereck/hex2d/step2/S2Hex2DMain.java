package de.schmiereck.hex2d.step2;

import de.schmiereck.hex2d.step2.view.Hex2DApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * see: https://blog.jetbrains.com/idea/2019/11/tutorial-reactive-spring-boot-a-javafx-spring-boot-application/
 */
@SpringBootApplication(scanBasePackages = "de.schmiereck.hex2d.step2")
public class S2Hex2DMain {

    public static void main(String[] args) {
        //org.springframework.boot.SpringApplication.run(Hex2DApplication.class, args);
        javafx.application.Application.launch(Hex2DApplication.class, args);
    }
}
