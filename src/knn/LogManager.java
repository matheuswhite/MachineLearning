package knn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LogManager {

	private static LogManager _instance = null;
	private FileWriter _file;
	private List<String> _content;
	private boolean _isOpen;
	
	private LogManager() {
		_content = new LinkedList<String>();
		_isOpen = false;
	}
	
	public static synchronized LogManager Instance() {
		if (_instance == null) {
			_instance = new LogManager();
		}
		return _instance;
	}
	
	public void openLog(String filePath) throws IOException {
		if (_isOpen) {
			closeLog();
		}
		_file = new FileWriter(filePath, false);
		_isOpen = true;
	}
	
	public void closeLog() throws IOException {
		_file.close();
		_isOpen = false;
	}
	
	public void commitAndWrite(String line) throws IOException {
		commit(line);
		writeToLog();
	}
	
	public void writeToLog() throws IOException {
		for (String string : _content) {
			_file.write(string);
		}
		_content.clear();
	}
	
	public void commit(String line) {
		_content.add(line);
	}
}
