package hr.fer.oop.wtw.entities;

import hr.fer.oop.wtw.Player;
import hr.fer.oop.wtw.SoundPlayer;

public class Dragon extends Monster{
	//TYPE_ID = 1
	private static SoundPlayer sp = new SoundPlayer();
	public Dragon() {
		super(15,10);
	}
	
	String name = "Dragon";
	
	//custom Dragon
	public Dragon(double damage, double energy) {
		super(damage, energy);
	}

	public void makeSound() {
		System.out.println("Whooooosh");
		sp.playSoundInBackground("Dragon.wav");
	}

	public int GetMonsterTypeId() {
		return 1;
	}

	@Override
	public void attack(Player p) {
		sp.playSoundInBackground("Dragon.wav");
		//Radi lakšeg formatiranja ispisa
		String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String YELLOW = "\u001B[33m";
		System.err.println(YELLOW + "Draagooonnn is attacking youuuuu!! - DAMAGE: " + RESET + RED + this.getDamage() + RESET);
		if (p.Damage(this.getDamage()) <= 0) p.IsDead(true);
	}

	@Override
	public String ToString() {return name;}

}
