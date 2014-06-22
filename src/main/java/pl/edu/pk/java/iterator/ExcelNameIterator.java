//: iterator/ExcelNameIterator.java
package pl.edu.pk.java.iterator;

public interface ExcelNameIterator {
	public void first();
	public void next();  
	public boolean isDone();
	public String currentName();
	public String getItem(int episodeNumber);
	public int position();
} ///:~