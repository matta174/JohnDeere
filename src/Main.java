import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

//    Coding Exercise (2-3 hrs max)
//1) Download a file from: http://www.gutenberg.org/files/2701/2701-0.txt
//2) Test drive cracking the file open, parse it and get a list of the top frequently occurring 50 words
//3) Exclude a set of common words (case insensitive) (the,of,to,and,a,in,is,it,you,that,he,was,for,on,are,with,as,I,his,they,be,at,one,have,this,from,or,had,by,not,word,but,what,some,we,can,out,other,were,all,there,when,up,use,your,how,said,an,each,she)
//    Use Collections API in SCALA or JAVA or any other language of your choice.
//4) Deploy and run this on one of cloud platforms - AWS/Azure/GCP
//5) Check the code into GitHub and Share the link
public class Main {
    public static void main(String[] args) throws IOException {
        getFileFromUrl("https://www.gutenberg.org/files/2701/2701-0.txt");

        String[] words = filterString();
        Map<String, Integer>wordCountMap = getWordCountMap(words);

        var topWords = getTopWords(wordCountMap,50);
        for (Map.Entry<String, Integer> topWord : topWords) {
             System.out.println(topWord);
        }
    }

    public static String[]  filterString() throws IOException {
        String MobyDick = readFile("MobyDick.txt", StandardCharsets.US_ASCII);
        return MobyDick.split("\\s+");
    }

    public static void getFileFromUrl(String givenUrl) throws IOException {
        System.out.println("opening connection");
        URL url = new URL(givenUrl);
        InputStream in = url.openStream();
        FileOutputStream fos = new FileOutputStream(new File("MobyDick.txt"));

        System.out.println("reading from resource and writing to file...");
        int length = -1;
        byte[] buffer = new byte[1024];// buffer for portion of data from connection
        while ((length = in.read(buffer)) > -1) {
            fos.write(buffer, 0, length);
        }
        fos.close();
        in.close();
        System.out.println("File downloaded");
    }

    public static Map<String, Integer> getWordCountMap(String[] words)
    {
        List<String> uniqueWords = new ArrayList<>(Arrays.asList(words));
        Map<String, Integer> wordCountMap = new HashMap<>();
        ArrayList<String> forbiddenWords = new ArrayList<>(Arrays.asList("the", "of", "to", "and", "a", "in", "is", "it", "you", "that", "he", "was", "for", "on", "are", "with", "as", "i", "his", "they", "be", "at", "one", "have", "this", "from", "or", "had", "by", "not", "word", "but", "what", "some", "we", "can", "out", "other", "were", "all", "there", "when", "up", "use", "your", "how", "said", "an", "each", "she"));

        for(int i = 0; i < uniqueWords.size(); i++)
        {
            String currentWord = uniqueWords.get(i).toLowerCase(Locale.ROOT);
            if(!forbiddenWords.contains(currentWord))
            {
                wordCountMap.merge(currentWord, 1, Integer::sum);
            }
        }

        return wordCountMap;
    }

    public static List<Map.Entry<String, Integer>> getTopWords(Map<String,Integer> map, Integer number)
    {
        Stream<Map.Entry<String,Integer>> sorted = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(number);
        var topList = sorted.toList();

        return topList;
    }

    public static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
