package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;

public class CustomMenuLabel extends JLabel {
	
	private static final String FONT_LIKE_SNOW = "likesnow.ttf";

	/**
	 * 
	 * @param name String
	 */
	public CustomMenuLabel(String name) {
		super(name);
		
		setCustomFont(32f);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param position
	 */
	CustomMenuLabel(String name, int position) {
		super(name);
		
		setCustomFont(24f);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param size
	 */
	public CustomMenuLabel(String name, float size) {
		super(name);
		
		setCustomFont(size);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param size
	 * @param position
	 */
	public CustomMenuLabel(String name, float size, int position) {
		super(name, position);
	
		setCustomFont(size);
	}
	
	/**
	 * Defines a new font
	 * @param fontSize float
	 */
	protected void setCustomFont(float fontSize){
		InputStream inStrm = CustomMenuLabel.class.getResourceAsStream(FONT_LIKE_SNOW);
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, inStrm);
			Font sizedFont = font.deriveFont(fontSize);
			this.setFont(sizedFont);
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
