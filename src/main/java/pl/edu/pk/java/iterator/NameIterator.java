//: iterator/NameIterator.java
package pl.edu.pk.java.iterator;
import jxl.Sheet;
import jxl.Workbook;
/** 
 * Implementacja wzorca iterator. 
 * Pozwala na przeszukiwanie pierwsego wiersza arkusza z Excela, a także zwrócenie jego zawartości w postaci Stringa
 * @author Michał Gołąb
 * @version 1.0
 * @see ExcelNameIterator
 */

public class NameIterator implements ExcelNameIterator{

	private int currentPosition = 0;

	Sheet sheet;

	/**
	 * Odczytuje pierwszy z arkuszy z pliku Excela
	 * @param w Wczytany z pliku zbiór arkuszy
	 */
	public NameIterator(Workbook w) {
		sheet = w.getSheet(0);
	}

	/**
	 * Aktualna kolumna jest zamianiana na pierwszą
	 */
	public void first() {
		currentPosition = 0;
	}

	/**
	 * Zmienia aktualną kolumnę na następną
	 */
	public void next() {
		if (currentPosition < (sheet.getColumns())) {
			++currentPosition;
		}
	}

	/**
	 * Zwraca true jeżeli przeszliśmy po wszystkich kolumnach
	 */
	public boolean isDone() {
		if (currentPosition >= (sheet.getColumns())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Zwraca String będący tytułem odcinka
	 */
	public String getItem(int episodeNumber) {
		return sheet.getCell(currentPosition, episodeNumber).getContents().toString();
	}

	/**
	 * Zwraca String będący nazwą serialu
	 */
	public String currentName() {
		return sheet.getCell(currentPosition, 0).getContents().toString();
	}

	/**
	 * Zwraca aktualny numer kolumny
	 */
	public int position(){
		return currentPosition;
	}
} ///:~

