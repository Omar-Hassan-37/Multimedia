import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;



public class Operations {
	HashMap<String, Category>categoryTable;
	HashMap<String, String>newCategory;
	String Numbers;
	ArrayList<String>tempdescriptors;
	ArrayList<String>descriptors;
	ArrayList<String>Inventory;
	HashMap<String , Double>probabilities;
	HashMap<String , String>huffmanTable;
	HashMap<String, String>newHuffmanTable;
	public Operations(HashMap<String, Category> categoryTable, String numbers) {
		this.categoryTable = categoryTable;
		Numbers = numbers;
		descriptors = new ArrayList<String>();
	}
	private void runLength_Encoding(){
		String[] arrayNumbers = Numbers.split(",");
		for (String s : arrayNumbers) {
            s = s.trim();
        }
		int i = 0 , skip = 0;
		Inventory = new ArrayList<String>();
		while(true) {
			if(arrayNumbers[i].equals("0")) {
				skip++;
			}
			else if(arrayNumbers[i].equals("EOB")) {
				descriptors.add("EOB");
				Inventory.add(arrayNumbers[i]);
				break;
			}
			else {
				StringBuilder tmp = new StringBuilder("");
				tmp.append(skip);
				tmp.append("/");
				tmp.append(categoryTable.get(arrayNumbers[i]).categoryNumber);
				descriptors.add(tmp.toString());
				Inventory.add(arrayNumbers[i]);
				skip = 0;
			}
			i++;
		}
	}
	private void makeProbabilities() {
		probabilities = new HashMap<String, Double>();
		tempdescriptors = new ArrayList<String>();
		for(String s:descriptors) {
			tempdescriptors.add(s);
		}
		int size = descriptors.size();
		for(int i = 0;i<descriptors.size();i++) {
			String temp = descriptors.get(i);
			double freq = 1;
			for(int j = i+1;j<descriptors.size();j++) {
				if(descriptors.get(j).equals(temp)) {
					freq++;
					descriptors.remove(j);
				}
			}
			Double temp2 = freq;
			probabilities.put(temp , freq/size);
		}
	}
	private void applyHuffman() {
		huffmanTable = new HashMap<String, String>();
		ArrayList<HuffmanNode>list = new ArrayList<HuffmanNode>();
		for(String s:probabilities.keySet()) {
			HuffmanNode node = new HuffmanNode();
			node.descriptor = s;
			node.probability = probabilities.get(s);
			node.left = null;
			node.right = null;
			list.add(node);
			java.util.Collections.sort(list);
		}
		HuffmanNode root = null;
		
		  while(list.size()>1) { 
			  HuffmanNode n1 = list.get(0);
			  list.remove(0);
			  HuffmanNode n2 = list.get(0);
			  list.remove(0);
			  HuffmanNode newNode = new HuffmanNode();
				  newNode.left = n1;
				  newNode.right = n2;
			  
			  BigDecimal bd = new BigDecimal(String.format("%.1f", n1.probability+n2.probability));
			  newNode.probability =  bd.doubleValue();
			  newNode.descriptor = n1.descriptor+","+n2.descriptor;
			  list.add(newNode);
			  java.util.Collections.sort(list);
			  }
		  root = list.get(0);
		  this.addCode(root, "");	
		 
	}
	private void addCode(HuffmanNode node , String code) {
		if(node.isLeaf()) {
			huffmanTable.put(node.descriptor, code);
		}
		else
		{
			addCode(node.right, code+"0");
			if(node.right.isLeaf()) {
				huffmanTable.put(node.right.descriptor, code+"0");
			}
			addCode(node.left, code+"1");
			if(node.left.isLeaf()) {
				huffmanTable.put(node.left.descriptor, code+"1");
			}
		}
	}
	public void Compress() {
		StringBuilder compressed = new StringBuilder("");
		this.runLength_Encoding();
		this.makeProbabilities();
		this.applyHuffman();
		int counter = 0;
		for(String desc:tempdescriptors) {
			String number = Inventory.get(counter);
			if(!desc.equals("EOB"))
			{
				compressed.append(huffmanTable.get(desc));
				compressed.append(categoryTable.get(number).binaryCode);
			}
			else
			{
				compressed.append(huffmanTable.get(desc));
			}
			counter++;
		}
		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter("compressed.txt"));
			wr.write(compressed.toString());
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(compressed.toString());
	}
	public void Decompress() {
		newCategory = new HashMap<String, String>();
		for(String s:categoryTable.keySet()) {
			newCategory.put(categoryTable.get(s).binaryCode ,s);
		}
		
		newHuffmanTable =new HashMap<String,String>();
		for(String s:huffmanTable.keySet()) {
			newHuffmanTable.put(huffmanTable.get(s), s);
		}
		
		File file = new File("compressed.txt");
		BufferedReader br;
		String code = null;
		try {
			 br = new BufferedReader(new FileReader(file));
			 code = br.readLine();
			 String temp = "";
			 StringBuilder originalText = new StringBuilder("");
			 for(int i =0;i<code.length();i++) {
				 temp+=code.charAt(i);
				 if(newHuffmanTable.containsKey(temp)) {
					 String[] splitArr = newHuffmanTable.get(temp).split("/");
					 if(newHuffmanTable.get(temp).equals("EOB")) {
						 originalText.append("EOB");
					 }
					 else
					 {
						 String repeatedZeroes = new String(new char[Integer.parseInt(splitArr[0])]).replace("\0", "0,");
						 originalText.append(repeatedZeroes);
						 int catNumber = Integer.parseInt(splitArr[1]);
						 i++;
						 String addBits = code.substring(i , i+catNumber);
						 i+=catNumber;
						 originalText.append(newCategory.get(addBits) + ",");
						 originalText.append(" ");
						 temp = "";
						 i--;
					 }
					 
				 }
				 
			 }
			 System.out.println(originalText.toString());
			 BufferedWriter wr;
				
					wr = new BufferedWriter(new FileWriter("Decompressed.txt"));
					wr.write(originalText.toString());
					wr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
