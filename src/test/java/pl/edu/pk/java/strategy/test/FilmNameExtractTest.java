package pl.edu.pk.java.strategy.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pl.edu.pk.java.strategy.FilmRenameFile;

@RunWith(Parameterized.class)
public class FilmNameExtractTest {
	// UWAGA: poniżej wpisać wartości parametrów:
	// - string do wydobycia nazwy
	// - oczekiwany wynik operacji 
	@Parameters
	public static Collection<String[]> data() {
		return Arrays.asList(new String[][] {
				{"300.Rise.of.an.Empire.2014.720p.BluRay.x264.YIFY.mp4","mp4","300 Rise of an Empire"}, 	
				{"Jack.Ryan.Shadow.Recruit.2014.720p.BluRay.x264.YIFY.mp4","mp4","Jack Ryan Shadow Recruit"},	
				{"Rob.the.Mob.2014.720p.BluRay.x264.YIFY.mp4","mp4","Rob the Mob"}
		});
	}

	private static File fileIn;
	private String nameOut;
	private static String ext;

	@SuppressWarnings("static-access")
	public FilmNameExtractTest(String nameIn, String ext, String nameOut){
		this.fileIn = new File(nameIn);
		this.nameOut = nameOut;
		this.ext = ext;
	}
	
	static FilmRenameFile films = null;
	
	static public void createData(){
		films = new FilmRenameFile(fileIn, ext);
		System.out.println(fileIn);
	}
	
	@AfterClass
	static public void removeData(){
		films = null;
	}
	
	@Test
	public void test() {
		createData();
		films.NameExtractor();
		assertEquals(nameOut,films.getName());
	}
}
