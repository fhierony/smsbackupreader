package com.devadvance.smsbackupreader;

import javax.swing.JFrame;

import java.awt.Composite;

import javax.annotation.PostConstruct;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import  java.util.prefs.*;

public class OptionForm extends JFrame{
	private Preferences p;
    
	private JCheckBox chckbxWarningMsg;
	private JCheckBox chckbxLoadPrevXML;
	private JButton btnSave;
	private JButton btnCancel;
	
	public OptionForm(){
		p = Preferences.userNodeForPackage(BackupReader.class);
		setTitle("Options");
		setBounds(100, 100, 300, 300);
		createControls();
		loadValues();
	}

	@PostConstruct
	private void createControls(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		chckbxWarningMsg = new JCheckBox("Ignore contact warning message");
		GridBagConstraints gbc_chckbxWarningMsg = new GridBagConstraints();
		gbc_chckbxWarningMsg.gridwidth = 2;
		gbc_chckbxWarningMsg.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxWarningMsg.gridx = 0;
		gbc_chckbxWarningMsg.gridy = 0;
		getContentPane().add(chckbxWarningMsg, gbc_chckbxWarningMsg);
		
		chckbxLoadPrevXML = new JCheckBox("Load previous xml file on startup");
		GridBagConstraints gbc_chckbxLoadPrevXML = new GridBagConstraints();
		gbc_chckbxLoadPrevXML.gridwidth = 2;
		gbc_chckbxLoadPrevXML.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxLoadPrevXML.gridx = 0;
		gbc_chckbxLoadPrevXML.gridy = 1;
		getContentPane().add(chckbxLoadPrevXML, gbc_chckbxLoadPrevXML);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveValues();
				dispose();
			}
			
		});
		btnSave.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 8;
		getContentPane().add(btnSave, gbc_btnSave);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 8;
		getContentPane().add(btnCancel, gbc_btnCancel);
	}
	
	private void loadValues(){
		chckbxWarningMsg.setSelected(p.getBoolean(PrefVals.IGNORE_WARNING, false));
		chckbxLoadPrevXML.setSelected(p.getBoolean(PrefVals.LOAD_PREV_XML, false));
	}
	
	private void saveValues(){
		p.putBoolean(PrefVals.IGNORE_WARNING, chckbxWarningMsg.isSelected());
		p.putBoolean(PrefVals.LOAD_PREV_XML, chckbxLoadPrevXML.isSelected());
	}
}
