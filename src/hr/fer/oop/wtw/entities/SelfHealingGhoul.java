package hr.fer.oop.wtw.entities;
import hr.fer.oop.wtw.Player;
import hr.fer.oop.wtw.SoundPlayer;

public class SelfHealingGhoul extends Ghoul{
	//TYPE_ID = 4
	private static SoundPlayer sp = new SoundPlayer();
	public SelfHealingGhoul() {
		super(20, 25);
		this.SetGhoulMaxEnergy(25);
	}
	
	//custom Ghoul
	public SelfHealingGhoul(double damage, double energy) {
		super(damage, energy);
		this.SetGhoulMaxEnergy(energy);
	}
	
	@Override
	public void makeSound() {
		System.out.println("Ča ti je, niš mi ni!");
		sp.playSoundInBackground("Ghoul.wav");
	}

	@Override
	public int GetMonsterTypeId() {
		return 4;
	}

	@Override
	public void attack(Player p) {
		sp.playSoundInBackground("Ghoul.wav");
		//Radi lakšeg formatiranja ispisa
		String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String YELLOW = "\u001B[33m";
		System.err.println(YELLOW + "Self-Healing Ghoul is attacking youuuuu!! - DAMAGE: " + RESET + RED + this.getDamage() + RESET);
		if (this.GetEnergy()<this.GetGhoulMaxEnergy()) this.SetGhoulMaxEnergy();
		if (p.Damage(this.getDamage()) <= 0) p.IsDead(true);
		
		
	}

}
