package utils;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class YC {
    static String url = "http://api.car.bitauto.com/CarInfo/getlefttreejson.ashx?tagtype=tupian&pagetype=brand&objid=10000";

    public static void main(String[] args) throws Exception {
        getUrl();
    }

    private static String getUrl() throws InterruptedException, Exception {
        List<String> url2 = getUrl2();
        for (int i = 49; i < url2.size(); i++) {
            String url3 = "http://photo.bitauto.com" + ((String)url2.get(i)).replace("\"", "");
            System.out.println(""+ url3);
            try {
                Document document = Jsoup.connect(url3).get();
                Elements elementsByTag7 = document.getElementsByTag("li");
                for (Element element : elementsByTag7) {
                    String attr = element.attr("class");
                    if (attr.equals("name")) {
                        Integer cont = Integer.valueOf(0);
                        String text = element.text();
                        System.out.println(""+ text);
                                Elements elementsByTag = element.getElementsByTag("a");
                        for (Element element2 : elementsByTag) {
                            String attr2 = element2.attr("href");
                            System.out.println(""+ attr2);
                                    String url4 = "http://photo.bitauto.com" + attr2;
                            System.out.println("url4:::" + url4);
                            try {
                                Document document2 = Jsoup.connect(url4).get();
                                Elements elementsByTag3 = document2.getElementsByTag("li");
                                for (Element element3 : elementsByTag3) {
                                    Elements elementsByTag2 = element3.getElementsByTag("a");
                                    for (Element element4 : elementsByTag2) {
                                        String attr3 = element4.attr("href");
                                        if (attr3.contains(attr2)) {
                                            String text2 = element4.text();
                                            if (!text2.isEmpty()) {
                                                System.out.println(""+ text2);
                                                        System.out.println("attr3::::" + attr3);
                                                String url9 = "http://photo.bitauto.com" + attr3;
                                                try {
                                                    Document document3 = Jsoup.connect(url9).get();
                                                    Elements elementsByTag4 = document3.getElementsByTag("a");
                                                    for (Element element5 : elementsByTag4) {
                                                        String text3 = element5.text();
                                                        if (text3.equals("")) {
                                                                String attr4 = element5.attr("href");
                                                        String url66 = "http://photo.bitauto.com" + attr4;
                                                        System.out.println("url66:::::" + url66);
                                                        try {
                                                            Document document4 = Jsoup.connect(url66).get();
                                                            Elements elementsByTag5 = document4.getElementsByTag("a");
                                                            for (Element element6 : elementsByTag5) {
                                                                System.out.println(element6);
                                                                String attr5 = element6.attr("href");
                                                                if (attr5.contains("/picture/")) {
                                                                    System.out.println(attr5);
                                                                    Elements elementsByTag6 = element4.getElementsByTag("img");
                                                                    String text5 = elementsByTag6.attr("alt");
                                                                    System.out.println(""+ text5);
                                                                            String url6 = "http://photo.bitauto.com" + attr5;
                                                                    System.out.println(url6);
                                                                    try {
                                                                        Document document5 = Jsoup.connect(url6).get();
                                                                        Elements elementsByTag8 = document5.getElementsByTag("img");
                                                                        System.out.println(elementsByTag8);
                                                                        for (Element element7 : elementsByTag8) {
                                                                            String attr6 = element7.attr("id");
                                                                            if (!attr6.isEmpty()) {
                                                                                String url7 = element7.attr("src");
                                                                                String name = String.valueOf(text) + " " + text2 + " " + text5;
                                                                                sdownloadPicture(url7, name, cont);
                                                                                cont = Integer.valueOf(cont.intValue() + 1);
                                                                            }
                                                                        }
                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                        } catch (Exception e1) {
                                                            e1.printStackTrace();
                                                        }
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }
    return url;
}

    public static List<String> getUrl2() {
        List<String> list = new LinkedList<>();
        try {
            URL url2 = new URL(url);
            URLConnection conn = url2.openConnection();
            InputStream is = conn.getInputStream();
            Scanner sc = new Scanner(is, "UTF-8");
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] split = s.split(",");
                byte b;
                int i;
                String[] arrayOfString1;
                for (i = (arrayOfString1 = split).length, b = 0; b < i; ) {
                    String string = arrayOfString1[b];
                    if (string.contains("url")) {
                        String[] split2 = string.split(":");
                        byte b1;
                        int j;
                        String[] arrayOfString2;
                        for (j = (arrayOfString2 = split2).length, b1 = 0; b1 < j; ) {
                            String string2 = arrayOfString2[b1];
                            if (!string2.equals("url")) {
                                list.add(string2);
                                System.out.println(string2);
                            }
                            b1++;
                        }
                    }
                    b++;
                }
            }
            sc.close();
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void sdownloadPicture(String urlList, String name, Integer s) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            name = name.replace(" ", "\\");
            File dir = new File("D:/YCW/" + name);
            if (dir.exists())
                System.out.println("+ name + ");
            if (!name.endsWith(File.separator))
                name = String.valueOf(name) + File.separator;
            if (dir.mkdirs()) {
                System.out.println("+ name + ");
            } else {
                System.out.println("+ name + ");
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:/YCW/" + name + s + ".jpg"));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0)
                fileOutputStream.write(buffer, 0, length);
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}