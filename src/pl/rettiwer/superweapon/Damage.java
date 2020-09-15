package pl.rettiwer.superweapon;

public class Damage {
	private int minDamage;
	private int maxDamage;
	private double chance;

	public Damage(int minDamage, int maxDamage, double chance) {
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.chance = chance;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}
}
