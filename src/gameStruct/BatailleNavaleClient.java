package gameStruct;

import java.util.Scanner;

public class BatailleNavaleClient extends ClientBattleField {

	// private char myGrid[][];
	// private char oppGrid[][];
	private String myPos;
	private Scanner sc = new Scanner(System.in);
	private int nombreErreurTirAutorise;

	public BatailleNavaleClient() {
		super();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				myGrid[i][j] = 'O';
				oppGrid[i][j] = '?';
				nombreErreurTirAutorise = 3;
			}
		}

		// On utilise les structures de bateaux juste pour détecter les colisions afin
		// d'envoyer des données sans erreurs au serveur.
		System.out.println("Positionnez vos navires.\nlongueur des navires\n\tPorte avion :"
				+ " 5\n\tCroiseur : 4\n\tContre-torpilleur : 3\n\tTorpilleur :20");
		System.out.println("Entrez Origine puis orientation du Porte-avion : modèle:1 2 V");
		String coord = sc.nextLine().trim();
		Ship porteAvion = new Ship(coord, "PA");
		while (!porteAvion.onGrid()) {
			System.out.println("Navire hors grille. Entrez une valeur correcte");
			coord = sc.nextLine().trim();
			porteAvion = new Ship(coord, "PA");
		}
		this.myPos = coord + "X";
		System.out.println("Entrez Origine puis orientation du Croiseur : modèle:1 2 V");
		coord = sc.nextLine().trim();
		Ship Croiseur = new Ship(coord, "CR");
		while (porteAvion.conflictedPosition(Croiseur) || !Croiseur.onGrid()) {
			System.out.println("Collision entre bateaux ou bateaux hors de grille. Choisissez une autre position");
			coord = sc.nextLine().trim();
			Croiseur = new Ship(coord, "CR");
		}
		this.myPos += coord + "X";
		System.out.println("Entrez Origine puis orientation du Contre-Torpilleur : modèle:1 2 V");
		coord = sc.nextLine().trim();
		Ship contreTorpilleur = new Ship(coord, "CT");
		while (porteAvion.conflictedPosition(contreTorpilleur) || Croiseur.conflictedPosition(contreTorpilleur)
				|| !contreTorpilleur.onGrid()) {
			System.out.println("Collision entre bateaux ou bateaux hors de grille. Choisissez une autre position");
			coord = sc.nextLine().trim();
			contreTorpilleur = new Ship(coord, "CT");
		}
		this.myPos += coord + "X";
		System.out.println("Entrez Origine puis orientation du Torpilleur : modèle:1 2 V");
		coord = sc.nextLine().trim();
		Ship torpilleur = new Ship(coord, "T");
		while (porteAvion.conflictedPosition(torpilleur) || Croiseur.conflictedPosition(torpilleur)
				|| contreTorpilleur.conflictedPosition(torpilleur) || !torpilleur.onGrid()) {
			System.out.println("Collision entre bateaux ou bateaux hors de grille. Choisissez une autre position");
			coord = sc.nextLine().trim();
			torpilleur = new Ship(coord, "T");
		}
		this.myPos += coord;

		// myPos = "0 0 vX1 1 HX2 2 HX3 7 V";
		init(myGrid, myPos);
		print();
	}

	public char[][] getMyGrid() {
		return myGrid;
	}

	public char[][] getOppGrid() {
		return oppGrid;
	}

	public String getMyPos() {
		return this.myPos;
	}

	public String shoot() {
		System.out.println("Entrez coordonées de shoot (De 0 à 9): x y modèle : 7 5");
		String shoot = sc.nextLine().trim();
		if (Integer.parseInt(shoot.trim().split(" ")[0]) > 9 || Integer.parseInt(shoot.trim().split(" ")[1]) > 9
				|| shoot.trim().split(" ").length != 2) {
			System.out
					.println("Erreur de saisie! la valeur entrée doit être entre 0 et 9 et espacé d'un unique espace");
			if (nombreErreurTirAutorise-- > 0) {
				return shoot();
			}
			return "0 0"; // Si trop d'erreurs sont commises, on tire à l'origine
		}
		return shoot;
	}

	public void receiveShootAndUpdateGrid(String shootedCase) {

		int x = Integer.parseInt(shootedCase.split(" ")[0]);
		int y = Integer.parseInt(shootedCase.split(" ")[1]);
		System.out.print("L'adversaire a shooté à " + shootedCase);
		if (this.myGrid[x][y] == 'I') {
			this.myGrid[x][y] = 'X';
			System.out.println(" Et vous a touché!");
		} else
			System.out.println(" Et vous a manqué");

		print();
	}

	public void updateOpponentGrid(String shootedCase, boolean reachedTarget) {
		int x = Integer.parseInt(shootedCase.split(" ")[0]);
		int y = Integer.parseInt(shootedCase.split(" ")[1]);
		if (reachedTarget)
			oppGrid[x][y] = 'X';
		else
			oppGrid[x][y] = 'O';
		print();
	}

	private void print() {
		for (int i = 0; i < myGrid.length; i++) {
			for (int j = 0; j < myGrid.length; j++) {
				System.out.print(myGrid[i][j] + " ");
			}
			System.out.print("   ");
			for (int j = 0; j < oppGrid.length; j++) {
				System.out.print(oppGrid[i][j] + " ");
			}
			System.out.println();
		}
	}

}
