/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binfa;

/**
 *
 * @author zenol
 */
public class CsomopontImpl implements Csomopont {
	public CsomopontImpl(char betu) {
		this.betu = betu;
		balNulla = null;
		jobbEgy = null;
	}

	public Csomopont nullasGyermek() {
		return balNulla;
	}

	public Csomopont egyesGyermek() {
		return jobbEgy;
	}

	public void ujNullasGyermek(Csomopont gy) {
		balNulla = gy;
	}

	public void ujEgyesGyermek(Csomopont gy) {
		jobbEgy = gy;
	}

	public char getBetu() {
		return betu;
	}

	private char betu;

	private Csomopont balNulla = null;
	private Csomopont jobbEgy = null;


}

