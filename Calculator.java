import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Calculator{
    public static void main (String args[]){
        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String data = in.nextLine();
        //System.out.printf("Введенное выражение: %s \n", data);
        in.close();

        if (isMatches(data)) {
            calc(data);
        } else {
            throw new IllegalArgumentException("Введенное выражение не соответствует заданным ограничениям.");
        }
    }

    public static boolean isMatches(String str) {
        return str.matches("(([1-9]|10)\\s([-+/*])\\s([1-9]|10))|((I|II|III|IV|V|VI|VII|VIII|IX|X)\\s([-+/*])\\s(I|II|III|IV|V|VI|VII|VIII|IX|X))");
    }

    public static boolean isArabicNumeral(String str) {
        return str.matches("[1-9]|10");
    }

    public static void calc(String str) {
    RomanArabicConverter conv = new RomanArabicConverter();
        String[] values = str.split(" ");
        String operation = values[1];
        int result;
        if (isArabicNumeral(values[0])) {
            int value1 = Integer.parseInt(values[0]);
            int value2 = Integer.parseInt(values[2]);
            switch (operation) {
              case "+":
                result = value1 + value2;
                break;
              case "-":
                result = value1 - value2;
                break;
              case "*":
                result = value1 * value2;
                break;
              case "/":
                result = (int)(value1 / value2);
                break;
              default:	
                throw new IllegalArgumentException("Ошибка.");
            }
            System.out.println(result);
        } else {
            int value1 = conv.romanToArabic(values[0]);
            int value2 = conv.romanToArabic(values[2]);
            switch (operation) {
              case "+":
                result = value1 + value2;
                break;
              case "-":
                result = value1 - value2;
                break;
              case "*":
                result = value1 * value2;
                break;
              case "/":
                result = (int)(value1 / value2);
                break;
              default:
                throw new IllegalArgumentException("Ошибка.");
            }
            if (result > 0) {
              System.out.println(conv.arabicToRoman(result));
            } else {
              throw new IllegalArgumentException("Результатом работы калькулятора с римскими числами могут быть только положительные числа");
            };
        };
    }
}

class RomanArabicConverter {

    public static int romanToArabic(String input) {
        String romanNumeral = input;
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }
        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " конвертация невозможна.");
        }

        return result;
    }

    public static String arabicToRoman(int number) {

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while (number > 0 && i < romanNumerals.size()) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
}

enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<RomanNumeral> getReverseSortedValues() {
        return Arrays.stream(values())
          .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
          .collect(Collectors.toList());
    }
}
