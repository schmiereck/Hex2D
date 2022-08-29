package de.schmiereck.hex2d;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    //@Value("classpath:/de/schmiereck/hex2d/hex2D-view.fxml")
    @Value("classpath:/hex2D-view.fxml")
    private Resource chartResource;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(final StageReadyEvent event) {
        final Stage stage = event.getStage();
        try {
            //FXMLLoader fxmlLoader = new FXMLLoader(Hex2DApplication.class.getResource("hex2D-view.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(this.chartResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> this.applicationContext.getBean(aClass));

            final Parent parent =fxmlLoader.load();

            final Scene scene = new Scene(parent, 320, 240);
            stage.setTitle(applicationTitle);
            stage.setScene(scene);

            //final BorderPane borderPane = (BorderPane) scene.lookup("#mainBoderPane");

            final Pane mainPane = (Pane) scene.lookup("#mainPane");
            //mainPane.setPrefSize(240, 200);

            //final Circle circle = new Circle(50, Color.BLUE);
            //circle.relocate(20, 20);

            //final Rectangle rectangle = new Rectangle(100, 100, Color.RED);
            //rectangle.relocate(70, 70);

            //canvas.getChildren().addAll(circle, rectangle);

            //borderPane.setCenter(mainPane);

            //scene.setRoot(borderPane);
            //scene.setRoot(borderPane);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
