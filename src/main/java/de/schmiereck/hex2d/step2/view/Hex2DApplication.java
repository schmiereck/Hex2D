package de.schmiereck.hex2d.step2.view;

import de.schmiereck.hex2d.step2.S2Hex2DMain;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ComponentScan(basePackages="de.schmiereck.hex2d.step2")
public class Hex2DApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(S2Hex2DMain.class).run();
    }

    @Override
    public void start(final Stage stage) throws IOException {
        this.applicationContext.publishEvent(new StageReadyEvent(stage));

    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}