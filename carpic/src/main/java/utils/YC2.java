package utils;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YC2 {
    static String url = "http://newcar.xcar.com.cn/photo/";

    public static void main(String[] args) throws Exception {
        (new YC2()).getUrl();
    }

    private void getUrl() throws InterruptedException, Exception {
        Document document = Jsoup.connect(url).get();
        Elements elementsByTag = document.getElementsByTag("li");
        for (Element element : elementsByTag) {
            String attr = element.attr("menu");
            if (!attr.isEmpty()) {
                System.out.println(""+ attr);
                        String text = element.text();
                System.out.println(""+ text);
                        Elements elementsByTag2 = element.getElementsByTag("a");
                String attr2 = elementsByTag2.attr("href");
                System.out.println(""+ attr2);
                        String url2 = "http://newcar.xcar.com.cn" + attr2;
                try {
                    geturl3(url2, text);
                } catch (Exception e) {
                    System.out.println("");
                            e.printStackTrace();
                }
            }
        }
    }

    private void geturl3(String url2, String text) throws IOException {
        Document document2 = Jsoup.connect(url2).get();
        Elements elementsByTag3 = document2.getElementsByTag("a");
        for (Element element2 : elementsByTag3) {
            Elements elements = element2.getElementsByTag("img");
            if (!elements.isEmpty()) {
                String attr3 = elements.attr("alt");
                if (!attr3.isEmpty()) {
                    System.out.println(""+ attr3);
                            String attr4 = element2.attr("href");
                    System.out.println(""+ attr4);
                            String url3 = "http://newcar.xcar.com.cn" + attr4;
                    Document document3 = Jsoup.connect(url3).get();
                    Elements elementsByTag4 = document3.getElementsByTag("a");
                    for (Element element3 : elementsByTag4) {
                        String text2 = element3.text();
                        if (text2.equals(">>")) {
                            System.out.println(""+ element3);
                                    String attr5 = element3.attr("href");
                            System.out.println(""+ attr5);
                                    String url4 = "http://newcar.xcar.com.cn" + attr5;
                            try {
                                geturl2(url4, text);
                            } catch (Exception e) {
                                System.out.println("");
                                        e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private void geturl2(String url4, String text) throws IOException {
        Document document4 = Jsoup.connect(url4).get();
        Elements elementsByTag5 = document4.getElementsByTag("a");
        for (Element element4 : elementsByTag5) {
            Elements elementsByTag6 = element4.getElementsByTag("img");
            if (!elementsByTag6.isEmpty()) {
                String attr6 = element4.attr("title");
                if (!attr6.isEmpty()) {
                    System.out.println(""+ element4);
                            String attr7 = element4.attr("href");
                    System.out.println(""+ attr7);
                            String url5 = "http://newcar.xcar.com.cn" + attr7;
                    try {
                        getUrl4(url5, text);
                    } catch (Exception e) {
                        System.out.println("");
                                e.printStackTrace();
                    }
                }
            }
        }
    }

    private void getUrl4(String url5, String text) throws Exception {
        Document document5 = Jsoup.connect(url5).get();
        Elements elementsByTag7 = document5.getElementsByTag("img");
        for (Element element5 : elementsByTag7) {
            String attr8 = element5.attr("alt");
            if (!attr8.isEmpty()) {
                String attr9 = element5.attr("src");
                System.out.println(""+ attr9);
                        String url6 = attr9;
                String name = String.valueOf(text) + " " + attr8;
                sdownloadPicture(url6, name);
            }
        }
    }

    private void sdownloadPicture(String urlList, String name) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            name = name.replace(" ", "\\");
            File dir = new File("D:/AKQ/" + name);
            if (dir.exists())
                System.out.println("+ name + ");
            if (!name.endsWith(File.separator))
                name = String.valueOf(name) + File.separator;
            if (dir.mkdirs())
                System.out.println("+ name + ");
            long s = genItemId();
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:/AKQ/" + name + s + ".jpg"));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0)
                fileOutputStream.write(buffer, 0, length);
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            System.out.println("");
                    e.printStackTrace();
        } catch (IOException e) {
            System.out.println("");
                    e.printStackTrace();
        }
    }

    public static long genItemId() {
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end2 = random.nextInt(99);
        String str = String.valueOf(millis) + String.format("%02d", new Object[] { Integer.valueOf(end2) });
        long id = (new Long(str)).longValue();
        return id;
    }
}
