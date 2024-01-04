package application;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ExpectedResultWithLines {

	Image light;
	Image unLight;
	Image source;

	List<Integer> leds = new ArrayList<>();
	//SLL leds = new SLL();

	public void LgiveERStage(String[][] lcs, int[] x, int[] y) {
		Stage stage = new Stage();

		Image icon = new Image(
				"C:\\Users\\Admin\\Desktop\\University\\ThirdYear\\comp336(algorithm)\\AlgoProject1\\img\\background.jpg");
		stage.getIcons().add(icon);

		Pane pane = new Pane();

		light = new Image(
				"file:C:/Users/Admin/Desktop/University/ThirdYear/comp336(algorithm)/AlgoProject1/img/light2.png");
		unLight = new Image(
				"file:C:/Users/Admin/Desktop/University/ThirdYear/comp336(algorithm)/AlgoProject1/img/light1.png");
		source = new Image(
				"file:C:/Users/Admin/Desktop/University/ThirdYear/comp336(algorithm)/AlgoProject1/img/source.png");

		// label in the top
		Label top = new Label("The LEDs that gives the expected result");
		pane.getChildren().add(top);
		top.setPrefSize(550, 50);
		top.getStyleClass().add("custom-root");

		Label sourse = new Label("source");
		sourse.setLayoutX(130);
		sourse.setLayoutY(90);
		sourse.getStyleClass().add("custom-root");

		Label led = new Label("led");
		led.setLayoutX(370);
		led.setLayoutY(90);
		led.getStyleClass().add("custom-root");

		pane.getChildren().addAll(sourse, led);

		int startX = 180;
		int startY = 70;
		int endX = 100;
		int endY = 70;

		int count = 0;

		leds.clear();
		printLCS(lcs, x, x.length, x.length);

		for (int i = 0; i < x.length; i++) {
			Label sourseNum = new Label();
			Label ledNum = new Label();

			pane.getChildren().addAll(sourseNum, ledNum);
			sourseNum.setLayoutX(startX);
			startY += 70;

			ImageView sourceView = new ImageView(source);
			sourceView.setLayoutX(startX-50);
			sourceView.setLayoutY(startY-10);
			pane.getChildren().add(sourceView);
			
			sourseNum.setLayoutY(startY);
			sourseNum.setText((i + 1) + "");

			ledNum.setLayoutX(startX + 200);
			ledNum.setLayoutY(startY);
			ledNum.setText(y[i] + "");

			if (count < leds.size() && (int)leds.get(count) == x[i]) {
				endY = 70;
				for (int j = 0; j < x.length; j++) {
					endY += 70;
					if (y[j] == (int)leds.get(count)) {
						Line line = new Line(sourseNum.getLayoutX() + 10, sourseNum.getLayoutY() + 10, startX + 195,
								endY + 10);
						ImageView lightView = new ImageView(light);
						lightView.setLayoutX(startX + 220);
						lightView.setLayoutY(endY - 15);
						pane.getChildren().addAll(line, lightView);
						count++;
						break;
					}
				}

			}
		}

		// Create a ScrollPane and set its content to the VBox
		ScrollPane scrollPane = new ScrollPane(pane);
		scrollPane.setFitToWidth(true); // Adjusts the width to the content's width

		// Create a Scene with the ScrollPane
		Scene scene = new Scene(scrollPane, 550, 650);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		// stage.setResizable(false);
		stage.show();
	}

	private void printLCS(String[][] b, int[] x, int i, int j) {
		if (i == 0 || j == 0)
			return;
		else {
			if (b[i][j].contains("CROSS")) {
				printLCS(b, x, i - 1, j - 1);
				leds.add(x[j - 1]);
			} else {
				if (b[i][j].contains("LEFT"))
					printLCS(b, x, i, j - 1);
				else
					printLCS(b, x, i - 1, j);
			}
		}
	}

}
