package kr.or.ddit.basic;

public class M22 {
	public static void main(String[] args) {
	Normal normal1 =new Normal();
	Normal normal2 =new Normal();
	Singleton singleton1 =Singleton.getInstance();
	Singleton singleton2 =Singleton.getInstance();
	Singleton singleton3 =Singleton.getInstance();
	Singleton singleton4 =Singleton.getInstance();
	Singleton singleton5 =Singleton.getInstance();
	Singleton singleton6 =Singleton.getInstance();
	
	}
}
class Normal {
	public Normal(){
		System.out.println("Normal Instance Created..");
	}
}

class Singleton {
	static String s= "";
	private static Singleton singleton = new Singleton(s);
	private Singleton(String s) {
		System.out.println("Singleton22 Instance Created .");
	}
//	private Singleton() {
//		System.out.println("Singleton Instance Created .");
//	}
	public static Singleton getInstance() {
		return singleton;
	}
}