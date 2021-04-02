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

public class Vokabeltrainer {

	private JFrame frame;
	private JEditorPane backpageEditorPane;
	private JEditorPane frontpageEditorPane;
	private JComboBox<String> comboBoxKategorie;
	
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
				
		//*********************Menue*************************************
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu menu = new JMenu("Men\u00FC");
		menuBar.add(menu);
		
		JMenuItem menuItemLearn = new JMenuItem("Lernen");
		menu.add(menuItemLearn);
		
		JMenuItem menuItemExam = new JMenuItem("Pr\u00FCfen");
		menu.add(menuItemExam);
		
		JMenuItem menuItemAddVoc = new JMenuItem("Vokabeleditor");
		menu.add(menuItemAddVoc);
		
		
		//*********************EditorPage*************************************
		
		JPanel vocableEditorPanel = new JPanel();
		frame.getContentPane().add(vocableEditorPanel, BorderLayout.CENTER);
		vocableEditorPanel.setLayout(null);
		
		
		//Header of Page
		JLabel pageTitle = new JLabel("Vokabeleditor");
		pageTitle.setFont(new Font("Arial", Font.PLAIN, 27));
		pageTitle.setBounds(394, 19, 186, 42);
		vocableEditorPanel.add(pageTitle);
		
		JLabel lblKategorie = new JLabel("Kategorie");
		lblKategorie.setBounds(615, 71, 82, 23);
		vocableEditorPanel.add(lblKategorie);
		
		comboBoxKategorie = new JComboBox<String>();
		comboBoxKategorie.setBounds(717, 68, 242, 29);
		vocableEditorPanel.add(comboBoxKategorie);
		List<String> KategoriesNames = jsonDataManager.getKategorie();
		for(String kategorie : KategoriesNames)
			comboBoxKategorie.addItem(kategorie);
		
		//Frontside 
		JPanel frontpagePanel = new JPanel();
		frontpagePanel.setLayout(null);
		frontpagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vorderseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frontpagePanel.setBounds(17, 100, 942, 164);
		vocableEditorPanel.add(frontpagePanel);
		
		frontpageEditorPane = new JEditorPane();
		frontpageEditorPane.setBounds(17, 29, 908, 116);
		frontpagePanel.add(frontpageEditorPane);
		
		
		//Backside 
		JPanel backpagePanel = new JPanel();
		backpagePanel.setLayout(null);
		backpagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "R\u00FCckseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		backpagePanel.setBounds(17, 272, 942, 175);
		vocableEditorPanel.add(backpagePanel);
		
		backpageEditorPane = new JEditorPane();
		backpageEditorPane.setBounds(17, 29, 908, 126);
		backpagePanel.add(backpageEditorPane);
		
		
		//Footer
		JButton btnAddVoc = new JButton("Add Voc");
		btnAddVoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Data.CardData cardData = new Data.CardData(
							frontpageEditorPane.getText(),
							backpageEditorPane.getText(),
							comboBoxKategorie.getSelectedItem().toString());
					jsonDataManager.addCard(cardData);
				}
				catch(NullPointerException e) {
					// Some Parameters are not Set
					System.out.println("Kategorie, Frontpage or Backpage not Set: " + e);
				}
			}
		});
		btnAddVoc.setBounds(828, 452, 131, 31);
		vocableEditorPanel.add(btnAddVoc);
		
		
	}
}
