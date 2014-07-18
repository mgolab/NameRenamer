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
	private static String extension;
	private static int episodeNumber;
	private static int seasonNumber;
	private static int length;

	@SuppressWarnings("static-access")
	public SerialRenameFile(File file, String ext) {
		this.file = file;
		this.name = file.getName().toLowerCase();
		this.path = file.getParent();
		this.extension = ext;
		this.episodeNumber = 0;
		this.seasonNumber = 0;
		this.length = 0;
	}

	public String renameFile(String excelPath){
		String newName = null;
		File plik = null;

		CutTheYear();
		NameLength();
		try {
			EpisodeNumberExtractor();
			SeasonNumberExtractor();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR!!! " + ": " + e.getMessage();
		}
		NameExtractor();
		// Dodajemy do nazwy tytuł odcinka
		NameDecorator rename = new NameDecorator(excelPath);
		try {
			name = rename.add(name, episodeNumber+1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR!!! " + ": " + e.getMessage();
		}
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
		length = i;
	}

	public void EpisodeNumberExtractor(){
		episodeNumber = Integer.parseInt(name.substring(length-3,length-1));
	}

	public void SeasonNumberExtractor(){
		if(isNumeric(name.substring(length-4,length-3))){
			seasonNumber = Integer.parseInt(name.substring(length-4,length-3));
		}
		else {
			seasonNumber = Integer.parseInt(name.substring(length-5,length-4));
		}
	}

	public void CreateNewPath(){
		path = path.substring(0,path.lastIndexOf("Torrent"));
		path = path + "Torrent\\Seriale\\Sezon " + seasonNumber + " - " + name.substring(0,name.lastIndexOf("-")-7);
		new File(path).mkdir();
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
