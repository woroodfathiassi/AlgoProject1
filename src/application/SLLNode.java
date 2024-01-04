package application;

public class SLLNode {
	private int element;
	private SLLNode next;

	public SLLNode() {

	}

	public SLLNode(int element) {
		this.element = element;
	}

	public int getElement() {
		return element;
	}

	public void setElement(int element) {
		this.element = element;
	}

	public SLLNode getNext() {
		return next;
	}

	public void setNext(SLLNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return element + "";
	}

}
