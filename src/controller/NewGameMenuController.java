package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.Cat;
import model.Dog;
import model.Familiar;
import model.Rabbit;
import model.Robot;
import model.SaveManager;
import util.SoundManager;
import view.MainFrame;
import view.NewGameMenu;

public class NewGameMenuController implements ActionListener {

	private MainFrame mainFrame;
	private NewGameMenu newGameMenu;
	private MenuController menuController;
	private SaveManager saveManager;
	
	private static final int CURSOR_MAX = 3;
	private static final int CURSOR_MIN = 0;
	private int cursorImage = 0;
	private final String[] familiarType = {"Chat", "Chien", "Robot", "Lapin"};
	private final String[] familiarTypeURL = {"cat.png", "dog.png", "robot.png", "rabbit.png"};
	
	private Pattern invalidPattern;
    private Matcher invalidMatcher;
	private Pattern emptyPattern;
    private Matcher emptyMatcher;
	
	/**
	 * Constructor
	 * @param menuController MenuController
	 * @param nFrame MainFrame
	 */
	public NewGameMenuController(MenuController menuController, MainFrame nFrame) {
		this.mainFrame = nFrame;
		this.menuController = menuController;
		this.newGameMenu = new NewGameMenu(this);
		this.saveManager = new SaveManager();
	}
	
	/**
	 * Displays the new main menu on the MainFrame
	 */
	public void newGameMenuDisplay() {
		flush();
		setCursorImage(0);
		newGameMenu.display();
	}


	/** 
	 * Reception of actions when clicking on buttons,
	 * Comparison of the source of the action and the different buttons,
	 * Execution of the corresponding action
	 * @param e 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.newGameMenu.getBackMenu()) {
			SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
			menuController.mainMenuDisplay();
			
        }else if(e.getSource() == this.newGameMenu.getLeftFamiliarType()) {
        	SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
        	turnLeftFamiliar();
        	
        }else if(e.getSource() == this.newGameMenu.getRightFamiliarType()) {
        	SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
        	turnRightFamiliar();
        	
        }else if(e.getSource() == this.newGameMenu.getLaunchGame()) {
        	SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
        	launchNewGame();
        }
		
	}
	
	/** 
	 * Empties the MainFrame of all its components
	 */
	private void flush() {
		mainFrame.getContentPane().removeAll();
		mainFrame.repaint();
	}
	
	/**
	 * Change the choice of the familiar with the one on his right
	 */
	private void turnRightFamiliar() {
		if(cursorImage < CURSOR_MAX) {
			cursorImage++;
		}else{
			cursorImage = CURSOR_MIN;
		}
			this.newGameMenu.getSpeciesIcon().setIcon(createImageIcon(familiarTypeURL[cursorImage]));
		}
	
	/**
	 * Change the choice of the familiar with the one on his left
	 */
	private void turnLeftFamiliar() {
		if(cursorImage > CURSOR_MIN) {
			cursorImage--;
		}else{
			cursorImage = CURSOR_MAX;
		}
		this.newGameMenu.getSpeciesIcon().setIcon(createImageIcon(familiarTypeURL[cursorImage]));
	}
	
