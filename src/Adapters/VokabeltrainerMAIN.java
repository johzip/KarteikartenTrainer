package Adapters;

import java.awt.EventQueue;

public class VokabeltrainerMAIN {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VocabelDisplayer window = new VokabeltrainerGUI();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
