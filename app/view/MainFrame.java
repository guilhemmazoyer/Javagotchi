package app.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
    
    private static final int SCREEN_RATIO = 2;
    private static final int INITIAL_POS = 0;
    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 720;


    public MainFrame() {
        super();
        
        // Creer une MainFrame de taille fixe 
        Dimension frame = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setPreferredSize(frame);
        setSize(frame);
        setResizable(false);
        
        // Centre la fenetre
        Dimension userScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((userScreenSize.getWidth()-DEFAULT_WIDTH) / SCREEN_RATIO);
        int y = (int) ((userScreenSize.getHeight()-DEFAULT_HEIGHT) / SCREEN_RATIO);
        setBounds(x, y, INITIAL_POS, INITIAL_POS);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //ends program when JFrame closed
        setTitle("Tamagotchi");
        
        pack();
    }
}