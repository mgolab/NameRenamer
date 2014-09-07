package pl.edu.pk.java.gui;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private GroupLayout mainFrameLayout;
	private String extension;
	private String path;
	private String destinationPath;
	private String[] contain;
	private String latePath;
	private String excelPath;
	private String picOne;
	private String picTwo;
	private String artistName;
	private String serialSeperator;
	private int choice;
	private int trackNumber;
	private long difference;
	private long numberOfContains;

	public RenamerGUI(){
		this.contain = new String[20];
		prepareGUI();
	}

	private void prepareGUI(){
		mainFrame = new JFrame("Name Renamer v1.8.0");
		mainFrame.setSize(425,510);
		mainFrame.setMinimumSize(new Dimension(425,510));

		mainFrameLayout = new GroupLayout(mainFrame.getContentPane());
		mainFrame.setLayout(mainFrameLayout);
		mainFrameLayout.setAutoCreateGaps(true);
		mainFrameLayout.setAutoCreateContainerGaps(true);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    

		mainFrame.setVisible(true);
	}

	public void createGUI(){  
		final JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setMaximumSize(new Dimension(1000,190));

		CardLayout layout = new CardLayout();
		layout.setHgap(8);
		layout.setVgap(8);
		panel.setLayout(layout);

		final JPanel filmBoxPanel = new JPanel();
		GroupLayout filmLayout = new GroupLayout(filmBoxPanel);
		filmBoxPanel.setLayout(filmLayout);
		filmLayout.setAutoCreateGaps(true);
		filmLayout.setAutoCreateContainerGaps(true);

		JLabel filmExtFieldLabel = new JLabel("Rozszerzenia plików:");
		final JTextField filmExtField = new JTextField("mp4",14);
		JLabel filmPathLabel = new JLabel("Ścieżka do folderu z filmami:");
		final JTextField filmPath = new JTextField("E:\\Torrent\\Pobrane",14);
		JLabel filmDestinationPathLabel = new JLabel("Ścieżka docelowa:");
		final JTextField filmDestinationPath = new JTextField("E:\\Torrent\\Brak napisów",14);
		JLabel filmNotContainsLabel = new JLabel("Nazwa zawiera:");
		final JTextField filmContains =  new JTextField("YIFY;YTS",14);

		filmLayout.setHorizontalGroup(filmLayout.createSequentialGroup()
				.addGroup(filmLayout.createParallelGroup(LEADING)
						.addComponent(filmExtFieldLabel)
						.addComponent(filmPathLabel)
						.addComponent(filmDestinationPathLabel)
						.addComponent(filmNotContainsLabel))
						.addGroup(filmLayout.createParallelGroup(LEADING)
								.addComponent(filmExtField)
								.addComponent(filmPath)
								.addComponent(filmDestinationPath)
								.addComponent(filmContains))
				);

		filmLayout.setVerticalGroup(filmLayout.createSequentialGroup()
				.addGroup(filmLayout.createParallelGroup(BASELINE)
						.addComponent(filmExtFieldLabel)
						.addComponent(filmExtField))
						.addGroup(filmLayout.createParallelGroup(BASELINE)
								.addComponent(filmPathLabel)
								.addComponent(filmPath))
								.addGroup(filmLayout.createParallelGroup(BASELINE)
										.addComponent(filmDestinationPathLabel)
										.addComponent(filmDestinationPath))
										.addGroup(filmLayout.createParallelGroup(BASELINE)
												.addComponent(filmNotContainsLabel)
												.addComponent(filmContains))
				);

		final JPanel serialBoxPanel = new JPanel();
		GroupLayout serialLayout = new GroupLayout(serialBoxPanel);
		serialBoxPanel.setLayout(serialLayout);
		serialLayout.setAutoCreateGaps(true);
		serialLayout.setAutoCreateContainerGaps(true);

		JLabel serialExtFieldLabel = new JLabel("Rozszerzenia plików:");
		final JTextField serialExtField = new JTextField("mp4",14);
		JLabel serialPathLabel = new JLabel("Ścieżka do folderu z serialami:");
		final JTextField serialPath = new JTextField("E:\\Torrent\\Pobrane",14);
		JLabel serialDestinationPathLabel = new JLabel("Ścieżka docelowa:");
		final JTextField serialDestinationPath = new JTextField("E:\\Torrent\\Seriale",14);
		JLabel serialExcelPathLabel = new JLabel("Ścieżka do pliku Excela:");
		final JTextField serialExcelPath = new JTextField("E:\\Torrent\\Seriale\\Seriale.xls",14);
		JLabel serialContainsLabel = new JLabel("Podaj seperator nazwy:");
		final JTextField serialContains = new JTextField("",14);
		JLabel serialNotContainsLabel = new JLabel("Pomijaj pliki zawierające:");
		final JTextField serialNotContains = new JTextField("YIFY;YTS;PL;DUB",14);

		serialLayout.setHorizontalGroup(serialLayout.createSequentialGroup()
				.addGroup(serialLayout.createParallelGroup(LEADING)
						.addComponent(serialExtFieldLabel)
						.addComponent(serialPathLabel)
						.addComponent(serialDestinationPathLabel)
						.addComponent(serialExcelPathLabel)
						.addComponent(serialContainsLabel)
						.addComponent(serialNotContainsLabel))
						.addGroup(serialLayout.createParallelGroup(LEADING)
								.addComponent(serialExtField)
								.addComponent(serialPath)
								.addComponent(serialDestinationPath)
								.addComponent(serialExcelPath)
								.addComponent(serialContains)
								.addComponent(serialNotContains))
				);

		serialLayout.setVerticalGroup(serialLayout.createSequentialGroup()
				.addGroup(serialLayout.createParallelGroup(BASELINE)
						.addComponent(serialExtFieldLabel)
						.addComponent(serialExtField))
						.addGroup(serialLayout.createParallelGroup(BASELINE)
								.addComponent(serialPathLabel)
								.addComponent(serialPath))
								.addGroup(serialLayout.createParallelGroup(BASELINE)
										.addComponent(serialDestinationPathLabel)
										.addComponent(serialDestinationPath))
										.addGroup(serialLayout.createParallelGroup(BASELINE)
												.addComponent(serialExcelPathLabel)
												.addComponent(serialExcelPath))
												.addGroup(serialLayout.createParallelGroup(BASELINE)
														.addComponent(serialContainsLabel)
														.addComponent(serialContains))
														.addGroup(serialLayout.createParallelGroup(BASELINE)
																.addComponent(serialNotContainsLabel)
																.addComponent(serialNotContains))
				);

		JPanel picturesBoxPanel = new JPanel();
		GroupLayout picturesLayout = new GroupLayout(picturesBoxPanel);
		picturesBoxPanel.setLayout(picturesLayout);
		picturesLayout.setAutoCreateGaps(true);
		picturesLayout.setAutoCreateContainerGaps(true);

		JLabel picturesExtFieldLabel = new JLabel("Rozszerzenia plików:");
		final JTextField picturesExtField = new JTextField("jpg",14);
		JLabel latePicturesPathLabel = new JLabel("Ścieżka do zdjęć z przesuniętą datą:");
		final JTextField latePicturesPath = new JTextField("E:\\Pics\\cellphone",14);
		JLabel picturesPathLabel = new JLabel("Ścieżka do zdjęć z dobrą datą:");
		final JTextField picturesPath = new JTextField("E:\\Pics\\camera",14);
		JLabel pictureOneLabel = new JLabel("Plik z dobrą datą:");
		final JTextField pictureOne = new JTextField("E:\\Pics\\good.jpg",14);
		JLabel pictureTwoLabel = new JLabel("Plik z błędną datą:");
		final JTextField pictureTwo = new JTextField("E:\\Pics\\late.jpg",14);
		JLabel delayLabel = new JLabel("Czas pomiędzy wykonaniem w sek:");
		final JTextField delay = new JTextField("14",14);

		picturesLayout.setHorizontalGroup(picturesLayout.createSequentialGroup()
				.addGroup(picturesLayout.createParallelGroup(LEADING)
						.addComponent(picturesExtFieldLabel)
						.addComponent(picturesPathLabel)
						.addComponent(latePicturesPathLabel)
						.addComponent(pictureOneLabel)
						.addComponent(pictureTwoLabel)
						.addComponent(delayLabel))
						.addGroup(picturesLayout.createParallelGroup(LEADING)
								.addComponent(picturesExtField)
								.addComponent(picturesPath)
								.addComponent(latePicturesPath)
								.addComponent(pictureOne)
								.addComponent(pictureTwo)
								.addComponent(delay))
				);

		picturesLayout.setVerticalGroup(picturesLayout.createSequentialGroup()
				.addGroup(picturesLayout.createParallelGroup(BASELINE)
						.addComponent(picturesExtFieldLabel)
						.addComponent(picturesExtField))
						.addGroup(picturesLayout.createParallelGroup(BASELINE)
								.addComponent(picturesPathLabel)
								.addComponent(picturesPath))
								.addGroup(picturesLayout.createParallelGroup(BASELINE)
										.addComponent(latePicturesPathLabel)
										.addComponent(latePicturesPath))
										.addGroup(picturesLayout.createParallelGroup(BASELINE)
												.addComponent(pictureOneLabel)
												.addComponent(pictureOne))
												.addGroup(picturesLayout.createParallelGroup(BASELINE)
														.addComponent(pictureTwoLabel)
														.addComponent(pictureTwo))
														.addGroup(picturesLayout.createParallelGroup(BASELINE)
																.addComponent(delayLabel)
																.addComponent(delay))
				);

		// Sprawdź datę emisji
		final JPanel emissionCheckBoxPanel = new JPanel();
		GroupLayout emissionCheckLayout = new GroupLayout(emissionCheckBoxPanel);
		emissionCheckBoxPanel.setLayout(emissionCheckLayout);
		emissionCheckLayout.setAutoCreateGaps(true);
		emissionCheckLayout.setAutoCreateContainerGaps(true);

		JLabel emissionCheckExtFieldLabel = new JLabel("Rozszerzenia plików:");
		final JTextField emissionCheckExtField = new JTextField("mp4",14);
		JLabel emissionCheckPathLabel = new JLabel("Ścieżka do folderu z serialami:");
		final JTextField emissionCheckPath = new JTextField("E:\\Torrent\\Seriale",14);
		JLabel emissionCheckExcelPathLabel = new JLabel("Ścieżka do pliku Excela:");
		final JTextField emissionCheckExcelPath = new JTextField("E:\\Torrent\\Seriale\\Seriale.xls",14);

		emissionCheckLayout.setHorizontalGroup(emissionCheckLayout.createSequentialGroup()
				.addGroup(emissionCheckLayout.createParallelGroup(LEADING)
						.addComponent(emissionCheckExtFieldLabel)
						.addComponent(emissionCheckPathLabel)
						.addComponent(emissionCheckExcelPathLabel))
						.addGroup(emissionCheckLayout.createParallelGroup(LEADING)
								.addComponent(emissionCheckExtField)
								.addComponent(emissionCheckPath)
								.addComponent(emissionCheckExcelPath))
				);

		emissionCheckLayout.setVerticalGroup(emissionCheckLayout.createSequentialGroup()
				.addGroup(emissionCheckLayout.createParallelGroup(BASELINE)
						.addComponent(emissionCheckExtFieldLabel)
						.addComponent(emissionCheckExtField))
						.addGroup(emissionCheckLayout.createParallelGroup(BASELINE)
								.addComponent(emissionCheckPathLabel)
								.addComponent(emissionCheckPath))
								.addGroup(emissionCheckLayout.createParallelGroup(BASELINE)
										.addComponent(emissionCheckExcelPathLabel)
										.addComponent(emissionCheckExcelPath))
				);

		// Zmień nazwę plików muzycznych
		final JPanel musicBoxPanel = new JPanel();
		GroupLayout musicLayout = new GroupLayout(musicBoxPanel);
		musicBoxPanel.setLayout(musicLayout);
		musicLayout.setAutoCreateGaps(true);
		musicLayout.setAutoCreateContainerGaps(true);

		JLabel musicExtFieldLabel = new JLabel("Rozszerzenie:");
		final JTextField musicExtField = new JTextField("mp3",14);
		JLabel musicPathLabel = new JLabel("Ścieżka do folderu z plikami:");
		final JTextField musicPath = new JTextField("E:\\Torrent\\Music_test\\",14);
		JLabel musicDestinationPathLabel = new JLabel("Ścieżka docelowa:");
		final JTextField musicDestinationPath = new JTextField("E:\\Torrent\\Music_test\\",14);
		JLabel musicArtistNameLabel = new JLabel("Dodaj wykonawcę:");
		final JTextField musicArtistName = new JTextField("",14);
		JLabel musicFileWithNamesLabel = new JLabel("Ścieżka do pliku z nazwami:");
		final JTextField musicFileWithNames = new JTextField("E:\\test\\Dicho.txt",14);
		JLabel musicTrackNumberLabel = new JLabel("Numer utworu:");
		final JRadioButton addMusicTrackNumber = new JRadioButton ("Dodaj");
		final JRadioButton extractMusicTrackNumber = new JRadioButton ("Wydobądź");
		final JRadioButton noMusicTrackNumber = new JRadioButton ("Brak");
		final ButtonGroup musicTrackNumberGroup = new ButtonGroup();

		musicTrackNumberGroup.add(addMusicTrackNumber);
		musicTrackNumberGroup.add(extractMusicTrackNumber);
		musicTrackNumberGroup.add(noMusicTrackNumber);
		noMusicTrackNumber.setSelected(true);

		musicLayout.setHorizontalGroup(musicLayout.createSequentialGroup()
				.addGroup(musicLayout.createParallelGroup(LEADING)
						.addComponent(musicExtFieldLabel)
						.addComponent(musicPathLabel)
						.addComponent(musicDestinationPathLabel)
						.addComponent(musicArtistNameLabel)
						.addComponent(musicFileWithNamesLabel)
						.addGroup(musicLayout.createSequentialGroup()
								.addComponent(musicTrackNumberLabel)
								.addComponent(addMusicTrackNumber)))
								.addGroup(musicLayout.createParallelGroup(LEADING)
										.addComponent(musicExtField)
										.addComponent(musicPath)
										.addComponent(musicDestinationPath)
										.addComponent(musicArtistName)
										.addComponent(musicFileWithNames)
										.addGroup(musicLayout.createSequentialGroup()
												.addComponent(extractMusicTrackNumber)
												.addComponent(noMusicTrackNumber)))
				);

		musicLayout.setVerticalGroup(musicLayout.createSequentialGroup()
				.addGroup(musicLayout.createParallelGroup(BASELINE)
						.addComponent(musicExtFieldLabel)
						.addComponent(musicExtField))
						.addGroup(musicLayout.createParallelGroup(BASELINE)
								.addComponent(musicPathLabel)
								.addComponent(musicPath))
								.addGroup(musicLayout.createParallelGroup(BASELINE)
										.addComponent(musicDestinationPathLabel)
										.addComponent(musicDestinationPath))
										.addGroup(musicLayout.createParallelGroup(BASELINE)
												.addComponent(musicArtistNameLabel)
												.addComponent(musicArtistName))
												.addGroup(musicLayout.createParallelGroup(BASELINE)
														.addComponent(musicFileWithNamesLabel)
														.addComponent(musicFileWithNames))
														.addGroup(musicLayout.createParallelGroup(BASELINE)
																.addComponent(musicTrackNumberLabel)
																.addComponent(addMusicTrackNumber)
																.addComponent(extractMusicTrackNumber)
																.addComponent(noMusicTrackNumber))
				);

		panel.add("Filmy", filmBoxPanel);
		panel.add("Seriale", serialBoxPanel);
		panel.add("Zdjęcia", picturesBoxPanel);
		panel.add("Data emisji", emissionCheckBoxPanel);
		panel.add("Muzyka", musicBoxPanel);

		final DefaultComboBoxModel<String> panelName = new DefaultComboBoxModel<String>();

		panelName.addElement("Filmy");
		panelName.addElement("Seriale");
		panelName.addElement("Zdjęcia");
		panelName.addElement("Data emisji");
		panelName.addElement("Muzyka");

		final JComboBox<String> listCombo = new JComboBox<String>(panelName);    
		listCombo.setSelectedIndex(0);

		JScrollPane listComboScrollPane = new JScrollPane(listCombo);
		listComboScrollPane.setMaximumSize(new Dimension(120,30));
		listComboScrollPane.setMinimumSize(new Dimension(120,30));

		final JTextArea statusTextArea = new JTextArea(7,30);
		statusTextArea.setEditable(false);

		JScrollPane scrollPanel = new JScrollPane(statusTextArea);	

		final JButton commitButton = new JButton("Zmień nazwę");

		listCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if (listCombo.getSelectedIndex() != -1) {  
					CardLayout cardLayout = (CardLayout)(panel.getLayout());
					cardLayout.show(panel, 
							(String)listCombo.getItemAt(listCombo.getSelectedIndex()));
					statusTextArea.setText("");
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
					case 4:
						commitButton.setText("Zmień nazwę");
						break;
					}
				}
			}
		}); 	

		commitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				statusTextArea.setText("");
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
										statusTextArea.append("Znaleziono plik: " + file + newline);
										FilmRenameFile film = new FilmRenameFile(file,ext,destinationPath);
										statusTextArea.append("ZMIENIONO:" + film.renameFile() + newline);
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
					if(serialSeperator==null){
						statusTextArea.setText("Podaj seperator nazwy");
						break;
					}
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
								if(file.getName().contains(serialSeperator) && decide){
									statusTextArea.append(file + newline);
									SerialRenameFile serial = new SerialRenameFile(file,ext,path,destinationPath);
									statusTextArea.append("ZMIENIONO:" + serial.renameFile(excelPath) + newline);
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
							statusTextArea.append("Opóźnienie nie jest liczbą" + newline);
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
								statusTextArea.append(file + newline);
								PictureSynchronizeFiles synchro = new PictureSynchronizeFiles(file,ext);
								statusTextArea.append(synchro.renameFile(difference) + newline);
							}
						}, extension).start(latePath);

						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								statusTextArea.append(file + newline);
								PictureSynchronizeFiles synchro = new PictureSynchronizeFiles(file,ext);
								statusTextArea.append(synchro.renameFile(0) + newline);
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
						statusTextArea.setText(checkEmission.checkDateOfEmission());
					}
					break;
				case 4:
					extension = musicExtField.getText();
					path = musicPath.getText();
					destinationPath = musicDestinationPath.getText();
					artistName = musicArtistName.getText();
					if (!extension.equals("") && !path.equals("") && !destinationPath.equals("")){
						choice = 0;
						if(!artistName.equals("")){
							choice += 4;
						}
						if(addMusicTrackNumber.isSelected()){
							choice += 1;
						} else if(extractMusicTrackNumber.isSelected()){
							choice += 2;
						}
						trackNumber = 1;
						new NameRenamer(new NameRenamer.Strategy() {
							public void process(File file, String ext) {
								statusTextArea.append("Znaleziono plik: " + file + newline);
								MusicRenameFile music = new MusicRenameFile(file,ext,destinationPath,artistName,trackNumber);
								statusTextArea.append("ZMIENIONO:" + music.renameFile(choice) + newline);
								trackNumber++;
							}
						}, extension).start(path);
					}
					break;
				default:
					break;
				}              
			}
		}); 

		JLabel mainFrameLabel1 = new JLabel("Wybierz rodzaj plików: ");
		JLabel mainFrameLabel2 = new JLabel("Status plików: ");

		mainFrameLayout.setHorizontalGroup(mainFrameLayout.createSequentialGroup()
				.addGroup(mainFrameLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(mainFrameLayout.createSequentialGroup()
								.addGroup(mainFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(mainFrameLabel1))
										.addGroup(mainFrameLayout.createParallelGroup()
												.addComponent(listComboScrollPane)))
												.addComponent(panel)
												.addComponent(commitButton)
												.addComponent(mainFrameLabel2)
												.addComponent(scrollPanel))
				);

		mainFrameLayout.setVerticalGroup(mainFrameLayout.createSequentialGroup()
				.addGroup(mainFrameLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(mainFrameLabel1)
						.addComponent(listComboScrollPane))
						.addGroup(mainFrameLayout.createParallelGroup(BASELINE)
								.addComponent(panel))
								.addGroup(mainFrameLayout.createParallelGroup(BASELINE)
										.addComponent(commitButton))
										.addGroup(mainFrameLayout.createParallelGroup(BASELINE)
												.addComponent(mainFrameLabel2))
												.addGroup(mainFrameLayout.createParallelGroup(BASELINE)
														.addComponent(scrollPanel))
				);

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