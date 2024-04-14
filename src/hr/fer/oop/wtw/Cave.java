package hr.fer.oop.wtw;

import hr.fer.oop.wtw.entities.Monster;

public class Cave {
	private int CaveInd;
	public int[] ConnectedCavesInd;
	private boolean IsDiscovered = false;
	private Monster CaveMonster=null;
	private boolean IsGoldenCave = false;
	private boolean IsTreasureOpened = false;
	
	public void SetGoldenCave() {
		IsGoldenCave = true;
	}
	public boolean GetIsGoldenCave() {
		return IsGoldenCave;
	}
	
	public void SetTreasureOpened() {
		if (this.IsGoldenCave) IsTreasureOpened = true;
	}
	
	public boolean IsTreasureOpened() {
		if (this.IsGoldenCave) return IsTreasureOpened;
		else return false;
	}
	
	
	public Cave(int CaveInd, Monster CaveMonster, int[] ConnectedCavesInd) {
		this.ConnectedCavesInd = new int[ConnectedCavesInd.length];
		int k=0;
		for (int i : ConnectedCavesInd) {
			this.ConnectedCavesInd[k] = i;
			k++;
		}
		this.CaveInd = CaveInd;
		this.CaveMonster = CaveMonster;
	}
	
	public Monster GetCaveMonster() {
		return CaveMonster;
	}
	
	public int GetInd() {
		return CaveInd;
	}
	
	public void SetDiscovered() {
		this.IsDiscovered = true;
	}
	
	public boolean IsDiscovered() {
		return this.IsDiscovered;
	}
	
	public String ToString() {
		if (CaveMonster != null && !CaveMonster.GetMonsterIsDead()) return "Spilja (ind: " + CaveInd + ")" + " sa čudovištem: " + CaveMonster.ToString() + "!";
		else return "Spilja (ind: " + CaveInd + ")" + " nema čudovišta. ";
		
	}
	
	//Vraća popis indeksa spilja koje su povezane sa onom nad kojom se poziva funkcija
	public String ConnectedCaves() {
		String r = "";
		for (int i : ConnectedCavesInd) {
			if (i != ConnectedCavesInd[ConnectedCavesInd.length-1]) r += i + ", ";
			else  r += " " + i;
		}
		return r;
	}
	
	
}
