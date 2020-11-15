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

public class Vokabeltrainer {

	private JFrame frame;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		JPanel vocableEditorPanel = new JPanel();
		frame.getContentPane().add(vocableEditorPanel, BorderLayout.CENTER);
		vocableEditorPanel.setLayout(null);
		
		JPanel frontpagePanel = new JPanel();
		frontpagePanel.setLayout(null);
		frontpagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vorderseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frontpagePanel.setBounds(17, 100, 942, 164);
		vocableEditorPanel.add(frontpagePanel);
		
		JEditorPane frontpageEditorPane = new JEditorPane();
		frontpageEditorPane.setBounds(17, 29, 908, 116);
		frontpagePanel.add(frontpageEditorPane);
		
		JLabel lblNewLabel = new JLabel("Vokabeleditor");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 27));
		lblNewLabel.setBounds(394, 19, 186, 42);
		vocableEditorPanel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(717, 68, 242, 29);
		vocableEditorPanel.add(comboBox);
		
		JPanel backpagePanel = new JPanel();
		backpagePanel.setLayout(null);
		backpagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "R\u00FCckseite", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		backpagePanel.setBounds(17, 272, 942, 175);
		vocableEditorPanel.add(backpagePanel);
		
		JEditorPane backpageEditorPane = new JEditorPane();
		backpageEditorPane.setBounds(17, 29, 908, 126);
		backpagePanel.add(backpageEditorPane);
		
		JButton btnAddVoc = new JButton("New button");
		btnAddVoc.setBounds(828, 452, 131, 31);
		vocableEditorPanel.add(btnAddVoc);
		
		JLabel lblNewLabel_1 = new JLabel("Kategorie");
		lblNewLabel_1.setBounds(615, 71, 82, 23);
		vocableEditorPanel.add(lblNewLabel_1);
	}
}
