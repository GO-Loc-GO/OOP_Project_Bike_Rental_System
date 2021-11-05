package DataStructure;

public class DENode implements Position {
	DENode previous;
	DENode next;
	private Object element;
	@Override
	public Object element() {
		return this.element;
	}
	public DENode(Object element) {
		this.element = element;
	}

}
