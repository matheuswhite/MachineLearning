package knn;

import java.util.ArrayList;

public class Table {

	protected ArrayList<ArrayList<Element> > _matrix;
	protected int _rows;
	protected int _cols;
	
	public Table(int rows, int cols) {
		_rows = rows;
		_cols = cols;
		
		_matrix = new ArrayList<ArrayList<Element> >();
		for (int i = 0; i < _rows; i++) {
			_matrix.add(new ArrayList<Element>());
			for (int j = 0; j < _cols; j++) {
				_matrix.get(i).add(new NullElement());
			}
		}
	}
	
	public boolean isEmpty() {
		return getRowLength() == 0 && getColumnsLength() == 0;
	}

	public int getRowLength() {
		return _rows;
	}
	
	public int getColumnsLength() {
		return _cols;
	}
	
	public Element getElement(int row, int col) {
		if (_rows <= row || _cols <= col)
			throw new IndexOutOfBoundsException();
		
		return _matrix.get(row).get(col);
	}
	
	public void putElement(int row, int col, Element element) {
		if (_rows <= row || _cols <= col) {
			throw new IndexOutOfBoundsException("Put Element Exception");
		}
		
		_matrix.get(row).add(col, element);
	}
	
	public void putRow(int row, ArrayList<Element> elements) {
		if (_rows <= row)
			throw new IndexOutOfBoundsException();
		
		_matrix.add(row, elements);
	}
	
	public void addRow() {
		_matrix.add(new ArrayList<Element>());
		
		for (int i = 0; i < _cols; i++) {
			_matrix.get(_rows).add(new NullElement());
		}
		_rows++;
	}
	
	public ArrayList<Element> getRow(int row) {
		if (_rows <= row)
			throw new IndexOutOfBoundsException();
		
		return _matrix.get(row);
	}
	
	public ArrayList<Element> getColumn(int col) {
		if (_cols <= col)
			throw new IndexOutOfBoundsException();
		
		ArrayList<Element> out = new ArrayList<Element>();
		
		for (int i = 0; i < _rows; i++) {
			out.add(_matrix.get(i).get(col));
		}
		
		return out;
	}

	public Table getSubTable(int initRow, int finalRow) {
		Table subtable = new Table(finalRow - initRow, _cols);
		
		for (int i = 0; i < finalRow - initRow; i++) {
			subtable.putRow(i, getRow(initRow + i));
		}
		
		return subtable;
	}
	
	public static Table joinTables(ArrayList<Table> tables) {
		int sumRows = 0;
		
		for (int i = 0; i < tables.size(); i++) {
			sumRows += tables.get(i).getRowLength();
		}
		
		Table out = new Table(sumRows, tables.get(0).getColumnsLength());
		
		int i = 0;
		for (int k = 0; k < tables.size(); k++) {
			int j = 0;
			
			while (i < sumRows && j < tables.get(k).getRowLength()) {
				out.putRow(i, tables.get(k).getRow(j));
				
				i++;
				j++;
			}
		}
		
		return out;
	}
}
