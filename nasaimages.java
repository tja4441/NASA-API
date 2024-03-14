import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class nasaimages {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException {
        String fileLocation = "C:\\Users\\Tammy\\Pictures\\Space\\";
        String date = "";

        String base = "https://api.nasa.gov/planetary/apod?api_key=kO6aPqrgkUVuvFYwiv4mlIHaHvWWXRRjvEg1dBam";
        String[] info = {"media_type","hdurl","title","explanation"};
        search(date(base, date), info);
        System.out.println(info[1]);
        InputStream in = new URL(info[1]).openStream();
        Files.copy(in, Paths.get(fileLocation+info[2]+".jpg"), StandardCopyOption.REPLACE_EXISTING);
    }

    public static String date(String existingURI, String date) {
        return existingURI + "&date=" + date;
    }

    public static void search(String uri, String[] info) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            PrintWriter out = new PrintWriter("data.json");
            out.println(response.body());
            out.close();
            String[] fields = response.body().split("\",\"");
            for(int i = 0; i < fields.length; i++){
                String[] subFields = fields[i].split("\":\"");
                for(int j = 0; j < info.length; j++){
                    if(info[j].equals(subFields[0])){
                        info[j] = subFields[1];
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
