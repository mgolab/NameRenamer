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

import pl.edu.pk.java.emissionDateCheck.emissionCheck;
import pl.edu.pk.java.files.NameRenamer;
import pl.edu.pk.java.strategy.*;

public class RenamerGUI {
	private final static String newline = "\n";
	private JFrame mainFrame;
	//private JLabel headerLabel;
	private JPanel statusPanel;
	private JPanel controlPanel;
	//private JLabel msglabel;
	private String extension;
	private String path;
	private String destinationPath;
	private String[] contain;
	private String latePath;
	private String excelPath;
	private String picOne;
	private String picTwo;
	private long difference;
	private long numberOfContains;

	public RenamerGUI(){
		this.contain = new String[18];
		prepareGUI();
	}

	private void prepareGUI(){
		mainFrame = new JFrame("Name Renamer");
		mainFrame.setSize(425,450);
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

		final JTextField filmExtField = new JTextField("mp4",18);
		final JTextField filmPath = new JTextField("E:\\Torrent\\Pobrane\\",18);
		final JTextField filmDestinationPath = new JTextField("E:\\Torrent\\Brak napisów\\",18);
		final JTextField filmContains = new JTextField("YIFY;YTS",18);

		filmBoxPanel.add(new JLabel("Rozszerzenie:"));
		filmBoxPanel.add(filmExtField);
		filmBoxPanel.add(new JLabel("Ścieżka do folderu z plikami:"));
		filmBoxPanel.add(filmPath);
		filmBoxPanel.add(new JLabel("Ścieżka docelowa:"));
		filmBoxPanel.add(filmDestinationPath);
		filmBoxPanel.add(new JLabel("Nazwa zawiera:"));
		filmBoxPanel.add(filmContains);
		filmBoxPanel.add(new JLabel(""));

		final JTextField serialExtField = new JTextField("mp4",18);
		final JTextField serialPath = new JTextField("E:\\Torrent\\Pobrane\\",18);
		final JTextField serialDestinationPath = new JTextField("E:\\Torrent\\Seriale\\",18);
		final JTextField serialExcelPath = new JTextField("E:\\Torrent\\Seriale\\Serials.xls",18);
		final JTextField serialNotContains = new JTextField("YIFY;YTS;PL;DUB",18);

		final JPanel serialBoxPanel = new JPanel();
		serialBoxPanel.setLayout(new GridLayout(6,2));

		serialBoxPanel.add(new JLabel("Rozszerzenie:"));
		serialBoxPanel.add(serialExtField);
		serialBoxPanel.add(new JLabel("Ścieżka do folderu z plikami:"));
		serialBoxPanel.add(serialPath);
		serialBoxPanel.add(new JLabel("Ścieżka docelowa:"));
		serialBoxPanel.add(serialDestinationPath);
		serialBoxPanel.add(new JLabel("Ścieżka do pliku Excela:"));
		serialBoxPanel.add(serialExcelPath);
		serialBoxPanel.add(new JLabel("Pomijaj pliki zawierające:"));
		serialBoxPanel.add(serialNotContains);

		final JTextField picturesExtField = new JTextField("jpg",18);
		final JTextField latePicturesPath = new JTextField("E:\\Pics\\cellphone\\",18);
		final JTextField picturesPath = new JTextField("E:\\Pics\\camera\\",18);
		final JTextField pictureOne = new JTextField("E:\\Pics\\good.jpg",18);
		final JTextField pictureTwo = new JTextField("E:\\Pics\\late.jpg",18);
		final JTextField delay = new JTextField("10",18);

		JPanel picturesBoxPanel = new JPanel();
		picturesBoxPanel.setLayout(new GridLayout(6,2));

		picturesBoxPanel.add(new JLabel("Rozszerzenie:"));
		picturesBoxPanel.add(picturesExtField);
		picturesBoxPanel.add(new JLabel("Plik z dobrą datą:"));
		picturesBoxPanel.add(pictureOne);
		picturesBoxPanel.add(new JLabel("Plik z błędną datą:"));
		picturesBoxPanel.add(pictureTwo);
		picturesBoxPanel.add(new JLabel("Czas pomiędzy wykonaniem w sek:"));
		picturesBoxPanel.add(delay);
		picturesBoxPanel.add(new JLabel("Ścieżka do zdjęć z dobrą datą:"));
		picturesBoxPanel.add(picturesPath);
		picturesBoxPanel.add(new JLabel("Ścieżka do zdjęć z przesuniętą datą:"));
		picturesBoxPanel.add(latePicturesPath);

		final JPanel emissionCheckBoxPanel = new JPanel();
		emissionCheckBoxPanel.setLayout(new GridLayout(6,2));

		final JTextField emissionCheckExtField = new JTextField("mp4",18);
		final JTextField emissionCheckPath = new JTextField("E:\\Torrent\\Seriale\\",18);
		final JTextField emissionCheckExcelPath = new JTextField("E:\\Torrent\\Seriale\\Seriale.xls",18);

		emissionCheckBoxPanel.add(new JLabel("Rozszerzenia plików:"));
		emissionCheckBoxPanel.add(emissionCheckExtField);
		emissionCheckBoxPanel.add(new JLabel("Ścieżka do folderu z serialami:"));
		emissionCheckBoxPanel.add(emissionCheckPath);
		emissionCheckBoxPanel.add(new JLabel("Ścieżka do pliku Excela:"));
		emissionCheckBoxPanel.add(emissionCheckExcelPath);
		emissionCheckBoxPanel.add(new JLabel(""));
		
		panel.add("Filmy", filmBoxPanel);
		panel.add("Seriale", serialBoxPanel);
		panel.add("Zdjęcia", picturesBoxPanel);
		panel.add("Data emisji", emissionCheckBoxPanel);

		final DefaultComboBoxModel<String> panelName = new DefaultComboBoxModel<String>();

		panelName.addElement("Filmy");
		panelName.addElement("Seriale");
		panelName.addElement("Zdjęcia");
		panelName.addElement("Data emisji");

		final JComboBox<String> listCombo = new JComboBox<String>(panelName);    
		listCombo.setSelectedIndex(0);

		JScrollPane listComboScrollPane = new JScrollPane(listCombo);    

		final JTextArea statusText = new JTextArea(10,37);
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
					switch (listCombo.getSelectedIndex()) {
					case 0:
						commitButton.setText("Zmień nazwę");
						break;
					case 1:
						commitButton.setText("Zmień nazwę");
						break;
					case 2:
						commitButton.setText("Zmień nazwę");
						break;
					case 3:
						commitButton.setText("Sprawdź");
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
					extension = filmExtField.getText();
					path = filmPath.getText();
					destinationPath = filmDestinationPath.getText();
					ExtractContains(filmContains);
					if (!extension.equals("") && !path.equals("") && !destinationPath.equals("")){
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								for (int i = 0; i < numberOfContains; i++) {
									if(file.getName().contains(contain[i])){
										statusText.append("Znaleziono plik: " + file + newline);
										FilmRenameFile film = new FilmRenameFile(file,ext,destinationPath);
										statusText.append("ZMIENIONO:" + film.renameFile() + newline);
									}
								}
							}
						}, extension).start(path);
					}
					break;
				case 1:
					extension = serialExtField.getText();
					path = serialPath.getText();
					excelPath = serialExcelPath.getText();
					destinationPath = serialDestinationPath.getText();
					ExtractContains(serialNotContains);
					if (!extension.equals("") && !path.equals("") && !excelPath.equals("") && !destinationPath.equals("")){
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								boolean decide = true;
								for (int i=0;i<numberOfContains;i++) {
									if(file.getName().contains(contain[i])){
										decide = false;
										break;
									}
								}
								if(!file.getName().contains(" ") && decide){
									statusText.append(file + newline);
									SerialRenameFile serial = new SerialRenameFile(file,ext,destinationPath);
									statusText.append("ZMIENIONO:" + serial.renameFile(excelPath) + newline);
								}
							}
						}, extension).start(path);
					}
					break;
				case 2:
					extension = picturesExtField.getText();
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
					if (!extension.equals("") && !path.equals("") && !latePath.equals("") && !picOne.equals("") && !picTwo.equals("") && value){
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
						}, extension).start(latePath);

						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								statusText.append(file + newline);
								PictureSynchronizeFiles synchro = new PictureSynchronizeFiles(file,ext);
								statusText.append(synchro.renameFile(0) + newline);
							}
						}, extension).start(path);
					}
					break;
				case 3:
					extension = emissionCheckExtField.getText();
					path = emissionCheckPath.getText();
					excelPath = emissionCheckExcelPath.getText();
					if (!extension.equals("") && !path.equals("")){
						emissionCheck checkEmission = new emissionCheck(path,extension,excelPath);
						statusText.setText(checkEmission.checkDateOfEmission());
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

	public void ExtractContains(JTextField textField){
		int i = 0;
		numberOfContains = 1;
		String containsText = textField.getText();
		while(containsText.lastIndexOf(";")!=-1){
			contain[i++] = containsText.substring(containsText.lastIndexOf(";")+1);
			containsText = containsText.substring(0,containsText.lastIndexOf(";"));
			numberOfContains++;
		}
		contain[i] = containsText;
	}

	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}
