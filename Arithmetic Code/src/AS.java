import java.util.*;
import java.io.*;

public class AS {

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);
		int ans;
		int numOfChar;
		char letter;
		Double prob = 0.0;
		Double prob2 = 0.0;
		String word;
		ArrayList <Character> arrayletter = new ArrayList <Character> ();
		ArrayList <Double> probability = new ArrayList <Double>();
		
		System.out.println("enter number of characters");
		numOfChar = in.nextInt();
		System.out.println("enter letter and probability");
		for(int i = 0 ; i < numOfChar ; i++) {
			letter = in.next().charAt(0);
			arrayletter.add(letter);
			prob = in.nextDouble();
			probability.add(prob);
			}
		
		ArrayList <Double> newProbab = new ArrayList <Double>();
		newProbab.add(0.0);
		for(int i = 0 ; i < probability.size() ; i++) {
			prob = probability.get(i);
			if(i == 0) {
				prob2 += prob;
				newProbab.add(prob);
			}
			else {
				prob2 += prob;
				newProbab.add(prob2);
			}
		}

			
		System.out.println("***************************************************");
		System.out.println("1.Compress");
		System.out.println("2.Decompress");
		ans = in.nextInt();
			
		if(ans == 1) {
			System.out.println("enter a word to compress");
			word = in.next();
			Compress code = new Compress();
			ArrayList <Sequence> ARcode = code.compress(word, newProbab, arrayletter);
			
			int i = 0;
			Double result = 0.0;
			for(Sequence s : ARcode) {
				System.out.println("range = "+ s.getRange());
				System.out.println("lower(" + word.charAt(i) + ")" + s.getLower());
				System.out.println("upper(" + word.charAt(i) + ")" + s.getUpper());
				System.out.println("***************************************************");
				i++;
				result = (s.getUpper()+s.getLower())/2;
			}
			Writer wr = new FileWriter("123.txt");
			System.out.println("the compression code = " + result);
			wr.write(String.valueOf(result));
			wr.close();
		}
		else if(ans == 2) {
			System.out.println("***************************************************");
			System.out.println("enter Compression Code");
			Double compressionCode = in.nextDouble();
			System.out.println("enter number of letters");
			int num = in.nextInt();
			Decompress decompress = new Decompress();
			String dcCode = decompress.decompress(num, compressionCode, newProbab, arrayletter);
			System.out.println("the decompressed code = " + dcCode);
			Writer wr2 = new FileWriter("1234.txt");
			wr2.write(dcCode);
			wr2.close();
		}
	}
		
	

}
