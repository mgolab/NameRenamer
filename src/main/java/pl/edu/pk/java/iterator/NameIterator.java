//: iterator/NameIterator.java
package pl.edu.pk.java.iterator;
import jxl.Sheet;
import jxl.Workbook;

public class NameIterator implements ExcelNameIterator{

	private int currentPosition = 0;

	Sheet sheet;

	public NameIterator(Workbook w) {
		sheet = w.getSheet(0);
	}

	public void first() {
		currentPosition = 0;
	}

	public void next() {
		if (currentPosition < (sheet.getColumns())) {
			++currentPosition;
		}
	}
	
	public void previous() {
		if (currentPosition > 0) {
			--currentPosition;
		}
	}

	public boolean isDone() {
		if (currentPosition >= (sheet.getColumns())) {
			return true;
		} else {
			return false;
		}
	}

	public String getItem(int episodeNumber) {
		return sheet.getCell(currentPosition, episodeNumber).getContents().toString();
	}

	public String currentName() {
		return sheet.getCell(currentPosition, 0).getContents().toString();
	}

	public int position(){
		return currentPosition;
	}

	@Override
	public boolean isEmpty(int episodeNumber) {
		return sheet.getCell(currentPosition, episodeNumber).getContents().toString().equals("");
	}

} ///:~

