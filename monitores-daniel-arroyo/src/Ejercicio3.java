
/*
 * Author: Daniel Arroyo
 */

class HardDrive {
	int[] pistas;
	int pistaAct;
	boolean estado;

	public HardDrive() {
		this.pistas = new int[20];
		this.pistaAct = -1;
		this.estado = false;
	}

	public synchronized void mostrarDiscos() {
		System.out.print("discos: ");
		for (int i = 0; i < pistas.length; i++) {

			System.out.print("[" + pistas[i] + "]");
		}
		System.out.println("");
	}

	public synchronized void leer(int numArchivo) throws InterruptedException {
		
		if (pistas[numArchivo] == 0) {
			pistas[numArchivo] = 1;
			pistaAct = numArchivo;
			estado = true;
			notifyAll();
			wait();
		} else if (pistas[numArchivo] == 1) {
			System.out.println("Sharing " + numArchivo);
			
		}
	}

	public int getPistaAct() {
		return pistaAct;
	}

}

class Lector extends Thread {
	private HardDrive hd;
	private boolean needleActiva;

	public Lector(HardDrive hd, boolean needleActiva) {
		this.hd = hd;
		this.needleActiva = needleActiva;
	}

	public void run() {
		while(needleActiva == true) {
			synchronized (hd) {	
				while (hd.estado == false) {
					try {
						hd.wait();					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				while (hd.estado == true) {
					System.out.println("Reading " + hd.pistaAct);
					try {
						sleep((long) (Math.random() * 2000 + 1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();	
					}
					hd.mostrarDiscos();
					System.out.println("Read " + hd.pistaAct);
					hd.pistas[hd.pistaAct] = 0;
					hd.notifyAll();
					hd.estado = false;
				}
			}
		}
	}

}

class Procesos extends Thread {
	private HardDrive hd;
	private int[] archivos = new int[10];

	public Procesos(HardDrive hd) {
		this.hd = hd;
		for (int i = 0; i < 10; i++) {
			archivos[i] = 0 + (int) (Math.random() * 19);
		}

	}

	public void run() {
	
		for (int i = 0; i < archivos.length; i++) {
			try {
				hd.leer(archivos[i]);			
				sleep((long) (Math.random() * 2000 + 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class Ejercicio3 {
	public static void main(String[] args) throws InterruptedException {
		HardDrive hd = new HardDrive();
		boolean needleActiva = true;
		Thread lector = new Lector(hd, needleActiva);
		lector.start();

		for (int i = 0; i < 10; i++) {
			Thread proceso = new Procesos(hd);
			proceso.start();
			proceso.join();
		}		
		
		needleActiva = false;
		
		System.out.println("Fin del programa");
	}
}
