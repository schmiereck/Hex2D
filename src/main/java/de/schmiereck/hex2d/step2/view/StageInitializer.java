package de.schmiereck.hex2d.step2.view;

import de.schmiereck.hex2d.step2.service.HexGrid;
import de.schmiereck.hex2d.step2.service.HexGridService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    //@Value("classpath:/de/schmiereck/hex2d/hex2D-view.fxml")
    @Value("classpath:/step2/hex2D-view.fxml")
    private Resource chartResource;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    @Autowired
    private HexGridService hexGridService;

    public StageInitializer(@Value("${spring.application.ui.title} (Step-2)") final String applicationTitle,
                            final ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(final StageReadyEvent event) {
        final Stage stage = event.getStage();
        try {
            //final FXMLLoader fxmlLoader = new FXMLLoader(Hex2DApplication.class.getResource("hex2D-view.fxml"));
            final FXMLLoader fxmlLoader = new FXMLLoader(this.chartResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> this.applicationContext.getBean(aClass));

            final Parent parent = fxmlLoader.load();

            final HexGrid hexGrid = this.hexGridService.getHexGrid();

            final double width = 920;//640 * 2; //hexGrid.getNodeCountX() * GridModel.StepX;
            //final double height = 840;//460 * 2; //(hexGrid.getNodeCountY()) * GridModel.StepY;
            final double height = Math.sqrt(Math.pow(width, 2.0D) - Math.pow(width / 2, 2.0D));

            final Scene scene = new Scene(parent, width, height);

            stage.setTitle(this.applicationTitle);
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
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
