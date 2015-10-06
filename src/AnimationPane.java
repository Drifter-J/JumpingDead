import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimationPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 820637459094729859L;

	/**
	 * @uml.property  name="image"
	 */
	Image image;
	/**
	 * @uml.property  name="icon"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	ImageIcon icon;
	/**
	 * @uml.property  name="dalekPoint"
	 */
	private Point dalekPoint;

	/**
	 * @uml.property  name="ob"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	MyObservable ob = new MyObservable();
	/**
	 * @uml.property  name="ov"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	MyObserver ov = new MyObserver();
	/**
	 * @uml.property  name="sd"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	SoundObserver sd = new SoundObserver();

	public AnimationPane() {
		ob.addObserver(ov);
		ob.addObserver(sd);
		icon = new ImageIcon("zombie.gif");
		dalekPoint = new Point();
		image = Toolkit.getDefaultToolkit().createImage("zombie_flip.gif");
		dalekPoint.y = 20;
		Timer timer = new Timer(70, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dalekPoint.x + icon.getIconWidth() > getWidth()) {
					image = Toolkit.getDefaultToolkit().createImage(
							"zombie.gif");
					dalekPoint.x = getWidth() - icon.getIconWidth();
					ob.setValue(ob.getValue() * -1);
				} else if (dalekPoint.x < 0) {
					image = Toolkit.getDefaultToolkit().createImage(
							"zombie_flip.gif");
					dalekPoint.x = 0;
					ob.setValue(ob.getValue() * -1);
				} else {
					dalekPoint.x += ob.getValue();
				}
				repaint();
			}
		});
		timer.start();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(340, 100);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		ImageIcon icon = new ImageIcon("forzombie_3.png");
		g.drawImage(icon.getImage(), 0, 0, null);
		g2d.drawImage(image, dalekPoint.x, dalekPoint.y, this);
		g2d.dispose();
	}
}