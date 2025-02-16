import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));

        // Set up the scene and stage
        primaryStage.setTitle("Login System");
        primaryStage.setScene(new Scene(root, 497, 400)); // Add this line to set the scene
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}