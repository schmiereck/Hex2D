package de.schmiereck.hex2d;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages="de.schmiereck.hex2d")
public class Hex2DApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(Hex2DMain.class).run();
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