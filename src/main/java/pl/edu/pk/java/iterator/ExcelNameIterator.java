//: iterator/ExcelNameIterator.java
package pl.edu.pk.java.iterator;
/** 
 * Interfejs wzorca iterator. 
 * @author Michał Gołąb
 * @version 1.0
 * @see NameIterator
 */
public interface ExcelNameIterator {
	public void first();
	public void next();  
	public boolean isDone();
	public String currentName();
	public String getItem(int episodeNumber);
	public int position();
} ///:~