package knn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LogManager {

	private static LogManager _instance = null;
	private FileWriter _file;
	private List<String> _content;
	
	private LogManager() {
		_content = new LinkedList<String>();
	}
	
	public static synchronized LogManager Instance() {
		if (_instance == null) {
			_instance = new LogManager();
		}
		return _instance;
	}
	
	public void openLog(String filePath) throws IOException {
		_file = new FileWriter(filePath, true);
	}
	
	public void closeLog() throws IOException {
		_file.close();
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
