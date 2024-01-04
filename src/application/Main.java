package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	File f;

	int S;
	int maxNum = 0;
	String[] array = null;
	int[] lEDLighted;
	int[] x;
	int[] y;
	String[][] lcs = null;
	
	List<led> L = new ArrayList<>();
	List<Integer> leds = new ArrayList<>();
	
	Label theResult = new Label("");

	TextField source_ = new TextField();
	TableView ledTV = new TableView<>();

	ListView<Integer> listView = new ListView<>();

	static WarningMessage wm = new WarningMessage();
	static DataPath dp = new DataPath();
	static LEDsExpectedResult er = new LEDsExpectedResult();
	
	boolean delete = false;

	Stage stage1 = new Stage();

	@Override
	public void start(Stage stage) {
		firstStage_File();

		Image icon = new Image(
				"C:\\Users\\Admin\\Desktop\\University\\ThirdYear\\comp336(algorithm)\\AlgoProject1\\img\\background.jpg");
		stage1.getIcons().add(icon);

		Pane pane = new Pane();

		pane.getChildren().add(ledTV);
		allLEDs();

		// label in the top
		Label top = new Label("Dynamic Programming");
		pane.getChildren().add(top);
		top.setPrefSize(550, 50);
		top.getStyleClass().add("custom-root");

		/*
		 * Enter the number of sources from the user and save it in S when pressing the
		 * button
		 */
		Text source = new Text("Sources: ");
		pane.getChildren().add(source);
		source.getStyleClass().add("text-style");
		source.setLayoutX(50);
		source.setLayoutY(100);

		pane.getChildren().add(source_);
		source_.setLayoutX(50);
		source_.setLayoutY(115);

		Button souButton = new Button("Ok");
		pane.getChildren().add(souButton);
		souButton.getStyleClass().add("button-style");
		souButton.setLayoutX(205);
		souButton.setLayoutY(115);

		Button ledButton = new Button("Add");
		Button randomButton = new Button("Random");

		souButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// chick if the text in trxtField is a number and great than zero
				if (!source_.getText().trim().isEmpty() && source_.getText().trim().matches("\\d+")
						&& Integer.parseInt(source_.getText().trim()) > 0) {
					maxNum = 0;
					theResult.setText(" ");
					L.clear();
					ledTV.getItems().clear();
					leds.clear();
					listView.getItems().clear();

					S = Integer.parseInt(source_.getText().trim());
					ledButton.setDisable(false);
					randomButton.setDisable(false);
					
					delete = false; //when i want to delete any value from the table view if i entered the values manually
				} else {
					source_.clear();
					wm.warning("Please enter the numder of sources !!!");
				}
			}
		});

		// choose read the data randomly after enter the number sources
		Text randomData = new Text("if you want to get a data randomly \n");
		pane.getChildren().add(randomData);
		randomData.setWrappingWidth(220);
		randomData.setLayoutX(50);
		randomData.setLayoutY(170);

		pane.getChildren().add(randomButton);
		randomButton.getStyleClass().add("button-style");
		randomButton.setLayoutX(50);
		randomButton.setLayoutY(180);
		randomButton.setDisable(true);

		randomButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				listView.getItems().clear();
				L.clear();
				leds.clear();
				x = new int[S];
				y = new int[S];
				for (int i = 1; i <= S; i++)
					x[i - 1] = i;
				for (int i = 1; i <= S; i++)
					y[i - 1] = i;

				// make random shuffling to Y values
				for (int i = 0; i < y.length - 1; i++) {
					// generate an index randomly
					int index = (int) (Math.random() * y.length);
					// swap y[i] with y[index]
					int temp = y[i];
					y[i] = y[index];
					y[index] = temp;
				}

				for (int i = 0; i < y.length; i++)
					L.add(new led(y[i]));

				allLEDs();
				lcs = LCS(y, x);
				maxNum = 0;
				lEDLighted = new int[S]; // store LED's value if it is lighting
				printLCS(lcs, x, S, S); // print max number of LEDs lighted
				theResult.setText(maxNum + ""); // display max number of LEDs

				// display LEDs numbers in horizontal list
				listView.getItems().addAll(leds);

				
			}
		});

		// Enter the LED numbers so that they are between 1 - the number of sources and
		// enter them in the table
		Text led = new Text("LEDs: ");
		pane.getChildren().add(led);
		led.getStyleClass().add("text-style");
		led.setLayoutX(290);
		led.setLayoutY(100);

		TextField led_ = new TextField();
		pane.getChildren().add(led_);
		led_.setLayoutX(290);
		led_.setLayoutY(115);

		pane.getChildren().add(ledButton);
		ledButton.getStyleClass().add("button-style");
		ledButton.setLayoutX(445);
		ledButton.setLayoutY(115);
		ledButton.setDisable(true);

		ledButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				// chick if the led number less than numbers of sources and all the inputs are
				// integers and has the correct value
				if (!led_.getText().trim().isEmpty() && led_.getText().trim().matches("\\d+")
						&& Integer.parseInt(led_.getText().trim()) <= Integer.parseInt(source_.getText().trim())
						&& Integer.parseInt(led_.getText().trim()) > 0 && L.size() < S
						&& !L.contains(new led(Integer.parseInt(led_.getText().trim())))) {
					
					delete = true; //if true => you can delete from the table
					
					// store the correct value in L"arrayList has LEDs values"
					L.add(new led(Integer.parseInt(led_.getText().trim())));
					allLEDs(); // save LEDs values in the table view
				} else
					wm.warning("Please enter a numder between 1-" + S + " without repetition !!! \nand only " + S);
				led_.clear();
				
				// after enter all values fill the x and y
				if (L.size() == S) {
					x = new int[S];
					y = new int[S];
					for (int i = 1; i <= S; i++)
						x[i - 1] = i;
					for (int i = 0; i < S; i++)
						y[i] = L.get(i).getNum();
					lcs = LCS(y, x);
					maxNum = 0;
					lEDLighted = new int[S]; // store LED's value if it is lighting
					printLCS(lcs, x, S, S); // print max number of LEDs lighted
					theResult.setText(maxNum + ""); // display max number of LEDs

					// display LEDs numbers in horizontal list
					listView.getItems().addAll(leds);
				}
			}
		});

		// to read the values from a file
		Text choser = new Text("if you want to read the data \n" + "from file: ");
		pane.getChildren().add(choser);
		choser.setWrappingWidth(220);
		choser.setLayoutX(290);
		choser.setLayoutY(170);

		Button loadFileButton = new Button("Load");
		pane.getChildren().add(loadFileButton);
		loadFileButton.getStyleClass().add("button-style");
		loadFileButton.setLayoutX(445);
		loadFileButton.setLayoutY(160);

		loadFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				firstStage_File();
			}
		});

		// 1-The expected result.
		Text result = new Text("1. The expected result: ");
		pane.getChildren().add(result);
		result.getStyleClass().add("text-style");
		result.setLayoutX(50);
		result.setLayoutY(510);

		pane.getChildren().add(theResult);
		theResult.setFont(new Font(20));
		theResult.setLayoutX(260);
		theResult.setLayoutY(490);

		// 2. The DP table.
		Text DPtable = new Text("2. The DP table: ");
		pane.getChildren().add(DPtable);
		DPtable.getStyleClass().add("text-style");
		DPtable.setLayoutX(50);
		DPtable.setLayoutY(560);

		Button DPtableButton = new Button("Show");
		pane.getChildren().add(DPtableButton);
		DPtableButton.getStyleClass().add("button-style");
		DPtableButton.setLayoutX(200);
		DPtableButton.setLayoutY(540);

		DPtableButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (lcs != null && x != null && y != null) {
					dp.DPStage(lcs, y, x); // call the data path stage
				} else
					wm.warning("Plese enter your \nvalues....");
			}
		});

		// 3. The LEDs that gives the expected result.
		Text ledLighted = new Text("3. The LEDs that gives the expected result: ");
		pane.getChildren().add(ledLighted);
		ledLighted.getStyleClass().add("text-style");
		ledLighted.setLayoutX(50);
		ledLighted.setLayoutY(610);

		Button ledLightedButton = new Button("Show");
		pane.getChildren().add(ledLightedButton);
		ledLightedButton.getStyleClass().add("button-style");
		ledLightedButton.setLayoutX(430);
		ledLightedButton.setLayoutY(590);

		ledLightedButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (lcs != null && x != null && y != null) {
					er.LgiveERStage(lcs, x, y); // call LEDs give expected result stage
//					for (int i = 0; i < S; i++)
//						System.out.println(lEDLighted[i] + " hi");
				} else
					wm.warning("Plese enter your \nvalues....");
			}
		});

		// Set the orientation of the ListView to horizontal
		listView.setOrientation(javafx.geometry.Orientation.HORIZONTAL);

		pane.getChildren().add(listView);
		listView.setLayoutX(120);
		listView.setLayoutY(635);
		listView.setPrefSize(300, 40);

		Scene scene = new Scene(pane, 550, 700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage1.setScene(scene);
		stage1.setResizable(false);

	}

	public static void main(String[] args) {
		launch(args);
	}

	private void readMyFile(File f) throws FileNotFoundException {
		Scanner read = new Scanner(f);

		// read from file
		while (read.hasNext()) {
			array = read.nextLine().trim().split(",");

		}
	}

	private void firstStage_File() {
		Stage stage = new Stage();

		Image icon = new Image(
				"C:\\Users\\Admin\\Desktop\\University\\ThirdYear\\comp336(algorithm)\\AlgoProject1\\img\\background.jpg");
		stage.getIcons().add(icon);

		Pane pane = new Pane();

		// label in the top
		Label top = new Label("Load file...");
		pane.getChildren().add(top);
		top.setPrefSize(300, 50);
		top.getStyleClass().add("custom-root");

		Text choser = new Text("if you want to read the data from file: ");
		pane.getChildren().add(choser);
		choser.getStyleClass().add("text-style");
		choser.setWrappingWidth(220);
		choser.setLayoutX(50);
		choser.setLayoutY(100);

		Button selectButton = new Button("Select");
		pane.getChildren().add(selectButton);
		selectButton.getStyleClass().add("button-style");
		selectButton.setLayoutX(50);
		selectButton.setLayoutY(150);

		// the file choser:
		FileChooser fileChooser = new FileChooser();
		selectButton.setOnAction(e -> {
			f = fileChooser.showOpenDialog(stage);

			try {
				readMyFile(f);
				stage.close();
				stage1.show();

				int maxInt = Arrays
						// create a stream from an array =>This means each element of the array becomes
						// an element in the stream.
						// to find the maximum/minimum value
						// it's time is O(n)
						.stream(array).
				// Convert strings to integers after trimming spaces
						mapToInt(s -> Integer.parseInt(s.trim())).max()
						// set the default value as Integer.MIN_VALUE, which is the smallest possible
						// value for an int
						.orElse(Integer.MIN_VALUE);

				if (array.length == maxInt) {
					x = new int[array.length];
					y = new int[array.length];
					for (int i = 1; i <= array.length; i++)
						x[i - 1] = i; // fill x
					L.clear();

					for (int i = 0; i < array.length; i++) {
						L.add(new led(Integer.parseInt(array[i].trim())));
						y[i] = Integer.parseInt(array[i].trim()); // fill y
					}

					// chick if the file has repeated in the values
					if (!hasDuplicates(y)) {

						S = x.length;
						lcs = LCS(y, x);
						maxNum = 0;
						lEDLighted = new int[S];
						leds.clear();
						printLCS(lcs, x, S, S);
						allLEDs();
						theResult.setText(maxNum + "");
						source_.setText(x.length + " ");
						
						// display LEDs numbers in horizontal list
						listView.getItems().clear();
						listView.getItems().addAll(leds);
					} else
						wm.warning("Error\nThe file has repetation...");
				} else if (maxInt > array.length) {
					wm.warning("Error...\nThe maxValue in file is " + maxInt + " so there must be " + maxInt
							+ " values, or there are values greater than permissible limit.");
				} else
					wm.warning("Error\nThere is error in the data(size of data, repeated data, etc...)!!! ");

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		Button cancel = new Button("Cancel");
		pane.getChildren().add(cancel);
		cancel.getStyleClass().add("button-style");
		cancel.setLayoutX(120);
		cancel.setLayoutY(150);

		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				stage.close();
				stage1.show();
			}
		});

		Scene scene = new Scene(pane, 300, 200);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	private void allLEDs() {
		ledTV.setLayoutX(50);
		ledTV.setLayoutY(230);
		ledTV.getColumns().clear();
		ledTV.getItems().clear();
		ledTV.refresh();
		ledTV.setPrefSize(430, 230);
		ledTV.getStyleClass().add("table-view");

		TableColumn<led, Integer> id = new TableColumn<>("LED's Number");
		id.setPrefWidth(430);
		id.setCellValueFactory(new PropertyValueFactory<led, Integer>("num"));
		
		ledTV.getColumns().add(id);

		if (L != null)
			ledTV.getItems().addAll(L);

		ledTV.refresh();
	}

	private static String[][] LCS(int[] x, int[] y) {
		int m = x.length;
		int n = y.length;
		int[][] c = new int[m + 1][n + 1];
		String[][] b = new String[m + 1][n + 1];

		for (int i = 0; i <= m; i++)
			c[i][0] = 0;
		for (int j = 0; j <= n; j++)
			c[0][j] = 0;

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (x[i - 1] == y[j - 1]) {
					c[i][j] = c[i - 1][j - 1] + 1;
					b[i][j] = c[i][j]+" CROSS"; // Mark diagonal for printing LCS
				} else {
					if (c[i][j - 1] >= c[i - 1][j]) {
						c[i][j] = c[i][j - 1];
						b[i][j] = c[i][j]+" LEFT"; // Mark left for printing LCS
					} else {
						c[i][j] = c[i - 1][j];
						b[i][j] = c[i][j]+" UP"; // Mark top for printing LCS
					}
				}
			}
		}
