package gameStruct;

public abstract class BattleField {

	protected void init(char[][] grid, String pos) {
		String[] posPA = pos.split("X")[0].split(" ");
		String[] posCr = pos.split("X")[1].split(" ");
		String[] posCT = pos.split("X")[2].split(" ");
		String[] posT = pos.split("X")[3].split(" ");

		// Positionnement PA
		int xPA = Integer.parseInt(posPA[0]);
		int yPA = Integer.parseInt(posPA[1]);
		if (posPA[2].equalsIgnoreCase("H")) {
			for (int i = 0; i < 5; i++) // Taille PA = 5
				grid[xPA][yPA + i] = 'I';
		} else {
			for (int i = 0; i < 5; i++)
				grid[xPA + i][yPA] = 'I';
		}

		// Positionnement Cr
		int xCr = Integer.parseInt(posCr[0]);
		int yCr = Integer.parseInt(posCr[1]);
		if (posCr[2].equalsIgnoreCase("H")) {
			for (int i = 0; i < 4; i++) // Taille Cr = 4
				grid[xCr][yCr + i] = 'I';
		} else {
			for (int i = 0; i < 4; i++)
				grid[xCr + i][yCr] = 'I';
		}

		// Positionnement CT
		int xCT = Integer.parseInt(posCT[0]);
		int yCT = Integer.parseInt(posCT[1]);
		if (posCT[2].equalsIgnoreCase("H")) {
			for (int i = 0; i < 3; i++) // Taille CT = 3
				grid[xCT][yCT + i] = 'I';
		} else {
			for (int i = 0; i < 3; i++)
				grid[xCT + i][yCT] = 'I';
		}

		// Positionnement T
		int xT = Integer.parseInt(posT[0]);
		int yT = Integer.parseInt(posT[1]);
		if (posT[2].equalsIgnoreCase("H")) {
			for (int i = 0; i < 2; i++) // Taille T = 2
				grid[xT][yT + i] = 'I';
		} else {
			for (int i = 0; i < 2; i++)
				grid[xT + i][yT] = 'I';
		}
	}

	// Les objets étant passés par référence, on pourra faire passer la grille
	protected boolean shoot(char[][] oppGrid, String coord) {
		int x = Integer.parseInt(coord.split(" ")[0]);
		int y = Integer.parseInt(coord.split(" ")[1]);
		if (oppGrid[x][y] == 'I') {
			oppGrid[x][y] = 'X';
			return true;
		}
		return false;
	}
}
