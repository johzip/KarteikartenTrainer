package Adapters;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.border.TitledBorder;

import Application.JsonDataManager;
import Domain.CardData;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import java.awt.Color;

public class VokabeltrainerGUI {
	
	//global and Menue
	private JFrame frame;
	private JLayeredPane layeredPane;
	private JPanel vocableEditorPanel;
	private JPanel learnPanel;
	private JLabel pageTitle;
	private JPanel examPanel;
	
	//backFront-Panel
	private JEditorPane frontpagePane;
	private JEditorPane backpagePane;
	private JPanel editorPanel;
	
	//Exam
	private JButton btn_exam_Correkt;
	private JButton btn_exam_submit;
	private Boolean isChecked = false;
	public List<CardData> cardQueue;
	
	private int queueCounter = 0;
	private CardData displayedCard;
	
	private List<String> KategoriesNames;
	JsonDataManager dataManager;
	private JTextField textAddKategorie;
	

	/**
	 * Create the application.
	 */
	public VokabeltrainerGUI() {
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dataManager = new JsonDataManager();
		
		//****************LEARN*************************
		learnPanel = new JPanel();
		JComboBox<String> comboBoxPhaseLearn = new JComboBox<String>();
		JLabel lblKategorieLearn = new JLabel("Kategorie");
		JComboBox<String> comboBoxKategorieLearn = new JComboBox<String>();
		JLabel lblPhaseLearn = new JLabel("Phase");
		
		//***************EXAM***************************
		JLabel lblKategorieExam = new JLabel("Kategorie");
		JComboBox<String> comboBoxKategorieExam = new JComboBox<String>();
		JLabel lblPhaseExam = new JLabel("Phase");
		JComboBox<String> comboBoxPhaseExam = new JComboBox<String>();
		
		//***************EDITOR*************************
		vocableEditorPanel = new JPanel();
		JLabel lblKategorie = new JLabel("Kategorie");
		JComboBox<String> comboBoxKategorie = new JComboBox<String>();
		JButton btnAddVoc = new JButton("Add Voc");
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		pageTitle = new JLabel("Vokabeltrainer");
		pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pageTitle.setFont(new Font("Arial", Font.PLAIN, 27));
		pageTitle.setBounds(375, 45, 212, 42);
		frame.getContentPane().add(pageTitle);
		
		
		//*********************Menue*************************************
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 35, 976, 502);
		frame.getContentPane().add(layeredPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 976, 35);
		frame.getContentPane().add(menuBar);
		
		JMenu menu = new JMenu("Men\u00FC");
		menuBar.add(menu);
		
