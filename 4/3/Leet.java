import java.io.*;
import java.util.*;

public class Leet {
	public static void main(String[] args) throws FileNotFoundException{
		Scanner input = new Scanner(new File("in.txt"));
		PrintStream output = new PrintStream(new File("out.txt"));
		leetSpeak(input, output);
	}

	public static void leetSpeak(Scanner input, PrintStream output){
		while (input.hasNextLine()){
			String line = input.nextLine();
			Scanner tokens = new Scanner(line);

			while (tokens.hasNext()){
				String token = tokens.next();
				token = token.replace("a", "4");
				token = token.replace("b", "|3");
				token = token.replace("c", "(");
				token = token.replace("d", "|)");
				token = token.replace("e", "3");
				token = token.replace("f", "|=");
				token = token.replace("g", "6");
				token = token.replace("h", "[-]");
				token = token.replace("i", "1");
				token = token.replace("j", "_|");
				token = token.replace("k", "I{");
				token = token.replace("l", "|_");
				token = token.replace("m", "(V)");
				token = token.replace("n", "|\\|");
				token = token.replace("o", "0");
				token = token.replace("p", "|°");
				token = token.replace("q", "O_");
				token = token.replace("r", "l2");
				token = token.replace("s", "$");
				token = token.replace("t", "7");
				token = token.replace("u", "|_|");
				token = token.replace("v", "\\/");
				token = token.replace("w", "VV");
				token = token.replace("x", "%");
				token = token.replace("y", "¥");
				token = token.replace("z", "2");

				output.print(token + " ");
			}
			output.println();
		}

	}

}
