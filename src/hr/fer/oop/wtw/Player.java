package hr.fer.oop.wtw;
import hr.fer.oop.wtw.entities.Monster;
import hr.fer.oop.wtw.weapons.Guns;
import hr.fer.oop.wtw.weapons.Shotgun;
import hr.fer.oop.wtw.weapons.Weapon;

public class Player {
	static SoundPlayer sp = new SoundPlayer();
	private double health;
	private boolean IsDead;
	private String name;
	private Weapon activeWeapon;
	private Cave activeCave;
	
	public double GetHealth() {
		return health;
	}
	
	
	
	
	public Cave getActiveCave() {
		return activeCave;
	}

	public void setActiveCave(Cave activeCave) {
		this.activeCave = activeCave;
	}

	public void SetName(String name) {
		this.name = name;
	}
	
	public Player(String name) {
		this.SetName(name);
		this.health = 100;
	}
	
	public String GetName() {
		return this.name;
	}
	
	//osim što skida hp, vraća i trenutni health
	public double Damage(double damageAmount) {
		health -= damageAmount;
		return health;
	} 
	public double Heal(double healAmount) {
		health += healAmount;
		return health;
	}
	
	public void IsDead(boolean IsDead) {
		sp.playSoundInBackground("Death.wav");
		this.IsDead = IsDead;
	}
	
	public boolean IsDead() {
		return this.IsDead;
	}
	
	public void SetWeapon(Weapon w) {
		activeWeapon = w;
	}
	
	public Weapon GetWeapon() {
		return (Weapon) activeWeapon;
	}
	
	public void AttackMonster(Monster m) {
		if (activeWeapon.getClass() == Shotgun.class) {
			if(((Shotgun) activeWeapon).GetIsOutOfAmmoStatus()) {
				System.out.println("Your shotgun is out of ammo! No damage was made!");
				return;
			} else {
				m.Damage(((Shotgun)activeWeapon).GetWeaponDamage());
				((Guns)activeWeapon).ShotFired(); //Umanjuje amuniciju za 1 dolje
				sp.playSoundInBackground("shotgun.wav");
			}
		} else { 
			sp.playSoundInBackground("sword.wav");
			m.Damage(activeWeapon.GetWeaponDamage());
		}
	} 
}