//		for (String[] row : b) {
//			for (String cell : row) {
//				System.out.print(cell + " ");
//			}
//			System.out.println();
//		}
		return b;
	}

	private void printLCS(String[][] b, int[] x, int i, int j) {
		if (i == 0 || j == 0)
			return;
		else {
			if ( b[i][j].contains("CROSS")) {
				printLCS(b, x, i - 1, j - 1);
				maxNum++;
				lEDLighted[i - 1] = x[j - 1];
				leds.add(x[j - 1]);
				//System.out.print(x[j - 1] + " ");
			} else {
				if (b[i][j].contains("LEFT"))
					printLCS(b, x, i, j - 1);
				else
					printLCS(b, x, i - 1, j);
			}
		}
	}

//	private static boolean has2(int[] arr) {
//		for (int i = 0; i < arr.length - 1; i++)
//			for (int j = i + 1; j < arr.length; j++)
//				if (arr[i] == arr[j])
//					return true;
//		return false;
//	}

	private static int findMax(int[] x) {
		int maxInX = Integer.MIN_VALUE; // Initialize maxValue to the smallest possible value

		// Find the max value in the X
		for (int num : x) {
			maxInX = Math.max(maxInX, num);
		}
		return maxInX;
	}

	private static boolean hasDuplicates(int[] x) {
		int byteNum = 0;
		int bitNum = 0;

		int[] c = new int[findMax(x) / 8 + 1];

		for (int i = 0; i < x.length; i++) {
			byteNum = x[i] / 8; // find the byte to be stored in
			bitNum = 7 - x[i] % 8; // find the bit to be stored in this byte(byteNum)
			if ((c[byteNum] & (1 << bitNum)) == 0) {
				c[byteNum] = c[byteNum] | (1 << bitNum);
			} else {
				return true; // Found a duplicate, return true
			}
		}

		return false; // No duplicates found
	}

}