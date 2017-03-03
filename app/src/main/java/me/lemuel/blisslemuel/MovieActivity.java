package me.lemuel.blisslemuel;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Document document = Jsoup.connect("http://www.meishichina.com/")
                            .header("Accept", "*/*")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                            .header("Referer", "http://www.meishichina.com/")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                            .timeout(5000)
                            .get();

                    Elements select = document.select("div.top-bar").select("ul.bar-left.left");
                     Toast.makeText(MovieActivity.this, select.text(), Toast.LENGTH_SHORT).show();
                    StringBuilder builder = new StringBuilder();
                    for (Element e : select) {
                        String title = e.select("a").attr("title");
                        builder.append(title).append(" / ");
                    }
                    String allTag = builder.toString();
                    Toast.makeText(MovieActivity.this, allTag, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();


    }
}
