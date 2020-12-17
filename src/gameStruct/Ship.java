package gameStruct;

import java.util.HashMap;

public class Ship {

	private static final HashMap<String, Integer> shipLength = new HashMap<String, Integer>() {
		{
			put("PA", 5); // Porte avion
			put("CR", 4); // Croiseur
			put("CT", 3); // Contre torpilleur
			put("T", 2); // Torpilleur
		}
	};

	private String origin;
	private String orientation;
	private int length, lives;
	private String[] parts;

	public Ship(String init, String type) {
		String[] position = init.split(" ");
		this.origin = position[0] + " " + position[1];
		this.orientation = position[2];
		this.length = shipLength.get(type);
		parts = new String[length];
		int x = Integer.parseInt(position[0]);
		int y = Integer.parseInt(position[1]);
		if (orientation.equalsIgnoreCase("H")) {
			for (int i = 0; i < length; i++) {
				parts[i] = x + " " + y;
				y++;
			}
		} else {
			for (int i = 0; i < length; i++) {
				parts[i] = x + " " + y;
				x++;
			}
		}
		lives = length;
	}

	public String[] getParts() {
		return parts;
	}

	private boolean contains(String s) {
		for (String i : parts) {
			if (i.equals(s))
				return true;
		}
		return false;
	}

	public boolean isHit(String shoot) {
		if (contains(shoot)) {
			lives--;
			return true;
		}
		return false;
	}

	public boolean isSunk() {
		return this.lives == 0;
	}

	public boolean conflictedPosition(Ship newShip) {
		for (String i : parts) {
			for (String j : newShip.getParts()) {
				if (i.equals(j))
					return true;
			}
		}
		return false;
	}

	public boolean onGrid() {
		int x = Integer.parseInt(origin.split(" ")[0]);
		int y = Integer.parseInt(origin.split(" ")[1]);
		if (orientation.equalsIgnoreCase("H")) {
			return (x + length) < 10;
		}
		return (y + length) < 10;
	}
}
