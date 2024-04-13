package hr.fer.oop.wtw.weapons;

import hr.fer.oop.wtw.SoundPlayer;

public class Shotgun extends Weapon implements Guns{
	int munnition;
	public boolean IsOutOfAmmo;
	{super.name = "Shotgun";}
	public Shotgun(double damage) {
		super(damage);
		this.munnition = 5;
		this.IsOutOfAmmo = false;
	}
	
	public Shotgun(double damage, int munnition) {
		super(damage);
		this.munnition = munnition;
		this.IsOutOfAmmo = false;
	}
	
	
	public int GetAmmoCount() {
		return this.munnition;
	}
	
	public boolean GetIsOutOfAmmoStatus() {
		return this.IsOutOfAmmo;
	}
	
	@Override
	public void ShotFired() {
		if (munnition==0) {
			this.IsOutOfAmmo = true;
			return;
		}
		if (munnition>=0) munnition--;
		else munnition++;
		
	}
	
}
