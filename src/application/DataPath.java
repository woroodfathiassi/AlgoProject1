package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DataPath {

	TableView<String[]> dpTV = new TableView<>();
	TableView<led> sourcesTV = new TableView<>();

	public void DPStage(String[][] lcs, int[] x, int[] y) {
		Stage stage = new Stage();

		Image icon = new Image(
				"C:\\Users\\Admin\\Desktop\\University\\ThirdYear\\comp336(algorithm)\\AlgoProject1\\img\\background.jpg");
		stage.getIcons().add(icon);

		Pane pane = new Pane();

		// label in the top
		Label top = new Label("The DP table");
		pane.getChildren().add(top);
		top.setPrefSize(750, 50);
		top.getStyleClass().add("custom-root");

		pane.getChildren().add(dpTV);
		pane.getChildren().add(sourcesTV);
		allLEDs(lcs, x, y);
		allSources(x);

		Scene scene = new Scene(pane, 750, 570);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	private void allLEDs(String[][] lcs, int[] x, int[] y) {
		dpTV.setLayoutX(80);
		dpTV.setLayoutY(70);
		dpTV.getColumns().clear();
		dpTV.getItems().clear();
		dpTV.refresh();
		dpTV.setPrefSize(650, 450);
		dpTV.getStyleClass().add("table-view");

		for (int i = 1; i <= y.length; i++) {
			int columnIndex = i;
			TableColumn<String[], String> column = new TableColumn<>(String.valueOf(y[i - 1]));
			if (y.length <= 10)
				column.setPrefWidth(650 / y.length);
			else
				column.setPrefWidth((650 / y.length) + (y.length / 1.5));
			column.setCellValueFactory(cellData -> {
				if (cellData != null && cellData.getValue() != null) {
					return new SimpleStringProperty(cellData.getValue()[columnIndex]);
				} else {
					return new SimpleStringProperty("");
				}
			});
			dpTV.getColumns().add(column);
		}

		// Add rows (arrays) to the TableView
		for (int i = 1; i < lcs.length; i++) {
			String[] row = new String[lcs.length];
			for (int j = 1; j < lcs.length; j++)
				row[j] = lcs[i][j];
			dpTV.getItems().add(row);
		}

		dpTV.refresh();
	}

	private void allSources(int[] x) {
		sourcesTV.setLayoutX(30);
		sourcesTV.setLayoutY(70);
		sourcesTV.getColumns().clear();
		sourcesTV.getItems().clear();
		sourcesTV.refresh();
		sourcesTV.setPrefSize(50, 450);
		sourcesTV.getStyleClass().add("table-view");

		TableColumn<led, Integer> id = new TableColumn<>("0");
		id.setPrefWidth(48);
		id.setCellValueFactory(new PropertyValueFactory<led, Integer>("num"));

		sourcesTV.getColumns().add(id);

		for (int i = 0; i < x.length; i++) {
			sourcesTV.getItems().add(new led(x[i]));
		}

		sourcesTV.refresh();
	}

}
