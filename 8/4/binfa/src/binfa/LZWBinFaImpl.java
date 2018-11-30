/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binfa;
/*
import binfa.Csomopont;
import binfa.LZWBinFa;
*/
/**
 *
 * @author zenol
 */
public class LZWBinFaImpl implements LZWBinFa {
	protected Csomopont gyoker = new CsomopontImpl('/');
	private Csomopont fa = null;

	private int melyseg;
	private int atlagosszeg;
	private int atlagdb;
	private double szorasosszeg;
	int maxMelyseg;
	double atlag, szoras;

	public LZWBinFaImpl() {

		fa = gyoker;

	}

	public void egyBitFeldolg(char b) {
		if (b == '0') {
			if (fa.nullasGyermek() == null) 
			{
			
				Csomopont uj = new CsomopontImpl('0');
				
				fa.ujNullasGyermek(uj);

				fa = gyoker;
			} else {

				fa = fa.nullasGyermek();
			}
		} else {
			if (fa.egyesGyermek() == null) {
				Csomopont uj = new CsomopontImpl('1');
				fa.ujEgyesGyermek(uj);
				fa = gyoker;
			} else {
				fa = fa.egyesGyermek();
			}
		}
	}

	public void printTree() {
		melyseg = 0;
		printTree(gyoker, new java.io.PrintWriter(System.out));
	}

	public void printTree(java.io.PrintWriter os) {
		melyseg = 0;
		printTree(gyoker, os);
	}

	public void printTree(Csomopont elem, java.io.PrintWriter os) {

		if (elem != null) {
			++melyseg;
			printTree(elem.egyesGyermek(), os);

			for (int i = 0; i < melyseg; ++i) {
				os.print("---");
			}
			os.print(elem.getBetu());
			os.print("(");
			os.print(melyseg - 1);
			os.println(")");
			printTree(elem.nullasGyermek(), os);
			--melyseg;
		}
	}

	public int getMelyseg() {
		melyseg = maxMelyseg = 0;
		rmelyseg(gyoker);
		return maxMelyseg - 1;
	}

	public double getAtlag() {
		melyseg = atlagosszeg = atlagdb = 0;
		ratlag(gyoker);
		atlag = ((double) atlagosszeg) / atlagdb;
		return atlag;
	}

	public double getSzoras() {
		atlag = getAtlag();
		szorasosszeg = 0.0;
		melyseg = atlagdb = 0;

		rszoras(gyoker);

		if (atlagdb - 1 > 0) {
			szoras = Math.sqrt(szorasosszeg / (atlagdb - 1));
		} else {
			szoras = Math.sqrt(szorasosszeg);
		}

		return szoras;
	}

	public void rmelyseg(Csomopont elem) {
		if (elem != null) {
			++melyseg;
			if (melyseg > maxMelyseg) {
				maxMelyseg = melyseg;
			}
			rmelyseg(elem.egyesGyermek());

			rmelyseg(elem.nullasGyermek());
			--melyseg;
		}
	}

	public void ratlag(Csomopont elem) {
		if (elem != null) {
			++melyseg;
			ratlag(elem.egyesGyermek());
			ratlag(elem.nullasGyermek());
			--melyseg;
			if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
				++atlagdb;
				atlagosszeg += melyseg;
			}
		}
	}

	public void rszoras(Csomopont elem) {
		if (elem != null) {
			++melyseg;
			rszoras(elem.egyesGyermek());
			rszoras(elem.nullasGyermek());
			--melyseg;
			if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
				++atlagdb;
				szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
			}
		}
	}
	
}
