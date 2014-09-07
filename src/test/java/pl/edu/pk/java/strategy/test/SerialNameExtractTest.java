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

import pl.edu.pk.java.strategy.SerialRenameFile;

@RunWith(Parameterized.class)
public class SerialNameExtractTest {
	// UWAGA: poniżej wpisać wartości parametrów:
	// - string do wydobycia nazwy
	// - oczekiwany wynik operacji 
	@Parameters
	public static Collection<String[]> data() {
		return Arrays.asList(new String[][] {
				{"hannibal.213.hdtv-lol.mp4","mp4","hannibal"}, 	
				{"beauty.and.the.beast.213.hdtv-lol.mp4","mp4","beauty and the beast"},	
				{"game.of.thrones.s04e09.hdtv.x264-killers.mp4","mp4","game of thrones"},
				{"supernatural.923.hdtv-lol.mp4","mp4","supernatural"},
				{"the.mentalist.622.hdtv-lol.mp4","mp4","the mentalist"},
				{"Hawaii.Five-0.2010.S04E22.HDTV.x264-LOL.mp4","mp4","hawaii five-0"}
		});
	}

	private static File fileIn;
	private static String ext;
	private String nameOut;

	@SuppressWarnings("static-access")
	public SerialNameExtractTest(String nameIn,String ext,String nameOut){
		this.fileIn = new File(nameIn);
		this.ext = ext;
		this.nameOut = nameOut;
	}

	static SerialRenameFile serials = null;

	static public void createData(){
		serials = new SerialRenameFile(fileIn, ext, "", "");
	}

	@AfterClass
	static public void removeData(){
		serials = null;
	}

	@Test
	public void test() {
		createData();
		serials.CutTheYear();
		serials.NameLength();
		serials.NameExtractor();
		assertEquals(nameOut.contains(serials.getName()),true);
	}
}
