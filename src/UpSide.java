import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class UpSide extends JPanel {

	private static final long serialVersionUID = -908456529560578028L;

	public UpSide(Game tetris) {

		setPreferredSize(new Dimension(200, 70));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		ImageIcon icon = new ImageIcon("top.png");
		Dimension d = getSize();
		g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
	}
}
