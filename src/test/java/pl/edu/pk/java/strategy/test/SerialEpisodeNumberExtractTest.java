package pl.edu.pk.java.strategy.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

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
				{"hannibal.213.hdtv-lol",13}, 	
				{"beauty.and.the.beast.213.hdtv-lol",13},	
				{"game.of.thrones.s04e09.hdtv.x264-killers",9},
				{"supernatural.923.hdtv-lol",23},
				{"the.mentalist.622.hdtv-lol",22}
		});
	}

	private String nameIn;
	private int episodeNumber;

	public SerialEpisodeNumberExtractTest(String nameIn,int episodeNumber){
		this.nameIn = nameIn;
		this.episodeNumber = episodeNumber;
	}

	@Test
	public void test() {
		assertEquals(episodeNumber,SerialRenameFile.EpisodeNumberExtractor(nameIn,SerialRenameFile.NameLength(nameIn)));
	}
}
