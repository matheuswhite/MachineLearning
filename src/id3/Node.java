package id3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

	private Element _classValue;
	private double _errorPercentage;
	private boolean _leaf;
	private int _attribute;
	private Map<String, Node> _childs;
	private Map<Table, String> _attributeValues;
	
	public Node() {
		_childs = new HashMap<String, Node>();
		_attributeValues = new HashMap<Table, String>();
	}
	
	public boolean isLeaf() {
		return _leaf;
	}
	
	public Element getClassValue() throws Exception {
		if (!_leaf)
			throw new Exception("This node is not leaf!");
			
		return _classValue;
	}
	
	public double getErrorPercentage() throws Exception {
		if (!_leaf)
			throw new Exception("This node is not leaf!");
		
		return _errorPercentage;
	}
	
	public int getAttribute() throws Exception {
		if (_leaf)
			throw new Exception("This node is a leaf!");
		
		return _attribute;
	}
	
	public Node genNode(SmartTable table) throws Exception {
		
		if (table.isEmpty())
			throw new Exception("Table is empty!");
		
		List<Element> classes = table.getClasses();
		List<Integer> attributesIndependents = table.getAttributesIndependents();
		
		if (classes.size() > 1) {
			if (table.getAttributesIndependents().size() > 0) {
				
				_attribute = table.chooseAttribute();
				attributesIndependents.remove(_attribute);
				
				List<SmartTable> subTables = table.splitTable(_attribute);
				
				for (SmartTable st : subTables) {
					Node child = genNode(st);
					_childs.put(_attributeValues.get(st), child);
				}
			}
			else {
				_classValue = table.getMostCommonClass();
				_errorPercentage = table.getErrorPercentage(_classValue);
				_leaf = true;
				
				return this;
			}
		}
		else {
			_classValue = classes.get(0);
			_errorPercentage = 0;
			_leaf = true;
			
			return this;
		}
		
		_leaf = false;
		return this;
	}
}
