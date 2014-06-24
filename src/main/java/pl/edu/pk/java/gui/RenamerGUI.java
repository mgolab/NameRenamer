package pl.edu.pk.java.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;

import pl.edu.pk.java.files.NameRenamer;
import pl.edu.pk.java.strategy.FilmRenameFile;
import pl.edu.pk.java.strategy.PictureDeleteFile;
import pl.edu.pk.java.strategy.PictureSynchronizeFiles;
import pl.edu.pk.java.strategy.SerialRenameFile;

public class RenamerGUI {
	private final static String newline = "\n";
	private JFrame mainFrame;
	//private JLabel headerLabel;
	private JPanel statusPanel;
	private JPanel controlPanel;
	//private JLabel msglabel;
	public String ext;
	public String path;
	public String excelPath;
	public String picOne;
	public String picTwo;

	private long difference;

	public RenamerGUI(){
		prepareGUI();
	}

	private void prepareGUI(){
		mainFrame = new JFrame("Name Renamer");
		mainFrame.setSize(400,600);
		mainFrame.setResizable(false);
		mainFrame.setLayout(new GridLayout(2, 1));

		//headerLabel = new JLabel("",JLabel.CENTER );

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		statusPanel = new JPanel();
		statusPanel.setLayout(new FlowLayout());

		//mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusPanel);
		mainFrame.setVisible(true);  
	}

	public void createGUI(){  
		final JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setSize(300,600);

		CardLayout layout = new CardLayout();
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);        

		JPanel filmBoxPanel = new JPanel();
		filmBoxPanel.setLayout(new GridLayout(8,1));

		final JTextField filmExtField = new JTextField("mp4",30);
		final JTextField filmPath = new JTextField("E:\\Torrent\\Brak napisów\\",30);

		filmBoxPanel.add(new JLabel("Rozszerzenie:"));
		filmBoxPanel.add(filmExtField);
		filmBoxPanel.add(new JLabel("Ścieżka do folderu z plikami:"));
		filmBoxPanel.add(filmPath);

		JPanel picturesFilmBoxPanel = new JPanel();
		picturesFilmBoxPanel.setLayout(new GridLayout(8,1));

		final JTextField pictureFilmExtField = new JTextField("jpg",30);
		final JTextField pictureFilmPath = new JTextField("E:\\Torrent\\Brak napisów\\",30);

		picturesFilmBoxPanel.add(new JLabel("Rozszerzenie:"));
		picturesFilmBoxPanel.add(pictureFilmExtField);
		picturesFilmBoxPanel.add(new JLabel("Ścieżka do folderu z plikami:"));
		picturesFilmBoxPanel.add(pictureFilmPath);

		final JTextField serialExtField = new JTextField("mp4",30);
		final JTextField serialPath = new JTextField("E:\\Torrent\\Seriale\\",30);
		final JTextField serialExcelPath = new JTextField("E:\\Torrent\\Seriale\\Serials.xls",30);

		JPanel serialBoxPanel = new JPanel();
		serialBoxPanel.setLayout(new GridLayout(8,1));

		serialBoxPanel.add(new JLabel("Rozszerzenie:"));
		serialBoxPanel.add(serialExtField);
		serialBoxPanel.add(new JLabel("Ścieżka do folderu z plikami:"));
		serialBoxPanel.add(serialPath);
		serialBoxPanel.add(new JLabel("Ścieżka do pliku Excela:"));
		serialBoxPanel.add(serialExcelPath);

		final JTextField picturesExtField = new JTextField("jpg",30);
		final JTextField picturesPath = new JTextField("E:\\Pics\\cellphone\\",30);
		final JTextField pictureOne = new JTextField("E:\\Pics\\good.jpg",30);
		final JTextField pictureTwo = new JTextField("E:\\Pics\\late.jpg",30);

		JPanel picturesBoxPanel = new JPanel();
		picturesBoxPanel.setLayout(new GridLayout(8,1));

		picturesBoxPanel.add(new JLabel("Rozszerzenie:"));
		picturesBoxPanel.add(picturesExtField);
		picturesBoxPanel.add(new JLabel("Ścieżka do pierwszego pliku:"));
		picturesBoxPanel.add(pictureOne);
		picturesBoxPanel.add(new JLabel("Ścieżka do drugiego pliku:"));
		picturesBoxPanel.add(pictureTwo);
		picturesBoxPanel.add(new JLabel("Ścieżka do folderu:"));
		picturesBoxPanel.add(picturesPath);

		panel.add("Filmy", filmBoxPanel);
		panel.add("YIFY.jpg", picturesFilmBoxPanel);
		panel.add("Seriale", serialBoxPanel);
		panel.add("Zdjęcia", picturesBoxPanel);

