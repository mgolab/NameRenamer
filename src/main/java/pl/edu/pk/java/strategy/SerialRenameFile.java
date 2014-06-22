//: renamer/SerialRenameFile.java
package pl.edu.pk.java.strategy;
import java.io.File;
import java.io.IOException;

import pl.edu.pk.java.decorator.NameDecorator;

public class SerialRenameFile {
	public static String renameFile(File file, String ext, String excelPath){
		String name = file.getName();
		String path = file.getParent();
		String newName = null;
		File plik = null;
		// Wydobywamy numer odcinka
		int i = NameLength(name);
		
		int episodeNumber = EpisodeNumberExtractor(name,i);
		
		name = NameExtractor(name,i);
		// Dodajemy do nazwy tytuł odcinka
		NameDecorator rename = new NameDecorator();
		rename.setInputFile(excelPath);
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
		file.renameTo(plik);
		return newName;
	}
	
	public static int NameLength(String name){
		int i;
		for (i = 0;i<name.length()-1;i++){
			if(isNumeric(name.substring(i+1,i+2))){
				break;
			}
		}
		int j = i;
		while (isNumeric(name.substring(j+1,j+2)) || isNumeric(name.substring(j+2,j+3))){
			j++;
		}
		if(j-i <= 3){
			i++;
		}
		return i;
	}
	
	public static int EpisodeNumberExtractor(String name, int i){
		int j = i;
		while (isNumeric(name.substring(j+1,j+2)) || isNumeric(name.substring(j+2,j+3))){
			j++;
		}
		int episodeNumber = Integer.parseInt(name.substring(j-1,j+1));
		return episodeNumber;
	}
	
	public static String NameExtractor(String name, int n) {
		int i;
		name = name.substring(0, n-1);
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
		return name;
	}
	
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
} ///:~
