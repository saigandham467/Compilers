

import java.util.ArrayList;

public class Newnode {
	String value;
	String category;
	String type;
	Newnode left;
	Newnode right;
	ArrayList<Newnode> lists;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Newnode getLeft() {
		return left;
	}
	public void setLeft(Newnode left) {
		this.left = left;
	}
	public Newnode getRight() {
		return right;
	}
	public void setRight(Newnode right) {
		this.right = right;
	}

}
