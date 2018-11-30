import org.junit.Test;

import hu.hnk.binfa.common.LZWBinFa;
import hu.hnk.binfa.core.LZWBinFaImpl;
import junit.framework.Assert.*;

@SuppressWarnings("deprecation")
public class AppTest {
	/*
	 * b = 01111001001001000111 melyseg=4 altag=2.750000 szoras=0.957427
	 */
	
	char [] b = {'0','1','1','1','1','0','0','1','0','0','1','0','0','1','0','0','0','1','1','1'};
	
	@SuppressWarnings("deprecation")
	@Test
	public void egyBitFeldolgMelysegTest() {
		LZWBinFa binfa = new LZWBinFaImpl();
		
		for(int i = 0; i < b.length; i++) {
			binfa.egyBitFeldolg(b[i]);
		}

		int melyseg = binfa.getMelyseg();
		Assert.assertEquals(4, melyseg);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void egyBitFeldolgAtlagTest() {
		LZWBinFa binfa = new LZWBinFaImpl();
		for(int i = 0; i < b.length; i++) {
			binfa.egyBitFeldolg(b[i]);
		}

		double atlag = binfa.getAtlag();
		Assert.assertEquals(2.750000, atlag);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void egyBitFeldolgSzorasTest() {
		LZWBinFa binfa = new LZWBinFaImpl();
		for(int i = 0; i < b.length; i++) {
			binfa.egyBitFeldolg(b[i]);
		}
		double szoras = binfa.getSzoras();
		Assert.assertEquals(0.957427, szoras,0.0001);
	}
}
