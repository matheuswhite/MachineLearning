package knn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Evaluator {

	private ArrayList<Table> _subTablesTraining;
	private ArrayList<Table> _subTablesTest;
	private Table _dataBase;
	private Sorter _sorter;
	private int[][] _statistics;

	public Evaluator(int logId) {
		try {
			System.out.println("Opening log file...");
			LogManager.Instance().openLog("src/iris" + logId + ".log");
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void loadDataBase() throws IOException {
		System.out.println("Loading data base...");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/iris.data"));
			String line = br.readLine();
			_dataBase = new Table(1, 5);
			int row = 0;

			while (!line.equals("end")) {
				List<String> itens = Arrays.asList(line.split(","));

				for (int j = 0; j < 5; j++) {
					String item = itens.get(j);
					Element element = (j == 4) ? new CategoricalElement(item) : new NumericalElement(item) ;
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
			LogManager.Instance().commitAndWrite("File of data base not found\n");
		} catch (IOException e) {
			LogManager.Instance().commitAndWrite("IO error in line " + 59);
			e.printStackTrace();
		}
		LogManager.Instance().commitAndWrite("Data base loaded successfully\n");
	}
	
	private void initVariables() throws IOException {
		loadDataBase();
		System.out.println("Initializing test and training subsets...");
		
		_statistics = new int[10][3];
		_subTablesTraining = new ArrayList<Table>(10);
		_subTablesTest = new ArrayList<Table>(10);
		int index = (int) (_dataBase.getRowLength() * 0.1);
		
		for (int i = 0; i < 10; i++) {
			_subTablesTest.add(_dataBase.getSubTable(index * i, (index * (i+1))));
		}
		
		for (int i = 0; i < 10; i++) {
			ArrayList<Table> tempList = new ArrayList<Table>(_subTablesTest);
			tempList.remove(i);
			_subTablesTraining.add(Table.joinTables(tempList));
		}
		
		LogManager.Instance().commitAndWrite("Test and traning subsets initialized successfully\n");
	}
	
	private void evaluate(Element answer, Element expected, int subSet, String message) {

		if (answer.equals(expected)) {
			message += " | True\n";
			_statistics[subSet][0]++;
		}
		else {
			message += " | False\n";
			_statistics[subSet][1]++;
		}
		_statistics[subSet][2]++;
		LogManager.Instance().commit(message);
	}
	
	private void writeStatisticTable() throws IOException {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 3; j++) {
				LogManager.Instance().commit(_statistics[i][j] + "|");
			}
			LogManager.Instance().commit("\n");
		}
		
		LogManager.Instance().writeToLog();
	}
	
	public void runEvaluation(int k) {
		try {
			initVariables();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		_sorter = new Sorter(k, null);

		System.out.println("Testing...");
		
		for (int i = 0; i < _subTablesTraining.size(); i++) {
			_sorter.changeTrainingTable(_subTablesTest.get(i));

			for (int j = 0; j < _subTablesTest.get(i).getRowLength(); j++) {
				ArrayList<Element> row = _subTablesTest.get(i).getRow(j);
				Element result = _subTablesTest.get(i).getElement(j, _subTablesTest.get(i).getColumnsLength() - 1);
				try {
					evaluate(_sorter.getClassOf(row), result, i, "Subset:" + i + " Case:" + j);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				LogManager.Instance().writeToLog();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			writeStatisticTable();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			LogManager.Instance().closeLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done\n");
	}
}
