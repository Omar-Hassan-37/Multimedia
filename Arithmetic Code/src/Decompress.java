import java.util.ArrayList;

public class Decompress {
	public String decompress(int num , Double compressionCode , ArrayList<Double> newProbab, ArrayList<Character> arrayletter) {
		ArrayList <Sequence> ARcode = new ArrayList();
		Double prob;
		Double prob2;
		Double Rcode;
		String result = "";
		for(int i = 0 ; i < num ; i++) {
			if(i == 0) {
				for(int j = 0 ; j < newProbab.size() ; j++) {
					if((compressionCode > newProbab.get(j) && (compressionCode < newProbab.get(j+1)))) {
						result += arrayletter.get(j);
						System.out.println("Symbol = " + result);
					}
				}
				int pos = arrayletter.indexOf(result.charAt(i));
				prob = newProbab.get(pos);
				prob2 = newProbab.get(pos+1);
				Sequence s = new Sequence(result.charAt(i) , prob , prob2);
				s.setLower(prob);
				s.setUpper(prob2);
				s.setRange(prob2 , prob);
				ARcode.add(s);
				
			}else {
				Sequence s2 = ARcode.get(i-1);
				Rcode = (compressionCode - s2.getLower())/(s2.getUpper() - s2.getLower());
				System.out.println("Rcode " + Rcode);
				for(int j = 0 ; j < newProbab.size() ; j++) {
					if((Rcode > newProbab.get(j) && (Rcode < newProbab.get(j+1)))) {
						result += arrayletter.get(j);
						System.out.println("Symbol = " + result);
					}
				}
				int pos = arrayletter.indexOf(result.charAt(i));
				prob = newProbab.get(pos);
				prob2 = newProbab.get(pos+1);
				Sequence s = new Sequence(result.charAt(i) , prob , prob2);
				s.setRange(s2.getUpper() , s2.getLower());
				s.setLower(s.calculateLower(s2.getLower(), s.getLowerprobability(), s.getRange()));
				s.setUpper( s.calculateUpper(s2.getLower(), s.getUpperProbability(), s.getRange()));
				ARcode.add(s);
			}
			
		}
		return result;
	}
}
