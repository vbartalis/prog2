/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binfa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zenol
 */
public class BinfaTest {
    
    public BinfaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    
    char [] b = {'0','1','1','1','1','0','0','1','0','0','1','0','0','1','0','0','0','1','1','1'};
	
	@SuppressWarnings("deprecation")
	@Test
	public void egyBitFeldolgMelysegTest() {
		LZWBinFa binfa = new LZWBinFaImpl();
		
		for(int i = 0; i < b.length; i++) {
			binfa.egyBitFeldolg(b[i]);
		}

		int melyseg = binfa.getMelyseg();
		assertEquals(4, melyseg);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void egyBitFeldolgAtlagTest() {
		LZWBinFa binfa = new LZWBinFaImpl();
		for(int i = 0; i < b.length; i++) {
			binfa.egyBitFeldolg(b[i]);
		}

		double atlag = binfa.getAtlag();
	//	assertEquals(2.750000, atlag, 0.0001);          //ok
                assertEquals(2.850000, atlag, 0.0001);          //fail
                
	}

	@SuppressWarnings("deprecation")
	@Test
	public void egyBitFeldolgSzorasTest() {
		LZWBinFa binfa = new LZWBinFaImpl();
		for(int i = 0; i < b.length; i++) {
			binfa.egyBitFeldolg(b[i]);
		}
		double szoras = binfa.getSzoras();
		assertEquals(0.957427, szoras,0.0001);
	}
    
}
