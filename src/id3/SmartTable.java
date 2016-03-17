package id3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SmartTable extends Table {

	private int _attributeDependent;
	private List<Integer> _attributesIndependents;
	
	public SmartTable(int rows, int cols, int attributeDependent, List<Integer> attributesIndependents) {
		super(rows, cols);
		
		_attributeDependent = attributeDependent;
		_attributesIndependents = attributesIndependents;
	}
	
	public int getAttributeDependent() {
		return _attributeDependent;
	}
	
	public List<Integer> getAttributesIndependents() {
		return _attributesIndependents;
	}
	
	@Override
	public void addColumn() throws Exception {
		throw new Exception("Method not avaliable!");
	}
	
	public List<SmartTable> splitTable(Integer attributeIndependent) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getErrorPercentage(Element classValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Element getMostCommonClass() {
		Element mostCommon = null;
		Map<Element, Integer> aux = new HashMap<Element, Integer>();
		
		for (Element element : this.getColumn(_attributeDependent)) {
			if (aux.get(element) == null) {
				aux.put(element, new Integer(0));
			}
			else {
				Integer e = aux.get(element);
				e++;
			}
			
			if (mostCommon == null || aux.get(mostCommon) < aux.get(element)) {
				mostCommon = element;
			}
		}

		return mostCommon;
	}

	public int chooseAttribute() {
		
		double entropyTable = entropy();
		Map<Integer, Double> entropiesAtt = new HashMap<Integer, Double>();
		
		for (Integer att : _attributesIndependents) {
			entropiesAtt.put(att, entropy(att));
		}
		
		Integer prevAtt = null;
		for (Integer att : entropiesAtt.keySet()) {
			if (prevAtt == null) {
				prevAtt = att;
			}
			else if (entropiesAtt.get(att) > entropiesAtt.get(prevAtt)) {
				prevAtt = att;
			}
		}
		
		return prevAtt;
	}

	private Double entropy(Integer att) {
		// TODO Auto-generated method stub
		return null;
	}

	private double entropy() {
		double entropy = 0;
		List<Element> classes = getClasses();
		
		for (Element element : classes) {
			double proportion = getProportionOfClasses(element);
			entropy -= proportion * Math.log(proportion);
		}
		
		return entropy;
	}

	private double getProportionOfClasses(Element classValue) {

		int count = 0;

		for (int i = 0; i < _rows; i++) {
			if (_matrix[i][0].equals(classValue))
				count++;
		}

		return count/_rows;
	}
	
	public List<Element> getClasses() {
		List<Element> out = new LinkedList<Element>();
		
		for (Element element : this.getColumn(_attributeDependent)) {
			if (!out.contains(element)) {
				out.add(element);
			}
		}
		
		return out;
	}
}
