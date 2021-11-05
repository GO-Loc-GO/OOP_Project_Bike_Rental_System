package DataStructure;

public class DoubleLinkedList implements List {
	private DENode first=null;
	private DENode last=null;
	private int size=0;
	@Override
	public Position first() {
		return this.first;
	}

	@Override
	public Position last() {
		return this.last;
	}

	@Override
	public Position before(Position p) {
		return ((DENode)p).previous;
	}

	@Override
	public Position after(Position p) {
		return ((DENode)p).next;
	}

	@Override
	public Position insertBefore(Position next_node, Object new_d) {
		if (next_node == null) 
		 {
			System.out.println("The given next node cannot be null");
			return null;
		 }
		
		DENode new_node = new DENode(new_d);
		
		new_node.next = ((DENode)next_node);
		new_node.previous = ((DENode)next_node).previous;
		
		if(((DENode)next_node)==first) {
			((DENode)next_node).previous = new_node;
			first=new_node;
		}else {
			((DENode)next_node).previous.next = new_node;
			((DENode)next_node).previous = new_node;
		}
		
		size++;
		
		return new_node;
	}

	@Override
	public Position insertAfter(Position prev_node, Object new_d) {
		if (prev_node == null) 
		 { 
			System.out.println("The given previous node cannot be null"); 
			return null;
		 }
		
		 DENode new_node = new DENode(new_d);
		 
		 new_node.next = ((DENode)prev_node).next;
		 new_node.previous = ((DENode)prev_node);
		 
		 if(((DENode)prev_node)==last) {
				 ((DENode)prev_node).next = new_node;
				 last = new_node;
		 }else {
			 ((DENode)prev_node).next.previous = new_node;
			 ((DENode)prev_node).next = new_node;
		 }
		 
		 size++;
		 
		return new_node;
	}

	@Override
	public Position insertFirst(Object d) {
		DENode N = new DENode(d);
		if(isEmpty()) {
			first = N;
			last = first;
			first.next = null;
			first.previous = null;
			last.next = null;
			last.previous = null;
		}else {
			first.previous = N;
			N.next = first;
			N.previous = null;
			first = N;
		}
		size++;
		return first;
	}

	@Override
	public Position insertLast(Object d) {
		DENode N = new DENode(d);
		if(isEmpty()) {
			first = N;
			last = first;
			first.next = null;
			first.previous = null;
			last.next = null;
			last.previous = null;
		}else {
			last.next = N;
			N.previous=last;
			N.next=null;
			last = N;
		}
		size++;
		return last;
	}

	@Override
	public Object remove(Position p) {
		Object d = p.element();
		if(size()==1) {
			first = null;
			last = null;
			return d;
		}
		
		if(p==first) {
			first=((DENode)p).next;
			((DENode)p).next.previous = null;
		}else if(p==last) {
			last=((DENode)p).previous;
			((DENode)p).previous.next = null;
		}else {
			DENode ap = (DENode) after(p);
			DENode bp = (DENode) before(p);
			ap.previous = bp;
			bp.next = ap;
			((DENode)p).next = null;
			((DENode)p).previous = null;
		}
		size--;
		return d;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}
	public void printList() { 
		DENode top = first; 
		while (top != null) { 
			System.out.print(top.element()+" "); 
			top = top.next; 
		} 
	}
	
	public void ForwardTraverseFrom(DENode node) {
		System.out.println("Traversal in forward Direction"); 
		while (node != null) { 
			System.out.print(node.element() + " "); 
		 	node = node.next; 
		} 

    }
	
	public void BackwardTraverseFrom(DENode node) {
		DENode last = node; 
		while (last != null) { 
		 	last = last.next; 
		}
		System.out.println("Traversal in reverse direction"); 
		while (last != null) { 
			System.out.print(last.element() + " "); 
			last = last.previous;
		}
	}

}
