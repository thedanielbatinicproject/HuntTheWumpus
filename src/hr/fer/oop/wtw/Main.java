package hr.fer.oop.wtw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.lang.Integer;

import hr.fer.oop.wtw.entities.Dragon;
import hr.fer.oop.wtw.entities.Ghoul;
import hr.fer.oop.wtw.entities.Monster;
import hr.fer.oop.wtw.entities.SelfHealingGhoul;
import hr.fer.oop.wtw.entities.Zombie;
import hr.fer.oop.wtw.weapons.Shotgun;
import hr.fer.oop.wtw.weapons.Sword;

public class Main {
	
	//Za lakše formatiranje teksta ==> nije podržano u cmd-u
  static String RESET = "\u001B[0m";
  static String RED_TEXT = "\u001B[31m";
  static String GREEN_TEXT = "\u001B[32m";
  static String YELLOW_TEXT = "\u001B[33m";
  static String BLACK_BG = "\u001B[40m";
  static String WHITE_BG = "\u001B[47m";
  
//ZA BUILD KORISTITI OVO DOLJE  \/
//	String RESET = "";
//	String RED_TEXT = "";
//	String GREEN_TEXT = "";
//	String YELLOW_TEXT = "";
//	String BLACK_BG = "";
//	String WHITE_BG = "";

