//: decorator/NameDecorator.java
package pl.edu.pk.java.decorator;
import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import pl.edu.pk.java.iterator.ExcelNameIterator;
import pl.edu.pk.java.iterator.NameIterator;
/** 
 * Klasa dodająca tytuł odcinka do nazwy pliku 
 * @author Michał Gołąb
 * @version 1.0
 */

public class NameDecorator {

	private String inputFile;
	
	/**
	 * Ustawia ścieżkę do pliku Excela
	 * @param inputFile Ścieżka do pliku Excela z zapisaną listą seriali i nazw odcinków, 
	 * gdzie piewrszy wiersz to nazwy seriali pisane z dużych liter (oprócz of, and, the),
	 * a kolejne to numer sezonu i odcinka (format 01x01) oraz nazwa odcinka
	 */
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	/**
	 * Dodaje do tytułu serialu nazwę odcinka. Pobiera arkusze z pliku Excela, 
	 * iteruje tylko po pierwszym wierszu pierwszego arkusza.
	 * @param inName String, który zawiera nazwę serialu.
	 * @param episodeNumber	Numer odcinka.
	 * @return Zwraca nazwę dla pliku w formacie: NazwaSerialu NumerSezonuxNumerOdcinka - NazwaOdcinka, 
	 * jeżeli znajdzie serial w arkuszu, lub null w przeciwnym wypadku 
	 * @throws IOException Zwraca wyjątek przy błędzie odczytu pliku Excela.
	 */
	public String add(String inName, int episodeNumber) throws IOException, NullPointerException  {
		String outName = null;
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Tworzenie iteratora
			ExcelNameIterator exceliter = createIterator(w);
			// Przeszukiwanie w poszukiwaniu serialu
			while (!exceliter.isDone()) {
				//System.out.println(exceliter.currentName());
				if (exceliter.currentName().compareTo(inName)==0){
					break;
				}
				exceliter.next();  
			}
			// Sprawdza czy znaleziono serial w arkuszu
			if(!exceliter.isDone()){
				outName = inName + " " + exceliter.getItem(episodeNumber).substring(0, 5) + " - " + exceliter.getItem(episodeNumber).substring(5);
			}else {
				throw new NullPointerException("Brak serialu w bazie");
			}
		} catch (BiffException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return outName;
	} 
	
	/**
	 * Tworzy obiekt typu iterator
	 * @param w Wczytany z pliku zbiór arkuszy
	 * @return Zwraca iterator powiązany z skoroszytem Excela
	 */
	public ExcelNameIterator createIterator(Workbook w) {
		return new NameIterator(w);
	}
} ///:~