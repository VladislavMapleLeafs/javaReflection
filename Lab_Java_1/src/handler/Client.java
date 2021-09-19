package handler;

public class Client {

	public static void modifiers(int mods, StringBuffer buf) {
		Handler m1 = new Public();
		Handler m2 = new Protected();
		Handler m3 = new Private();
		Handler m4 = new Abstract();
		Handler m5 = new Final();
		Handler m6 = new Static();
		Handler m7 = new Interface();
		Handler m8 = new Synchronized();
		Handler m9 = new Transient();
		Handler m10 = new Volatile();
		Handler m11 = new Native();
		m1.next = m2;
		m2.next = m3;
		m3.next = m4;
		m4.next = m5;
		m5.next = m6;
		m6.next = m7;
		m7.next = m8;
		m8.next = m9;
		m9.next = m10;
		m10.next = m11;
		m1.writeModifier(mods, buf);
	}
}
