package lsg.graphics.widgets.characters.statbars;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lsg.graphics.ImageFactory;
import lsg.graphics.widgets.texts.GameLabel;

public class Statbar extends BorderPane
{
    private ImageView avatar;
    private final GameLabel name;
    private final ProgressBar lifeBar;
    private final ProgressBar staminaBar;
    private final BorderPane rightPane;

    public ImageView getAvatar() { return avatar; }
    public GameLabel getName() { return name; }
    public ProgressBar getLifeBar() { return lifeBar; }
    public ProgressBar getStaminaBar() { return staminaBar; }

    public Statbar()
    {
        this.setHeight(100);
        this.setWidth(350);

        Image[] imgs = ImageFactory.getSprites(ImageFactory.SPRITES_ID.HERO_HEAD);
        if (imgs != null)
        {
            avatar = new ImageView(imgs[0]);
            avatar.setFitWidth(100);
            avatar.setPreserveRatio(true);
            this.setLeft(avatar);
        }

        rightPane = new BorderPane();

        name = new GameLabel("Name");

        // Si le nom du personnage est trop long, on réduit la taille de la police
        name.textProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.length() > 16) { name.setStyle("-fx-font-size: 18;"); }
            else if (newVal.length() > 12) { name.setStyle("-fx-font-size: 25px;"); }
            else { name.setStyle("-fx-font-size: 33px;"); }
        });

        name.setMinWidth(200);
        name.setMaxWidth(200);
        rightPane.setTop(name);
        VBox bars = new VBox();
        
        lifeBar = new ProgressBar();
        lifeBar.setPrefWidth(250); 
        lifeBar.setStyle("-fx-accent: red;");
        bars.getChildren().add(lifeBar);

        staminaBar = new ProgressBar();
        staminaBar.setPrefWidth(190);
        staminaBar.setStyle("-fx-accent: greenyellow;");
        bars.getChildren().add(staminaBar);

        rightPane.setCenter(bars);

        this.setRight(rightPane);
    }

    public void flip()
    {
        this.setScaleX(-this.getScaleX());
        this.name.setScaleX(-this.name.getScaleX());
        
        if (this.name.getScaleX() < 0)
            this.name.setStyle("-fx-font-size: 33px; -fx-alignment: center-right;");
        else
            this.name.setStyle("-fx-font-size: 33px; -fx-alignment: center-left;");
    }
}