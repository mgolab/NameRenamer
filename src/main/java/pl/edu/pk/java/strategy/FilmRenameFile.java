package pl.edu.pk.java.strategy;

import java.io.File;
import java.io.IOException;

public class FilmRenameFile {
	public static String renameFile(File file, String ext){
		String name = file.getName();
		String path = file.getParent();
		String newName = null;
		File plik = null;

		name = NameExtractor(name);

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
		file.renameTo(plik);
		return newName;
	}

	public static String NameExtractor(String name){
		int i = 0;
		for (i = 3; i<name.length()-1; i++){
			if(isNumeric(name.substring(i+1,i+2))){
				break;
			}
		}
		name = name.substring(0,i).replace(".", " ");
		return name;
	}
	
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
} ///:~
