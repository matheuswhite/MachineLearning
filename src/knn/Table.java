package knn;

public class Table {

	protected Element[][] _matrix;
	protected int _rows;
	protected int _cols;
	
	public Table(int rows, int cols) {
		_rows = rows;
		_cols = cols;
		
		_matrix = new Element[rows][cols];
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
		
		return _matrix[row][col];
	}
	
	public void putElement(int row, int col, Element element) {
		if (_rows <= row || _cols <= col)
			throw new IndexOutOfBoundsException();
		
		_matrix[row][col] = element;
	}
	
	public void putRow(int row, Element[] elements) {
		if (_rows <= row)
			throw new IndexOutOfBoundsException();
		
		_matrix[row] = elements;
	}
	
	public void addRow() {
		Element[][] elements = _matrix;
		_matrix = new Element[_rows++][_cols];
		_matrix = elements;
	}
	
	public Element[] getRow(int row) {
		if (_rows <= row)
			throw new IndexOutOfBoundsException();
		
		return _matrix[row];
	}
	
	public Element[] getColumn(int col) {
		if (_cols <= col)
			throw new IndexOutOfBoundsException();
		Element[] out = new Element[_rows];
		
		for (int i = 0; i < _rows; i++) {
			out[i] = _matrix[i][col];
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
	
	public static Table joinTables(Table...tables) {
		int sumRows = 0;
		
		for (int i = 0; i < tables.length; i++) {
			sumRows += tables[i].getRowLength();
		}
		
		Table out = new Table(sumRows, tables[0].getColumnsLength());
		
		//fazer
		
		return out;
	}
}
