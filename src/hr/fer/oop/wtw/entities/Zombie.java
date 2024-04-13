package hr.fer.oop.wtw.entities;

import hr.fer.oop.wtw.Player;
import hr.fer.oop.wtw.SoundPlayer;

public class Zombie extends Monster{
	private static SoundPlayer sp = new SoundPlayer();
	//TYPE_ID = 2
	public Zombie() {
		super(10,10);
	}
	String name = "Zombie";
	
	//custom Zombie
	public Zombie(double damage, double energy) {
		super(damage, energy);
	}
	
	public void makeSound() {
		System.out.println("Brainzzzzzzzzz");
		sp.playSoundInBackground("Zombie.wav");
	}
	
	public int GetMonsterTypeId() {
		return 2;
	}
	
	public void attack(Player p) {
		//Radi lakšeg formatiranja ispisa
		sp.playSoundInBackground("Zombie.wav");
		String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String YELLOW = "\u001B[33m";
		System.err.println(YELLOW + "Zombie is attacking youuuuu!! - DAMAGE: " + RESET + RED + this.getDamage() + RESET);
		if (p.Damage(this.getDamage()) <= 0) p.IsDead(true);
	}

	@Override
	public String ToString() {return name;}

}
