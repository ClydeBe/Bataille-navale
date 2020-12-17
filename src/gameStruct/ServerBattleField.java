package gameStruct;

public class ServerBattleField extends BattleField {

	private final int GRID__SIZE = 10;
	private char[][] grid1, grid2;
	private Ship porteAvion1, croiseur1, torpilleur1, contreTorpilleur1, porteAvion2, croiseur2, torpilleur2,
			contreTorpilleur2;

	// Etat des bateaux dans l'ordre Porte A, Croiseur, Contre T, Torpilleur Joueur
	// 1 puis 2. 1 pour up
	private short[] shipsState = { 1, 1, 1, 1, 1, 1, 1, 1 };
	private int shoutedCases1, shoutedCases2;
	private boolean isOver;

	public ServerBattleField(String posPlayer1, String posPlayer2) {
		grid1 = new char[GRID__SIZE][GRID__SIZE];
		grid2 = new char[GRID__SIZE][GRID__SIZE];
		for (int i = 0; i < GRID__SIZE; i++) {
			for (int j = 0; j < GRID__SIZE; j++) {
				grid1[i][j] = grid2[i][j] = 'O';
			}
		}
		init(grid1, posPlayer1);
		init(grid2, posPlayer2);
		shoutedCases1 = 0;
		shoutedCases2 = 0;
		isOver = false;
	}

	public ServerBattleField(int gridSize, String posPlayer1, String posPlayer2) {
		grid1 = new char[gridSize][gridSize];
		grid2 = new char[gridSize][gridSize];
		for (int i = 0; i < GRID__SIZE; i++) {
			for (int j = 0; j < GRID__SIZE; j++) {
				grid1[i][j] = grid2[i][j] = 'O';
			}
		}
		init(grid1, posPlayer1);
		init(grid2, posPlayer2);
		shoutedCases1 = 0;
		shoutedCases2 = 0;
		isOver = false;
	}

	public char[][] getGrid1() {
		return grid1;
	}

	public char[][] getGrid2() {
		return grid2;
	}

	public boolean isOver() {
		return isOver;
	}

	public boolean shoot(int oppNumber, String coord) {
		return privateShoot(oppNumber, coord);
	}

	private boolean privateShoot(int oppNumber, String coord) {
		if (oppNumber == 1) {
			if (shoot(grid1, coord)) {
				// Si unee case est touchée, on repercute l'action sur le navire et augmente le
				// nombre de cases
				// touchés afin de déterminer plus simplement une fin de partie
				hit(oppNumber, coord);
				shoutedCases2++;
				isOver = shoutedCases1 == 11 || shoutedCases2 == 11 ? true : false;
				return true;
			}
		} else if (oppNumber == 2) {
			if (shoot(grid2, coord)) {
				hit(oppNumber, coord);
				shoutedCases1++;
				isOver = shoutedCases1 == 11 || shoutedCases2 == 11 ? true : false;
				return true;
			}
		}
		return false;
	}

	// Didn't found a better way. At least it's not a life's cost.
	// Unfortunately, the server has to deal with it
	public String notifySinkedShip(int oppNumber) {
		if (oppNumber == 1) {
			if (shipsState[0] == 1 && porteAvion1.isSunk()) {
				shipsState[0] = 0;
				return "Porte Avion coulé";
			}
			if (shipsState[1] == 1 && croiseur1.isSunk()) {
				shipsState[1] = 0;
				return "Croiseur coulé";
			}
			if (shipsState[2] == 1 && contreTorpilleur1.isSunk()) {
				shipsState[2] = 0;
				return "Contre torpilleur coulé";
			}
			if (shipsState[3] == 1 && torpilleur1.isSunk()) {
				shipsState[3] = 0;
				return "Torpilleur coulé";
			}
		} else if (oppNumber == 2) {
			if (shipsState[4] == 1 && porteAvion2.isSunk()) {
				shipsState[4] = 0;
				return "Porte avion coulé";
			}
			if (shipsState[5] == 1 && croiseur2.isSunk()) {
				shipsState[5] = 0;
				return "Croiseur coulé";
			}
			if (shipsState[6] == 1 && contreTorpilleur2.isSunk()) {
				shipsState[6] = 0;
				return "Contre Torpilleur oulé";
			}
			if (shipsState[7] == 1 && torpilleur2.isSunk()) {
				shipsState[7] = 0;
				return "Torpilleur coulé";
			}
		}
		return null;
	}

	private void hit(int oppNumber, String shoot) {
		/*
		 * Réduire la vie des navires
		 */
		if (oppNumber == 1) {
			porteAvion1.isHit(shoot);
			croiseur1.isHit(shoot);
			contreTorpilleur1.isHit(shoot);
			torpilleur1.isHit(shoot);
		} else {
			porteAvion2.isHit(shoot);
			croiseur2.isHit(shoot);
			contreTorpilleur2.isHit(shoot);
			torpilleur2.isHit(shoot);
		}
	}

	@Override
	protected void init(char[][] grid, String pos) {
		super.init(grid, pos);
		if (grid.equals(grid1)) {
			porteAvion1 = new Ship(pos.split("X")[0], "PA");
			croiseur1 = new Ship(pos.split("X")[1], "CR");
			contreTorpilleur1 = new Ship(pos.split("X")[2], "CT");
			torpilleur1 = new Ship(pos.split("X")[3], "T");
		} else {
			porteAvion2 = new Ship(pos.split("X")[0], "PA");
			croiseur2 = new Ship(pos.split("X")[1], "CR");
			contreTorpilleur2 = new Ship(pos.split("X")[2], "CT");
			torpilleur2 = new Ship(pos.split("X")[3], "T");
		}
	}

}