	//Shotgun ne može biti static jer se mijenja broj preostale amunicije
    private static Sword sword = new Sword(10); //damage = 10
    public static SoundPlayer soundPlayer = new SoundPlayer();;
	public static void main(String[] args) {
		
		//Pušta glavnu cave muziku koju sam skladao
		SoundPlayer mainMusicSoundPlayer = new SoundPlayer();
		mainMusicSoundPlayer.playSoundInBackground("MuonDance.wav");
		
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
		
		//Postavljamo stoga shotgun ovdje
		Shotgun shotgun = new Shotgun(30); //damage = 30
		
    	//postavljanje potrebnih objekata
    	Scanner sc = new Scanner(System.in);
    	Random rand = new Random();
    	
        //Unos imena igrača
    	System.out.print(GREEN_TEXT + WHITE_BG + "Enter your name: ");
    	String name = sc.nextLine();
    	Player MainPlayer = new Player(name);    	
    	
    	//Resetiranje formata
    	System.out.print(RESET);
    	
    	
    	//Generiranje spilja
    	Cave[] Caves = GenerateCaves(rand);
    	
    	
    	//ISPIS SPILJA ===> IZBRISATIIIIIIIIII KASNIJEEEEEEEEEEEEEE
//    	for (Cave x : Caves) {
//    		System.out.print(x.ToString());
//    		System.out.println(" --> " + x.ConnectedCaves());
//    	}
    	
    	//Postavljanje zlatne spilje
    	int GoldenCaveInd = rand.nextInt(20)+1; //biramo spilju sa zlatom u rasponu indeksa 1 do 20 (znači bez 0)
    	Caves[GoldenCaveInd].SetGoldenCave();
    	
    	MainPlayer.setActiveCave(Caves[0]); //postavljamo početnu spilju igrača na 0
    	MainPlayer.SetWeapon(sword); //Postavljamo zadano oružje na mač
    	
    	//glavni loop, tj. uvjet za prolazak igre, i stopSound bool uvjet
    	boolean GoldCaveDiscovered = false;
    	
    	while (!MainPlayer.getActiveCave().IsTreasureOpened()) {
    		
    		//Čistimo ekran nakon svakog ciklusa
    		System.out.print("\033[H\033[2J");  
    		System.out.flush();  //Trebalo bi raditi u CMDu?
    		
    		if (MainPlayer.IsDead()) {
    			System.out.print(RESET+RED_TEXT+BLACK_BG + "GAME OVER!" + RESET);
    			soundPlayer.playSoundInBackground("GameOver.wav");
    			break;
    		}
    		
    		if (GoldCaveDiscovered) {
    			System.out.print(RESET+RED_TEXT+WHITE_BG+"A cave with gold treasure was discovered! Cave no.: " + GoldenCaveInd + RESET);
    			soundPlayer.playSoundInBackground("Achi.wav");
    		}
    		
    		//ISPIS ZA SLUČAJ KADA UGRAČ UĐE U SPILJU SA ZLATOM
    		if(MainPlayer.getActiveCave().GetIsGoldenCave()) EnteredGoldenCave(MainPlayer, GoldenCaveInd, GoldCaveDiscovered, sc);
    		
    		//ISPIS ZA SLUČAJ KADA IGRAČ POBJEDI
    		if(MainPlayer.getActiveCave().IsTreasureOpened() && MainPlayer.getActiveCave().GetIsGoldenCave()) { 
    			System.out.println(RESET+GREEN_TEXT+WHITE_BG+ "VICTORY!" + RESET);
    			soundPlayer.playSoundInBackground("Victory.wav");
    			break;
    		}
    		
    		//ISPIS ZA SLUČAJ KADA SE IGRAČ NALAZI U SPILJI SA ŽIVIM ČUDOVIŠTEM
    		if (MainPlayer.getActiveCave().GetCaveMonster()!=null) {
    			if (!MainPlayer.getActiveCave().GetCaveMonster().GetMonsterIsDead()) {
    				System.out.println(RESET + BLACK_BG + RED_TEXT + "There is monster inside this cave!!!! You must act quickly!!!" + RESET);
    				MainPlayer.getActiveCave().GetCaveMonster().attack(MainPlayer);
        	    	//System.out.println(RESET+"Your health now is: "+RED_TEXT+ MainPlayer.GetHealth()+"/100.0"+RESET);
    			}
    		}
    		
    		
    		MainMenuPrint();
    		System.out.print(RESET); //RESETIRANJE FORMATIRANJA
    		String odabir = sc.next();
    		
    		switch (odabir.toUpperCase()) {
    		case "I":
    			InfoMenu(MainPlayer, Caves);
    			break;
    		case "W":
    			WeaponMenu(MainPlayer, shotgun, sc);
    			break;
    		case "A":
    			Attack(MainPlayer, MainPlayer.getActiveCave());
    			break;
    		case "M": //VRATITI UVJET
    			int a = sc.nextInt();
    			MoveMenu(MainPlayer, Caves, a);
    			break;
    		default:
    			System.out.println(RESET + RED_TEXT + "WRONG ENTRY!" + RESET);
    			break;
    		}
    		
    	}
    	
    	System.out.print(RESET + WHITE_BG + "Enter anything to exit the game.");
    	sc.next();
    	sc.close();
    	return;
}
    	
    	
    	
    	
    	
	@SuppressWarnings("unused")
	public static void EnteredGoldenCave(Player MainPlayer, int GoldenCaveInd, boolean GoldCaveDiscovered, Scanner sc) {
//		System.out.println(MainPlayer.getActiveCave().GetInd() + "   -   " + GoldenCaveInd);

		System.out.println(RESET+GREEN_TEXT+WHITE_BG+"You have found a cave with gold treasure! - Cave no. " + GoldenCaveInd+RESET);
		GoldCaveDiscovered = true;
		if (MainPlayer.getActiveCave().GetCaveMonster()!=null && MainPlayer.getActiveCave().GetCaveMonster().GetEnergy()>0) System.out.println(RESET+RED_TEXT+"There is a monster alive in the cave, you must first defeat it to open the chest with gold!"+RESET);
		if (MainPlayer.getActiveCave().GetCaveMonster()==null || MainPlayer.getActiveCave().GetCaveMonster().GetEnergy()<=0) {
			System.out.println(RESET+GREEN_TEXT+"There is no living monster in the cave. Do you want to open the treasure chest? - Y/N" + RESET);
			String[] a = sc.next().split("");
			if (a[0].toUpperCase().equals("Y"))
			{
				MainPlayer.getActiveCave().SetTreasureOpened();
			}
		}
	}

