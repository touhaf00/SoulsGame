package lsg;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.characters.Hero;
import lsg.characters.Zombie;
import lsg.weapons.Sword;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.AnimationPane;
import lsg.graphics.panes.CreationPane;
import lsg.graphics.panes.HUDPane;
import lsg.graphics.panes.TitlePane;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;
import lsg.graphics.widgets.skills.SkillBar;




public class LearningSoulsGameApplication extends Application  {
    private Scene scene;
    private AnchorPane root;
    private TitlePane gameTitle;
    private CreationPane creationPane;
    private String heroName;
    private AnimationPane animationPane;
    private Hero hero;
    private HeroRenderer heroRenderer;
    private Zombie zombie;
    private ZombieRenderer zombieRenderer;
    private HUDPane hudPane ;
    private SkillBar skillBar;
    private BooleanProperty heroCanPlay = new SimpleBooleanProperty(false);
    

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Learning Souls Game");
        root = new AnchorPane();
        scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        buildUI();

        primaryStage.show();
        startGame();
        addListeners();
    }

    private void buildUI() {

        gameTitle = new TitlePane(scene, "Learning Souls Game");
        root.getChildren().add(gameTitle);
        AnchorPane.setTopAnchor(gameTitle, 10.);
        gameTitle.translateXProperty().bind(scene.widthProperty().subtract(gameTitle.widthProperty()).divide(2));
        String css = CSSFactory.getStyleSheet("LSG.css");
         
        creationPane = new CreationPane();
        creationPane.setOpacity(0);
        AnchorPane.setBottomAnchor(creationPane, 0.);
        AnchorPane.setLeftAnchor(creationPane, 0.);
        AnchorPane.setRightAnchor(creationPane, 0.);
        AnchorPane.setTopAnchor(creationPane, 0.);
        root.getChildren().add(creationPane);

        hudPane = new HUDPane();
        AnchorPane.setBottomAnchor(hudPane, 0.);
        AnchorPane.setLeftAnchor(hudPane, 0.);
        AnchorPane.setRightAnchor(hudPane, 0.);
        AnchorPane.setTopAnchor(hudPane, 0.);

        
        animationPane = new AnimationPane(root);
       
        scene.getStylesheets().add(css);
        
    }
    private void startGame(){
        gameTitle.zoomIn((event -> {
            creationPane.fadeIn((event1->{
                ImageFactory.preloadAll((() -> {
                    System.out.println("Pré-chargement des images terminé");
                }));
            }));
        }));
    }
    private void addListeners(){
        creationPane.getNameField().setOnAction((event -> {
            heroName = creationPane.getNameField().getText();
            System.out.println("Nom du héro : " + heroName);

            if(!heroName.equals("")){

                root.getChildren().removeAll(creationPane);
                gameTitle.zoomOut((event1 -> {
                    play();
                }));
        }}));   
    }

    private void play() {
        System.out.println("play method called");
        root.getChildren().add(animationPane);
        root.getChildren().add(hudPane);
        createHero();
        createSkills();
        createMonster(event -> {
            System.out.println("Finished handling for monster creation");
            hudPane.getMessagePane().showMessage("Fight!", 4, event1 -> heroCanPlay.setValue(true));
            System.out.println("heroCanPlay set to true");
        });

    }
    

    private void createHero()
    {
        if (heroName == null || heroName.isEmpty() || heroName.replace(" ", "").isEmpty()) { hero = new Hero(); }
        else { hero = new Hero(heroName); }
        hero.setWeapon(new Sword());
        heroRenderer = animationPane.createHeroRenderer();
        heroRenderer.goTo(animationPane.getPrefWidth()*0.5 - heroRenderer.getFitWidth()*0.65, null);
        
        hudPane.getHeroStatbar().getName().setText(hero.getName());

        hudPane.getHeroStatbar().setPadding(new javafx.geometry.Insets(10, 0, 0, 0));
        hudPane.getHeroStatbar().getLifeBar().progressProperty().bind(hero.lifeRateProperty());
        hudPane.getHeroStatbar().getStaminaBar().progressProperty().bind(hero.stamRateProperty());
    }
    public void createMonster(EventHandler<ActionEvent> finishedHandler){
        System.out.println("createMonster method called");
        zombie = new Zombie();
        zombieRenderer = animationPane.createZombieRenderer();
        zombieRenderer.goTo(animationPane.getPrefWidth()*0.5 - zombieRenderer.getBoundsInLocal().getWidth() * 0.15, finishedHandler);
        hudPane.getMonsterStatbar().getName().setText(zombie.getName());
        Image[] imgs = ImageFactory.getSprites(ImageFactory.SPRITES_ID.ZOMBIE_HEAD);
        if (imgs != null)
        {
            hudPane.getMonsterStatbar().getAvatar().setImage(imgs[0]);
            hudPane.getMonsterStatbar().getAvatar().setRotate(40);
        }
        hudPane.getMonsterStatbar().getLifeBar().progressProperty().bind(zombie.lifeRateProperty());
        hudPane.getMonsterStatbar().getStaminaBar().progressProperty().bind(zombie.stamRateProperty());

    
    }

    private void createSkills(){
        skillBar = hudPane.getSkillBar();
        skillBar.setDisable(!heroCanPlay.get());
        heroCanPlay.addListener((observable, oldValue, newValue) -> skillBar.setDisable(!newValue));

        skillBar.getTrigger(0).setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.ATTACK_SKILL)[0]);
        skillBar.getTrigger(0).setOnMouseClicked(event -> {
            System.out.println("ATTACK");
        });
        scene.setOnKeyReleased(event ->{
            skillBar.process(event.getCode());
        });
    }
}
