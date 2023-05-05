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
        List<Double> inputNumbers = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null){
                try {
                    String[] subStr = line.split(" ");
                    if (subStr.length < 2){
                        throw new FileFormatException("Неподходящий формат записи строки в файле - строка содержала: " + line);
                    }
                    String inputNum = subStr[0];
                    String inputLocale = subStr[1];
                    String[] inputFormat = inputLocale.split("_");
                    if (inputFormat.length < 2){
                        throw new FileFormatException("Неподходящий формат записи локали в файле - строка содержала:\n" + line + "\nНеверная локаль: " + inputLocale);
                    }
                    String inputLang = inputFormat[0];
                    String inputCountry = inputFormat[1];
                    double num = 0;
                    NumberFormat nf = NumberFormat.getInstance(new Locale(inputLang, inputCountry));
                    num = nf.parse(inputNum).doubleValue();
                    inputNumbers.add(num);
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
        String filePath = "C:\\Users\\Pozer\\IdeaProjects\\PromProgLab_8\\numbers.txt";
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
