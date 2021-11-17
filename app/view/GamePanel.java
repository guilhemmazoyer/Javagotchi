package app.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.event.*;

import app.controller.GameController;

public class GamePanel {
    private MainFrame mainFrame = new MainFrame();
    private JButton eatButton = new JButton("Nourrir");
    private GameController gameController = new GameController();
    private JProgressBar progressBar = new JProgressBar(0, 100);

    public void init()
    {
        this.mainFrame.add(eatButton);
        this.progressBar.setValue(0);
        this.eatButton.setPreferredSize(new Dimension(150,100));
    }

    public void gamePanel(MainFrame mainFrame) { 
    }

    public void actionPerformed(Action e){
        this.progressBar.setValue(gameController.onClickEatButton());
    }

}
