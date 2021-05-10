import java.util.Scanner;

/*
 * Author: Daniel Arroyo
 */

class Contador {
	private int c = 0;

	public Contador(int num) {
		this.c = num;
	}

	public synchronized void increment() {
		c++;
	}

	public synchronized void decrement() {
		c--;
	}

	public synchronized int value() {
		return c;
	}
}

class CuentaVocales extends Thread {
	private Contador miCont;
	private String texto;
	private char vocal;

	public CuentaVocales(Contador miCont, String texto, char vocal) {
		this.miCont = miCont;
		this.texto = texto;
		this.vocal = vocal;
	}

	public void run() {

		for (int x = 0; x < texto.length(); x++) {
			char letraActual = texto.charAt(x);

			if (vocal == letraActual) {
				miCont.increment();
			} else {

			}

		}
	}
}

public class Ejercicio1 {
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		Contador miContador = new Contador(0);
		char[] vocales = { 'a', 'e', 'i', 'o', 'u' };
		String texto = sc.nextLine();
		for (int i = 0; i < 5; i++) {
			Thread hilo = new CuentaVocales(miContador, texto, vocales[i]);
			hilo.start();
			hilo.join();
		}

		System.out.println("El numerom total de vocales es: " + miContador.value());
	}
}