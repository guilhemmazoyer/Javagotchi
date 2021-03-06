package controller;

import view.MainFrame;
import view.SavesMenu;
import model.Familiar;
import model.SaveManager;
import util.SoundManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SaveMenuController implements ActionListener, ListSelectionListener {

	private MainFrame mainFrame;
	private SaveManager saveManager;
	private SavesMenu savesMenu;
	private MenuController menuController;
	
	/**
	 * Constructor
	 * @param menuController
	 * @param nFrame
	 */
	public SaveMenuController(MenuController menuController, MainFrame nFrame) {
		this.mainFrame = nFrame;
		this.menuController = menuController;
		this.saveManager = new SaveManager();
		this.savesMenu = new SavesMenu(nFrame);
	}
	
	/**
	 * Displays the backup menu
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void savesMenuDisplay() throws ClassNotFoundException, IOException {
		flush();
		savesMenu.display(this);
	}

	/**
	 * @return save's name
	 */
	public String[] getSaveName() {
		return saveManager.getNameSave();
	}
	
	/**
	 * @return All familiar of the list
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public List<Familiar> getAllFamiliar() throws ClassNotFoundException, IOException {
		return saveManager.getAllFamiliar();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
    	if (e.getSource().equals(this.savesMenu.getBackMenu())) { // back to main menu
    		this.onClickBackMenu();
    	}
    	else if (e.getSource().equals(this.savesMenu.getDeleteFamiliar())) { // deletion of the familiar
    		this.onClickDeleteFamiliarButton();
        }
    	else if (e.getSource().equals(this.savesMenu.getLoadSave())) {
    		this.onClickLoadSave();
    	}
	}
	
	/**
	 * @param e
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
        if (!(e.getValueIsAdjusting())) {
        	SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
        	this.savesMenu.enableToAction();
        }
	}
	
	/**
	 * Action when the button BackMenu is clicked
	 */
	private void onClickBackMenu() {
		SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
		menuController.mainMenuDisplay();
	}
	

	/**
	 * Action when the button DeleteFamiliar is clicked
	 */
	private void onClickDeleteFamiliarButton() {
    	if (!(this.savesMenu.getListSave().isSelectionEmpty())) { // checking a selection
    		SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
    		Familiar f = this.savesMenu.getListSave().getSelectedValue(); // recovery of a selection
    		int confirmDelete = JOptionPane.showConfirmDialog(
    				null, 
    				"Supprimer "+f.getName()+" le "+f.getFamiliarType()+" ?",
    				"Confirmer suppression",
    				JOptionPane.YES_NO_OPTION);
    		
    		if (confirmDelete == 0) { // yes == 0
        		SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
    			this.savesMenu.getModelFamiliar().removeElement(f); // removal of the display
    			this.saveManager.deleteSave(f.getUID()); // deleting the backup file

    			this.savesMenu.getListSave().clearSelection(); // update selection
    			this.savesMenu.disableToAction(); // disabling the action buttons on the backup
    		} else { // no
			SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
    		}
		}
	}

	/**
	 * Action when the button LoadSave is clicked
	 */
	private void onClickLoadSave() {
		SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
		if (!(this.savesMenu.getListSave().isSelectionEmpty())) { // checking a selection
    		Familiar familiarToLoad = this.savesMenu.getListSave().getSelectedValue(); // recovery of a selection
    		if (!(familiarToLoad.isDead())) {
				familiarToLoad.resetPosition();
				new GameController(familiarToLoad, mainFrame, menuController);
	        	SoundManager.playsound(SoundManager.SOUNDS_LAUNCH);
    		} else {
    	        JOptionPane.showMessageDialog(
    	                null, 
    	                familiarToLoad.getName()+" est mort, il faut le laisser en paix en le supprimant.");
    		}
		}
	}
	
	/**
	 * Give the mainFrame
	 * @return mainFrame MainFrame
	 */
	public MainFrame getMainFrame() {
		return this.mainFrame;
	}
	
	/** 
	 * Empty the mainFrame of all its components
	 */
	private void flush() {
		mainFrame.getContentPane().removeAll();
		mainFrame.repaint();
	}
	
}