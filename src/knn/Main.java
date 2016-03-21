package knn;

public class Main {

	public static void main(String[] args) {
		
		for (int k = 1; k <= 5; k++) {
			Evaluator e = new Evaluator(k);
			e.runEvaluation(k);
		}
	}
}
