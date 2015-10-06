import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3863008324233284327L;
	/**
	 * @uml.property  name="gameOverState"
	 * @uml.associationEnd  
	 */
	State gameOverState = new GameOverState(this);
	/**
	 * @uml.property  name="newGameState"
	 * @uml.associationEnd  
	 */
	State newGameState = new NewGameState(this);
	/**
	 * @uml.property  name="gameClearState"
	 * @uml.associationEnd  
	 */
	State gameClearState = new GameClearState(this);

	/**
	 * @uml.property  name="state"
	 * @uml.associationEnd  
	 */
	State state = newGameState;

	/**
	 * @uml.property  name="backgroundMusic"
	 * @uml.associationEnd  
	 */
	private Sound backgroundMusic = new Sound("/background.wav");
	/**
	 * @uml.property  name="remote"
	 * @uml.associationEnd  
	 */
	private SimpleRemoteControl remote = new SimpleRemoteControl();
	/**
	 * @uml.property  name="soundOn"
	 * @uml.associationEnd  
	 */
	private SoundOnCommand SoundOn = new SoundOnCommand(backgroundMusic);
	/**
	 * @uml.property  name="soundOff"
	 * @uml.associationEnd  
	 */
	private SoundOffCommand SoundOff = new SoundOffCommand(backgroundMusic);

	/**
	 * @uml.property  name="remain_time"
	 */
	public long remain_time;
	/**
	 * @uml.property  name="label_timer"
	 * @uml.associationEnd  
	 */
	public JLabel label_timer = new JLabel("TIME:  100", 2);
	/**
	 * @uml.property  name="zombieeffect"
	 * @uml.associationEnd  
	 */
	private Sound zombieeffect = new Sound("/scream.wav");


	private static final long FRAME_TIME = 1000L / 50L;

	public final int TYPE_COUNT = BlockType.values().length;

	/**
	 * @uml.property  name="board"
	 * @uml.associationEnd  inverse="jumpingDead:Panel"
	 */
	public Panel board;
	/**
	 * @uml.property  name="rightS"
	 * @uml.associationEnd  inverse="jumpingDead:RightSide"
	 */
	private RightSide RightS;
	/**
	 * @uml.property  name="leftS"
	 * @uml.associationEnd  inverse="tetris:LeftSide"
	 */
	private LeftSide LeftS;
	/**
	 * @uml.property  name="upS"
	 * @uml.associationEnd  
	 */
	private UpSide UpS;
	/**
	 * @uml.property  name="downS"
	 * @uml.associationEnd  
	 */
	private DownSide DownS;

	/**
	 * @uml.property  name="level"
	 */
	public int level;

	/**
	 * @uml.property  name="score"
	 */
	public int score;

	/**
	 * @uml.property  name="random"
	 */
	private Random random;

	/**
	 * @uml.property  name="logicTimer"
	 * @uml.associationEnd  
	 */
	public Clock logicTimer;

	/**
	 * @uml.property  name="currentType"
	 * @uml.associationEnd  
	 */
	private BlockType currentType;

	/**
	 * @uml.property  name="nextType"
	 * @uml.associationEnd  
	 */
	public BlockType nextType;

	/**
	 * @uml.property  name="person"
	 * @uml.associationEnd  
	 */
	private BlockType person = BlockType.TypeP;

	/**
	 * @uml.property  name="bombCol"
	 * @uml.associationEnd  
	 */
	private BlockType BombCol = BlockType.ItemCol;

	/**
	 * @uml.property  name="bombRow"
	 * @uml.associationEnd  
	 */
	private BlockType BombRow = BlockType.ItemRow;

	/**
	 * @uml.property  name="currentCol"
	 */
	private int currentCol;

	/**
	 * @uml.property  name="currentRow"
	 */
	private int currentRow;

	/**
	 * @uml.property  name="currentRotation"
	 */
	private int currentRotation;

	/**
	 * @uml.property  name="currentPersonCol"
	 */
	public int currentPersonCol = 5;

	/**
	 * @uml.property  name="currentPersonRow"
	 */
	public int currentPersonRow = 21;

	/**
	 * @uml.property  name="dropCooldown"
	 */
	private int dropCooldown;

	/**
	 * @uml.property  name="gameSpeed"
	 */
	public float gameSpeed;

	/**
	 * @uml.property  name="rank1"
	 */
	public String rank1;
	/**
	 * @uml.property  name="rank2"
	 */
	public String rank2;
	/**
	 * @uml.property  name="rank3"
	 */
	public String rank3;
	/**
	 * @uml.property  name="rank4"
	 */
	public String rank4;
	/**
	 * @uml.property  name="rank5"
	 */
	public String rank5;
	/**
	 * @uml.property  name="currentTime"
	 */
	private long currentTime;

	public void setcurrentTime() {
		this.currentTime = System.currentTimeMillis() / 1000;
	}

	public long elapsedTime() {
		remain_time = System.currentTimeMillis() / 1000 - this.currentTime;

		return remain_time;
	}

	public Game() {
		super("Jumping Dead");
	}

	public void init() {

		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		System.out.println("call tetris");

		this.board = new Panel(this);
		this.RightS = new RightSide(this);
		this.LeftS = new LeftSide(this);
		this.UpS = new UpSide(this);
		this.DownS = new DownSide(this);

		this.label_timer.setFont(new Font("godo", 3, 15));
		this.label_timer.setForeground(Color.WHITE);

		LeftS.add(this.label_timer);

		add(board, BorderLayout.CENTER);
		add(UpS, BorderLayout.NORTH);
		add(DownS, BorderLayout.SOUTH);
		add(RightS, BorderLayout.EAST);
		add(LeftS, BorderLayout.WEST);

		DownS.add(new AnimationPane());

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_Q:
					state = gameOverState;
					remote.setCommand(SoundOff);
					remote.sendCommand();
				case KeyEvent.VK_A:
					if (!board.isOkay(person, currentPersonCol - 1,
							currentPersonRow, 0)
							&& board.isOkay(person, currentPersonCol - 1,
									currentPersonRow - 1, 0)) {
						currentPersonCol--;
						currentPersonRow--;
					} else if (board.isOkay(person, currentPersonCol - 1,
							currentPersonRow, 0)) {
						currentPersonCol--;
					}
					while (board.isOkay(person, currentPersonCol,
							currentPersonRow + 1, 0)) {
						currentPersonRow++;
					}
					break;
				case KeyEvent.VK_D:
					if (!board.isOkay(person, currentPersonCol + 1,
							currentPersonRow, 0)
							&& board.isOkay(person, currentPersonCol + 1,
									currentPersonRow - 1, 0)) {
						currentPersonCol++;
						currentPersonRow--;
					} else if (board.isOkay(person, currentPersonCol + 1,
							currentPersonRow, 0))
						currentPersonCol++;
					while (board.isOkay(person, currentPersonCol,
							currentPersonRow + 1, 0)) {
						currentPersonRow++;

					}
					break;
				case KeyEvent.VK_S:
					if (board.isOkay(person, currentPersonCol,
							currentPersonRow + 1, 0)) {
						currentPersonRow++;
					}
					break;

				case KeyEvent.VK_DOWN:
					if (dropCooldown == 0) {
						logicTimer.setCyclesPerSecond(25.0f);
					}
					break;

				case KeyEvent.VK_SPACE:
					while (board.isOkay(currentType, currentCol,
							currentRow + 1, currentRotation)) {
						currentRow++;
						logicTimer.setCyclesPerSecond(12.0f);
					}
					break;

				case KeyEvent.VK_LEFT:
					if (board.isOkay(currentType, currentCol - 1, currentRow,
							currentRotation)) {
						currentCol--;
					}
					break;

				case KeyEvent.VK_RIGHT:
					if (board.isOkay(currentType, currentCol + 1, currentRow,
							currentRotation)) {
						currentCol++;
					}
					break;

				case KeyEvent.VK_UP:
					rotatePiece((currentRotation == 3) ? 0
							: currentRotation + 1);

					break;

				case KeyEvent.VK_ENTER:
					if (state == (gameOverState) || state == (newGameState)
							|| state == (gameClearState)) {
						state.resetGame();
						remote.setCommand(SoundOn);
						remote.sendCommand();
					}
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {

				case KeyEvent.VK_DOWN:
					logicTimer.setCyclesPerSecond(gameSpeed);
					logicTimer.reset();
					break;
				}
			}
		});

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void startGame() {

		init();

		System.out.println("call startGame");

		this.random = new Random();
		state = newGameState;
		this.gameSpeed = 1.0f;

		this.logicTimer = new Clock(gameSpeed);
		logicTimer.setPaused(true);
		while (true) {

			long start = System.nanoTime();

			logicTimer.update();

			if (logicTimer.hasElapsedCycle()) {
				updateGame();
			}

			if (dropCooldown > 0) {
				dropCooldown--;
			}

			renderGame();

			long delta = (System.nanoTime() - start) / 1000000L;
			if (delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateGame() {

		if (elapsedTime() % 92 == 0) {
			System.out.println("zombie roar");
			zombieSound();
		}

		if (100 - elapsedTime() < 0) {
			remote.setCommand(SoundOff);
			remote.sendCommand();
			state = gameOverState;
			remote.setCommand(SoundOff);
			remote.sendCommand();
		}
		if (currentPersonRow <= 2) {
			state = gameClearState;
			logicTimer.setPaused(true);
			remote.setCommand(SoundOff);
			remote.sendCommand();
		}
		if (board.isPersonThere(currentType, currentCol, currentRow,
				currentPersonCol, currentPersonRow, currentRotation)) {
			state = gameOverState;
			remote.setCommand(SoundOff);
			remote.sendCommand();
		}
		if (board.isOkay(currentType, currentCol, currentRow + 1,
				currentRotation)) {

			currentRow++;
		} else {

			board.addPiece(currentType, currentCol, currentRow, currentRotation);

			if (currentType == BombCol) {
				board.removeCol(currentRow, currentCol);
			} else if (currentType == BombRow) {
				board.removeRow(currentRow);
			}

			board.checkLines();

			gameSpeed += 0.035f;
			logicTimer.setCyclesPerSecond(gameSpeed);
			logicTimer.reset();

			dropCooldown = 25;

			level = (int) (gameSpeed * 1.70f);

			spawnPiece();
		}

		gameTime();
	}

	private void renderGame() {
		board.repaint();
		RightS.repaint();
		LeftS.repaint();
	}

	/**
	 * @uml.property  name="rank"
	 * @uml.associationEnd  readOnly="true"
	 */
	Rank rank;

	public void zombieSound() {
		System.out.println("gameSoundplay");
		if (state != gameOverState) {
			zombieeffect.play();
		}
	}

	public void zombieSoundStop() {
		if (state == gameOverState || state == gameClearState) {
			System.out.println("gameSoundstop");
			zombieeffect.stop();
		}
	}

	public void gameTime() {
		if (remain_time == 100L || state == gameOverState) {
			remain_time = 0L;
			this.label_timer.setText("TIME: " + (int) (100L - remain_time));
			return;
		}
		if (state == gameClearState) {
			System.out.println("Im in!");
			score = (int) remain_time;
			remain_time = 0L;
			this.label_timer.setText("TIME: " + (int) (100L - remain_time));
		}
		this.label_timer.setText("TIME: " + (int) (100L - remain_time));
	}

	public void spawnPiece() {
		this.currentType = nextType;
		this.currentCol = currentType.getSpawnColumn();
		this.currentRow = currentType.getSpawnRow();
		this.currentRotation = 0;
		BlockType temp = BlockType.values()[random.nextInt(TYPE_COUNT)];
		if (temp != BlockType.TypeP)
			this.nextType = temp;
		else
			this.nextType = BlockType.TypeO;
		
		if(this.currentType == BlockType.ItemCol || this.currentType == BlockType.ItemRow)
		{
			if(temp == BlockType.ItemCol || temp == BlockType.ItemRow)
				this.nextType = BlockType.TypeT;
		}
		
		if (!board.isOkay(currentType, currentCol, currentRow, currentRotation)
				&& state != gameClearState) {
			// this.isGameOver = true;
			logicTimer.setPaused(true);
		}
		
	}

	private void rotatePiece(int newRotation) {
		int newColumn = currentCol;
		int newRow = currentRow;

		int left = currentType.getLeftInset(newRotation);
		int right = currentType.getRightInset(newRotation);
		int top = currentType.getTopInset(newRotation);
		int bottom = currentType.getBottomInset(newRotation);

		if (currentCol < -left) {
			newColumn -= currentCol - left;
		} else if (currentCol + currentType.getDimension() - right >= Panel.COL_COUNT) {
			newColumn -= (currentCol + currentType.getDimension() - right)
					- Panel.COL_COUNT + 1;
		}

		if (currentRow < -top) {
			newRow -= currentRow - top;
		} else if (currentRow + currentType.getDimension() - bottom >= Panel.ROW_COUNT) {
			newRow -= (currentRow + currentType.getDimension() - bottom)
					- Panel.ROW_COUNT + 1;
		}

		if (board.isOkay(currentType, newColumn, newRow, newRotation)) {
			currentRotation = newRotation;
			currentRow = newRow;
			currentCol = newColumn;
		}
	}

	/*
	 * public boolean isGameOver() { return isGameOver; }
	 * 
	 * public boolean isNewGame() { return isNewGame; }
	 * 
	 * public boolean isGameClear() { return isGameClear; }
	 */
	/**
	 * @return
	 * @uml.property  name="score"
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return
	 * @uml.property  name="level"
	 */
	public int getLevel() {
		return level;
	}

	public BlockType getPieceType() {
		return currentType;
	}

	public BlockType getNextPieceType() {
		return nextType;
	}

	public int getPieceCol() {
		return currentCol;
	}

	public int getPieceRow() {
		return currentRow;
	}

	public int getPieceRotation() {
		return currentRotation;
	}

	public int getPersonPieceCol() {
		return currentPersonCol;
	}

	public int getPersonPieceRow() {
		return currentPersonRow;
	}

	public void setPersonPieceRow(int newPersonRow) {
		this.currentPersonRow = newPersonRow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	private static Game jumpingDead = new Game();

	public static Game getInstance() {
		return jumpingDead;
	}
}
