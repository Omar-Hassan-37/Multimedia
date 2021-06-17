import java.util.*;
public class Compress {
	
	public ArrayList compress(String word, ArrayList<Double> newProbab, ArrayList<Character> arrayletter) {
		ArrayList <Sequence> ARcode = new ArrayList();
		Double prob;
		Double prob2;
		for(int i = 0 ; i < word.length() ; i++) {
			if(i == 0) {
				int pos = arrayletter.indexOf(word.charAt(i));
				prob = newProbab.get(pos);
				prob2 = newProbab.get(pos+1);
				Sequence s = new Sequence(word.charAt(i) , prob , prob2);
				s.setLower(prob);
				s.setUpper(prob2);
				s.setRange(prob2 , prob);
				ARcode.add(s);
			}
			else {
				
				int pos = arrayletter.indexOf(word.charAt(i));
				prob = newProbab.get(pos);
				prob2 = newProbab.get(pos+1);
				Sequence s = new Sequence(word.charAt(i) , prob , prob2);
				Sequence s2 = ARcode.get(i-1);
				s.setRange(s2.getUpper() , s2.getLower());
				s.setLower(s.calculateLower(s2.getLower(), s.getLowerprobability(), s.getRange()));
				s.setUpper( s.calculateUpper(s2.getLower(), s.getUpperProbability(), s.getRange()));
				ARcode.add(s);
			}
			
		}
		return (ARcode);
	}
}
