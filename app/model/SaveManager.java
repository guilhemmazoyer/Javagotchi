package app.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de gerer les sauvegardes du jeu<br>
 * Fonctionnement:
 * <ul>
 * 		<li>creation du SaveManager 		-> new SaveManager(absolutePath)</li>
 * 		<li>ouverture du fichier courant	-> openFile(nameSave)</li>
 * 		<li>ecriture dans le fichier		-> writeSave(saveFamiliar)</li>
 * 		<li>lecture du fichier/donnees		-> loadSave()
 * 		<li>recuperation des donnes			-> loadFamiliar = this.getFamiliar() 
 * </ul>
 */
public class SaveManager {
	
    /**
     * Permet d'ecrire un objet (Familiar) serialise
     */
    private ObjectOutputStream saveDatas;
    
    /**
     * Permet de lire un objet (Familiar) serialise
     */
    private ObjectInputStream loadDatas;

    /**
     * Flux d'ecriture dans un fichier / gere aussi la creation de fichier automatiquement
     */
    private FileOutputStream dataOutStream;
    
    /**
     * Flux de lecture dans un fichier
     */
    private FileInputStream dataInStream;
    
    /**
     * Chemin designant l'emplacement des sauvegardes
     */
    private static final String DIRECTORY_PATH = "./save/";
    
    /**
     * Repertoire ou se trouve les sauvegardes
     */
    private File repSave;
    
    /**
     * Fichier courant dans lequel les donnes vont ecrites/lues
     */
    private File currentFile;
    
    /**
     * Familier dont les informations ont ete recuperees
     */
    private Familiar currentFamiliar;

    /**
     * Constructeur
     * @param directoryPath String, initialise l'attribut directoryPath 
     */
    public SaveManager() {
        this.repSave = new File(DIRECTORY_PATH);
        if (!(repSave.exists()))
        	repSave.mkdir();
    }

    /**
     * Permet de designer sur quel fichier on va travailler
     * @param saveName String, base du nom du fichier a sauvegarder
     * @throws FileNotFoundException
     */
    public void openFile(String saveName) {
    	currentFile = new File(DIRECTORY_PATH + saveName + ".dat");
    }

    /**
     * Permet de creer un fichier si inexistant et ecrire les donnees de f dedans
     * @param f Familiar, familier dont on veut enregistrer les donnees
     * @throws IOException
     */
    public void writeSave(Familiar f) throws IOException {
        if(currentFile == null) openFile(f.getUID());
    	if (isEnableToSave()) {
    		dataOutStream = new FileOutputStream(currentFile); // ouverture du fichier en ecriture seule
    		saveDatas = new ObjectOutputStream(dataOutStream);

    		saveDatas.writeObject(f);

    		saveDatas.close();
    		dataOutStream.close();
    	}
    }

    /**
     * Supprimer un fichier de sauvegarde
     * @param saveName String, nom du fichier a supprimer
     **/
    public void deleteSave(String saveName) {
        openFile(saveName);
        if (currentFile.exists())
            try {
                Files.delete(currentFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
        	System.err.println("le nom du fichier rentré n'est pas bon");
    }

    /**
     * Permet de recuperer les informations a partir du fichier currentFile et
     * de les copier dans currentFamiliar
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public void loadSave() throws ClassNotFoundException, IOException {
        dataInStream = new FileInputStream(currentFile); // ouverture du fichier en lecture seule
        loadDatas = new ObjectInputStream(dataInStream);
        
        currentFamiliar = (Familiar)loadDatas.readObject();

        loadDatas.close();
        dataInStream.close();
    }

    /**
     * accesseur de l'attribut currentFamilier
     * @return currentFamiliar Familiar
     */
    public Familiar getFamiliar() {
        return currentFamiliar;
    }
    
    /**
     * Recupere tous les noms de sauvegarde dans le directoryPath en les filtrant
     * @return listFileName String[], noms des sauvegardes
     */
    public String[] getNameSave() {
    	File rep = new File(DIRECTORY_PATH);
    	return rep.list((dir, name) -> name.endsWith(".dat"));
    	
    }
    
    /**
     * Recupere tous les familier de chaque fichier et les place dans un vecteur
     * @return listFamiliar Vector<Familiar>, vecteur contenant les donnees sauvegardees dans chaque fichier
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public List<Familiar> getAllFamiliar() throws ClassNotFoundException, IOException {
    	ArrayList<Familiar> listFamiliar = new ArrayList<>();
    	String[] listFileName = getNameSave();
    	
		for (String fileName: listFileName) {
			// recuperation des infos du familier
			openFile(getBaseName(fileName));
			loadSave();
			// on l'ajoute au vecteur
			listFamiliar.add(currentFamiliar);
		}
		
		return listFamiliar;
    }
    
    /**
     * Permet de s'assurer qu'il y est moins de 3 sauvegardes
     * @return
     */
    public boolean isEnableToSave() {
    	return getNbSave() < 3;
    }
    
    /**
     * Permet de connaitre le nombre de sauvegarde dans le dossier meme si l'utilisateur le supprime "a la main"
     * @return nombre de sauvegarde int
     */
    private int getNbSave() {
    	return (getNameSave() != null) ? getNameSave().length : 0;
    }
    
    /**
     * Permet de recuperer le nom de base d'un nom de fichier / enlever l'extension
     * @param name String, nom du fichier
     * @return nom du fichier sans l'extension
     */
	private String getBaseName(String name) {
		return name.substring(0, name.lastIndexOf("."));
	}
	
}