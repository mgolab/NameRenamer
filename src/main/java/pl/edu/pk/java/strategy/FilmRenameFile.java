//: renamer/FilmRenameFile.java
package pl.edu.pk.java.strategy;

import java.io.File;
import java.io.IOException;

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
		@SuppressWarnings("unused")
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
			return "ERROR!!! " + name + ": " + e1.getMessage();
		}
		System.out.println(newName);
		// Zmieniamy nazwę
		//file.renameTo(plik);
		return newName;
	}

	public void NameExtractor(){
		int i = 0;
		for (i = 3; i<name.length()-1; i++){
			if(isNumeric(name.substring(i+1,i+2))){
				break;
			}
		}
		name = name.substring(0,i).replace(".", " ");
	}
	
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public String getName(){
		return name;
	}
} ///:~
