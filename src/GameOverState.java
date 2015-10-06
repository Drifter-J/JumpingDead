import java.util.Random;


public class GameOverState implements State {
	
	/**
	 * @uml.property  name="jumpingDead"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Game jumpingDead;

	public GameOverState(Game jumpingDead)
	{
		this.jumpingDead = jumpingDead;
	}

	/**
	 * @uml.property  name="random"
	 */
	private Random random=new Random();
	/**
	 * Resets the game variables to their default values at the start of a new game.
	 * @uml.property  name="rank"
	 * @uml.associationEnd  
	 */
	Rank rank;

	public void resetGame() {
		jumpingDead.setcurrentTime();
		rank = new Rank(jumpingDead.getScore());
		jumpingDead.rank1 = rank.getRank1();
		jumpingDead.rank2 = rank.getRank2();
		jumpingDead.rank3 = rank.getRank3();
		jumpingDead.rank4 = rank.getRank4();
		jumpingDead.rank5 = rank.getRank5();
		System.out.println("call resetGame");
		jumpingDead.currentPersonCol = 6;
		jumpingDead.currentPersonRow = 22;
		jumpingDead.level = 1;
		jumpingDead.score = 0;
		jumpingDead.gameSpeed = 1.0f;

		BlockType temp = BlockType.values()[random.nextInt(jumpingDead.TYPE_COUNT)];
		if (temp != BlockType.TypeP)
			jumpingDead.nextType = temp;
		else
			jumpingDead.nextType = BlockType.TypeO;

		jumpingDead.state=null;
		
		jumpingDead.board.clear();
		jumpingDead.logicTimer.reset();
		jumpingDead.logicTimer.setCyclesPerSecond(jumpingDead.gameSpeed);

		jumpingDead.spawnPiece();
	}
}
