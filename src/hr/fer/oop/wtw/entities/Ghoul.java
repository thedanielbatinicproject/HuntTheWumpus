package hr.fer.oop.wtw.entities;

import hr.fer.oop.wtw.Player;
import hr.fer.oop.wtw.SoundPlayer;

public class Ghoul extends Monster{
	//TYPE_ID = 3
	private static SoundPlayer sp = new SoundPlayer();
	private double MaxEnergy; //početna postavljena maximalna energija ghoula
	
	String name = "Ghoul";
	
	public Ghoul() {
		super(20, 25);
		MaxEnergy = 25;
	}
	
	//custom Ghoul
	public Ghoul(double damage, double energy) {
		super(damage, energy);
		MaxEnergy = energy;
	}
	
	
	public double GetGhoulMaxEnergy() {
		return this.MaxEnergy;
	}
	
	//postavlja energiju Ghoula na vrijednost MaxEnergy
	public void SetGhoulMaxEnergy() {
		this.SetEnergy(this.GetGhoulMaxEnergy());
	}
	//Postavlja maxenergy na vrijednost x
	public void SetGhoulMaxEnergy(double x) {
		MaxEnergy = x;
	}
	
	public void makeSound() {
		System.out.println("Not great, not terrible... atttaaack!");
		sp.playSoundInBackground("Ghoul.wav");
	}
	public int GetMonsterTypeId() {
		return 3;
	}
	
	@Override
	public String ToString() {return name;}
	
	@Override
	public void attack(Player p) {
		sp.playSoundInBackground("Ghoul.wav");
		//Radi lakšeg formatiranja ispisa
		String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String YELLOW = "\u001B[33m";
		System.err.println(YELLOW + "Ghoullll is attacking youuu!! - DAMAGE: " + RESET + RED + this.getDamage() + RESET);
		if (p.Damage(this.getDamage()) <= 0) p.IsDead(true);
	}

}
