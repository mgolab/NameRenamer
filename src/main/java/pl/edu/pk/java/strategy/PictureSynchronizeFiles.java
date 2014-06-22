package pl.edu.pk.java.strategy;

import java.io.File;
import java.io.IOException;

public class PictureSynchronizeFiles {
	public static String renameFile(File file, String ext, long difference){
		long name = file.lastModified();
		//String name = file.getName();
		String path = file.getParent();
		String newName = null;
		File plik = null;
		
		// Tworzymy nową nazwę wraz z rozszerzeniem
		newName = path + "\\" + (name+difference) + "." + ext;
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
}
