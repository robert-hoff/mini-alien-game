package proj;

import java.util.ArrayList;

public class Player {
	private int health;
	private int grassAmount;
	private int powerAmount;
	private int rockAmount;
	private ArrayList<Unit> units = new ArrayList<>();
	
	public void removeUnit(Unit unit) {
		units.remove(unit);
	}
	
	public void addUnit(Unit unit) {
		units.add(unit);
	}	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getGrassAmount() {
		return grassAmount;
	}
	public void setGrassAmount(int grassAmount) {
		this.grassAmount = grassAmount;
	}
	public int getPowerAmount() {
		return powerAmount;
	}
	public void setPowerAmount(int powerAmount) {
		this.powerAmount = powerAmount;
	}
	public int getRockAmount() {
		return rockAmount;
	}
	public void setRockAmount(int rockAmount) {
		this.rockAmount = rockAmount;
	}
	public ArrayList<Unit> getUnits() {
		return units;
	}

}
