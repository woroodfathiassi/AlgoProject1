package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleFloatProperty;

public class SLL {
	private SLLNode first, last;
	int count = 0;

	public SLL() {
		
	}

	public SLL(SLLNode first, SLLNode last) {
		this.first = first;
		this.last = last;
	}

	public void setFirst(SLLNode first) {
		this.first = first;
	}

	public void setLast(SLLNode last) {
		this.last = last;
	}

	public Object getFirst() {
		return first;
	}

	public Object getLast() {
		return last;
	}

	public void addFirst(int element) {
		if (count == 0)
			first = last = new SLLNode(element);
		else {
			SLLNode temp = new SLLNode(element);
			temp.setNext(first);
			first = temp;
		}
		count++;
	}

	public void addLast(int element) {
		if (count == 0)
			first = last = new SLLNode(element);
		else {
			SLLNode temp = new SLLNode(element);
			last.setNext(temp);
			last = temp;
		}
		count++;
	}

	public void add(int element, int index) {
		if (count == 0)
			addFirst(element);
		else if (index == 0)
			addFirst(element);
		else if (index == count)
			addLast(element);
		else if (index < 0 || count <= index)
			addLast(element);
		else {
			SLLNode temp = new SLLNode(element);
			SLLNode current = first;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			temp.setNext(current.getNext());
			current.setNext(temp);
		}
		count++;
	}

	public void addSorted(int element)  {
		if (count == 0) {
			addFirst(element);
			return;
		}
		SLLNode newnode1 = new SLLNode(element);
		SLLNode prev = first, current = first;
		while (current != null && (int)element < (int)element ) {
			prev = current;
			current = current.getNext();
		}

		if (prev.equals(current)) {
			newnode1.setNext(first);
			first = newnode1;
		} else {
			newnode1.setNext(current);
			prev.setNext(newnode1);
		}

		count++;

	}
	
	public void clear() {
		first = last = null;
	}

	public boolean removeFirst() {
		if (count == 0)
			return false;
		else if (count == 1)
			first = last = null;
		else {
			SLLNode temp = first;
			first = first.getNext();
			temp.setNext(null);
		}
		count--;
		return true;
	}

	public boolean removeLast() {
		if (count == 0)
			return false;
		else if (count == 1)
			first = last = null;
		else {
			SLLNode current = first;
			while (current.getNext().getNext() != null)
				current = current.getNext();
			last = current;
			last.setNext(null);

		}
		count--;
		return true;
	}

	public boolean remove(int index) {
		SLLNode ptr = null, prev = null;
		if (index < 0 || index >= count)
			return false;
		else if (index == 0)
			return removeFirst();
		else if (index == count - 1)
			return removeLast();
		else {
			ptr = first;
			for (int i = 1; i <= index; i++) {
				prev = ptr;
				ptr = ptr.getNext();
			}
			prev.setNext(ptr.getNext());
			ptr.setNext(null);
			count--;
			return true;
		}
	}

	public boolean remove(Object element) {
		SLLNode prev = null, current = null;
		if (count != 0) {
			if (count == 1)
				removeFirst();
			if (element.equals(first.getElement()))
				return removeFirst();
			else if (element.equals(last.getElement()))
				return removeLast();
			else {
				current = first;
				for (int i = 0; i < count; i++) {
					if (element.equals(current.getElement()))
						return remove(i);
					current = current.getNext();
				}
			}
		}
		System.out.println("i am in remove object");
		return false;
	}

	public void printList() {
		SLLNode current = first;
		if (count == 0)
			return;
		while (current != null) {
			System.out.println(current.getElement());
			current = current.getNext();
		}
	}



	public Object get(int index) {
		SLLNode temp = first;
		if (index >= count || index < 0)
			return null;
		int i = 0;
		while (temp != null && i < index) {
			temp = temp.getNext();
			i++;
		}
		return temp.getElement();
	}

	public int indexOf(int s) {
		SLLNode current = first;
		int i = 0;
		if (count == 0)
			return -1;
		else if (count == 1)
			return 0;
		else {
			while (i < count && current != null) {
				if (current.getElement() == s) {
					break;
				}
				current = current.getNext();
				i++;

			}
			return i;

		}
	}

	public int getSize() {
		return count;
	}
	
	public boolean isExisting(Object element) {

		SLLNode current = first;
		for (int i = 0; i < count; i++) {
			if (((int)current.getElement()) == (int)element)
				return true;
			current = current.getNext();
		}
		return false;
	}

}
