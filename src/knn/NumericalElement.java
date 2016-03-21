package knn;

public class NumericalElement extends Element {
	
	public NumericalElement(String value) {
		super(value);
	}

	@Override
	public double minus(Element element) {
		double out = Double.MAX_VALUE;
		
		if (element instanceof NumericalElement) {
			out = Double.parseDouble(_value) - Double.parseDouble(element.getValue());
		}
		
		return out;
	}

}
