package hr.fer.oop.wtw.entities;

import hr.fer.oop.wtw.Player;

public abstract class  Monster {
	private double damage;
	private double energy;
	private boolean isDead;
	private String name;
	
	public Monster() {
		this.damage = 10;
		this.energy = 10;
	}
	
	public void SetMonsterIsDead(boolean b) {
		this.isDead = b;
	}
	
	public boolean GetMonsterIsDead() {
		return this.isDead;
	}
	
	public Monster(double damage, double energy) {
		this.damage = damage;
		this.energy = energy;
	}
	
	public double GetEnergy() {
		return energy;
	}
	
	public void SetEnergy(double x) {
		this.energy = x;
	}
	
	protected double getDamage() {
		return damage;
	}
	
	public double Damage(double dmgAmount) {
		this.energy -= dmgAmount;
		if (this.energy<=0) this.SetMonsterIsDead(true);
		return this.energy<=0 ? 0 : this.energy; //vraća 0 ako je this.energy<0 (što nema smisla da vraća)
	}
	
	public String ToString() {return name;}
	
	public abstract void makeSound();
	
	public abstract int GetMonsterTypeId();
	
	public abstract void attack(Player p);
}
