package hu.hnk.binfa.core;

import hu.hnk.binfa.common.Csomopont;
import hu.hnk.binfa.common.LZWBinFa;

//
// z3a7.cpp to (Z3A7.java) LZWBinFa.java, a z3a7.cpp mechanikus átírata Java-ba.
//
// Együtt támadjuk meg: http://progpater.blog.hu/2011/04/14/egyutt_tamadjuk_meg
// LZW fa építő 3. C++ átirata a C valtozatbol (+mélység, atlag és szórás)
// Programozó Páternoszter
//
// Copyright (C) 2011, 2012, Bátfai Norbert, nbatfai@inf.unideb.hu, nbatfai@gmail.com
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
// Ez a program szabad szoftver; terjeszthetõ illetve módosítható a
// Free Software Foundation által kiadott GNU General Public License
// dokumentumában leírtak; akár a licenc 3-as, akár (tetszõleges) késõbbi
// változata szerint.
//
// Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
// További részleteket a GNU General Public License tartalmaz.
//
// A felhasználónak a programmal együtt meg kell kapnia a GNU General
// Public License egy példányát; ha mégsem kapta meg, akkor
// tekintse meg a <http://www.gnu.org/licenses/> oldalon.
//
//
// Version history:
//
// 0.0.1,       http://progpater.blog.hu/2011/02/19/gyonyor_a_tomor
// 0.0.2,       csomópontok mutatóinak NULLázása (nem fejtette meg senki :)
// 0.0.3,       http://progpater.blog.hu/2011/03/05/labormeres_otthon_avagy_hogyan_dolgozok_fel_egy_pedat
// 0.0.4,       z.cpp: a C verzióból svn: bevezetes/C/ziv/z.c átírjuk C++-ra
//              http://progpater.blog.hu/2011/03/31/imadni_fogjatok_a_c_t_egy_emberkent_tiszta_szivbol
// 0.0.5,       z2.cpp: az fgv(*mut)-ok helyett fgv(&ref)
// 0.0.6,       z3.cpp: Csomopont beágyazva
//              http://progpater.blog.hu/2011/04/01/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_2
// 0.0.6.1      z3a2.c: LZWBinFa már nem barátja a Csomopont-nak, mert annak tagjait nem használja direktben
// 0.0.6.2      Kis kommentezést teszünk bele 1. lépésként (hogy a kicsit lemaradt hallgatóknak is
//              könnyebb legyen, jól megtűzdeljük további olvasmányokkal)
//              http://progpater.blog.hu/2011/04/14/egyutt_tamadjuk_meg
//              (majd a 2. lépésben "beletesszük a d.c-t", majd s 3. lépésben a parancssorsor argok feldolgozását)
// 0.0.6.3      z3a2.c: Fejlesztgetjük a forrást: http://progpater.blog.hu/2011/04/17/a_tizedik_tizenegyedik_labor
// 0.0.6.4      SVN-beli, http://www.inf.unideb.hu/~nbatfai/p1/forrasok-SVN/bevezetes/vedes/
// 0.0.6.5      2012.03.20, z3a4.cpp: N betűk (hiányok), sorvégek, vezető komment figyelmen kívül: http://progpater.blog.hu/2012/03/20/a_vedes_elokeszitese
// 0.0.6.6      z3a5.cpp: mamenyaka kolléga észrevételére a több komment sor figyelmen kívül hagyása
//		http://progpater.blog.hu/2012/03/20/a_vedes_elokeszitese/fullcommentlist/1#c16150365
// 0.0.6.7	Javaslom ezt a verziót választani védendő programnak
// 0.0.6.8	z3a7.cpp: pár kisebb javítás, illetve a védések támogatásához további komment a <<
// 		eltoló operátort tagfüggvényként, illetve globális függvényként túlterhelő részekhez.
//		http://progpater.blog.hu/2012/04/10/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_4/fullcommentlist/1#c16341099
// prog2-re,    z3a7.cpp to Z3A7.java, teljesen mechanikus átírás, néhány 
//              soronként a C++ kódot betettem kommentbe és utána leírtam 
//              ugyanazt a néhány sort Javában. Ez tehát nem egy tiszta Java 
//              implementáció, from scratch, hanem egy szinte soronkénti átírat, 
//              de éppen ezért érdekes összevetni a C++ és a Java leírást.
//              Ennek megfelelően a Java progi OO szerkezete ua., mint a C++ 
//              változat volt, a lényegi működést tekintve is helyesek a 
//              kommentek (de a Javában kimaradt a << operátor túlterhelése, a 
//              destruktorok stb. természetesen). 
//

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