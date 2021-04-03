package Main;
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

import Data.CardData;
import Data.JsonDataManager;

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

public class Vokabeltrainer {
	
	//global and Menue
	private JFrame frame;
	private JLayeredPane layeredPane;
	private JPanel vocableEditorPanel;
	private JPanel LearnPanel;
	
	private List<String> KategoriesNames;
	JsonDataManager dataManager;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vokabeltrainer window = new Vokabeltrainer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Vokabeltrainer() {
		dataManager = new JsonDataManager();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Data.JsonDataManager jsonDataManager = new Data.JsonDataManager();
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		JLabel pageTitle = new JLabel("Vokabeleditor");
		pageTitle.setFont(new Font("Arial", Font.PLAIN, 27));
		pageTitle.setBounds(394, 45, 186, 42);
		frame.getContentPane().add(pageTitle);
		
		
		//*********************Menue*************************************
		
		layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu menu = new JMenu("Men\u00FC");
		menuBar.add(menu);
		
		JMenuItem menuItemLearn = new JMenuItem("Lernen");
		menuItemLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
				{
					switchPanels(LearnPanel);
				}
			});
		menu.add(menuItemLearn);
		
		
		JMenuItem menuItemExam = new JMenuItem("Pr\u00FCfen");
		menu.add(menuItemExam);
		menuItemExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//switchPanels(ExamPanel);
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
		
		
		//*********************Editor*************************************
		
		vocableEditorPanel = new JPanel();
		JLabel lblKategorie = new JLabel("Kategorie");
		JComboBox<String> comboBoxKategorie = new JComboBox<String>();
		JPanel frontpageEditorPanel = new JPanel();
		JEditorPane frontpageEditorPane = new JEditorPane();
		JPanel backpageEditorPanel = new JPanel();
		JEditorPane backpageEditorPane = new JEditorPane();
		JButton btnAddVoc = new JButton("Add Voc");
		
		
		layeredPane.setLayer(vocableEditorPanel, 0);
		vocableEditorPanel.setBounds(0, 55, 975, 445);
		layeredPane.add(vocableEditorPanel);
		vocableEditorPanel.setLayout(null);
		
		
		lblKategorie.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKategorie.setBounds(615, 19, 82, 23);
		vocableEditorPanel.add(lblKategorie);
		
		
		comboBoxKategorie.setBounds(717, 16, 242, 29);
		vocableEditorPanel.add(comboBoxKategorie);
		KategoriesNames = jsonDataManager.getKategorie();
		for(String kategorie : KategoriesNames)
			comboBoxKategorie.addItem(kategorie);
		
		
		frontpageEditorPanel.setBounds(17, 61, 942, 164);
		frontpageEditorPanel.setLayout(null);
		frontpageEditorPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vorderseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vocableEditorPanel.add(frontpageEditorPanel);
		
		
		frontpageEditorPane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frontpageEditorPane.setForeground(Color.BLACK);
		frontpageEditorPane.setBounds(17, 29, 908, 116);
		frontpageEditorPanel.add(frontpageEditorPane);
		
		
		backpageEditorPanel.setBounds(17, 226, 942, 175);
		backpageEditorPanel.setLayout(null);
		backpageEditorPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "R\u00FCckseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vocableEditorPanel.add(backpageEditorPanel);
		
		
		backpageEditorPane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		backpageEditorPane.setForeground(Color.BLACK);
		backpageEditorPane.setBounds(17, 29, 908, 126);
		backpageEditorPanel.add(backpageEditorPane);
		
		
		btnAddVoc.setBounds(828, 407, 131, 31);
		vocableEditorPanel.add(btnAddVoc);
		btnAddVoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dataManager.addCard(new CardData(frontpageEditorPane.getText(), backpageEditorPane.getText(), comboBoxKategorie.getSelectedItem().toString(),1));
			}
		});
		
		//*********************Learn*************************************
		
		LearnPanel = new JPanel();
		JComboBox<String> comboBoxPhaseLearn = new JComboBox<String>();
		JLabel lblKategorieLearn = new JLabel("Kategorie");
		JComboBox<String> comboBoxKategorieLearn = new JComboBox<String>();
		JPanel frontpageLearnPanel = new JPanel();
		JEditorPane frontpageLearnPane = new JEditorPane();
		JPanel backpageLearnPanel = new JPanel();
		JEditorPane backpageLearnPane = new JEditorPane();
		JLabel lblPhaseLearn = new JLabel("Phase");
		
		
		LearnPanel.setBounds(0, 55, 975, 445);
		layeredPane.setLayer(LearnPanel, 1);
		layeredPane.add(LearnPanel);
		LearnPanel.setLayout(null);
		
		
		lblKategorieLearn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKategorieLearn.setBounds(615, 19, 82, 23);
		LearnPanel.add(lblKategorieLearn);
		
		
		comboBoxKategorieLearn.setBounds(717, 16, 242, 29);
		LearnPanel.add(comboBoxKategorieLearn);
		KategoriesNames = jsonDataManager.getKategorie();
		for(String kategorie : KategoriesNames)
			comboBoxKategorieLearn.addItem(kategorie);
		comboBoxKategorieLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dataManager.openCard( Integer.parseInt(comboBoxPhaseLearn.getSelectedItem().toString()), comboBoxKategorieLearn.getSelectedItem().toString());
			}
		});
		
		
		frontpageLearnPanel.setBounds(17, 61, 942, 164);
		frontpageLearnPanel.setLayout(null);
		frontpageLearnPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vorderseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		LearnPanel.add(frontpageLearnPanel);
		
		
		frontpageLearnPane.setForeground(Color.BLACK);
		frontpageLearnPane.setBackground(Color.WHITE);
		frontpageLearnPane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frontpageLearnPane.setEnabled(false);
		frontpageLearnPane.setEditable(false);
		frontpageLearnPane.setBounds(17, 29, 908, 116);
		frontpageLearnPanel.add(frontpageLearnPane);
		
		
		backpageLearnPanel.setBounds(17, 226, 942, 175);
		backpageLearnPanel.setLayout(null);
		backpageLearnPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "R\u00FCckseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		LearnPanel.add(backpageLearnPanel);
		
		
		backpageLearnPane.setFont(new Font("Tahoma", Font.PLAIN, 22));
		backpageLearnPane.setForeground(Color.BLACK);
		backpageLearnPane.setBounds(17, 29, 908, 126);
		backpageLearnPanel.add(backpageLearnPane);
		
		
		lblPhaseLearn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPhaseLearn.setBounds(385, 19, 82, 23);
		LearnPanel.add(lblPhaseLearn);
		
		
		comboBoxPhaseLearn.setBounds(487, 16, 56, 29);
		comboBoxPhaseLearn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int phase = Integer.parseInt(comboBoxPhaseLearn.getSelectedItem().toString());
				String kategorie =comboBoxKategorieLearn.getSelectedItem().toString();
				//System.out.println("phase: "+ phase + " | kategorie: " + kategorie);
				dataManager.openCard( phase, kategorie);
			}
		});
		LearnPanel.add(comboBoxPhaseLearn);
		for(int phaseNr =1; phaseNr <=6 ; phaseNr++)
			comboBoxPhaseLearn.addItem(""+phaseNr);
		
		
	}
	
	
	public void switchPanels(JPanel panel) {
		System.out.println("hi");
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	public void setCardQue(List<CardData> cards) {
		//TODO set Card Que
		//this.CardQue = 
	}
	
}
