import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class RightSide extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5541614929039911465L;

	private static final int BLOCK_SIZE = Panel.BLOCK_SIZE >> 1;

	private static final int SHADE_WIDTH = Panel.SHADE_WIDTH >> 1;

	private static final int BLOCK_COUNT = 5;

	private static final int SQUARE_CENTER_X = 130;

	private static final int SQUARE_CENTER_Y = 65;

	private static final int SQUARE_SIZE = (BLOCK_SIZE * BLOCK_COUNT >> 1);

	private static final int SMALL_INSET = 50;

	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);

	private static final Color DRAW_COLOR = new Color(255, 255, 255);

	/**
	 * @uml.property  name="jumpingDead"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="rightS:Game"
	 */
	private Game jumpingDead;

	/**
	 * Creates a new RightSide and sets it's display properties.
	 * 
	 * @param jumpingDead
	 *            The jumpingDead instance to use.
	 */
	public RightSide(Game jumpingDead) {
		this.jumpingDead = jumpingDead;

		setPreferredSize(new Dimension(211, Panel.PANEL_HEIGHT));
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(DRAW_COLOR);
		ImageIcon icon = new ImageIcon("right.png");
		Dimension d = getSize();
		g.drawImage(icon.getImage(), 0, -2, d.width, d.height + 2, null);

		g.setFont(LARGE_FONT);
		g.drawString("Next:", SMALL_INSET, 80);
		g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y + 15
				- SQUARE_SIZE, SQUARE_SIZE * 2, SQUARE_SIZE * 2);

		BlockType type = jumpingDead.getNextPieceType();
		if (jumpingDead.state != jumpingDead.gameOverState && type != null) {

			int cols = type.getCols();
			int rows = type.getRows();
			int dimension = type.getDimension();

			int startX = (SQUARE_CENTER_X - (cols * BLOCK_SIZE / 2));
			int startY = (SQUARE_CENTER_Y - (rows * BLOCK_SIZE / 2) + 10);

			int top = type.getTopInset(0);
			int left = type.getLeftInset(0);

			Image item;

			for (int row = 0; row < dimension; row++) {
				for (int col = 0; col < dimension; col++) {
					if (type.isBlock(col, row, 0)) {
						if (type == BlockType.ItemCol) {
							item = Toolkit.getDefaultToolkit().getImage(
									"itemCol.png");
							g.drawImage(item, startX
									+ ((col - left) * BLOCK_SIZE), startY
									+ ((row - top) * BLOCK_SIZE), BLOCK_SIZE,
									BLOCK_SIZE, this);
						} else if (type == BlockType.ItemRow) {
							item = Toolkit.getDefaultToolkit().getImage(
									"itemRow.png");
							g.drawImage(item, startX
									+ ((col - left) * BLOCK_SIZE), startY
									+ ((row - top) * BLOCK_SIZE), BLOCK_SIZE,
									BLOCK_SIZE, this);
						} else {
							drawBlock(type, startX
									+ ((col - left) * BLOCK_SIZE), startY
									+ ((row - top) * BLOCK_SIZE), g);
						}
					}
				}
			}
		}
	}

	private void drawBlock(BlockType type, int x, int y, Graphics g) {

		g.setColor(type.getBaseColor());
		g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

		g.setColor(type.getDarkColor());
		g.fillRect(x, y + BLOCK_SIZE - SHADE_WIDTH, BLOCK_SIZE, SHADE_WIDTH);
		g.fillRect(x + BLOCK_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, BLOCK_SIZE);

		g.setColor(type.getLightColor());

		for (int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + BLOCK_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + BLOCK_SIZE - i - 1);
		}
	}

}
