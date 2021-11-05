package DataStructure;

public interface List {
	public Position first();//Output: the position at the front of the list
	public Position last();//Output: The last position in the list
	public Position before(Position next_node) ;//Output: The position before p
	public Position after(Position prev_node) ;//Output: the position after p
	public Position insertBefore( Position next_node, Object new_d) ;//insert a Node storing data d before p, then output: The position it was inserted in
	public Position insertAfter( Position prev_node, Object new_d) ;//Input: position&value Output: position inserted
	public Position insertFirst(Object new_d) ;//insert a a Node storing data d at the front,Output: The position it was inserted in
	public Position insertLast(Object new_d) ;//insert a a Node storing data d at the end,Output: The position it was inserted in
	public Object remove(Position p);//Input: the position to be removed Output: the value that was removed
	public int size() ;//return the size
	public boolean isEmpty() ;//tells if the list is empty
}
