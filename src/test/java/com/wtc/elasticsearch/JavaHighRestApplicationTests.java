package com.wtc.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaHighRestApplicationTests {

    protected RestHighLevelClient client;

    public JavaHighRestApplicationTests() {
       this.client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("127.0.0.1", 9200)));
    }
}