    public static Cave[] GenerateCaves(Random rand) {
    	Cave[] Caves = new Cave[21];
    	
    	//korisno za provjeru, dinamičko dodavanje i brisanje elemenata u listi
    	ArrayList<ArrayList<Integer>> ConnectedCaves = new ArrayList<>(); 
    	
    	for (int i=0; i<21; i++) {
    		int randCaveIndLen = rand.nextInt(2)+1; //količina tunela - 2 generirana
    		ArrayList<Integer> randCaveInd = new ArrayList<Integer>();
    		for (int j=0; j < randCaveIndLen; j++) {
    			randCaveInd.add(rand.nextInt(20)+1);
    		}
    		ConnectedCaves.add(randCaveInd);
    	}
    	
    	for (ArrayList<Integer> i : ConnectedCaves) {
    		// i - Lista indeksa unutar objekta Cave kojeg promatramo, sa kojima je taj isti objekt povezan
    		// j - Integer svake pojedine povezane spilje sa Spiljom koju promatramo (ona čiji je "vlasnik" i) 
    		for(Integer j : i) {
    			if (!ConnectedCaves.get(j).contains(ConnectedCaves.indexOf(i)) ) {
    				ConnectedCaves.get(j).add(ConnectedCaves.indexOf(i)); //dodaje tunel nazad prema spilji, iz koje tunel već postoji
    			}
    		}
    	}
    	
    	//ConnectedCaves - popis svih veza za svaku spilju
    	for (int i=0; i<21; i++) {
    		
    		int monster = rand.nextInt(9);
    		
    		if (monster == 0) {
    			Zombie addZombie = new Zombie();
    		// https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
        		Cave newCave = new Cave(i, addZombie, ConnectedCaves.get(i).stream().mapToInt(z -> z).toArray());
        		Caves[i] = newCave;
    		} else if (monster == 1) {
    			Dragon addDragon = new Dragon();
        		Cave newCave = new Cave(i, addDragon, ConnectedCaves.get(i).stream().mapToInt(z -> z).toArray());
        		Caves[i] = newCave;
    		} else if (monster == 2) {
    			Ghoul addGhoul = new Ghoul();
        		Cave newCave = new Cave(i, addGhoul, ConnectedCaves.get(i).stream().mapToInt(z -> z).toArray());
        		Caves[i] = newCave;
    		} else if (monster == 3) {
    			SelfHealingGhoul addSHGhoul = new SelfHealingGhoul();
        		Cave newCave = new Cave(i, addSHGhoul, ConnectedCaves.get(i).stream().mapToInt(z -> z).toArray());
        		Caves[i] = newCave;
    		} else {
    			Monster addZombie = null;
        		Cave newCave = new Cave(i, addZombie, ConnectedCaves.get(i).stream().mapToInt(z -> z).toArray());
        		Caves[i] = newCave;
    		}
    	}
    	return Caves;
    }

	
    public static void MainMenuPrint() {
        
    	System.out.println(BLACK_BG + YELLOW_TEXT);
    	System.out.println("(I)nfo - information about the current cave, a list of neighboring caves and \n         info about sounds from neighboring caves \r\n"
    			+ "(W)eapon - weapon change\r\n"
    			+ "(A)ttack - attack the monster in the current cave\r\n"
    			+ "(M)ove N - shift to the adjacent cave");
    	System.out.print(RESET + WHITE_BG + RED_TEXT + "Enter your selection ==> ");
}

