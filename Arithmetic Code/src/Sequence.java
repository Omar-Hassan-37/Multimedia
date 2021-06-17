
public class Sequence {
	
	private char letter;
	private Double lowerProbability;
	private Double upperProbability;
	private Double lower;
	private Double upper;
	private Double range;
	
	Sequence(char l , Double lp , Double up){
		this.letter = l;
		this.lowerProbability = lp;
		this.upperProbability = up;
	}
	
	public void setLowerProbability(Double lowerProbability) {
		this.lowerProbability = lowerProbability;
	}
	
	public Double getLowerprobability() {
		return lowerProbability;
	}
	
	public void setUpperProbability(Double upperProbability) {
		this.upperProbability = upperProbability;
	}
	
	public Double getUpperProbability() {
		return upperProbability;
	}
	
	public void setLower(Double lower) {
		this.lower = lower;
	}
	
	public Double getLower() {
		return lower;
	}
	
	public void setUpper(Double upper) {
		this.upper = upper;
	}
	
	public Double getUpper() {
		return upper;
	}
	
	public void setRange(Double lower , Double upper) {
		range = upper - lower;
		range = range * -1;
	}
	
	public Double getRange() {
		return range;
	}
	
	public Double calculateLower(Double lower , Double lowerProbability , Double range) {
		lower = lower + range * lowerProbability;
		return lower;
	}
	
	public Double calculateUpper(Double lower  , Double upperProbability , Double range) {
		upper = lower + range * upperProbability;
		return upper;
	}
	
}
