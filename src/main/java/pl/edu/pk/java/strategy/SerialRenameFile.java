//: renamer/SerialRenameFile.java
package pl.edu.pk.java.strategy;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import pl.edu.pk.java.decorator.NameDecorator;

public class SerialRenameFile {

	private static File file;
	private static String name;
	private static String path;
	private static String ext;
	private static int episodeNumber;
	private static int length;

	@SuppressWarnings("static-access")
	public SerialRenameFile(File file, String ext) {
		this.file = file;
		this.name = file.getName().toLowerCase();
		this.path = file.getParent();
		this.ext = ext;
		this.episodeNumber = 0;
		this.length = 0;
	}

	public String renameFile(String excelPath){
		String newName = null;
		File plik = null;

		CutTheYear();
		NameLength();
		EpisodeNumberExtractor();
		NameExtractor();

		// Dodajemy do nazwy tytuł odcinka
		NameDecorator rename = new NameDecorator(excelPath);
		try {
			name = rename.add(name, episodeNumber+1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR!!! " + ": " + e.getMessage();
		}
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
		return newName;
	}

	public void CutTheYear(){
		int i = 0;
		Calendar rightNow = Calendar.getInstance();
		for (i = 2000; i <= rightNow.get(Calendar.YEAR); i++){
			if(name.contains(i + "")) {
				name = name.replaceAll(i + ".", "");
			}
		}
	}

	public void NameLength(){
		int i;
		if(name.contains("x264")) {
			name = name.replaceAll("x264", "");
		}
		for (i = name.length(); i > 3; i--){
			if(isNumeric(name.substring(i-2,i-1)) && isNumeric(name.substring(i-3,i-2))){
				break;
			}
		}
		System.out.println(name.substring(0,i));
		length = i;
	}

	public void EpisodeNumberExtractor(){
		episodeNumber = Integer.parseInt(name.substring(length-3,length-1));
	}

	public void NameExtractor() {
		name = name.substring(0, 5);
		name = name.replace(".", " ");
	}

	public static boolean isNumeric(String str)	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	public String getName() {
		return name;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

} ///:~