		JMenuItem menuItemLearn = new JMenuItem("Lernen");
		menuItemLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
				{
					switchPanels(learnPanel);
					updateCardView(comboBoxKategorieLearn, comboBoxPhaseLearn);
				}
			});
		menu.add(menuItemLearn);
		
		
		JMenuItem menuItemExam = new JMenuItem("Pr\u00FCfen");
		menu.add(menuItemExam);
		menuItemExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				switchPanels(examPanel);
				updateCardView(comboBoxKategorieExam, comboBoxPhaseExam);
			}
		});
		
		JMenuItem menuItemAddVoc = new JMenuItem("Vokabeleditor");
		menu.add(menuItemAddVoc);
		menuItemAddVoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				switchPanels(vocableEditorPanel);
			}
		});
		
		//*******************FrontBackPanel*******************************
		editorPanel = new JPanel();
		layeredPane.setLayer(editorPanel, 4);
		editorPanel.setLayout(null);
		editorPanel.setBounds(17, 111, 941, 335);
		layeredPane.add(editorPanel);
		
		JPanel frontpagePanel = new JPanel();
		frontpagePanel.setLayout(null);
		frontpagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vorderseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frontpagePanel.setBounds(0, 0, 937, 162);
		editorPanel.add(frontpagePanel);
		
		frontpagePane = new JEditorPane();
		frontpagePane.setForeground(Color.BLACK);
		frontpagePane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frontpagePane.setBackground(Color.WHITE);
		frontpagePane.setBounds(17, 29, 908, 116);
		frontpagePane.setEnabled(false);
		frontpagePane.setEditable(false);
		frontpagePanel.add(frontpagePane);
		
		JPanel backpagePanel = new JPanel();
		backpagePanel.setLayout(null);
		backpagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "R\u00FCckseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		backpagePanel.setBounds(0, 165, 937, 169);
		editorPanel.add(backpagePanel);
		
		backpagePane = new JEditorPane();
		backpagePane.setForeground(Color.BLACK);
		backpagePane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		backpagePane.setBounds(17, 29, 908, 126);
		backpagePanel.add(backpagePane);
		
		
		
		
		
		//*********************Editor*************************************
		
		layeredPane.setLayer(vocableEditorPanel, 2);
		vocableEditorPanel.setBounds(0, 55, 975, 445);
		layeredPane.add(vocableEditorPanel);
		vocableEditorPanel.setLayout(null);
		
		
		lblKategorie.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKategorie.setBounds(615, 19, 82, 23);
		vocableEditorPanel.add(lblKategorie);
		
		
		comboBoxKategorie.setBounds(717, 16, 242, 29);
		vocableEditorPanel.add(comboBoxKategorie);
		fillKathegorieBoxes(dataManager, comboBoxKategorie);
		
		
		textAddKategorie = new JTextField();
		textAddKategorie.setBounds(369, 16, 166, 29);
		vocableEditorPanel.add(textAddKategorie);
		textAddKategorie.setColumns(10);
		
		JButton btnAddKategorie = new JButton("+");
		btnAddKategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addKategorie(comboBoxKategorie);
			}

		});
		btnAddKategorie.setBounds(535, 16, 46, 29);
		vocableEditorPanel.add(btnAddKategorie);
		
		btnAddVoc.setBounds(828, 407, 131, 31);
		vocableEditorPanel.add(btnAddVoc);
		btnAddVoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				addCard(comboBoxKategorie);
			}
			
		});
		
		//*********************Learn*************************************
		
		
		learnPanel.setBounds(0, 55, 975, 445);
		layeredPane.setLayer(learnPanel, 0);
		layeredPane.add(learnPanel);
		learnPanel.setLayout(null);
		
		
		lblKategorieLearn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKategorieLearn.setBounds(615, 19, 82, 23);
		learnPanel.add(lblKategorieLearn);
		
		
		comboBoxKategorieLearn.setBounds(717, 16, 242, 29);
		learnPanel.add(comboBoxKategorieLearn);
		fillKathegorieBoxes(dataManager, comboBoxKategorieLearn);
		comboBoxKategorieLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				updateCardView(comboBoxKategorieLearn, comboBoxPhaseLearn);
			}

		});
		
		lblPhaseLearn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPhaseLearn.setBounds(385, 19, 82, 23);
		learnPanel.add(lblPhaseLearn);
		
		comboBoxPhaseLearn.setBounds(487, 16, 56, 29);
		comboBoxPhaseLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				updateCardView(comboBoxKategorieLearn, comboBoxPhaseLearn);
			}
		});
		learnPanel.add(comboBoxPhaseLearn);
		for(int phaseNr =1; phaseNr <=6 ; phaseNr++)
			comboBoxPhaseLearn.addItem(""+phaseNr);
		
		JButton btn_learn_next = new JButton("n\u00E4chstes");
		btn_learn_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayNextCard();
			}
		});
		btn_learn_next.setBounds(820, 405, 131, 31);
		learnPanel.add(btn_learn_next);
		
		JButton btn_learn_Check = new JButton("\u00DCberpr\u00FCfen");
		btn_learn_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkifCorrectLearn();
			}

		});
		btn_learn_Check.setBounds(672, 405, 131, 31);
		learnPanel.add(btn_learn_Check);
		
		
		//******************************EXAM*****************************
		examPanel = new JPanel();
		
		
		layeredPane.setLayer(examPanel, 3);
		examPanel.setLayout(null);
		examPanel.setBounds(0, 55, 975, 445);
		layeredPane.add(examPanel);
		
		lblKategorieExam.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKategorieExam.setBounds(615, 19, 82, 23);
		examPanel.add(lblKategorieExam);
		
		comboBoxKategorieExam.setBounds(717, 16, 242, 29);
		examPanel.add(comboBoxKategorieExam);
		fillKathegorieBoxes(dataManager, comboBoxKategorieExam);
		comboBoxKategorieExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				updateCardView(comboBoxKategorieExam, comboBoxPhaseExam);
			}

		});
		
		lblPhaseExam.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPhaseExam.setBounds(385, 19, 82, 23);
		examPanel.add(lblPhaseExam);
		
		comboBoxPhaseExam.setBounds(487, 16, 56, 29);
		examPanel.add(comboBoxPhaseExam);
		for(int phaseNr =1; phaseNr <=6 ; phaseNr++)
			comboBoxPhaseExam.addItem(""+phaseNr);
		comboBoxPhaseExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				updateCardView(comboBoxKategorieExam, comboBoxPhaseExam);
			}
		});
		
		btn_exam_submit = new JButton("Abgaben");
		btn_exam_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit_Input();
			}
		});
		btn_exam_submit.setBounds(820, 405, 131, 31);
		examPanel.add(btn_exam_submit);
		
		btn_exam_Correkt = new JButton("Korrekt");
		btn_exam_Correkt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submittAsCorrectInput();
			}

			

		});
		btn_exam_Correkt.setBounds(672, 405, 131, 31);
		examPanel.add(btn_exam_Correkt);
		btn_exam_Correkt.setEnabled(false);
		
		switchPanels(vocableEditorPanel);
	}

	private void fillKathegorieBoxes(Application.JsonDataManager jsonDataManager, JComboBox<String> comboBoxKategorieLearn) {
		KategoriesNames = jsonDataManager.getKategorie();
		for(String kategorie : KategoriesNames)
			comboBoxKategorieLearn.addItem(kategorie);
	}
	
	private void displayNextCard() {
		isChecked = false;
		backpagePane.setText("");
		backpagePane.setForeground(Color.BLACK);
		try {
			displayedCard = this.cardQueue.get(queueCounter);
			frontpagePane.setText(displayedCard.getFrontSide());
			queueCounter++;
		}catch(IndexOutOfBoundsException ex) {
			frontpagePane.setText("Es giebt keine Karten mehr in der ausgewählten Phase, von dieser Kategorie");
		}
	}

	//TODO: hier refactor in eigene Klasse oder dahin wo aufgerufen du HUND
	private void checkifCorrectLearn() {
		if(checkIfCorrect(backpagePane.getText(), displayedCard.getBackSide())) {
			backpagePane.setForeground(Color.green);
		}else {
			backpagePane.setForeground(Color.red);
		}
	}
	
	//TODO: hier refactor in eigene Klasse oder dahin wo aufgerufen du HUND
	private void checkifCorrectExam() {
		if(checkIfCorrect(backpagePane.getText(), displayedCard.getBackSide())) {
			backpagePane.setForeground(Color.green);
			dataManager.changePhase(displayedCard,true);
		}else {
			backpagePane.setForeground(Color.red);
			btn_exam_Correkt.setEnabled(true);
		}
		btn_exam_submit.setText("Nächstes");
		isChecked = true;
	}
	
	private boolean checkIfCorrect(String input, String correctAnswer) {
		if(input.equals(correctAnswer))
			return true;
		return false;
	}

	private void startQueue() {
		queueCounter = 0;
		displayNextCard();
		
	}
	
	private void updateCardView(JComboBox<String> comboBoxKategorieExam, JComboBox<String> comboBoxPhaseExam) {
		int phase = Integer.parseInt(comboBoxPhaseExam.getSelectedItem().toString());
		String kategorie =comboBoxKategorieExam.getSelectedItem().toString();
		cardQueue = dataManager.loadCards( phase, kategorie);
		startQueue();
	}
	
	public void switchPanels(JPanel panel) {
		backpagePane.setText("");
		frontpagePane.setText("");
		if(panel.equals(vocableEditorPanel)) {
			frontpagePane.setEnabled(true);
			frontpagePane.setEditable(true);
			pageTitle.setText("Vokabeleditor");
		}else {
			if(panel.equals(learnPanel))
				pageTitle.setText("Übungsmodus");
			else
				pageTitle.setText("Prüfungsmodus");
			frontpagePane.setEnabled(false);
			frontpagePane.setEditable(false);
		}
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.add(editorPanel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	private void submit_Input() {
		if(!isChecked) {
			checkifCorrectExam();
		}else {
			submittAsCorrectInput();
		}
	}
	
	private void submittAsCorrectInput() {
		dataManager.changePhase(displayedCard,true);
		displayNextCard();
		btn_exam_Correkt.setEnabled(false);
	}
	
	private void addCard(JComboBox<String> comboBoxKategorie) {
		dataManager.addCard(new CardData(frontpagePane.getText(), backpagePane.getText(), comboBoxKategorie.getSelectedItem().toString(),1));
		frontpagePane.setText("");
		backpagePane.setText("");
	}
	

	private void addKategorie(JComboBox<String> comboBoxKategorie) {
		dataManager.addKategorie(textAddKategorie.getText());
		fillKathegorieBoxes(dataManager, comboBoxKategorie);
	}
}
