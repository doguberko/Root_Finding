
public class Test {

	static int countNR = 0;
	static int countVM = 0;
	static double dfr0;
	static int countB = 0;
	static int countRF = 0;
	static int countS = 0;

	static double func(double x, double a, double b, double c) {
		return a * x * x + b * x + c;
	}

	static double dfunc(double x, double a, double b) {
		return 2 * a * x + b;
	}

	// NEWTON-RAPHSON NEWTON-RAPHSON NEWTON-RAPHSON NEWTON-RAPHSON
	static void newtonRaphson(double x, double a, double b, double c, double i1, double i2, double tvalue) {

		if (countNR == 0) {
			System.out.println(">>>> NEWTON-RAPHSON ITERATION <<<<");
		}

		double temproot = x - (func(x, a, b, c) / dfunc(x, a, b));
		double abserror = Math.abs(temproot - x);

		while (abserror >= tvalue) {
			countNR++;
			if (countNR > 99) {
				System.out.println("  K = 100, there is not convergence.");
				break;
			}
			System.out.println(
					"  " + countNR + ". Rold:" + x + "   Rnew:" + temproot + "   |error|:" + Math.abs(temproot - x));
			newtonRaphson(temproot, a, b, c, i1, i2, tvalue);
			break;
		}

		if (abserror < tvalue) {
			System.out.println("  Root is " + Math.round(temproot * 100) / 100 + "\n");
		}
	}

	// VON-MISES VON-MISES VON-MISES VON-MISES
	@SuppressWarnings("unused")
	static void vonMises(double x, double a, double b, double c, double i1, double i2, double tvalue) {

		if (countVM == 0) {
			System.out.println(">>>> VON-MISES METHOD <<<<");
			dfr0 = dfunc(x, a, b);// Newton-Raphson'dan farkli olarak sadece r0 icin turev fonksiyonu kullanilir
		}

		double temproot = x - (func(x, a, b, c) / dfr0);// Her iterasyonda turevi yeniden hesaplamiyoruz
		double abserror = Math.abs(temproot - x);

		while (abserror >= tvalue) {
			countVM++;
			if (countVM > 99) {
				System.out.println("  K = 100, there is not convergence.");
				break;
			}
			System.out.println(
					"  " + countVM + ". Rold:" + x + "   Rnew:" + temproot + "   |error|:" + Math.abs(temproot - x));
			vonMises(temproot, a, b, c, i1, i2, tvalue);
			break;
		}

		if (abserror < tvalue) {
			System.out.println("  Root is " + Math.round(temproot * 100) / 100 + "\n");
		}
	}

	// BISECTION BISECTION BISECTION BISECTION
	static void bisection(double x, double a, double b, double c, double i1, double i2, double tvalue) {

		if (countB == 0) {
			System.out.println(">>>> BISECTION METHOD <<<<");
		}

		double iteration = (i1 + i2) / 2;

		while (Math.abs(x - iteration) > tvalue) {
			countB++;
			System.out.println("  " + countB + ". A " + func(i1, a, b, c) + "   B " + func(i2, a, b, c) + "   C "
					+ func(iteration, a, b, c) + "   error:" + (iteration - x));

			if (func(i1, a, b, c) * func(iteration, a, b, c) < 0) {
				bisection(x, a, b, c, i1, iteration, tvalue);
				break;
			} else if (func(iteration, a, b, c) * func(i2, a, b, c) < 0) {
				bisection(x, a, b, c, iteration, i2, tvalue);
				break;
			} else {
				System.out.println("  THE ROOT DOES NOT EXIST IN [" + i1 + "," + i2 + "]\n");
				break;
			}
		}

		if (Math.abs(x - iteration) <= tvalue) {
			// System.out.println(" Root is " + Math.round(iteration * 100) / 100 + "\n");
			System.out.println("  Root is " + iteration + "\n");
		}
	}

	// REGULA FALSI REGULA FALSI REGULA FALSI REGULA FALSI
	@SuppressWarnings("unused")
	static void regulaFalsi(double x, double a, double b, double c, double i1, double i2, double tvalue) {

		if (countRF == 0) {
			System.out.println(">>>> REGULA FALSI METHOD <<<<");
		}

		double ac, bc;
		double tempr = i1 * func(i2, a, b, c);
		tempr = tempr - (i2 * func(i1, a, b, c));
		tempr = tempr / (func(i2, a, b, c) - func(i1, a, b, c));

		System.out.println("  " + countRF + ". A " + func(i1, a, b, c) + "   B " + func(i2, a, b, c) + "   C "
				+ func(tempr, a, b, c));

		ac = func(i1, a, b, c) * func(tempr, a, b, c);
		bc = func(i2, a, b, c) * func(tempr, a, b, c);

		while (Math.abs(tempr - x) >= tvalue) {
			countRF++;

			if (ac < 0 && bc >= 0) {
				regulaFalsi(x, a, b, c, i1, tempr, tvalue);
				break;
			} else if (bc < 0) {
				regulaFalsi(x, a, b, c, tempr, i2, tvalue);
				break;
			} else if (countRF > 99) {
				System.out.println("  K = 100, THE ROOT DOES NOT EXIST IN [" + i1 + "," + i2 + "]\n");
				break;
			}

		}

		if (!(Math.abs(x - tempr) >= tvalue)) {
			// System.out.println(" Root is " + Math.round(tempr * 100) / 100 + "\n");
			System.out.println("  Root is " + tempr);

		}
	}

	// SECANT SECANT SECANT SECANT
	static void secant(double x, double a, double b, double c, double i1, double i2, double tvalue) {

		double n0 = i1, n1 = i2, n2;

		if (countS == 0) {

			System.out.println("\n>>>> SECANT METHOD <<<<");
			n0 = x;
		}

		n2 = func(n1, a, b, c) * (n1 - n0);
		n2 = n2 / (func(n1, a, b, c) - func(n0, a, b, c));
		n2 = n1 - n2;

		while (Math.abs(n2 - x) > tvalue) {

			countS++;

			if (Math.abs(n2 - x) > tvalue && countS++ < 100) {
				secant(x, a, b, c, n1, n2, tvalue);
				break;
			} else if (Math.abs(n2 - x) > tvalue && countS > 99) {
				System.out.println("  K = 100, there is not convergence.");
				break;
			}
		}

		if (Math.abs(n2 - x) <= tvalue) {
			System.out.println("  Root is " + n2);
		}
	}

	static void findRoot(double x, double a, double b, double c, double i1, double i2, double tvalue) {

		newtonRaphson(x, a, b, c, i1, i2, tvalue);
		vonMises(x, a, b, c, i1, i2, tvalue);
		bisection(x, a, b, c, i1, i2, tvalue);
		regulaFalsi(x, a, b, c, i1, i2, tvalue);
		secant(x, a, b, c, i1, i2, tvalue);
	}

	public static void main(String[] args) {

		/*newtonRaphson(-4, 1, -1, -2, -5, 5, 0.01);
		vonMises(-4, 1, -1, -2, -5, 5, 0.01);
		bisection(1.7320508075688772, 1, 0, -3, 0, 4, 0.000001);
		regulaFalsi(1.7320508075688772, 1, 0, -3, 0, 4, 0.000001);
		secant();*/
		
		findRoot(10,1,-4,-60,5,12,0.01);

	}

}
