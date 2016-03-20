package knn;

public class CategoricalElement extends Element {

	public CategoricalElement(String value) {
		super(value);
	}

	@Override
	public double minus(Element element) {
		double out = 1;
		
		if (element instanceof CategoricalElement) {
			out = _value.equals(element.getValue()) ? 0 : 1; 
		}
		
		return out;
	}

}
