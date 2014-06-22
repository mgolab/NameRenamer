package pl.edu.pk.java.files;

import java.io.File;
import java.io.IOException;

public class NameRenamer {
	public interface Strategy {
		void process(File file, String ext);
	}

	private Strategy strategy;
	private String ext;
	public NameRenamer(Strategy strategy, String ext) {
		this.strategy = strategy;
		this.ext = ext;
	}

	public void start(String path) {
		try {
			processDirectoryTree(new File(path));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void	processDirectoryTree(File root) throws IOException {
		for(File file : Directory.walk(root.getAbsolutePath(), ".*\\." + ext)){
			strategy.process(file.getCanonicalFile(),ext);
		}
	}
}
