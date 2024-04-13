package hr.fer.oop.wtw.weapons;

public abstract class Weapon {
	//private double perishableState = 10; --> moguća buduća nadogradnja da se oružje može pokvariti
	
	//SWORD - 10
	//SHOTGUN - 30
	protected String name;
	public String GetName() {
		return name;
	}
	private double damage;
	
	public Weapon(double damage) {
		this.damage = damage;
	}
	
	public double GetWeaponDamage() {
		return damage;
	}
}
