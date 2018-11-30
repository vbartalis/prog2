
package hu.hnk.binfa.core;

import hu.hnk.binfa.common.LZWBinFa;

public class App {

	public static void main(String args[]) {
		// application context kell a spring bean injektálásához
		//ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "spring.xml" });
		// itt pedig lekérjük
		//LZWBinFa binfa = (LZWBinFa) appContext.getBean("lzwBinFa");
		LZWBinFa binfa = new LZWBinFaImpl();
		//testMethod(binfa);
		for (char c :"01111001001001000111".toCharArray()) {
			binfa.egyBitFeldolg(c);
		}
		System.out.println("depth = " + binfa.getMelyseg());
		System.out.println("mean = " + binfa.getAtlag());
		System.out.println("var = " + binfa.getSzoras());
	}

	public static void testMethod(LZWBinFa binfa) {
		char [] b = {'0','1','1','1','1','0','0','1','0','0','1','0','0','1','0','0','0','1','1','1'};
		for(int i = 0; i < b.length; i++) {
			binfa.egyBitFeldolg(b[i]);
		}
	}
	
}
