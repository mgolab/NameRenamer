//: renamer/FilmRenameFile.java
package pl.edu.pk.java.strategy;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import pl.edu.pk.java.files.NameRenamer;

public class FilmRenameFile {
	
	private static File file;
	private static String name;
	private static String path;
	private static String ext;
	
	@SuppressWarnings("static-access")
	public FilmRenameFile(File file, String ext) {
		this.file = file;
		this.name = file.getName();
		this.path = file.getParent();
		this.ext = ext;
	}
	
	public String renameFile(){
		String newName = null;
		File plik = null;

		NameExtractor();

		// Tworzymy nową nazwę wraz z rozszerzeniem
		newName = path + "\\" + name + "." + ext;
		// Tworzymy plik tymczasowy
		plik=new File(newName);
		try {
			File.createTempFile(newName,ext);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			return "ERROR!!! " + ": " + e1.getMessage();
		}
		System.out.println(newName);
		// Zmieniamy nazwę
		file.renameTo(plik);
		
		new NameRenamer(new NameRenamer.Strategy() {
			public void process(File file, String ext) {
				file.delete();
			}
		}, "jpg").start(path);
		
		return newName;
	}

	public void NameExtractor(){
		int i = 0;
		Calendar rightNow = Calendar.getInstance();
		for (i = rightNow.get(Calendar.YEAR); i > 1990 ; i--){
			if(name.contains(i + "")) {
				name = name.substring(0,name.lastIndexOf(i+"")-1).replace(".", " ");
			}
		}
	}
	
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public String getName(){
		return name;
	}
} ///:~