	/**
	 * Checks the validity of the name entered
	 * Create a familiar and start the game
	 */
	private void launchNewGame() {
		String fName = this.newGameMenu.getName().getText();
		String fType = familiarType[cursorImage];
		
    	// verification of the validity of the name
    	// search according to the matcher
    	invalidPattern = Pattern.compile("[<>:\"/]");
    	invalidMatcher = invalidPattern.matcher(fName);
    	
    	emptyPattern = Pattern.compile("^(\s{1,21})");
    	emptyMatcher = emptyPattern.matcher(fName);
    	
    	if(fName.length()>=20){
    		JOptionPane maxLenghtProb = new JOptionPane("Le nom du familier ne peut pas depasser 20 caract?res.",JOptionPane.WARNING_MESSAGE);
    		setOptionBoxVisual(maxLenghtProb);
    		
    	}else if((fName.isEmpty()) || (emptyMatcher.find())) {
    		JOptionPane emptyNameProb = new JOptionPane("Le nom du familier doit comporter au moins 1 caract?re et ne pas commencer par des espaces.",JOptionPane.WARNING_MESSAGE);
    		setOptionBoxVisual(emptyNameProb);
    		
    	}else if((invalidMatcher.find())) {
    		JOptionPane invalidNameProb = new JOptionPane("Les caract?res <, >, :, \", /, \\, |, ?, * sont interdits dans le nom du familier.",JOptionPane.WARNING_MESSAGE);
    		setOptionBoxVisual(invalidNameProb);
    		
    	
    	}else if(!(saveManager.isEnableToSave())) {
    		String saveListFullMsg = "Vous ne pouvez pas creer de nouveau familier. Limite de 3 sauvegardes atteintes.";
    		String[] saveListFullOptions = {"Ok", "Menu sauvegardes", "Menu principal"};
    		int saveListFullAnswer = JOptionPane.showOptionDialog(null, saveListFullMsg, "Erreur nombre maximal de familier atteint", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, saveListFullOptions, saveListFullOptions[1]);
    		
    		switch(saveListFullAnswer){
    			case 0: // ok
					SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
    				break;
    				
	    		case 1: // backups
					SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
	    			try {
	    				menuController.saveMenuController.savesMenuDisplay();
	    			} catch (ClassNotFoundException | IOException e) {
	    				e.printStackTrace();
	    			}
	    			break;
    		
    			default:
					SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
    				menuController.mainMenuDisplay();
    				break;
    		}
    		
    	}else {
    		String confirmMsg = "Cr?ation de "+fName+" le "+fType+" ?";
    		String[] confirmOptions = {"Oui", "Non"};
    		int confirmAnswer = JOptionPane.showOptionDialog(null, confirmMsg, "Confirmer cr?ation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmOptions, confirmOptions[0]);
    		
    		switch(confirmAnswer){
	    		case 0:
	    		    // create a new Familiar
	    	        	// attributes for the new Familiar
	    	        	Familiar newFamiliar = null;
	    	        	
	    	        	switch(fType) {
	    	        		case "Chat":
	    	        			newFamiliar = new Cat(fName, this.familiarTypeURL[cursorImage]);
	    	        			break;
	    	        			
	    	        		case "Chien":
	    	        			newFamiliar = new Dog(fName, this.familiarTypeURL[cursorImage]);
	    	        			break;
	    	        		
	    	        		case "Robot":
								newFamiliar = new Robot(fName, this.familiarTypeURL[cursorImage]);
	    	        			break;
	    	        			
	    	        		case "Lapin":
	    	        			newFamiliar = new Rabbit(fName, this.familiarTypeURL[cursorImage]);
	    	        			break;
	    	        		
	    	        		default:
	    	        			menuController.mainMenuDisplay();
	    	        			JOptionPane launchProb = new JOptionPane("Erreur lors du choix du type de familier.",JOptionPane.WARNING_MESSAGE);
	    	        			setOptionBoxVisual(launchProb);
	    	        			break;
	    	        	}
						if(newFamiliar != null) {
							SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
							try { 
								saveManager.openFile(newFamiliar.getUID());
								saveManager.writeSave(newFamiliar);
							} catch (IOException e) {
								e.printStackTrace();
							}
							new GameController(newFamiliar, mainFrame, menuController);
				        	SoundManager.playsound(SoundManager.SOUNDS_LAUNCH);
						}
					break;
					
	    		case 1:
				SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
	    			break;
    		
    			default:
					SoundManager.playsound(SoundManager.SOUNDS_MENU_CLICK);
    				menuController.mainMenuDisplay();
    				break;
    		}
    	}
	}
	
	/**
	 * Makes a standard dressing of a JOptionPane
	 * @param pane JOptionPane
	 * @return pane JOptionPane
	 */
	private JOptionPane setOptionBoxVisual(JOptionPane pane) {
		JDialog boite = pane.createDialog("Robot v?rificateur");
	    boite.setVisible(true);
		return pane;
	}
	
	/**
	 * Check that the entered URL leads to a file
	 * Create and return an ImageIcon from the source file
	 * @param path
	 * @return imgURL
	 */
	private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = NewGameMenu.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	/**
	 * @return MainFrame
	 */
	public MainFrame getMainFrame() {
		return this.mainFrame;
	}
	
	/**
	 * @param i
	 */
	private void setCursorImage(int i) {
		cursorImage=0;
	}
}