import java.awt.image.BufferedImage;
import java.util.ArrayList;




public class VectorQuantizer {
	private BufferedImage img = null;
	private ArrayList<Double[][]> averages = null;
	public VectorQuantizer(BufferedImage img) {
		this.img = img;
	}
	
	private class QuantizedMatrix{
		private Integer[][][][] mat;
		private Integer error;
		public QuantizedMatrix(Integer[][][][] mat, Integer error) {
			this.mat = mat;
			this.error = error;
		}
		
		public Integer[][][][] getMat() {
			return mat;
		}
		public void setMat(Integer[][][][] mat) {
			this.mat = mat;
		}
		public Integer getError() {
			return error;
		}
		public void setError(Integer error) {
			this.error = error;
		}
	}
	
	private Integer[][][][] splitImage(int size){
		
		Integer[][][][] data;
		data = new Integer[img.getHeight()/size][img.getWidth()/size][size][size];
		
		for(int i = 0; i < img.getHeight()/size; i++) {
			for(int j = 0; j < img.getWidth()/size; j++) {
				for(int k = 0; k < size; k++) {
					for(int l = 0; l < size; l++) {
						data[i][j][k][l] = (img.getRGB((j*size) + k, (i*size)+l) & 0x000000ff);
					}
				}
			}
		}
		System.out.println(Integer.toHexString((img.getRGB(30, 30))));
		return data;
	}
	
	private int getDiff(Double[][] mean, Integer[][] data) {
		int distance = 0;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				distance += (data[i][j] - mean[i][j]);
			}
		}
		return distance;
	}
	
	private int byteToRGB(int b) {
		b = (b) | (b<<8) | (b<<16) | (b<<24);
		b = b | 0xff000000;
		
		return b;
	}
	
	private int getError(Integer[][] mat, int cat) {
		int error = 0;
		
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat[i].length; j++) {
				error += averages.get(cat)[i][j] - mat[i][j] ;
			}
		}
		
		return error;
		
	}
	
	private QuantizedMatrix getQuantizedMatrix(int bits, int size, Integer[][][][] data){
		Integer[][][][] rgbMatrix = new Integer[img.getHeight()/size][img.getWidth()/size][size][size];
		Integer error = 0;
		
		 
		int[][] categories = new int[img.getHeight()/size][img.getWidth()/size];
		averages = new ArrayList<Double[][]>();
		ArrayList<ArrayList<Integer[][]>> vects = new ArrayList<ArrayList<Integer[][]>>();


		
		
		
		for(int lr = 0; lr <= Math.pow(2, bits); lr++) {
			
			for(int i = 0; i < categories.length; i++) {
				for(int j = 0; j < categories[i].length; j++) {
					categories[i][j] = 0;
				}
			}
			
			//get averages
			averages.clear();
			for(int i = 0; i < vects.size(); i++) {
				
				Double[][] temp = new Double[size][size];
				for(int j = 0; j < temp.length; j++) {
					for(int k = 0; k < temp[j].length; k++) {
						temp[j][k] = 0.0;
					}
				}
				
				
				
				for(int j = 0; j < vects.get(i).size(); j++) {
					for(int k = 0; k < size; k++) {
						for(int l = 0; l < size; l++) {
							
							temp[k][l] += vects.get(i).get(j)[k][l];
						}
					}
				}
				
				for(int m = 0; m < size; m++) {
					for(int n = 0; n < size; n++) {
						temp[m][n] /= vects.get(i).size();
					}
				}
				averages.add(temp);
			}
			
			
			//Find which is less than average and which is higher
			for(int i = 0; i < averages.size(); i++) {
				for(int j = 0; j < data.length; j++) {
					for(int k = 0; k < data[j].length; k++) {
						int diff = getDiff(averages.get(i), data[j][k]);
						
						if(diff > 0) {
							categories[j][k]++;
						}
						
					}
				}
			}
			
			vects.clear();
			
			for(int i = 0; i <= averages.size(); i++) {
				vects.add(new ArrayList<Integer[][]>());
			}
			
				
			for(int j = 0; j < data.length; j++) {
				for(int k = 0; k < data[j].length; k++) {
					vects.get(categories[j][k]).add(data[j][k]);
				}
			}
		}
		
		
		//write to image
		for(int i = 0; i < img.getHeight()/size; i++) {
			for(int j = 0; j < img.getWidth()/size; j++) {
				int cat = (categories[i][j] == 0)? categories[i][j] : categories[i][j]-1;
				for(int k = 0; k < size; k++) {
					for(int l = 0; l < size; l++) {
						
						rgbMatrix[i][j][k][l] = averages.get(cat)[k][l].intValue();
					}
				}
				error += getError(data[i][j], cat);
			}
		}
		error = (int) (Math.pow(error, 2)/ (img.getHeight())*(img.getWidth()));
		QuantizedMatrix q = new QuantizedMatrix(rgbMatrix, error);
		
		return q;
	}
	
	public BufferedImage quantize(int bits, int size) {
		
		Integer[][][][] data = splitImage(size);
		
		QuantizedMatrix quantizedMatrix = getQuantizedMatrix(bits, size, data);
		

		
		for(int i = 0; i < img.getHeight()/size; i++) {
			for(int j = 0; j < img.getWidth()/size; j++) {
				for(int k = 0; k < size; k++) {
					for(int l = 0; l < size; l++) {
						

						img.setRGB((j*size) +k, (i*size) +l, byteToRGB(quantizedMatrix.getMat()[i][j][k][l]));
					}
				}
			}
		}
		
		System.out.println(averages.size());
		return img;
	}

}
