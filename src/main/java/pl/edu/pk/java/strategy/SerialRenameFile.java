//: renamer/SerialRenameFile.java
package pl.edu.pk.java.strategy;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import pl.edu.pk.java.decorator.NameDecorator;
import pl.edu.pk.java.files.NameRenamer;

public class SerialRenameFile {

	private final static String newline = "\n";
	private File file;
	private String name;
	private String path;
	private String extension;
	private String generalPath;
	private String destinationPath;
	private String filesInFolder;
	private int episodeNumber;
	private int seasonNumber;
	private int length;

	public SerialRenameFile(File file, String ext, String path, String dest) {
		this.file = file;
		this.name = file.getName().toLowerCase();
		this.path = file.getParent();
		this.filesInFolder = "";
		this.extension = ext;
		this.generalPath = path;
		this.destinationPath = dest;
		this.episodeNumber = 0;
		this.seasonNumber = 0;
		this.length = 0;
	}

	public String renameFile(String excelPath){
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
			System.out.println(episodeNumber);
			name = rename.add(name, episodeNumber+1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR!!! " + ": " + e.getMessage();
		}
		CreateNewPath();
		// Tworzymy nową nazwę wraz z rozszerzeniem
		String newName = destinationPath + "\\" + name + "." + extension;
		// Tworzymy plik tymczasowy
		File plik=new File(newName);
		try {
			File.createTempFile(newName,extension);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			return "ERROR!!! " + ": " + e1.getMessage();
		}
		file.renameTo(plik);
		
		if(generalPath.endsWith("\\")){
			generalPath = generalPath.substring(0, generalPath.length()-1);
		}
		
		if(path.compareTo(generalPath)!=0) {
			new NameRenamer(new NameRenamer.Strategy() {
				public void process(File file, String ext) {
					filesInFolder += file.getName() + newline;
				}
			}, "*").start(path);

			int output = JOptionPane.showConfirmDialog(null, 
					"Czy na pewno chcesz usunąć folder" + newline 
					+ path.substring(path.lastIndexOf("\\")+1) + newline 
					+ "wraz z zawartością:" + newline + filesInFolder,
					"Delete Folder?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);

			if(output == JOptionPane.YES_OPTION){
				deleteFolder();
			}
		}
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
		if(name.contains("480p")) {
			name = name.replaceAll("480p", "");
		}
		if(name.contains("720p")) {
			name = name.replaceAll("720p", "");
		}
		if(name.contains("1080p")) {
			name = name.replaceAll("1080p", "");
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
			if(isNumeric(name.substring(length-5,length-4))){
				seasonNumber = seasonNumber + 10*Integer.parseInt(name.substring(length-5,length-4));
			}
		}
		else {
			seasonNumber = Integer.parseInt(name.substring(length-5,length-4));
			if(isNumeric(name.substring(length-6,length-5))){
				seasonNumber = seasonNumber + 10*Integer.parseInt(name.substring(length-6,length-5));
			}
		}
	}

	public void CreateNewPath(){
		if(destinationPath.endsWith("\\")){
			destinationPath = destinationPath + "Sezon " + seasonNumber + " - " + name.substring(0,name.lastIndexOf("-")-7);
		} else {
			destinationPath = destinationPath + "\\" + "Sezon " + seasonNumber + " - " + name.substring(0,name.lastIndexOf("-")-7);
		}
		new File(destinationPath).mkdir();
		//System.out.println(destinationPath);
	}

	public void deleteFolder(){
		new NameRenamer(new NameRenamer.Strategy() {
			public void process(File file, String ext) {
				file.delete();
				//System.out.println(file);
			}
		}, "*").start(path);
		File f = new File(path);
		f.delete();
		//System.out.println(f);
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
