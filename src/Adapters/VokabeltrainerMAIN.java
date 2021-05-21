package Adapters;

import java.awt.EventQueue;

import Application.VocabelDisplayer;

public class VokabeltrainerMAIN {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(args.length > 0)
						new VokabeltrainerGUI(args[0]);
					new VokabeltrainerGUI(System.getProperty("user.dir") + "\\JSON_Data");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
