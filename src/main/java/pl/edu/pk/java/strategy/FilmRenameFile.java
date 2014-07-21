//: renamer/FilmRenameFile.java
package pl.edu.pk.java.strategy;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class FilmRenameFile {

	private static File file;
	private static String name;
	private static String path;
	private static String extension;
	private static String destinationPath;

	@SuppressWarnings("static-access")
	public FilmRenameFile(File file, String ext, String dest) {
		this.file = file;
		this.name = file.getName();
		this.path = file.getParent();
		this.destinationPath = dest;
		this.extension = ext;
	}

	public String renameFile(){
		String newName = null;
		File plik = null;

		NameExtractor();
		CreateNewPath();

		// Tworzymy nową nazwę wraz z rozszerzeniem
		newName = path + "\\" + name + "." + extension;
		// Tworzymy plik tymczasowy
		plik=new File(newName);
		try {
			File.createTempFile(newName,extension);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			return "ERROR!!! " + ": " + e1.getMessage();
		}
		System.out.println(newName);
		// Zmieniamy nazwę
		file.renameTo(plik);

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
	
	public void CreateNewPath(){
		path = destinationPath + path.substring(path.lastIndexOf("\\")+1);
		new File(path).mkdir();
		System.out.println(path);
	}

	public String getName(){
		return name;
	}
} ///:~
