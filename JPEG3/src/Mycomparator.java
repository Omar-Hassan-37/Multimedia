import java.util.Comparator;

public class Mycomparator implements Comparator<HuffmanNode>{
	public int compare(HuffmanNode x, HuffmanNode y) { 
		if(x.probability < y.probability)
			return -1;
		if(x.probability>y.probability)
			return 1;
		return 0;
			
	}

}
