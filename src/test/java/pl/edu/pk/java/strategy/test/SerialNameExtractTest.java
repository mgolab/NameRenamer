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
public class SerialNameExtractTest {
	// UWAGA: poniżej wpisać wartości parametrów:
	// - string do wydobycia nazwy
	// - oczekiwany wynik operacji 
	@Parameters
	public static Collection<String[]> data() {
		return Arrays.asList(new String[][] {
				{"hannibal.213.hdtv-lol","Hannibal"}, 	
				{"beauty.and.the.beast.213.hdtv-lol","Beauty and the Beast"},	
				{"game.of.thrones.s04e09.hdtv.x264-killers","Game of Thrones"},
				{"supernatural.923.hdtv-lol","Supernatural"},
				{"the.mentalist.622.hdtv-lol","The Mentalist"}
		});
	}

	private String nameIn;
	private String nameOut;

	public SerialNameExtractTest(String nameIn,String nameOut){
		this.nameIn = nameIn;
		this.nameOut = nameOut;
	}

	@Test
	public void test() {
		assertEquals(nameOut,SerialRenameFile.NameExtractor(nameIn,SerialRenameFile.NameLength(nameIn)));
	}
}
