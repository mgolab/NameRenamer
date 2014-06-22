package pl.edu.pk.java.strategy.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

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
				{"300.Rise.of.an.Empire.2014.720p.BluRay.x264.YIFY","300 Rise of an Empire"}, 	
				{"Jack.Ryan.Shadow.Recruit.2014.720p.BluRay.x264.YIFY","Jack Ryan Shadow Recruit"},	
				{"Rob.the.Mob.2014.720p.BluRay.x264.YIFY","Rob the Mob"}
		});
	}

	private String nameIn;
	private String nameOut;

	public FilmNameExtractTest(String nameIn,String nameOut){
		this.nameIn = nameIn;
		this.nameOut = nameOut;
	}

	@Test
	public void test() {
		assertEquals(nameOut,FilmRenameFile.NameExtractor(nameIn));
	}
}
