import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class LeftSide extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3848777674214422586L;

	private static final int SMALL_INSET = 75;

	private static final int LARGE_INSET = 60;

	private static final int STATS_INSET = 100;

	private static final int TEXT_STRIDE = 25;

	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);

	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);

	private static final Color DRAW_COLOR = new Color(255, 255, 255);

	/**
	 * @uml.property  name="tetris"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="leftS:Game"
	 */
	private Game tetris;

	/**
	 * @uml.property  name="image"
	 */
	Image image;

	public LeftSide(Game tetris) {
		this.tetris = tetris;

		setPreferredSize(new Dimension(215, Panel.PANEL_HEIGHT));
		setBackground(Color.BLACK);
		image = Toolkit.getDefaultToolkit().getImage("left_2.png");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(DRAW_COLOR);
		g.drawImage(image, 0, 0, null);

		int offset;

		g.setFont(LARGE_FONT);
		g.drawString("TOP 5", SMALL_INSET, offset = STATS_INSET);
		g.setFont(SMALL_FONT);

		g.drawString("TOP 1 :  " + tetris.rank1 + " sec", LARGE_INSET,
				offset += TEXT_STRIDE);
		g.drawString("TOP 2 :  " + tetris.rank2 + " sec", LARGE_INSET,
				offset += TEXT_STRIDE);
		g.drawString("TOP 3 :  " + tetris.rank3 + " sec", LARGE_INSET,
				offset += TEXT_STRIDE);
		g.drawString("TOP 4 :  " + tetris.rank4 + " sec", LARGE_INSET,
				offset += TEXT_STRIDE);
		g.drawString("TOP 5 :  " + tetris.rank5 + " sec", LARGE_INSET,
				offset += TEXT_STRIDE);

	}

}
