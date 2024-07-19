import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    public static boolean appendable = false; //Переменная указывает на наличие аргумента -a
    public static String fileNamePrefix; //Переменная указывающая префикс имени файла
    public static String filePath; //Переменная указывающая на путь к файлу
    public static char statistic; // Переменная указывающая на аргумент -s или -f, краткая и полная статистика

    public static ArrayList<String> fileRead(String[] files){ //Метод чтения из входных файлов
        ArrayList<String> writtenText = new ArrayList<>(); //Лист, куда сохраняются все прочитанные строки
        for (int i=0;i<files.length && (files[i]!= null);i++) { //Цикл, итерирующий по каждому файлу
            try(Scanner scan = new Scanner(new FileReader(files[i]))) //Создание сканера, позволяющего считывать строки
            {
                while (scan.hasNextLine()) {
                    writtenText.add(scan.nextLine()); //Добавление в Лист считанной строки
                }
            }
            catch(Exception e){
                System.out.println("Cant read data from import files!");
            }
        }
        return writtenText;
    }

    public static void fullStatistic(String[][] arg){ //Метод подсчета и вывода полной статистики по выходным данным
        // 0 - int
        // 1 - double
        // 2 - string
        ArrayList<Integer> ints = new ArrayList<>(); //Создание двух Листов для хранения целочисленных данных и чисел с плавающей точкой
        ArrayList<Double> doubles = new ArrayList<>();
        for (int i=0;arg[0][i] != null;i++){ //Добавление в Лист целых чисел
            ints.add(Integer.parseInt(arg[0][i]));
        }
        for (int i=0;arg[1][i] != null;i++){ //Добавление в Лист дробных чисел
            doubles.add(Double.parseDouble(arg[1][i]));
        }
        int maxInt=Integer.MIN_VALUE;
        int minInt=Integer.MAX_VALUE;
        int sumInt=0;
        for (int number : ints){ //Цикл, необходимый для нахождения наибольшего числа, наименьшего, для подсчета суммы чисел
            if (number>maxInt){
                maxInt=number;
            }
            if (number<minInt){
                minInt=number;
            }
            sumInt+=number;
        }
        double maxDouble=Double.MIN_VALUE;
        double minDouble=Double.MAX_VALUE;
        double sumDouble=0;
        for (double number : doubles){ //Цикл, необходимый для нахождения наибольшего числа, наименьшего, для подсчета суммы чисел
            if (number>maxDouble){
                maxDouble=number;
            }
            if (number<minDouble){
                minDouble=number;
            }
            sumDouble+=number;
        }
        int shortString=arg[2][0].length();
        int longString=arg[2][0].length();
        for (int i = 0; arg[2][i] != null; i++) { //Цикл, необходимый для нахождения самой короткой и самой длинной строки
            if (shortString>arg[2][i].length()){
                shortString=arg[2][i].length();
            }
            if (longString<arg[2][i].length()){
                longString=arg[2][i].length();
            }
        }
        System.out.println("Full statistic for Integers:\nMaximum: " + maxInt + "\nMinimum: " + minInt +"\nSummary: " + sumInt + "\nAverage: " + String.format("%.3f", (double) (sumInt / ints.size())));
        System.out.println("Full statistic for Doubles:\nMaximum: " + maxDouble + "\nMinimum: " + minDouble +"\nSummary: " + sumDouble + "\nAverage: " + String.format("%.3f", sumDouble / doubles.size()));
        System.out.println("Full statistic for Strings:\nAmount: " + arg[2].length + "\nShortest: " + shortString + " symbols\nLongest: " + longString + " symbols");
    }

    public static String[][] lookingTypeOfNumber(ArrayList<String> textArray){ //Метод, разбивающий Лист на двумерный массив, где каждая строка - разный тип данных, а в ячейках хранятся данные
        String[][] textArrayModified=new String[3][textArray.size()];
        int i = 0;
        int k = 0;
        int m = 0;
        try {
            for (Iterator<String> it = textArray.iterator(); it.hasNext();) { //Цикл который проверяет Лист по соответствию регулярному выражению. Если соответствует, то добавляет элемент в нужную строку массива и удаляет из Листа
                String iterString = it.next();
                Pattern pattern = Pattern.compile("^[0-9]+$");
                Matcher matcher = pattern.matcher(iterString);
                while (matcher.find()) {
                        textArrayModified[0][i++]=iterString;
                        it.remove();
                    }
            }
            for (Iterator<String> it = textArray.iterator(); it.hasNext();) { //То же самое, но дробное число может быть записано в двух вариантах, поэтому два регулярных выражения и 2 цикла While
                String iterString = it.next();
                Pattern pattern = Pattern.compile("^[0-9]+[.,][0-9]+[Ee][+-][0-9]+$");
                Matcher matcher = pattern.matcher(iterString);
                while (matcher.find()) {
                    textArrayModified[1][k++]=iterString;
                    it.remove();
                }
                pattern = Pattern.compile("^[0-9]+[.,][0-9]+$");
                matcher = pattern.matcher(iterString);
                while (matcher.find()) {
                    matcher = pattern.matcher(iterString);
                    if (matcher.find()) {
                        textArrayModified[1][k++]=iterString;
                        it.remove();
                    }
                }
            }
            for (Iterator<String> it = textArray.iterator(); it.hasNext();) { //То же самое, только для String. Сюда записываются все данные, не подошедшие под прошлые критерии отбора
                String iterString = it.next();
                textArrayModified[2][m++]=iterString;
                it.remove();
            }
            if (statistic == 1){ //Вывод различных видов статистики
            System.out.println("There are: \n"+ i + " Integers\n" + k + " Doubles\n" + m + " Strings");
            }
            if (statistic == 2){
                fullStatistic(textArrayModified);
            }
            return textArrayModified;
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void fileWrite(String[][] textArray){ //Метод записи в выходные файлы
        try {
            for (int i=0;i<textArray.length;i++){
                try(FileWriter writer = new FileWriter((filePath != null ? filePath : "") + "\\" + fileNamePrefix+ (i == 0 ? "Integer" : i == 1 ? "Double" : "String") + ".txt", appendable)){ //Создание FileWriter, с атрибутами, согласно введенным при запуске аргументам
                    for (int j=0; j<textArray[i].length && (textArray[i][j]!=null);j++){
                        writer.write(textArray[i][j]);
                        writer.append('\n');
                    }
                    writer.flush();
                }
                catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Cant print data in text! NullPointerException");
        }
    }

    public static Path pathFinder(){ // Метод поиска пути. В случае, если пользователь введет неверный путь - программа выкинет исключение
        Path path;
        try {
            path = Paths.get(filePath);
            return path;
        }
        catch(InvalidPathException e){
            System.out.println("I cant find this path! InvalidPathException");
            return null;
        }
    }

    public static String[] argument(String[] args){ //Метод, считывающий аргументы, введенные в консоли
        String[] fileName=new String[args.length];
        int j=0;
        for (int i=0; i<args.length;i++){ //Перебор по всему массиву args[]
            if (!args[i].isEmpty()){
                switch (args[i]) { //Поиск соответствий с конкретными аргументами
                    case "-o"://Задание пути к файлу
                        filePath=args[i+1];
                        try {
                            filePath=pathFinder().toString();
                        }
                        catch (NullPointerException e) {
                            System.out.println("\n Invalid Path!");
                        }
                        args[i]=null;
                        break;
                    case "-p"://Задание префикса названия файла
                        fileNamePrefix=args[i+1];
                        args[i]=null;
                        System.out.println("Prefix is done");
                        break;
                    case "-a"://Выбор создания нового файла, либо добавления в старый. Appendable = true - добавление.
                              //Appendable = false - перезапись файла.
                        appendable = true;
                        args[i]=null;
                        break;
                    case "-s"://Краткая статистика
                        statistic=1;
                        args[i]=null;
                        break;
                    case "-f"://Полная статистика
                        statistic=2;
                        args[i]=null;
                        break;
                }
                if ((args[i] != null) && (args[i].contains(".txt"))){ //Сохранение входных файлов
                    fileName[j++]=args[i];
                }
            }
        }
        return fileName;
    }

    public static void main(String[] args) {
        try {
            fileWrite(lookingTypeOfNumber(fileRead(argument(args))));
        }
        catch (IllegalArgumentException e) {
            System.out.println("Program failed! IllegalArgumentException");
        }
    }
}