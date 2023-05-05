import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
public class Main {
    public static List<Double> readFileOfDoubles(String filePath) throws IOException, FileFormatException{
        String regexRU = "^\\d*\\,\\d*";
        String regexDE ="^\\d{1,3}(\\.\\d{3})*\\,\\d*";
        String regexUS = "^\\d*\\.\\d*";
        String regexIN = "^\\d{1,3}(\\,\\d{3})*\\.\\d*";
        List<Double> inputNumbers = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null){
                try {
                    String[] subStr = line.split(" ");
                    String inputNum = subStr[0];
                    String inputLocale = subStr[1];
                    double num = 0;
                    if (Pattern.matches(regexRU,inputNum) && inputLocale.equals("RU")){
                        NumberFormat nf = NumberFormat.getInstance(new Locale("ru", "RU"));
                        num = nf.parse(inputNum).doubleValue();
                        inputNumbers.add(num);
                    }
                    else if (Pattern.matches(regexDE,inputNum) && inputLocale.equals("DE")){
                        NumberFormat nf = NumberFormat.getInstance(new Locale("de", "DE"));
                        num = nf.parse(inputNum).doubleValue();
                        inputNumbers.add(num);
                    }
                    else if (Pattern.matches(regexUS,inputNum) && inputLocale.equals("US")){
                        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
                        num = nf.parse(inputNum).doubleValue();
                        inputNumbers.add(num);
                    }
                    else if (Pattern.matches(regexIN,inputNum) && inputLocale.equals("IN")){
                        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "IN"));
                        num = nf.parse(inputNum).doubleValue();
                        inputNumbers.add(num);
                    }
                    else
                    {
                        throw new FileFormatException("Неподходящий формат записи числа в файле - строка содержала: " + line);
                    }

                } catch (ParseException e) {
                    throw new FileFormatException("PE " + e.getMessage());
                }
                catch (FileFormatException e){
                    throw new FileFormatException("FFE " + e.getMessage());
                }
            }
        }
        return inputNumbers;
    }

    public static double calculateSum(List<Double> numbers) throws FileFormatException {
        double sum = 0;
        if (!numbers.isEmpty()) {
            for (double number : numbers) {
                sum += number;
            }
        } else {
            throw new FileFormatException("Файл не содержит чисел");
        }
        return sum;
    }

    public static double calculateAverage(List<Double> numbers) throws FileFormatException{
        try {
            return calculateSum(numbers) / numbers.size();
        } catch (FileFormatException e) {
            throw new FileFormatException(e + " поэтому не удалось посчитать среднее значение");
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\StudFiles\\Учеба\\ПромышленноеПрограммирование\\Лабы\\8\\PromProgLab_8\\numbers.txt";
        try {
            List<Double> numbers = readFileOfDoubles(filePath);
            System.out.println("Считанные числа: " + numbers);
            System.out.println("Сумма чисел: " + calculateSum(numbers));
            System.out.println("Среднее арифметическое: " + calculateAverage(numbers));
        }
        catch (FileFormatException e ){
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("IOError: " + e.getMessage());
        }
    }
}
