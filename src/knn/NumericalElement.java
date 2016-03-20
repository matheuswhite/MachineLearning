package knn;

public class NumericalElement extends Element {
	
	public NumericalElement(double value) {
		super(Double.toString(value));
	}

	@Override
	public double minus(Element element) {
		double out = Double.MAX_VALUE;
		
		if (element instanceof NumericalElement) {
			out = Integer.parseInt(_value) - Integer.parseInt(element.getValue());
		}
		
		return out;
	}

}
