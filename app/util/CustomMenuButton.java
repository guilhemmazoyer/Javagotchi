package app.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

public class CustomMenuButton extends JButton {
	
	private static final String FONT_LIKE_SNOW = "../assets/fonts/likesnow.ttf";
	private static final Color COLOR_CACTUS_GREEN = new Color(104, 131, 53);
	private static final Color COLOR_PEARL = new Color(245, 235, 218);
	private final transient RoundedBorder border;	
	
    public CustomMenuButton() {
        this(null, null, 48f);
    }

    public CustomMenuButton(Icon icon) {
        this(null, icon, 48f);
    }
    
	CustomMenuButton(String name) {
		this(name,null,48f);
	}
	
    public CustomMenuButton(String text, Icon icon) {
        this(text, icon, 48f);
    }
	
    public CustomMenuButton(String name, Float size) {
		this(name,null,size);
	}
    
    /**
     * Create a JButton according to specific criteria
     * @param name String
     * @param icon Icon
     * @param size float
     */
    public CustomMenuButton(String name, Icon icon, float size) {
		super(name,icon);
		
		this.border = new RoundedBorder(20);
		setBorder(border);
		setCustomSize(1000, 100);
		setCustomFont(size);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Shape oldClip = g2.getClip();
		g2.clip(border.createRoundRect(0, 0, getWidth(), getHeight()));
		super.paint(g);
		g2.clip(oldClip);
	}
	
	/**
	 * Defines a size for the component
	 * @param width
	 * @param height
	 */
	protected void setCustomSize(int width, int height) {
    	setMaximumSize(new Dimension(width, height));
	}
	
	/**
	 * Defines a new font size to be defined
	 * @param fontSize float
	 */
	protected void setCustomFont(float fontSize){
		InputStream inStrm = CustomMenuButton.class.getResourceAsStream(FONT_LIKE_SNOW);
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, inStrm);
			Font sizedFont = font.deriveFont(fontSize);
			setFont(sizedFont);
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
        setBackground(COLOR_PEARL);
	}
	
	// return the pearl color
	public static Color getPearl() {
		return COLOR_PEARL;
	}

	// return the green color
	public static Color getColorGreen() {
		return COLOR_CACTUS_GREEN;
	}

}