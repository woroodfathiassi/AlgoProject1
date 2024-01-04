package application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LEDsExpectedResult {
	
	static ExpectedResultWithLines t = new ExpectedResultWithLines();

	TableView<info> expectedResultTV = new TableView<>();
	Image light;
	Image unLight;

	List<Integer> leds = new ArrayList<>();
	int count = 0;

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

		// label in the top
		Label top = new Label("The LEDs that gives the expected result");
		pane.getChildren().add(top);
		top.setPrefSize(550, 50);
		top.getStyleClass().add("custom-root");

		leds.clear();
		printLCS(lcs, x, x.length, x.length);
		pane.getChildren().add(expectedResultTV);
		ExResult(x, y);

		// display LEDs numbers in horizontal list
		ListView<Integer> listView = new ListView<>();
		listView.getItems().addAll(leds);

		// Set the orientation of the ListView to horizontal
		listView.setOrientation(javafx.geometry.Orientation.HORIZONTAL);

		pane.getChildren().add(listView);
		listView.setLayoutX(120);
		listView.setLayoutY(525);
		listView.setPrefSize(300, 40);
		
		//show the expected result with lines
		Button show = new Button("Show");
		pane.getChildren().add(show);
		show.getStyleClass().add("button-style");
		show.setLayoutX(440);
		show.setLayoutY(535);
		
		show.setOnAction(e->{
			t.LgiveERStage(lcs, x, y);
		});
		

		Scene scene = new Scene(pane, 550, 570);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	private void ExResult(int[] x, int[] y) {
		expectedResultTV.setLayoutX(60);
		expectedResultTV.setLayoutY(90);
		expectedResultTV.getColumns().clear();
		expectedResultTV.getItems().clear();
		expectedResultTV.refresh();
		expectedResultTV.setPrefSize(430, 430);
		expectedResultTV.getStyleClass().add("table-view");

		TableColumn<info, Integer> source = new TableColumn<>("Sources");
		source.setPrefWidth(200);
		source.setCellValueFactory(new PropertyValueFactory<info, Integer>("source"));

		TableColumn<info, ImageView> led = new TableColumn<>("LEDs");
		led.setPrefWidth(230);
		led.setCellValueFactory(new PropertyValueFactory<>("led"));

		expectedResultTV.getColumns().addAll(source, led);

		for (int i = 0; i < x.length; i++) {
			if (leds.contains(y[i])) {
				ImageView iv1 = new ImageView(light);
				expectedResultTV.getItems().add(new info(y[i], iv1));
			} else {
				ImageView iv2 = new ImageView(unLight);
				expectedResultTV.getItems().add(new info(y[i], iv2));
			}
		}

//		count = 0;
//		for (int i = 0; i < x.length; i++) {
//			if (count < leds.size()) {
//				if (leds.get(count) == i + 1) {
//					ImageView iv1 = new ImageView(light);
//					expectedResultTV.getItems().add(new info(i + 1, iv1));
//					count++;
//				} else {
//					ImageView iv2 = new ImageView(unLight);
//					expectedResultTV.getItems().add(new info(i + 1, iv2));
//				}
//			} else {
//				ImageView iv2 = new ImageView(unLight);
//				expectedResultTV.getItems().add(new info(i + 1, iv2));
//			}
//		}

		expectedResultTV.refresh();
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
