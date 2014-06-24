package pl.edu.pk.java.strategy;

import java.io.File;
import java.util.Date;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;

public class PictureSynchronizeFiles {

	private static File file;
	private static long name;
	private static String path;
	private static String ext;
	private static long picNumber;

	@SuppressWarnings("static-access")
	public PictureSynchronizeFiles(File file, String ext) {
		this.file = file;
		this.name = file.lastModified();
		this.path = file.getParent();
		this.ext = ext.toLowerCase();
		this.picNumber = 0;
	}	
	public String renameFile(long difference){
		try {
			Metadata metadata = ImageMetadataReader.readMetadata( file );
			Date date = metadata.getDirectory(ExifDirectory.class).getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
			name = date.getTime();
		} catch (ImageProcessingException | MetadataException e1) {
			System.out.println(e1.getMessage());
			return "ERROR!!! " + name + ": " + e1.getMessage();
		}
		
		String newName = null;
		File plik = null;
		
		// Tworzymy nową nazwę wraz z rozszerzeniem
		getNumber();
		newName = path + "\\" + (name + difference + picNumber) + "." + ext;
		//newName = name + difference + "";
		// Tworzymy plik tymczasowy
		try {
			plik=new File(newName);
			File.createTempFile(newName,ext);
			file.renameTo(plik);
		} catch(Exception  e){
			System.out.println(e.getMessage());
			return "ERROR!!! " + name + ": " + e.getMessage();
		}
		//System.out.println(newName);
		//file.setLastModified(name+difference);
		return newName;
	}
	public void getNumber(){
		String name = file.getName();
		for(int i=name.length()-4;i>0;i--){
			if(isNumeric(name.substring(i-1, i))){
				picNumber = Integer.parseInt(name.substring(i-1, i));
				if(isNumeric(name.substring(i-2, i-1))){
					picNumber += 10*Integer.parseInt(name.substring(i-2, i-1));
					if(isNumeric(name.substring(i-3, i-2))){
						picNumber += 100*Integer.parseInt(name.substring(i-3, i-2));
					}
				}
				break;
			}
		}
	}
	
	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}
