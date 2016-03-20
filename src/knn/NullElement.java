package knn;

public class NullElement extends Element {
	
	public NullElement() {
		super("");
	}

	@Override
	public double minus(Element element) {
		return 0;
	}

}
