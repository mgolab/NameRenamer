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
import pl.edu.pk.java.strategy.*;

public class RenamerGUI {
	private final static String newline = "\n";
	private JFrame mainFrame;
	//private JLabel headerLabel;
	private JPanel statusPanel;
	private JPanel controlPanel;
	//private JLabel msglabel;
	public String ext;
	public String path;
	public String contains;
	public String latePath;
	public String excelPath;
	public String picOne;
	public String picTwo;
	private long difference;

	public RenamerGUI(){
		prepareGUI();
	}

	private void prepareGUI(){
		mainFrame = new JFrame("Name Renamer");
		mainFrame.setSize(500,450);
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

		CardLayout layout = new CardLayout();
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);

		final JPanel filmBoxPanel = new JPanel();
		filmBoxPanel.setLayout(new GridLayout(6,2));

		final JTextField filmExtField = new JTextField("mp4",20);
		final JTextField filmPath = new JTextField("E:\\Torrent\\Brak napisów\\",20);
		final JTextField filmContains = new JTextField("YIFY",20);

		filmBoxPanel.add(new JLabel("Rozszerzenie:"));
		filmBoxPanel.add(filmExtField);
		filmBoxPanel.add(new JLabel("Ścieżka do folderu z plikami:"));
		filmBoxPanel.add(filmPath);
		filmBoxPanel.add(new JLabel("Nazwa zawiera:"));
		filmBoxPanel.add(filmContains);
		filmBoxPanel.add(new JLabel(""));

		final JTextField serialExtField = new JTextField("mp4",20);
		final JTextField serialPath = new JTextField("E:\\Torrent\\Seriale\\",20);
		final JTextField serialExcelPath = new JTextField("E:\\Torrent\\Seriale\\Serials.xls",20);
		
		final JPanel serialBoxPanel = new JPanel();
		serialBoxPanel.setLayout(new GridLayout(6,2));

		serialBoxPanel.add(new JLabel("Rozszerzenie:"));
		serialBoxPanel.add(serialExtField);
		serialBoxPanel.add(new JLabel("Ścieżka do folderu z plikami:"));
		serialBoxPanel.add(serialPath);
		serialBoxPanel.add(new JLabel("Ścieżka do pliku Excela:"));
		serialBoxPanel.add(serialExcelPath);
		serialBoxPanel.add(new JLabel(""));

		final JTextField picturesExtField = new JTextField("jpg",20);
		final JTextField latePicturesPath = new JTextField("E:\\Pics\\cellphone\\",20);
		final JTextField picturesPath = new JTextField("E:\\Pics\\camera\\",20);
		final JTextField pictureOne = new JTextField("E:\\Pics\\good.jpg",20);
		final JTextField pictureTwo = new JTextField("E:\\Pics\\late.jpg",20);
		final JTextField delay = new JTextField("10",20);

		JPanel picturesBoxPanel = new JPanel();
		picturesBoxPanel.setLayout(new GridLayout(6,2));

		picturesBoxPanel.add(new JLabel("Rozszerzenie:"));
		picturesBoxPanel.add(picturesExtField);
		picturesBoxPanel.add(new JLabel("Ścieżka do pliku z dobrą datą:"));
		picturesBoxPanel.add(pictureOne);
		picturesBoxPanel.add(new JLabel("Ścieżka do pliku z błędną datą:"));
		picturesBoxPanel.add(pictureTwo);
		picturesBoxPanel.add(new JLabel("Czas pomiędzy wykonaniem w sekundach:"));
		picturesBoxPanel.add(delay);
		picturesBoxPanel.add(new JLabel("Ścieżka do zdjęć z przesuniętą datą:"));
		picturesBoxPanel.add(latePicturesPath);
		picturesBoxPanel.add(new JLabel("Ścieżka do zdjęć z dobrą datą:"));
		picturesBoxPanel.add(picturesPath);

		panel.add("Filmy", filmBoxPanel);
		panel.add("Seriale", serialBoxPanel);
		panel.add("Zdjęcia", picturesBoxPanel);

		final DefaultComboBoxModel<String> panelName = new DefaultComboBoxModel<String>();

		panelName.addElement("Filmy");
		panelName.addElement("Seriale");
		panelName.addElement("Zdjęcia");

		final JComboBox<String> listCombo = new JComboBox<String>(panelName);    
		listCombo.setSelectedIndex(0);

		JScrollPane listComboScrollPane = new JScrollPane(listCombo);    

		final JTextArea statusText = new JTextArea(10,40);
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
					contains = filmContains.getText();
					if (!ext.equals("") && !path.equals("")){
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								if(file.getName().contains(contains)){
									statusText.append("Znaleziono plik: " + file + newline);
									FilmRenameFile film = new FilmRenameFile(file,ext);
									statusText.append("Zamieniono na: " + film.renameFile() + newline);
								}
							}
						}, ext).start(path);
					}
					break;
				case 1:
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
				case 2:
					ext = picturesExtField.getText();
					latePath = latePicturesPath.getText();
					path = picturesPath.getText();
					picOne = pictureOne.getText();
					picTwo = pictureTwo.getText();
					boolean value = false;
					for(int i=0;i<delay.getText().length()-1;i++){
						if(!isNumeric(delay.getText().substring(i, i+1))){
							statusText.append("Opóźnienie nie jest liczbą" + newline);
							value = false;
							break;
						}
						value = true;
					}
					if (!ext.equals("") && !path.equals("") && !latePath.equals("") && !picOne.equals("") && !picTwo.equals("") && value){
						File fileOne = new File(picOne);
						File fileTwo = new File(picTwo);
						try {
							Metadata metadata1 = ImageMetadataReader.readMetadata( fileOne );
							Metadata metadata2 = ImageMetadataReader.readMetadata( fileTwo );
							Date date1 = metadata1.getDirectory(ExifDirectory.class).getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
							Date date2 = metadata2.getDirectory(ExifDirectory.class).getDate(ExifDirectory.TAG_DATETIME_ORIGINAL);
							difference = date1.getTime() - date2.getTime() + Integer.parseInt(delay.getText())*1000;
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
						}, ext).start(latePath);

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

	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}

