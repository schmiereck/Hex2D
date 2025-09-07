package de.schmiereck.hex2d.step2.view;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(final Stage stage) {
        super(stage);
    }

    public Stage getStage() {
        return ((Stage) this.getSource());
    }
}
