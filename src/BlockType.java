import java.awt.Color;

public enum BlockType {
	TypeA(new Color(255, 255, 255), 1, 1, 1, new boolean[][] { { true } }), TypeP(
			new Color(0, 0, 0, 0), 1, 1, 1, new boolean[][] { { true } }),

	ItemCol(new Color(0, 0, 0, 0), 1, 1, 1, new boolean[][] { { true } }),

	ItemRow(new Color(0, 0, 0, 0), 1, 1, 1, new boolean[][] { { true } }), TypeI(
			new Color(Panel.COLOR_MIN, Panel.COLOR_MAX, Panel.COLOR_MAX),
			4,
			4,
			1,
			new boolean[][] {
					{ false, false, false, false, true, true, true, true,
							false, false, false, false, false, false, false,
							false, },
					{ false, false, true, false, false, false, true, false,
							false, false, true, false, false, false, true,
							false, },
					{ false, false, false, false, false, false, false, false,
							true, true, true, true, false, false, false, false, },
					{ false, true, false, false, false, true, false, false,
							false, true, false, false, false, true, false,
							false, } }),

	TypeJ(new Color(Panel.COLOR_MIN, Panel.COLOR_MIN, Panel.COLOR_MAX), 3, 3,
			1, new boolean[][] {
					{ true, false, false, true, true, true, false, false,
							false, },
					{ false, true, true, false, true, false, false, true,
							false, },
					{ false, false, false, true, true, true, false, false,
							true, },
					{ false, true, false, false, true, false, true, true,
							false, } }),

	TypeL(new Color(Panel.COLOR_MAX, 127, Panel.COLOR_MIN), 3, 3, 1,
			new boolean[][] {
					{ false, false, true, true, true, true, false, false,
							false, },
					{ false, true, false, false, true, false, false, true,
							true, },
					{ false, false, false, true, true, true, true, false,
							false, },
					{ true, true, false, false, true, false, false, true,
							false, } }),

	TypeO(new Color(Panel.COLOR_MAX, Panel.COLOR_MAX, Panel.COLOR_MIN), 2, 2,
			1, new boolean[][] { { true, true, true, true, },
					{ true, true, true, true, }, { true, true, true, true, },
					{ true, true, true, true, } }),

	TypeS(new Color(Panel.COLOR_MIN, Panel.COLOR_MAX, Panel.COLOR_MIN), 3, 3,
			1, new boolean[][] {
					{ false, true, true, true, true, false, false, false,
							false, },
					{ false, true, false, false, true, true, false, false,
							true, },
					{ false, false, false, false, true, true, true, true,
							false, },
					{ true, false, false, true, true, false, false, true,
							false, } }),

	TypeT(new Color(128, Panel.COLOR_MIN, 128), 3, 3, 1, new boolean[][] {
			{ false, true, false, true, true, true, false, false, false, },
			{ false, true, false, false, true, true, false, true, false, },
			{ false, false, false, true, true, true, false, true, false, },
			{ false, true, false, true, true, false, false, true, false, } }),

	TypeZ(new Color(Panel.COLOR_MAX, Panel.COLOR_MIN, Panel.COLOR_MIN), 3, 3,
			1, new boolean[][] {
					{ true, true, false, false, true, true, false, false,
							false, },
					{ false, false, true, false, true, true, false, true,
							false, },
					{ false, false, false, true, true, false, false, true,
							true, },
					{ false, true, false, true, true, false, true, false,
							false, } });

	/**
	 * @uml.property  name="baseColor"
	 */
	private Color baseColor;

	/**
	 * @uml.property  name="lightColor"
	 */
	private Color lightColor;

	/**
	 * @uml.property  name="darkColor"
	 */
	private Color darkColor;

	/**
	 * @uml.property  name="spawnCol"
	 */
	private int spawnCol;

	/**
	 * @uml.property  name="spawnRow"
	 */
	private int spawnRow;

	/**
	 * @uml.property  name="dimension"
	 */
	private int dimension;

	/**
	 * @uml.property  name="rows"
	 */
	private int rows;

	/**
	 * @uml.property  name="cols"
	 */
	private int cols;

	/**
	 * @uml.property  name="blocks" multiplicity="(0 -1)" dimension="2"
	 */
	private boolean[][] blocks;

	private BlockType(Color color, int dimension, int cols, int rows,
			boolean[][] blocks) {
		this.baseColor = color;
		this.lightColor = color.brighter();
		this.darkColor = color.darker();
		this.dimension = dimension;
		this.blocks = blocks;
		this.cols = cols;
		this.rows = rows;

		this.spawnCol = 5 - (dimension >> 1);
		this.spawnRow = getTopInset(0);
	}

	/**
	 * @return
	 * @uml.property  name="baseColor"
	 */
	public Color getBaseColor() {
		return baseColor;
	}

	/**
	 * @return
	 * @uml.property  name="lightColor"
	 */
	public Color getLightColor() {
		return lightColor;
	}

	/**
	 * @return
	 * @uml.property  name="darkColor"
	 */
	public Color getDarkColor() {
		return darkColor;
	}

	/**
	 * @return
	 * @uml.property  name="dimension"
	 */
	public int getDimension() {
		return dimension;
	}

	public int getSpawnColumn() {
		return spawnCol;
	}

	/**
	 * @return
	 * @uml.property  name="spawnRow"
	 */
	public int getSpawnRow() {
		return spawnRow;
	}

	/**
	 * @return
	 * @uml.property  name="rows"
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return
	 * @uml.property  name="cols"
	 */
	public int getCols() {
		return cols;
	}

	public boolean isBlock(int x, int y, int rotation) {
		return blocks[rotation][y * dimension + x];
	}

	public int getLeftInset(int rotation) {
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				if (isBlock(x, y, rotation)) {
					return x;
				}
			}
		}
		return -1;
	}

	public int getRightInset(int rotation) {
		for (int x = dimension - 1; x >= 0; x--) {
			for (int y = 0; y < dimension; y++) {
				if (isBlock(x, y, rotation)) {
					return dimension - x;
				}
			}
		}
		return -1;
	}

	public int getTopInset(int rotation) {
		for (int y = 0; y < dimension; y++) {
			for (int x = 0; x < dimension; x++) {
				if (isBlock(x, y, rotation)) {
					return y;
				}
			}
		}
		return -1;
	}

	public int getBottomInset(int rotation) {
		for (int y = dimension - 1; y >= 0; y--) {
			for (int x = 0; x < dimension; x++) {
				if (isBlock(x, y, rotation)) {
					return dimension - y;
				}
			}
		}
		return -1;
	}

}
