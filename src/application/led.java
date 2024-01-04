package application;

import java.util.Objects;

public class led {
	private int num;

	public led(int num) {
		super();
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return num + "";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		led ledObject = (led) o;
		return num == ledObject.num;
	}

	@Override
	public int hashCode() {
		return Objects.hash(num);
	}

}
