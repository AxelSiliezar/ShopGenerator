import java.util.Scanner;

public class Shop {
    static boolean setup = false;
    static boolean buy = false;
    static int items = 0;
    static String[] names;
    static Double[] prices;
    static int[] amount;
    static Double mainDiscount = 0.0;
    static Double mainRate;
    static Double[] packages;

    public static void setup() {
        Scanner input = new Scanner(System.in);
        int max;
        int quantity;

        System.out.print("Please enter the number of items to setup the shop: ");
        max = input.nextInt();

        System.out.println();
        items = max;

        names = new String[max];
        prices = new Double[max];
        amount = new int[max];

        for(int i = 0; i < max; i++) {
            switch (i) {
                case 0 -> System.out.print("Enter the name of the 1st product: ");
                case 1 -> System.out.print("Enter the name of the 2nd product: ");
                case 2 -> System.out.print("Enter the name of the 3rd product: ");
                case 3 -> System.out.print("Enter the name of the " + (i + 1) + "th product: ");
                default -> System.out.print("Enter the name of the " + i + "th product: ");
            }
            names[i] = input.next();

            System.out.print("Enter the per package price of " + names[i] + ": ");
            prices[i] = input.nextDouble();

            System.out.print("Enter the number of packages ('x') to qualify for Special Discount "
                    + "(buy 'x' and get 1 free) \nfor " + names[i] + " or 0 if no Special Discount offered: ");
            quantity = input.nextInt();
            if (quantity > 0) {
                amount[i] = quantity;
            }
        }
        System.out.print("\nEnter the dollar amount to qualify for "
                + "additional discount (or 0 if none offered): ");
        double dollars = input.nextDouble();
        if(dollars > 0) {
            System.out.print("Enter the Additional Discount rate (e.g., 0.1 for 10%): ");
            double rate = input.nextDouble();
            if (rate <= 0 || rate > 0.5) {
                do {
                    System.out.print("Invalid input. Enter a value > 0 and <= 0.5: ");
                    rate = input.nextDouble();
                } while (rate <= 0 || rate > 0.5);
            }
            mainRate = rate;
            mainDiscount = dollars;
        }
        setup = true;
        System.out.println();
    }

    public static void purchase() {
        Scanner input = new Scanner(System.in);
        System.out.print("\n");
        if (!setup) {
            System.out.print("Shop is not setup yet!");
        }
        else {
            packages = new Double[items];
            for(int i = 0; i < items; i++) {
                System.out.print("Enter the number of " + names [i] + " packages to buy: ");
                packages[i] = input.nextDouble();
                while (packages[i] < 0) {
                    System.out.print("Invalid input. Enter a value >= 0:");
                    packages[i] = input.nextDouble();
                }
            }
            buy = true;
        }

        System.out.println();
    }

    public static void list() {
        System.out.println();
        int valueTemporary = 0;
        for (Double aPackage : packages) {
            valueTemporary += aPackage;
        }
        if (valueTemporary > 0 ) {
            for (int i = 0; i < items; i++) {
                if (packages[i] > 0) {
                    System.out.println(packages[i] + " package(s) of "
                            + names[i] + " @ $" + prices[i] + " per pkg = $ "
                            + (Math.round((packages[i]*prices[i])*100.0)/100.0));
                }
            }
        }
        else {
            System.out.println("No items were purchased");
        }
        System.out.println();
    }

    public static void checkout() {
        System.out.println();

        double originalTotal = 0;
        for (int i = 0; i < items; i++) {
            originalTotal += (packages[i]*prices[i]);
        }
        System.out.println("Original Sub Total:\t\t\t $"
                + (Math.round(originalTotal*100.0)/100.0));
        double specialDiscount = 0;
        for (int i = 0; i< items; i++) {
            if(amount[i] > 0) {
                double temp = (int) (packages[i] / (amount[i] + 1));
                specialDiscount += (temp * prices[i]);
            }
        }
        if (specialDiscount > 0) {
            System.out.println("Special Discount:\t\t\t -$" + (Math.round(specialDiscount*100.0)/100.0));
        }
        else {
            System.out.println("No Special Discount applied");
        }

        double newSubtotal = originalTotal - specialDiscount;
        System.out.println("New Sub Total: \t\t\t\t $"
                + (Math.round(newSubtotal*100.0)/100.0));

        double additional = 0;
        if (mainDiscount > 0 && newSubtotal > mainDiscount) {
            additional = newSubtotal* mainRate;
            System.out.println("Additional " + ((int)(mainRate *100)) + "% Discount:\t-$"+ (Math.round(additional*100.0)/100.0));
        }
        else {
            System.out.println("You did not qualify for an Additional Discount");
        }

        double finalTotal = newSubtotal - additional;
        System.out.println("Final Sub Total:\t\t\t $" + (Math.round(finalTotal*100.0)/100.0) + "\n");
    }


    // MAIN Starts Here
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num = 0;
        lastNum(input, num);
    }

    private static void lastNum(Scanner input, int num) {
        while(num != 4) {
            System.out.println("This program supports 4 functions: ");
            System.out.println("\t1. Setup Shop \n\t2. Buy \n\t3. List Items \n\t4. Checkout");
            System.out.print("Please choose which function you want: ");
            num = input.nextInt();

            switch (num) {
                case 1:
                    setup();
                    break;
                case 2:
                    if (setup) {
                        purchase();
                    } else {
                        System.out.println("\nShop is not set up yet!\n");
                    }
                    break;
                case 3:
                    if (setup && buy) {
                        list();
                    } else if (setup && !buy) {
                        System.out.println("\nYou have not bought anything!\n");
                    } else {
                        System.out.println("\nShop is not set up yet!\n");
                    }
                    break;
                case 4:
                    if (setup && buy) {
                        checkout();
                        System.out.println("Thanks for coming\n");
                        int checker;
                        System.out.println("----------------------------------------------------");
                        System.out.print("Would you like to re-run (1 for yes, 0 for no)? : ");
                        checker = input.nextInt();
                        System.out.println("----------------------------------------------------\n");
                        if (checker == 1) {
                            num = 0;
                            setup = false;
                            buy = false;
                        }
                    } else if (!setup) {
                        System.out.println("\nShop is not set up yet!\n");
                        num = 0;
                    } else {
                        System.out.println("\nYou have not bought anything!\n");
                        num = 0;
                    }
                    break;
            }
        }
    }
}
