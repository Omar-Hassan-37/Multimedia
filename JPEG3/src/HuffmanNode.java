
public class HuffmanNode implements Comparable<HuffmanNode> {
	Double probability; 
	String descriptor; 
	String code = "";
	HuffmanNode left; 
	HuffmanNode right;
	public boolean isLeaf() {
		boolean leaf = false;
		if(left==null && right==null) {
			leaf = true;
		}
		return leaf;
			
	}
	@Override
	public int compareTo(HuffmanNode n1) {
		if(this.probability<n1.probability) 
		{
			return -1;
		}
		else if(this.probability>n1.probability)
		{
			return 1;
		}
		else
		{
			if(this.descriptor.length()<n1.descriptor.length()) {
				return 1;
			}
			else if(this.descriptor.length()>n1.descriptor.length()) {
				return -1;
			}
		else {
				String [] temp1 = this.descriptor.split("/");
				String [] temp2 = n1.descriptor.split("/");
				if(Integer.parseInt(temp1[1]) <Integer.parseInt(temp2[1])) {
					return -1;
				}
				else
					return 1;
			}
		}
	}
}
