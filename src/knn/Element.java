package knn;

public abstract class Element {
	
	protected String _value;
	
	public Element(String value) {
		_value = value;
	}
	
	public String getValue() {
		return _value;
	}

	public abstract double minus(Element element);
}
