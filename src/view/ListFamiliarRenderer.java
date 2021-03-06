package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import model.Familiar;
import util.*;

/**
 * 
 * Allows you to customise a JList
 *
 */
public class ListFamiliarRenderer implements ListCellRenderer<Familiar> {
	
	private static final float DEFAULT_BUTTON_SIZE = 32f;

	/**
	 * Create the panel that display the save
	 * @param list
	 * @param familiar
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return panFam
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Familiar> list, Familiar familiar, int index, boolean isSelected, boolean cellHasFocus) {
		
		JPanel panFam = new JPanel();
		panFam.setLayout(new BoxLayout(panFam, BoxLayout.Y_AXIS));
		panFam.setPreferredSize(new Dimension(420, 400));
		// picture of the familiar
		JLabel image = new JLabel(createImageIcon(familiar.getFamiliarType()));
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// name of the familiar
		JLabel textName = new CustomMenuLabel("Nom : "+ familiar.getName(), DEFAULT_BUTTON_SIZE);
		textName.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// type of the familiar
		JLabel textType = new CustomMenuLabel("Type : "+familiar.getFamiliarType(), DEFAULT_BUTTON_SIZE);
		textType.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel textStatut = new CustomMenuLabel("Statut : "+statutToString(familiar), DEFAULT_BUTTON_SIZE);
		textStatut.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		// adding elements to the panel
		panFam.add(image);
		panFam.add(textName);
		panFam.add(textType);
		panFam.add(textStatut);

		// setting up the list
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // select only 1 item
        
        if (isSelected) {
    		panFam.setBackground(Color.WHITE);
        	panFam.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        }
        
		return panFam;
	}
	
	/**
	 * Check that the entered URL leads to a file
	 * Create and return an ImageIcon from the source file
	 * 
	 * @param path
	 * @return imgURL
	 */
	private static ImageIcon createImageIcon(String path) {
        switch(path) {
        case "Chat":
        	return IconUtil.createImageIcon("cat.png");
        case "Chien":
        	return IconUtil.createImageIcon("dog.png");
        case "Robot":
        	return IconUtil.createImageIcon("robot.png");
        case "Lapin":
        	return IconUtil.createImageIcon("rabbit.png");
        default:
        	return null;
        }
    }
	
	private String statutToString(Familiar f) {
		return (f.isDead() ? "Mort" : "Vivant");
	}

}