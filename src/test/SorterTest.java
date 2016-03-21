package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import knn.CategoricalElement;
import knn.Element;
import knn.NumericalElement;
import knn.Sorter;
import knn.Table;

public class SorterTest {
	
	private static List<Table> _subTablesTraining;
	private static List<Table> _subTablesTest;
	private static Table _dataBase;
	private static Sorter _sorter;
	
	private static void loadDataBase() {

		try {
			BufferedReader br = new BufferedReader(new FileReader("iris.data"));
			String line = br.readLine();
			_dataBase = new Table(1, 5);
			int row = 0;

			while (!line.equals("end")) {
				List<String> itens = Arrays.asList(line.split(","));

				for (int j = 0; j < 5; j++) {
					String item = itens.get(j);
					Element element = (j == 4) ? new CategoricalElement(item) : new NumericalElement(Integer.parseInt(item)) ;
					if (_dataBase.getRowLength() - 1 < row) {
						_dataBase.addRow();
					}
					_dataBase.putElement(row, j, element);
				}

				line = br.readLine();
				row++;
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void initVariables() {
		loadDataBase();
		
		_subTablesTraining = new ArrayList<Table>(10);
		_subTablesTest = new ArrayList<Table>(10);
		int index = (int) (_dataBase.getRowLength() * 0.1);
		
		for (int i = 0; i < 10; i++) {
			_subTablesTest.add(_dataBase.getSubTable(index * i, index * (i+1)));
		}
		
		for (int i = 0; i < 10; i++) {
			List<Table> tempList = _subTablesTest;
			tempList.remove(i);
			_subTablesTraining.add(Table.joinTables((Table[]) tempList.toArray()));
		}
	}
	
	public static void main(String[] args) {
		initVariables();
		
		_sorter = new Sorter(1, null);
		
		for (int i = 0; i < _subTablesTraining.size(); i++) {
			_sorter.changeTrainingTable(_subTablesTest.get(i));
			
			for (int j = 0; j < _subTablesTest.get(i).getRowLength(); j++) {
				Element[] row = _subTablesTest.get(i).getRow(j);
				Element result = _subTablesTest.get(i).getElement(j, _subTablesTest.get(i).getColumnsLength() - 1);
				try {
					Assert.assertEquals(_sorter.getClassOf(row), result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}