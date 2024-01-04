package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class info {
	private int source;
	private ImageView led;

	public info(int source, ImageView led) {
		super();
		this.source = source;
		this.led = led;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public ImageView getLed() {
		return led;
	}

	public void setLed(ImageView led) {
		this.led = led;
	}

}