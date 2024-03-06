import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * The GamePane class represents a custom BorderPane for the game in JavaFX.
 * It contains an ImageView for the background, foreground, and crosshair.
 * It handles mouse events to control the movement of the crosshair.
 * It also provides methods to move and animate a duck.
 */
public class GamePane extends BorderPane {
    private ImageView crosshair;

    /**
     * Constructs a new GamePane with the specified background, foreground, and crosshair ImageView.
     *
     * @param background the ImageView for the background
     * @param foreground the ImageView for the foreground
     * @param crosshair  the ImageView for the crosshair
     */
    public GamePane(ImageView background, ImageView foreground, ImageView crosshair) {
        this.crosshair = crosshair;
        this.getChildren().addAll(background, foreground, crosshair);
        cursorChanger();
        this.setWidth(background.getFitWidth());
        this.setHeight(background.getFitHeight());
    }

    /**
     * Handles the cursor change and crosshair movement based on mouse events.
     * In order to handle the size difference in scaling the game, it changes the
     * cursor to none, unvisible, and moves the scaled crosshair image along with
     * the mouse movement.
     * When moved outside the application, it returns back to default cursor.
     */
    public void cursorChanger() {
//        this part was hard to implement since image cursor does not get scaled
//        along with the size of the window.
        this.setOnMouseEntered(mouseEvent -> {
            crosshair.setVisible(true);
            this.setCursor(Cursor.NONE);
            crosshair.setX(mouseEvent.getX() - (crosshair.getFitWidth() / 2));
            crosshair.setY(mouseEvent.getY() - (crosshair.getFitHeight() / 2));
        });
        this.setOnMouseMoved(mouseEvent -> {
            crosshair.setX(mouseEvent.getX() - (crosshair.getFitWidth() / 2));
            crosshair.setY(mouseEvent.getY() - (crosshair.getFitHeight() / 2));
        });
        this.setOnMouseDragged(mouseEvent -> {
            crosshair.setX(mouseEvent.getX() - (crosshair.getFitWidth() / 2));
            crosshair.setY(mouseEvent.getY() - (crosshair.getFitHeight() / 2));
        });
        this.setOnMouseExited(mouseEvent -> {
            this.setCursor(Cursor.DEFAULT);
            crosshair.setVisible(false);
        });
    }

    /**
     * Moves the duck within the game pane based on its velocity.
     *
     * @param duck the duck to move
     */
    public void moveDuck(Duck duck) {
        if (!duck.isDead()) {
            if (duck.getX() < 0 || duck.getX() > this.getWidth() - duck.getAverageWidth()) {
                duck.setxVelocity(duck.getxVelocity() * -1);
                duck.turnAround();
            }
            if (duck.getY() < 0 || duck.getY() > this.getHeight() - duck.getAverageHeight()) {
                duck.setyVelocity(duck.getyVelocity() * -1);
                duck.upsideDown();
            }
            duck.setX(duck.getX() + duck.getxVelocity());
            duck.setY(duck.getY() + duck.getyVelocity());
        }
    }

    /**
     * Animates the duck's movement .
     *
     * @param duck the duck to animate
     */
    public void animateDuck(Duck duck) {
        Timeline movingAnimation, flyingAnimation;
        KeyFrame moving = new KeyFrame(Duration.millis(5), e -> moveDuck(duck));
        movingAnimation = new Timeline(moving);
        KeyFrame flying = new KeyFrame(Duration.millis(300), e -> duck.paintDuck());
        flyingAnimation = new Timeline(flying);

        movingAnimation.setCycleCount(Timeline.INDEFINITE);
        flyingAnimation.setCycleCount(Timeline.INDEFINITE);

        movingAnimation.play();
        flyingAnimation.play();
    }
}
