//: renamer/SerialRenameFile.java
package pl.edu.pk.java.strategy;
import java.io.File;
import java.io.IOException;

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
		this.name = file.getName();
		this.path = file.getParent();
		this.ext = ext;
		this.episodeNumber = 0;
	}
	
	public String renameFile(String excelPath){
		String newName = null;
		@SuppressWarnings("unused")
		File plik = null;
		
		CutTheYear();
		NameLength();
		EpisodeNumberExtractor();
		NameExtractor();
		
		// Dodajemy do nazwy tytuł odcinka
		NameDecorator rename = new NameDecorator(excelPath);
		try {
			name = rename.add(name, episodeNumber);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR!!! " + name + ": " + e.getMessage();
		}
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
	
	public static void CutTheYear(){
		int i = 0;
		for (i = 3; i < name.length()-1; i++){
			int j = 0;
			while(isNumeric(name.substring(i+j+1,i+j+2))){
				j++;
			}
			if(j==4){
				name = name.substring(0,i) + name.substring(i+5);
			}
		}
	}
	
	public static void NameLength(){
		int i;
		for (i = 0; i < name.length()-1; i++){
			if(isNumeric(name.substring(i+1,i+2)) && isNumeric(name.substring(i+2,i+3))){
				break;
			}
		}
		int j = i;
		while (isNumeric(name.substring(j+1,j+2)) || isNumeric(name.substring(j+2,j+3))){
			j++;
		}
		if(j-i == 3){
			i++;
		}
		length = i;
	}
	
	public static void EpisodeNumberExtractor(){
		int j = length;
		while (isNumeric(name.substring(j+1,j+2)) || isNumeric(name.substring(j+2,j+3))){
			j++;
		}
		episodeNumber = Integer.parseInt(name.substring(j-1,j+1));
	}
	
	public static void NameExtractor() {
		int i;
		name = name.substring(0, length-1);
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		for (i = 2; i < name.length(); i++){
			if(name.substring(i,i+1).compareTo(".")==0){
				if(name.substring(i+1,i+3).compareTo("of")==0){
					continue;
				}
				else if(name.substring(i+1,i+4).compareTo("the")==0){
					continue;
				}
				else if(name.substring(i+1,i+4).compareTo("and")==0){
					continue;
				}
				name = name.substring(0,i+1) + name.substring(i+1,i+2).toUpperCase() + name.substring(i+2);
			}
		}
		name = name.replace(".", " ");
	}
	
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
} ///:~