		final DefaultComboBoxModel<String> panelName = new DefaultComboBoxModel<String>();

		panelName.addElement("Filmy");
		panelName.addElement("YIFY.jpg");
		panelName.addElement("Seriale");
		panelName.addElement("Zdjęcia");

		final JComboBox<String> listCombo = new JComboBox<String>(panelName);    
		listCombo.setSelectedIndex(0);

		JScrollPane listComboScrollPane = new JScrollPane(listCombo);    

		final JTextArea statusText = new JTextArea(10,30);
		statusText.setEditable(false);

		JScrollPane scrollPanel = new JScrollPane(statusText);	

		final JButton commitButton = new JButton("Zmień nazwę");

		listCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if (listCombo.getSelectedIndex() != -1) {  
					CardLayout cardLayout = (CardLayout)(panel.getLayout());
					cardLayout.show(panel, 
							(String)listCombo.getItemAt(listCombo.getSelectedIndex()));
					statusText.setText("");
					switch(listCombo.getSelectedIndex()){
					case 0:
						commitButton.setText("Zmień nazwę");
						break;
					case 1:
						commitButton.setText("Usuń obrazy");
						break;
					case 2:
						commitButton.setText("Zmień nazwę");
						break;
					case 3:
						commitButton.setText("Zmień nazwę");
						break;
					}
				}              
			}
		}); 	

		commitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				statusText.setText("");
				switch (listCombo.getSelectedIndex()) { 
				case 0:
					ext = filmExtField.getText();
					path = filmPath.getText();
					if (!ext.equals("") && !path.equals("")){
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								if(file.getName().contains("YIFY")){
									statusText.append("Znaleziono plik: " + file + newline);
									FilmRenameFile film = new FilmRenameFile(file,ext);
									statusText.append("Zamieniono na: " + film.renameFile() + newline);
								}
							}
						}, ext).start(path);
					}
					break;
				case 1:
					ext = pictureFilmExtField.getText();
					path = pictureFilmPath.getText();
					if (!ext.equals("") && !path.equals("")){
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								if(file.getName().contains("YIFY")){
									statusText.append("Znaleziono plik: " + file + newline);
									PictureDeleteFile.deleteFile(file);
									statusText.append("Usunięto plik" + newline);
								}
							}
						}, ext).start(path);
					}
					break;
				case 2:
					ext = serialExtField.getText();
					path = serialPath.getText();
					excelPath = serialExcelPath.getText();
					if (!ext.equals("") && !path.equals("") && !excelPath.equals("")){
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								if(!file.getName().contains(" ")){
									statusText.append(file + newline);
									SerialRenameFile serial = new SerialRenameFile(file,ext);
									statusText.append(serial.renameFile(excelPath) + newline);
								}
							}
						}, ext).start(path);
					}
					break;
				case 3:
					ext = picturesExtField.getText();
					path = picturesPath.getText();
					picOne = pictureOne.getText();
					picTwo = pictureTwo.getText();
					if (!ext.equals("") && !path.equals("") && !picOne.equals("") && !picTwo.equals("")){
						File fileOne = new File(picOne);
						File fileTwo = new File(picTwo);
						try {
							Metadata metadata1 = ImageMetadataReader.readMetadata( fileOne );
							Metadata metadata2 = ImageMetadataReader.readMetadata( fileTwo );
							Date date1 = metadata1.getDirectory(ExifDirectory.class).getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
							Date date2 = metadata2.getDirectory(ExifDirectory.class).getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
							difference = date1.getTime() - date2.getTime() + 1;
							System.out.println("Różnica czasów: " + difference);
						} catch (ImageProcessingException | MetadataException e1) {
							System.out.println(e1.getMessage());
							break;
						}
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								statusText.append(file + newline);
								PictureSynchronizeFiles synchro = new PictureSynchronizeFiles(file,ext);
								statusText.append(synchro.renameFile(difference) + newline);
							}
						}, ext).start(path);
					}
					else if (!ext.equals("") && !path.equals("")){
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								statusText.append(file + newline);
								PictureSynchronizeFiles synchro = new PictureSynchronizeFiles(file,ext);
								statusText.append(synchro.renameFile(0) + newline);
							}
						}, ext).start(path);
					}
					break;
				default:
					break;
				}              
			}
		}); 

		controlPanel.add(new JLabel("Wybierz rodzaj plików: "));
		controlPanel.add(listComboScrollPane);
		controlPanel.add(panel);
		controlPanel.add(commitButton);

		statusPanel.add(new JLabel("Status plików: "));
		statusPanel.add(scrollPanel);

		mainFrame.setVisible(true);  
	}
}

