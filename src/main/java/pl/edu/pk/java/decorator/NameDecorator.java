//: decorator/NameDecorator.java
package pl.edu.pk.java.decorator;
import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import pl.edu.pk.java.iterator.ExcelNameIterator;
import pl.edu.pk.java.iterator.NameIterator;

public class NameDecorator {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
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

	public ExcelNameIterator createIterator(Workbook w) {
		return new NameIterator(w);
	}
} ///:~