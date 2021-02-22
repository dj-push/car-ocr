package utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsonTest {
    static String url = "https://car.autohome.com.cn/AsLeftMenu/As_LeftListNew.ashx?typeId=2%20&brandId=0%20&fctId=0%20&seriesId=0";

    private static String getUrl() throws InterruptedException, Exception {
        Document document = Jsoup.connect(url).get();
        Elements elementsByTag3 = document.getElementsByTag("a");
        for (int i = 256; i < elementsByTag3.size(); i++) {
            Thread.sleep(2000L);
            String text = ((Element)elementsByTag3.get(i)).text();
            System.out.println(""+ text);
                    String url2 = ((Element)elementsByTag3.get(i)).attr("href");
            System.out.println(""+ url2);
                    String url3 = "https://car.autohome.com.cn" + url2;
            Document document2 = Jsoup.connect(url3).get();
            Elements elementsByTag4 = document2.getElementsByTag("a");
            System.out.println(""+ elementsByTag4);
            for (Element element2 : elementsByTag4) {
                String attr = element2.attr("href");
                if (attr.startsWith("/pic/series/")) {
                    String text2 = element2.text();
                    if (!text2.isEmpty()) {
                        System.out.println(""+ text2);
                                System.out.println("::::::::" + attr);
                        String url4 = "https://car.autohome.com.cn" + attr;
                        Document document3 = Jsoup.connect(url4).get();
                        Elements elementsByTag = document3.getElementsByTag("a");
                        System.out.println(elementsByTag);
                        for (Element element3 : elementsByTag) {
                            String text3 = element3.text();
                            if (text3.equals("")) {
                                    String attr2 = element3.attr("href");
                            System.out.println(""+ attr2);
                                    String url5 = "https://car.autohome.com.cn" + attr2;
                            Document document4 = Jsoup.connect(url5).get();
                            Elements elementsByTag2 = document4.getElementsByTag("a");
                            System.out.println(elementsByTag2);
                            int cont = 0;
                            for (Element element4 : elementsByTag2) {
                                String attr3 = element4.attr("title");
                                if (!attr3.isEmpty()) {
                                    String text4 = element4.text();
                                    if (!text4.isEmpty()) {
                                        System.out.println(attr3);
                                        String name = String.valueOf(text) + attr3;
                                        String attr4 = element4.attr("href");
                                        System.out.println(""+ attr4);
                                                String url6 = "https://car.autohome.com.cn" + attr4;
                                        Document document5 = Jsoup.connect(url6).get();
                                        Elements elementsByTag5 = document5.getElementsByTag("img");
                                        System.out.println(elementsByTag5);
                                        for (Element element5 : elementsByTag5) {
                                            String attr5 = element5.attr("src");
                                            if (!attr5.contains("s.autoimg.cn/www/common/images")) {
                                                System.out.println(""+ attr5);
                                                        String url7 = "https:" + attr5;
                                                System.out.println(url7);
                                                try {
                                                    sdownloadPicture(url7, name, Integer.valueOf(cont));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                cont++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return url;
}

    public static void main(String[] args) throws Exception {
        getUrl();
    }

    public static void sdownloadPicture(String urlList, String name, Integer s) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            name = name.replace(" ", "\\");
            File dir = new File("D:/qczj/" + name);
            if (dir.exists())
                System.out.println("+ name + ");
            if (!name.endsWith(File.separator))
                name = String.valueOf(name) + File.separator;
            if (dir.mkdirs()) {
                System.out.println("+ name + ");
            } else {
                System.out.println("+ name + ");
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:/qczj/" + name + s + ".jpg"));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
                System.out.println(length);
            }
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}