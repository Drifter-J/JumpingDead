import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2463696289366489459L;

	public static final int COLOR_MIN = 35;

	public static final int COLOR_MAX = 255 - COLOR_MIN;

	public static final int COL_COUNT = 13;

	private static final int VISIBLE_ROW_COUNT = 21;

	private static final int HIDDEN_ROW_COUNT = 2;

	public static final int ROW_COUNT = VISIBLE_ROW_COUNT + HIDDEN_ROW_COUNT;

	public static final int BLOCK_SIZE = 24;

	public static final int SHADE_WIDTH = 4;

	static final int CENTER_X = COL_COUNT * BLOCK_SIZE / 2;

	public static final int PANEL_WIDTH = COL_COUNT * BLOCK_SIZE;

	public static final int PANEL_HEIGHT = VISIBLE_ROW_COUNT * BLOCK_SIZE;

	private static final Font LARGE_FONT = new Font("Apple Gothic", Font.BOLD,
			30);

	private static final Font SMALL_FONT = new Font("Apple Gothic", Font.BOLD,
			20);

	/**
	 * @uml.property  name="jumpingDead"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="board:Game"
	 */
	private Game jumpingDead;

	/**
	 * @uml.property  name="preCol"
	 */
	private int preCol = 5;

	/**
	 * @uml.property  name="preRow"
	 */
	public int preRow = 20;

	/**
	 * @uml.property  name="image"
	 */
	Image image;

	/**
	 * @uml.property  name="blocks" multiplicity="(0 -1)" dimension="2"
	 */
	private BlockType[][] blocks;

	public Panel(Game jumpingDead) {
		this.jumpingDead = jumpingDead;
		this.blocks = new BlockType[ROW_COUNT][COL_COUNT];

		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
	}

	public void clear() {
		for (int i = 0; i < ROW_COUNT; i++) {
			for (int j = 0; j < COL_COUNT; j++) {
				blocks[i][j] = null;
			}
		}
	}

	public boolean isOkay(BlockType type, int x, int y, int rotation) {

		if (x < -type.getLeftInset(rotation)
				|| x + type.getDimension() - type.getRightInset(rotation) >= COL_COUNT) {
			return false;
		}

		if (y < -type.getTopInset(rotation)
				|| y + type.getDimension() - type.getBottomInset(rotation) >= ROW_COUNT) {
			return false;
		}

		for (int col = 0; col < type.getDimension(); col++) {
			for (int row = 0; row < type.getDimension(); row++) {
				if (type.isBlock(col, row, rotation)
						&& isOccupied(x + col, y + row)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isPersonThere(BlockType type, int x, int y, int x2, int y2,
			int rotation) {

		if (x < -type.getLeftInset(rotation)
				|| x + type.getDimension() - type.getRightInset(rotation) >= COL_COUNT) {
			return false;
		}

		if (y < -type.getTopInset(rotation)
				|| y + type.getDimension() - type.getBottomInset(rotation) >= ROW_COUNT) {
			return false;
		}

		for (int col = 0; col < type.getDimension(); col++) {
			for (int row = 0; row < type.getDimension(); row++) {
				if (type.isBlock(col, row, rotation) && (x + col == x2)
						&& (y + row == y2)) {
					return true;
				}
			}
		}
		return false;
	}

	public void addPiece(BlockType type, int x, int y, int rotation) {
		for (int col = 0; col < type.getDimension(); col++) {
			for (int row = 0; row < type.getDimension(); row++) {
				if (type.isBlock(col, row, rotation)) {
					setBlock(col + x, row + y, type);
				}
			}
		}
	}

	public void checkLines() {

		for (int row = 0; row < ROW_COUNT; row++) {
			checkLine(row);
		}
	}

	private boolean checkLine(int line) {

		for (int col = 0; col < COL_COUNT; col++) {
			if (!isOccupied(col, line)) {
				return false;
			}
		}

		for (int row = line - 1; row >= 0; row--) {
			for (int col = 0; col < COL_COUNT; col++) {
				setBlock(col, row + 1, getBlock(col, row));
			}
		}
		if (jumpingDead.getPersonPieceRow() < line) {
			while (isOkay(BlockType.TypeP, jumpingDead.getPersonPieceCol(),
					jumpingDead.getPersonPieceRow() + 1, 0)) {
				jumpingDead
						.setPersonPieceRow(jumpingDead.getPersonPieceRow() + 1);
			}
		}
		return true;
	}

	public void removeCol(int line, int column) {
		for (int row = line; row < ROW_COUNT; row++) {
			setBlock(column, row, null);
		}
	}

	public void removeRow(int line) {

		if (line != 22) {
			for (int row = line; row >= line + 1; row++) {
				for (int col = 0; col < COL_COUNT; col++) {
					setBlock(col, row, null);
				}

			}
			for (int row = line - 1; row > 1; row--) {
				for (int col = 0; col < COL_COUNT; col++) {
					setBlock(col, row + 2, getBlock(col, row));
				}
			}
			if (jumpingDead.getPersonPieceRow() <= line) {
				while (isOkay(BlockType.TypeP, jumpingDead.getPersonPieceCol(),
						jumpingDead.getPersonPieceRow() + 1, 0)) {
					jumpingDead.setPersonPieceRow(jumpingDead
							.getPersonPieceRow() + 1);
				}
			}

		} else {
			for (int row = line; row >= line + 1; row++) {
				for (int col = 0; col < COL_COUNT; col++) {
					setBlock(col, 22, null);
				}
			}

			for (int row = line - 1; row > 1; row--) {
				for (int col = 0; col < COL_COUNT; col++) {
					setBlock(col, row + 1, getBlock(col, row));
				}
			}
			if (jumpingDead.getPersonPieceRow() < line)
				jumpingDead
						.setPersonPieceRow(jumpingDead.getPersonPieceRow() + 1);

		}
	}

	private boolean isOccupied(int x, int y) {
		return blocks[y][x] != null;
	}

	private void setBlock(int x, int y, BlockType type) {
		blocks[y][x] = type;
	}

	private BlockType getBlock(int x, int y) {
		return blocks[y][x];
	}

	@SuppressWarnings("static-access")
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		ImageIcon icon = new ImageIcon("map.png");
		Dimension d = getSize();
		g.drawImage(icon.getImage(), -3, -3, d.width + 3, d.height + 5, null);

		if (jumpingDead.state == jumpingDead.newGameState
				|| jumpingDead.state == jumpingDead.gameOverState) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);

			ImageIcon icon_g_c_2 = new ImageIcon("map_black.png");

			g.drawImage(icon_g_c_2.getImage(), -2, 60, 318, 500, null);

			String msg = jumpingDead.state == jumpingDead.newGameState ? "JUMPING DEAD"
					: "GAME OVER";

			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg)
					/ 2, 200);
			g.setFont(SMALL_FONT);
			msg = "Press Enter to Play"
					+ (jumpingDead.state == jumpingDead.newGameState ? ""
							: " Again");
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg)
					/ 2, 400);
		} else if (jumpingDead.state == jumpingDead.gameClearState) {
			g.setFont(LARGE_FONT);
			g.setColor(Color.WHITE);

			ImageIcon icon_g_c = new ImageIcon("map_black.png");
			g.drawImage(icon_g_c.getImage(), -2, 60, 318, 500, null);

			String msg = "Congratulation!";
			g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg)
					/ 2, 200);
			g.setFont(SMALL_FONT);
			String msg_2 = String.valueOf("Your Score: "
					+ this.jumpingDead.getScore() + "sec!");
			g.drawString(msg_2, CENTER_X - g.getFontMetrics().stringWidth(msg)
					/ 2 - 25, 250);
			g.setFont(SMALL_FONT);
			String msg_3 = String.valueOf("Thank you for saving his life!");
			g.drawString(msg_3, CENTER_X - g.getFontMetrics().stringWidth(msg)
					/ 2 - 70, 400);

		} else {
			BlockType person = BlockType.TypeP;
			for (int col = 0; col < person.getDimension(); col++) {
				for (int row = 0; row < person.getDimension(); row++) {
					if (person.isBlock(col, row, 0)) {
						drawBlock(
								person,
								(jumpingDead.getPersonPieceCol()) * BLOCK_SIZE,
								(jumpingDead.getPersonPieceRow() - HIDDEN_ROW_COUNT)
										* BLOCK_SIZE, g);
					}
				}
			}
			if (jumpingDead.getPersonPieceCol() > preCol) {
				image = Toolkit.getDefaultToolkit().getImage("char_r.png");
			} else if (jumpingDead.getPersonPieceCol() < preCol) {
				image = Toolkit.getDefaultToolkit().getImage("char_l.png");
			} else if (jumpingDead.getPersonPieceCol() == preCol) {
				g.drawImage(image, (jumpingDead.getPersonPieceCol())
						* BLOCK_SIZE,
						(jumpingDead.getPersonPieceRow() - HIDDEN_ROW_COUNT)
								* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, this);
			} else
				g.drawImage(image, (jumpingDead.getPersonPieceCol())
						* BLOCK_SIZE,
						(jumpingDead.getPersonPieceRow() - HIDDEN_ROW_COUNT)
								* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, this);

			preCol = jumpingDead.getPersonPieceCol();
			preRow = jumpingDead.getPersonPieceRow();

			for (int x = 0; x < COL_COUNT; x++) {
				for (int y = HIDDEN_ROW_COUNT; y < ROW_COUNT; y++) {
					BlockType BLOCK = getBlock(x, y);
					if (BLOCK != null) {
						drawBlock(BLOCK, x * BLOCK_SIZE, (y - HIDDEN_ROW_COUNT)
								* BLOCK_SIZE, g);
					}
				}
			}

			BlockType type = jumpingDead.getPieceType();
			int pieceCol = jumpingDead.getPieceCol();
			int pieceRow = jumpingDead.getPieceRow();
			int rotation = jumpingDead.getPieceRotation();

			for (int col = 0; col < type.getDimension(); col++) {
				for (int row = 0; row < type.getDimension(); row++) {
					if (pieceRow + row >= 2 && type.isBlock(col, row, rotation)) {
						drawBlock(type, (pieceCol + col) * BLOCK_SIZE,
								(pieceRow + row - HIDDEN_ROW_COUNT)
										* BLOCK_SIZE, g);
					}
				}
			}

			Image item;
			if (jumpingDead.getPieceType() == BlockType.ItemCol) {
				item = Toolkit.getDefaultToolkit().getImage("itemCol.png");
				g.drawImage(item, (jumpingDead.getPieceCol()) * BLOCK_SIZE,
						(jumpingDead.getPieceRow() - HIDDEN_ROW_COUNT)
								* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, this);
			} else if (jumpingDead.getPieceType() == BlockType.ItemRow) {
				item = Toolkit.getDefaultToolkit().getImage("itemRow.png");
				g.drawImage(item, (jumpingDead.getPieceCol()) * BLOCK_SIZE,
						(jumpingDead.getPieceRow() - HIDDEN_ROW_COUNT)
								* BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, this);
			}

			Color base = type.getBaseColor();
			base = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);
			for (int lowest = pieceRow; lowest < ROW_COUNT; lowest++) {

				if (isOkay(type, pieceCol, lowest, rotation)) {
					continue;
				}

				lowest--;

				for (int col = 0; col < type.getDimension(); col++) {
					for (int row = 0; row < type.getDimension(); row++) {
						if (lowest + row >= 2
								&& type.isBlock(col, row, rotation)) {
							drawBlock(base, base.GRAY, base.darker(),
									(pieceCol + col) * BLOCK_SIZE, (lowest
											+ row - HIDDEN_ROW_COUNT)
											* BLOCK_SIZE, g);
						}
					}
				}
				break;
			}
		}
	}

	private void drawBlock(BlockType type, int x, int y, Graphics g) {
		drawBlock(type.getBaseColor(), type.getLightColor(),
				type.getDarkColor(), x, y, g);
	}

	private void drawBlock(Color base, Color light, Color dark, int x, int y,
			Graphics g) {

		g.setColor(base);
		g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

		g.setColor(dark);
		g.fillRect(x, y + BLOCK_SIZE - SHADE_WIDTH, BLOCK_SIZE, SHADE_WIDTH);
		g.fillRect(x + BLOCK_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, BLOCK_SIZE);

		g.setColor(light);
		for (int i = 0; i < SHADE_WIDTH; i++) {
			g.drawLine(x, y + i, x + BLOCK_SIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + BLOCK_SIZE - i - 1);
		}
	}

}
