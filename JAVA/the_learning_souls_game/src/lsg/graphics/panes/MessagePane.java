package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;

public class MessagePane extends VBox {

    public MessagePane() {
        this.setAlignment(Pos.CENTER);
    }
    public void showMessage(String msg, int cycleCount, EventHandler<ActionEvent> finishedEvent){
        System.out.println("Message displayed: Fight!");

        GameLabel gameLabel = new GameLabel(msg);
        this.getChildren().add(gameLabel);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), gameLabel);
        translateTransition.setByY(-200);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), gameLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        translateTransition.play();
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> this.getChildren().remove(gameLabel));

    }
}
