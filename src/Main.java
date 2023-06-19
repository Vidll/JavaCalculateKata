import java.util.*;

public class Main {
    public static String[] MathActions = {"+","-","*","/"};
    public static String[] MistakesValues = {".",","};
    public static String[] RomanValues = {"I", "II", "III", "IV", "V", "VI", "VII", "IIX", "IX", "X"};
    public static boolean UseRomanValues = false;

    public static void main(String[] args) {
        System.out.println("Kata calculate");
        System.out.println("(Values:0,1,2,3,4,5,6,7,8,9,10,I,II,III,IV,V,VI,VII,IIX,IX,X)");
        System.out.println("(Actions + - * /)");
        System.out.print("Input: ");
        InputValuesOnCalculate();
    }

    public static void InputValuesOnCalculate() {
        List<String> Values = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        StringTokenizer token = new StringTokenizer(scanner.nextLine());

        while (token.hasMoreElements()) {
            Values.add(token.nextToken());
        }
        scanner.close();

        if(Values.size() > 1){
            StringBuilder SB = new StringBuilder();
            for(String str: Values){
                SB.append(str);
            }
            String inputValue = SB.toString();
            CheckStringValue(inputValue);
        }
        else{
            CheckStringValue(Values.get(0));
        }
    }

    public static void CheckStringValue(String _values){
        StringBuilder valueOne = new StringBuilder();
        StringBuilder valueTwo = new StringBuilder();
        String action = "";
        boolean actionSet = false;

        for (int valueIndex = 0; valueIndex < _values.length(); valueIndex++){
            for(int mathIndex = 0; mathIndex < MathActions.length; mathIndex++){
                if(_values.charAt(valueIndex) == MistakesValues[0].charAt(0) ||
                _values.charAt(valueIndex) == MistakesValues[1].charAt(0)){
                    SetMistakes(Mistakes.NoInt);
                }
                if(_values.charAt(valueIndex) == MathActions[mathIndex].charAt(0)){
                    if(actionSet){
                        SetMistakes(Mistakes.OnlyOneMathAction);
                    }
                    action = MathActions[mathIndex];
                    actionSet = true;
                    valueIndex++;
                }
            }
            if(!actionSet){
                valueOne.append(_values.charAt(valueIndex));
            }
            else{
                valueTwo.append(_values.charAt(valueIndex));
            }
        }

        if (!actionSet){
            SetMistakes(Mistakes.ActionNotFind);
        }

        actionSet = false;
        String[] Values = CheckRomanValue(valueOne.toString(), valueTwo.toString());
        Calculate(Values,action);
    }

    public static void Calculate(String[] _values, String _action){
        int one = Integer.parseInt(_values[0]);
        int two = Integer.parseInt(_values[1]);
        int answer;

        if(one > 10 || two > 10){
            SetMistakes(Mistakes.NumberMore10);
        }
        if(one < 0 || two < 0){
            SetMistakes(Mistakes.NumberLess0);
        }

        switch (_action){
            case ("+"):
                answer = one + two;
                break;
            case("-"):
                answer = one - two;
                break;
            case("*"):
                answer = one * two;
                break;
            case("/"):
                if(two == 0){
                    SetMistakes(Mistakes.CantDivideByZero);
                }
                answer = one / two;
                break;
            default:
                answer = 2147483647;
                break;
        }

        String answerString = Integer.toString(answer);

        if(UseRomanValues) {
            if(answer < 0){
                SetMistakes(Mistakes.NotNegativesInRomanValues);
            }
            answerString = ConvertToRomanValues(answer);
        }

        UseRomanValues = false;
        System.out.println("Output: " + answerString);
    }

    public static String ConvertToRomanValues(int number) {
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder romanNumber = new StringBuilder();

        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                romanNumber.append(romanSymbols[i]);
                number -= arabicValues[i];
            }
        }
        return romanNumber.toString();
    }

    public static String[] CheckRomanValue(String _valueOne,String _valueTwo){
        String[] outValues = {_valueOne, _valueTwo};
        boolean one = false;
        boolean two = false;

        for (int index = 0; index < RomanValues.length; index++){
            if(!one && _valueOne.equals(RomanValues[index])){
                outValues[0] = Integer.toString(index + 1);
                one = true;
            }
            if(!two && _valueTwo.equals(RomanValues[index])){
                outValues[1] = Integer.toString(index + 1);
                two = true;
            }
        }

        if(one && two){
            UseRomanValues = true;
        }
        else if(one || two){
            SetMistakes(Mistakes.DifferentTypeValue);
        }
        else{
            UseRomanValues = false;
        }
        return outValues;
    }

    public static void SetMistakes(Mistakes _mistake){
        switch (_mistake){
            case NumberMore10:
                System.out.println("Ошибка: значение больше 10"); // use
                break;
            case NumberLess0:
                System.out.println("Ошибка: значение меньше 0"); // use
                break;
            case OnlyOneMathAction:
                System.out.println("Ошибка: разрешено только одно математическое действие"); //use
                break;
            case NoInt:
                System.out.println("Ошибка: значение не целое"); //use
                break;
            case ActionNotFind:
                System.out.println("Ошибка: математическое действие не найдено"); //use
                break;
            case DifferentTypeValue:
                System.out.println("Ошибка: разные типы значений"); //use
                break;
            case NotNegativesInRomanValues:
                System.out.println("Ошибка: римские значения не могут быть меньше 0"); //use
                break;
            case CantDivideByZero:
                System.out.println("Ошибка: нельзя дельть на ноль");
                break;
        }
        System.exit(0);
    }

    public enum Mistakes{
        NumberMore10, //>10
        NumberLess0, //<0
        NoInt, //0.5f or q
        ActionNotFind, // %
        DifferentTypeValue, // 1 + I
        NotNegativesInRomanValues, // I - II
        OnlyOneMathAction, // 1 + 1 + 1
        CantDivideByZero,
    }
}