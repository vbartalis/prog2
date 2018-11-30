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
public interface Csomopont {
	Csomopont nullasGyermek();

	Csomopont egyesGyermek();

	void ujNullasGyermek(Csomopont gy);

	void ujEgyesGyermek(Csomopont gy);

	char getBetu();

}

