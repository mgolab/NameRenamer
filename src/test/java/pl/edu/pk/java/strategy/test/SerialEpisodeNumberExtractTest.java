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
public class SerialEpisodeNumberExtractTest {
	// UWAGA: poniżej wpisać wartości parametrów:
	// - string do wydobycia nazwy
	// - oczekiwany wynik operacji 
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{"hannibal.213.hdtv-lol.mp4","mp4",13}, 	
				{"beauty.and.the.beast.213.hdtv-lol.mp4","mp4",13},	
				{"game.of.thrones.s04e09.hdtv.x264-killers.mp4","mp4",9},
				{"supernatural.923.hdtv-lol.mp4","mp4",23},
				{"the.mentalist.622.hdtv-lol.mp4","mp4",22},
				{"Hawaii.Five-0.2010.S04E22.HDTV.x264-LOL.mp4","mp4",22}
		});
	}

	private static File fileIn;
	private static String ext;
	private int episodeNumber;

	@SuppressWarnings("static-access")
	public SerialEpisodeNumberExtractTest(String nameIn, String ext, int episodeNumber){
		this.fileIn = new File(nameIn);
		this.ext = ext;
		this.episodeNumber = episodeNumber;
	}

	static SerialRenameFile serials = null;

	static public void createData(){
		serials = new SerialRenameFile(fileIn, ext);
		System.out.println(fileIn);
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
		serials.EpisodeNumberExtractor();
		assertEquals(episodeNumber,serials.getEpisodeNumber());
	}
}
