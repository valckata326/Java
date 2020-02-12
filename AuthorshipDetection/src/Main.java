package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("resources/TestAuthors");
        try(InputStream stream = new FileInputStream(file)){
            Stream<String> lines = new BufferedReader(new InputStreamReader(stream)).lines();

            List<String> answer = new ArrayList<>();
            answer = lines.map(LinguisticSignature::cleanUp)
                    .flatMap(line->Stream.of(line.split("\\s+")))

                    .filter(x->!x.isEmpty())
                    .collect(Collectors.toList());
            System.out.println(answer);
            System.out.println(answer.size());

            /*answer = lines.map(LinguisticSignature::cleanUp)
                    .flatMap(line->Stream.of(line.replaceAll(System.lineSeparator(),"\\n")))
                    .flatMap(line->Stream.of(line.split("[;:,]")))
                    .collect(Collectors.toList());
            System.out.println(answer);
            */

            double[] weights  = {11,33,50,0.4,4};
            InputStream authorsInput = new FileInputStream("./resources/authors");
            AuthorshipDetectorImpl testing = new AuthorshipDetectorImpl(authorsInput, weights);
            LinguisticSignature test1 = testing.calculateSignature(stream);
            for(FeatureType ft: test1.getFeatures().keySet()){
                System.out.print(ft+ " ");
                System.out.print(test1.getFeatures().get(ft));
                System.out.println();
            }
            InputStream mysteryText = new FileInputStream("./resources/EMMA");
            String author = testing.findAuthor(mysteryText);
            System.out.println(author);
        }

        catch(FileNotFoundException exception){
            System.out.println("File not found!");
        }
    }
}
