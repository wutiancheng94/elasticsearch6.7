package com.wtc.elasticsearch.DocumentAPIs;

import com.wtc.elasticsearch.JavaHighRestApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;

import java.io.IOException;

/**
 * @author 吴天成
 * @create 2019/4/16
 * @since 1.0.0
 */
@Slf4j
public class GetAPI extends JavaHighRestApplicationTests {

    String index = "posts";
    String type = "doc";
    String id = "1";

    /**
     * GET posts/doc/1
     */
    @Test
    public void simpleExample() {
        GetRequest request = new GetRequest(index, type, id);

        // GET posts/doc/1?_source=false 禁用_source 返回的_source为null  默认是启动
        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        StringBuilder dsl = new StringBuilder();
        log.info("dsl:\n{}",
                dsl.append("GET ")
                    .append(request.index()).append("/")
                    .append(request.type()).append("/")
                    .append(request.id()).append("/?")
                    .append("_source").append(request.fetchSourceContext().fetchSource())
                    .toString());

        GetResponse response = null;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                if (response.isSourceEmpty()) {
                    log.info("_source被禁用");
                }
            } else {}
            log.info("响应回来的_source：{}", response.getSourceAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定返回来的字段数据
     */
    @Test
    public void OrderSourceField() {
        GetRequest request = new GetRequest(index, type, id);

//        GET posts/doc/1?_source_includes=user,message
//        String[] includes = new String[]{"user", "message"};
//        String[] excludes = Strings.EMPTY_ARRAY;

//        GET posts/doc/1?_source_excludes=use*
        String[] includes = Strings.EMPTY_ARRAY;
        String[] excludes = new String[]{"use*"};

        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);

        log.info("{}&{}", request.fetchSourceContext().includes(), request.fetchSourceContext().excludes());

        GetResponse response = null;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
            log.info("{}", response.getSourceAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void storedFields() {
        GetRequest request = new GetRequest(index, type, id);
        request.storedFields("message");
    }

}