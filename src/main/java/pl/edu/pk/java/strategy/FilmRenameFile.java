//: renamer/FilmRenameFile.java
package pl.edu.pk.java.strategy;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import pl.edu.pk.java.files.NameRenamer;

public class FilmRenameFile {

	private final static String newline = "\n";
	private File file;
	private String name;
	private String path;
	private String extension;
	private String destinationPath;
	private String filesInFolder;

	public FilmRenameFile(File file, String ext, String dest) {
		this.file = file;
		this.name = file.getName();
		this.path = file.getParent();
		this.filesInFolder = "";
		this.destinationPath = dest;
		this.extension = ext;
	}

	public String renameFile(){
		NameExtractor();
		CreateNewPath();
		// Tworzymy nową nazwę wraz z rozszerzeniem
		String newName = destinationPath + "\\" + name + "." + extension;
		// Tworzymy plik tymczasowy
		File plik = new File(newName);
		try {
			File.createTempFile(newName,extension);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			return "ERROR!!! " + ": " + e1.getMessage();
		}
		file.renameTo(plik);
		
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

		return newName;
	}

	public void NameExtractor(){
		String oldName = name;
		Calendar rightNow = Calendar.getInstance();
		for (int i = rightNow.get(Calendar.YEAR); i > 1990 ; i--){
			if(name.contains(i + "")) {
				name = name.substring(0,name.lastIndexOf(i+"")-1).replace(".", " ").replace("_", "");
			}
		}
		if(name.compareTo(oldName)==0) {
			if(name.contains("BrRip")) {
				name = name.substring(0,name.lastIndexOf("BrRip")-1).replace(".", " ").replace("_", "");
			} else if(name.contains("brrip")) {
				name = name.substring(0,name.lastIndexOf("brrip")-1).replace(".", " ").replace("_", "");
			} else if(name.contains("720p")) {
				name = name.substring(0,name.lastIndexOf("720p")-1).replace(".", " ").replace("_", "");
			} else if(name.contains("1080p")) {
				name = name.substring(0,name.lastIndexOf("1080p")-1).replace(".", " ").replace("_", "");
			}
		}
	}

	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	public void CreateNewPath(){
		if(destinationPath.endsWith("\\")){
			destinationPath = destinationPath + path.substring(path.lastIndexOf("\\")+1);
		} else {
			destinationPath = destinationPath + "\\" + path.substring(path.lastIndexOf("\\")+1);
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

	public String getName(){
		return name;
	}
} ///:~
