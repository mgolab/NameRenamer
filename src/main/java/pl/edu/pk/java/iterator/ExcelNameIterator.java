//: iterator/ExcelNameIterator.java
package pl.edu.pk.java.iterator;

public interface ExcelNameIterator {
	public void first();
	public void next();  
	public void previous();  
	public boolean isDone();
	public boolean isEmpty(int episodeNumber);
	public String currentName();
	public String getItem(int episodeNumber);
	public int position();
} ///:~