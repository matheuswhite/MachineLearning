package id3;

public class Element {
	
	private boolean _categorical;
	private String _value;
	
	public Element(String value, boolean categorical) {
		_categorical = categorical;
		_value = value;
	}
	
	public boolean isCategorical() {
		return _categorical;
	}
	
	public String getValue() {
		return _value;
	}
}
