import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class DownSide extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8353912432665447346L;

	public DownSide(Game tetris) {
		setPreferredSize(new Dimension(200, 100));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon icon = new ImageIcon("bottom.png");
		Dimension d = getSize();
		g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
	}
}
