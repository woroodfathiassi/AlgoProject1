package application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WarningMessage {
	public void warning(String str) {
		Stage stage = new Stage();

		Image icon = new Image(
				"C:\\Users\\Admin\\Desktop\\University\\ThirdYear\\comp336(algorithm)\\AlgoProject1\\img\\background.jpg");
		stage.getIcons().add(icon);

		Pane pane = new Pane();

		// label in the top
		Label top = new Label("Warning...");
		pane.getChildren().add(top);
		top.setPrefSize(300, 50);
		top.getStyleClass().add("custom-root");

		Text text = new Text(str);
		text.setWrappingWidth(200);
		pane.getChildren().add(text);
		text.setLayoutX(105);
		text.setLayoutY(105);
		text.setFont(new Font(15));

		ImageView image = new ImageView(
				"C:\\\\Users\\\\Admin\\\\Desktop\\\\University\\\\ThirdYear\\\\comp336(algorithm)\\\\AlgoProject1\\\\img\\\\warning.png");
		pane.getChildren().add(image);
		image.setLayoutX(45);
		image.setLayoutY(90);
		image.setFitWidth(45);
		image.setFitHeight(45);

		Scene scene = new Scene(pane, 300, 200);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
