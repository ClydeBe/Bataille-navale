package gameStruct;

public class BatailleNavale {

	private ServerBattleField Grid;

	public BatailleNavale(String posPlayer1, String posPlayer2) {
		Grid = new ServerBattleField(posPlayer1, posPlayer2);
	}

	public boolean isOver() {
		return Grid.isOver();
	}

	public boolean shoot(int oppNumber, String coord) {
		return Grid.shoot(oppNumber, coord);
	}

	public String sunk(int oppNumber) {
		return Grid.notifySinkedShip(oppNumber);
	}
}
