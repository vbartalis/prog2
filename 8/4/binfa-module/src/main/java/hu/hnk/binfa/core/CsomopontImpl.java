package hu.hnk.binfa.core;

import hu.hnk.binfa.common.Csomopont;

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