	@SuppressWarnings("unused")
	public static void InfoMenu(Player MainPlayer, Cave[] Caves) {
        
//        System.out.println(Arrays.toString(MainPlayer.getActiveCave().ConnectedCavesInd));
		System.out.println(YELLOW_TEXT + MainPlayer.getActiveCave().ToString() + RESET);
		System.out.println(YELLOW_TEXT + "Tunnels from your cave connect following caves: " + MainPlayer.getActiveCave().ConnectedCaves());
		
		System.out.println(RESET + YELLOW_TEXT + "Sounds can be heard from the neighboring caves: " + RESET);
		
		boolean isAny = false;
		//gledamo postoji li ijedna spilja sa čudovištem uokolo, ako ne, onda ispisujemo "...", inače ispisujemo monster.makesound
		for (int x : MainPlayer.getActiveCave().ConnectedCavesInd) {
			boolean isAny1 = false; 
			if (Caves[x].GetCaveMonster()!=null) {
				Caves[x].GetCaveMonster().makeSound(); 
				isAny1 = true;
				isAny = isAny1;
			}
			
		}
		if (!isAny) System.out.println(RESET + GREEN_TEXT + "There is only silence in the tunnels." + RESET);
		
		if(MainPlayer.GetHealth()<0) {
			System.out.println(RESET + WHITE_BG + "Your health is: " + RED_TEXT + "0.0/100.0"+RESET);
		} else {
			System.out.println(RESET + WHITE_BG + "Your health is: " + RED_TEXT + MainPlayer.GetHealth()+ "/100.0");
		}
		
		System.out.print(WHITE_BG + "Your selected weapon is: "+ RED_TEXT + MainPlayer.GetWeapon().GetName());
		//Ispisujemo ammo ukoliko je selected weapon shotgun
		if (MainPlayer.GetWeapon().getClass() == Shotgun.class) {
			System.out.println(" | Ammo count: " + RED_TEXT + ((Shotgun) MainPlayer.GetWeapon()).GetAmmoCount() + "/5"+RESET);
		} else System.out.println(RESET);
		
		
		
	}

	@SuppressWarnings("unused")
	public static void Attack(Player MainPlayer, Cave cave) {
		if(cave.GetCaveMonster() == null) {
			System.out.println(RESET + YELLOW_TEXT + "There are no monsters inside this cave!" + RESET);
		} else {
			//System.out.println(cave.GetCaveMonster().ToString());
			MainPlayer.AttackMonster(cave.GetCaveMonster());
			if (cave.GetCaveMonster().GetMonsterIsDead()) {
				System.out.println(RESET+GREEN_TEXT+"You have successfully killed the monster! The cave is now free from monster!"+RESET);
			}
			else {
				System.out.println(RESET+RED_TEXT+"You haven't killed the monster! Now, it will attack you!" + RESET);
			}
		}
	}
	
	public static void WeaponMenu(Player MainPlayer, Shotgun shotgun, Scanner sc) {
		System.out.println("So, the weapon you want to change?\r\n"
				+ "Okay then, tell me which one to give you.\r\n"
				+ "Press 1 to select a sword\r\n"
				+ "Press 2 to select shotgun");
		
		int a = sc.nextInt();
		if (a == 1) {
			System.out.println("Selected 1");
			MainPlayer.SetWeapon(sword);
			soundPlayer.playSoundInBackground("drawSword.wav");
		} else if (a == 2){
			MainPlayer.SetWeapon(shotgun);
			soundPlayer.playSoundInBackground("DrawShotgun.wav");
			System.out.println("Selected 2");
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void MoveMenu(Player MainPlayer, Cave[] Caves, int desirableCaveInd) {
        boolean moved = false;
		if (desirableCaveInd <= Caves.length && desirableCaveInd>=0)
		{
			for (int k : MainPlayer.getActiveCave().ConnectedCavesInd)
				if (desirableCaveInd == k) { 
					MainPlayer.setActiveCave(Caves[desirableCaveInd]); 
					moved = true; 
					return;
				}
		} else {
			System.out.println(RESET + RED_TEXT + "WRONG ENTRY!" + RESET);
			return;
		}
		if (!moved) {
			System.out.println(RESET + RED_TEXT + "There is no such tunnel...." + RESET);
			return;
		} 
	}
	
} //CLASS BODY ZAGRADA


