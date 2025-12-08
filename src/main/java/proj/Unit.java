package proj;

public class Unit {
	private int strength;
	private int range;
	private int speed;
	private int health;
	private int shield;
	private double accuracy;
	private int xPos;
	private int yPos;
	
	public Unit(int strength, int range, int speed, int health, int shield, double accuracy, int xPos, int yPos) {
		this.strength = strength;
		this.range = range;
		this.speed = speed;
		this.health = health;
		this.shield = shield;
		this.accuracy = accuracy;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void takeDamage(int damage) {
		if (shield > 0) {
			shield -= damage;
		}
		else {
			health -= damage;
		}
	}
	
	public void move() {
		xPos += speed;
	}
	
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getShield() {
		return shield;
	}
	public void setShield(int shield) {
		this.shield = shield;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
	
}
