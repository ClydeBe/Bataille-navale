package gameStruct;

public class ClientBattleField extends BattleField {

	private final int GRID_SIZE = 11;
	protected char[][] myGrid, oppGrid;

	public ClientBattleField() {
		myGrid = new char[GRID_SIZE][GRID_SIZE];
		oppGrid = new char[GRID_SIZE][GRID_SIZE];
	}

	public ClientBattleField(String pos) {
		myGrid = new char[GRID_SIZE][GRID_SIZE];
		oppGrid = new char[GRID_SIZE][GRID_SIZE];
		init(myGrid, pos);
	}

	public ClientBattleField(String pos, int size) {
		myGrid = new char[size][size];
		oppGrid = new char[size][size];
		init(myGrid, pos);
	}

	@Override
	protected void init(char[][] grid, String pos) {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				myGrid[i][j] = 'O';
				oppGrid[i][j] = '?';
			}
		}
		super.init(grid, pos);
	}

}
