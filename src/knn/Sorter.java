package knn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sorter {

	private Table _trainingTable;
	private int _k;
	
	public Sorter(int k, Table trainingTable) {
		_trainingTable = trainingTable;
		_k = k;
	}
	
	public Element getClassOf(Element[] row) throws Exception {
		if (row.length < _trainingTable.getColumnsLength() - 1)
			throw new Exception("This row has minus columns that training table!");
		
		//Create and initialize variables
		List<Element> listOfClasses = new ArrayList<Element>(_k);
		List<Double> listOfDistances = new ArrayList<Double>(_k);
		
		for (int i = 0; i < _k; i++) {
			listOfDistances.add(Double.MAX_VALUE);
			listOfClasses.add(new NullElement());
		}
		
		//get the k nearest neighbors
		for (int i = 0; i < _trainingTable.getRowLength(); i++) {
			double rowDistance = distance(row, _trainingTable.getRow(i));
			Element classOfRow = _trainingTable.getElement(i, _trainingTable.getColumnsLength() - 1);

			for (int j = 0; j < listOfDistances.size(); j++) {
				if (rowDistance < listOfDistances.get(j)) {
					listOfDistances.add(j, rowDistance);
					listOfClasses.add(j, classOfRow);
					break;
				}
			}
		}
		
		//return the class most common between the neighbors
		return getMostCommonElement(listOfClasses);
	}
	
	private Element getMostCommonElement(List<Element> elements) {
		Element mostCommon = null;
		Map<Element, Integer> aux = new HashMap<Element, Integer>();
		
		for (Element element : elements) {
			if (!(element instanceof NullElement)) {
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
		}

		return mostCommon;
	}
	
	private double distance(Element[] row1, Element[] row2) {
		double sum = 0;
		
		for (int i = 0; i < row1.length; i++) {
			double result = row1[i].minus(row2[i]);
			sum += (result * result);
		}
		
		return Math.sqrt(sum);
	}

	public Table getTrainingTable() {
		return _trainingTable;
	}
	
	public int getK() {
		return _k;
	}
}
