package standarhuffman;

public class Tree {
	
	Node root;
	public void addNode(Double key , Character letter) {
		Node newNode = new Node(key,letter);
		
		if(root == null) {
			root = newNode;
		}
		
		else {
			Node focusNode = root;
			Node parent;
			
			while(true) {
				parent = focusNode;
				if(key < focusNode.key) {
					focusNode = focusNode.leftChild;
					
					if(focusNode == null) {
						parent.leftChild = newNode;
						return;
					}
				}
				else {
					focusNode = focusNode.rightChild;
					
					if(focusNode == null) {
						parent.rightChild = newNode;
						return;
					}
				}
			}
		}
	}

}

class Node{
	Double key;
	Character letter;
	
	
	Node leftChild;
	Node rightChild;
	
	Node(Double k , Character l){
		this.key = k;
		this.letter = l;
	}
}