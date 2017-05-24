package com.web.crawler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CrawlerGUI extends javax.swing.JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	public static boolean isCrawl;
	// Variables declaration
	private javax.swing.JButton StartButton;
	private javax.swing.JScrollPane displayResultsScrollPane;
	private javax.swing.JTextArea displayResultsTextArea;
	private javax.swing.JLabel headingLabel;
	private javax.swing.JLabel headingLabel1;
	private javax.swing.JTextField inputTextField;
	private javax.swing.JLabel resultsLabel;
	private javax.swing.JLabel urlAlertsLabel;
	private javax.swing.JScrollPane urlAlertsScrollPane;
	private javax.swing.JTextArea urlAlertsTextArea;
	// End of variables declaration

	/** Creates new form Antenna */
	public CrawlerGUI() {
		initComponents();

	}

	private void initComponents() {
		headingLabel = new javax.swing.JLabel();
		headingLabel1 = new javax.swing.JLabel();
		inputTextField = new javax.swing.JTextField();
		StartButton = new javax.swing.JButton();
		urlAlertsLabel = new javax.swing.JLabel();
		urlAlertsScrollPane = new javax.swing.JScrollPane();
		urlAlertsTextArea = new javax.swing.JTextArea();
		resultsLabel = new javax.swing.JLabel();
		displayResultsScrollPane = new javax.swing.JScrollPane();
		displayResultsTextArea = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Crawler");

		headingLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		headingLabel.setText("You call, we crawl!");

		headingLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		headingLabel1.setText("Web Crawler.js");

		StartButton.setText("Start");
		StartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startCrawling();

			}
		});

		urlAlertsLabel.setText("Url Alerts");

		urlAlertsTextArea.setColumns(20);
		urlAlertsTextArea.setRows(5);
		urlAlertsScrollPane.setViewportView(urlAlertsTextArea);

		resultsLabel.setText("Results");

		displayResultsTextArea.setColumns(20);
		displayResultsTextArea.setRows(5);
		displayResultsScrollPane.setViewportView(displayResultsTextArea);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup().add(25, 25, 25)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
								.add(displayResultsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 533,
										Short.MAX_VALUE)
								.add(urlAlertsScrollPane).add(StartButton)
								.add(headingLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 226,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(headingLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 226,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(inputTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(urlAlertsLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(resultsLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(250, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup().add(39, 39, 39)
								.add(headingLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(18, 18, 18)
								.add(headingLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(18, 18, 18)
								.add(inputTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(StartButton)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(urlAlertsLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(urlAlertsScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 221,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(18, 18, 18)
								.add(resultsLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(18, 18, 18)
								.add(displayResultsScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 239,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(38, Short.MAX_VALUE)));

		pack();
	}

	Thread t = null;

	String input = "";
	static boolean isNew = false;

	protected void startCrawling() {
		String currentinput = inputTextField.getText();// "http://youtube.com/"
		if (input.equals("")) {
			input = currentinput;
		} else if (input.length() > 0 && !input.equals(currentinput)) {
			input = currentinput;
			Spider.url = input;
			urlAlertsTextArea.setText("");
			displayResultsTextArea.setText("");
			isNew = true;
		}
		if (StartButton.getText().equals("Start")) {
			StartButton.setText("Resume");
			isCrawl = true;
			if (t == null) {
				t = new Thread() {
					public void run() {
						Spider spider = new Spider();
						Spider.url = input;
						spider.search(urlAlertsTextArea, displayResultsTextArea);
					}
				};
				t.start();
			}

		} else {
			StartButton.setText("Start");
			isCrawl = false;
		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels = javax.swing.UIManager
					.getInstalledLookAndFeels();
			for (int idx = 0; idx < installedLookAndFeels.length; idx++)
				if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
					javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
					break;
				}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(CrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CrawlerGUI().setVisible(true);
			}
		});
	}

}
