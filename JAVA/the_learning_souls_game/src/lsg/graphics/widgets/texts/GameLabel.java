package lsg.graphics.widgets.texts;
import javafx.scene.Node;
import javafx.scene.control.Label;
import lsg.graphics.CSSFactory;

public class GameLabel  extends Label{
    public GameLabel() {
        super();
        init();
    }

    public GameLabel(String text) {
        super(text);
        init();
    }

    public GameLabel(String text, Node graphic) {
        super(text, graphic);
        init();
    }

    private void init() {
        // Associer LSGFont.css au composant
        this.getStylesheets().add(CSSFactory.getStyleSheet("LSGFont.css"));
        
        // Appliquer la classe css "game-font"
        this.getStyleClass().addAll("game-font");
        this.getStyleClass().add("game-font-fx");
    }
}
