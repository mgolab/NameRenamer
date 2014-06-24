package pl.edu.pk.java.strategy;

import java.io.File;

public class PictureSynchronizeFiles {
	
	private static File file;
	private static long name;
	private static String path;
	private static String ext;
	
	@SuppressWarnings("static-access")
	public PictureSynchronizeFiles(File file, String ext) {
		this.file = file;
		this.name = file.lastModified();
		this.path = file.getParent();
		this.ext = ext;
	}	
	public String renameFile(long difference){
		String newName = null;
		File plik = null;
		
		// Tworzymy nową nazwę wraz z rozszerzeniem
		newName = path + "\\" + (name+difference) + "." + ext;
		// Tworzymy plik tymczasowy
		plik=new File(newName);
		try {
			File.createTempFile(newName,ext);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			return "ERROR!!! " + name + ": " + e1.getMessage();
		}
		System.out.println(newName);
		// Zmieniamy nazwę
		file.setLastModified(name+difference);
		file.renameTo(plik);
		return newName;
	}
}
