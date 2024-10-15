package workshop6;

public class ExceptionApp {
    public static void main(String[] args) {

        try {
            int x = 0;
            while (true) {
                x++;
                if (x > 5) {
                    try {
                        demoException();
                    } catch (PokemonException e) {
                        System.err.println("I AM LEAVING: ");
                        System.exit(1);
                    }
                }
            }
        } catch (ArithmeticException e) {
            System.out.println("Exception: " + e.getMessage());
            // } catch (PokemonException e) {
            // System.out.println("PokemonException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // public static void main(String[] args) {
    // try {

    // demoException();
    // } catch (ArithmeticException e) {
    // System.out.println("Exception: " + e.getMessage());
    // } catch (PokemonException e) {
    // System.out.println("PokemonException: " + e.getMessage());
    // } catch (Exception e) {
    // System.out.println("Exception: " + e.getMessage());
    // }
    // }

    public static void demoException() throws PokemonException {
        int a = 10;
        int b = 0;
        try {
            int c = a / b;
        } catch (ArithmeticException e) {
            System.out.println("demoException - >ArithmeticException: " + e.getMessage());
            throw new PokemonException("Raichu !");
        }
    }
}
