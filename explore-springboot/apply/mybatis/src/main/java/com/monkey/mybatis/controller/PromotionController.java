package com.monkey.mybatis.controller;

import com.monkey.mybatis.mapper.PromotionMapper;
import com.monkey.mybatis.model.TagGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class PromotionController {

    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private StringRedisTemplate tuijianRedisTemplate;

    @RequestMapping("promotion33")
    public void ids(){
        List<Integer> ids = promotionMapper.ids();
        StringBuilder sb = new StringBuilder();
        for (Integer id : ids) {
            sb.append(id).append("_");
        }

        tuijianRedisTemplate.opsForValue().set("hykb_hotGame", sb.substring(0, sb.length() - 1));
    }

    @RequestMapping("promotion1")
    public Set<String> queryInfos(){

        return promotionMapper.queryIds();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("114403,116535,114421,116852,116796,83761,84756,84886,116620,51095,64551,42897,79010,49769,66216,62445,59382,83424,59369,69699,63171,59308,59368,63594,67967,116842,54332,81698,68995,76834,54250,53231,75125,46013,70684,75690,51352,67265,78455,80252,47172,44551,60374,80210,75288,39156,73108,84171,79174,69347,116739".replaceAll(",", "_"));
    }

    @RequestMapping("promotion2")
    public List<TagGroup> queryInfos2() {
        return promotionMapper.queryGroups("media-3be91501b5514b8e", "adp-f57bf42ae29e47b7", 0);
    }

    @RequestMapping("delete")
    @Transactional
    public void delete(){
        //查出状态是0的，即要关闭的资源位
        List<String> query = promotionMapper.query2();


        for (String id : query) {
            //把这些资源位加入到过滤表中
            promotionMapper.insert(id);
        }


        System.out.println(query.size());
    }

    @RequestMapping("movetoPid")
    public void huifu()throws Exception{

        //查出状态是0的，即要关闭的资源位
        List<String> datatraceIds = promotionMapper.query2();
        for (String datatraceId : datatraceIds) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut("http://gateway.4399fat.com/tuijian-server/folder/postionMoveToFolder");
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("moveToPid", "0"));
            list.add(new BasicNameValuePair("mediaUniqueId", "media-3be91501b5514b8e"));
            list.add(new BasicNameValuePair("datatraceId",datatraceId));
            list.add(new BasicNameValuePair("mediaId", "254"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "utf-8");
            Header header1 = new BasicHeader("Cookie", "Pauth_S=3119174973|2111940468|841e96a61dd04d5290cd1d9888b823fa|1581402910|1000|2403baaf37493286b42e13f147b6f23b|0; Pauth_S=3119174973|2111940468|068fbbd29fda43118e560849e1352763|1581402502|20001|8425efcab20c734d6b0652518d45522f|0; e_token=UCFB602C760640A51201215E7D7B1B81; e_uid=3119174973; e_username=2111940468; Pauth=3119174973|2111940468|8b522f29599f466e968692c464df0aae|1581406283|1000|64ad34fe48bff2a6cc1e283d3cbb90d8|0");
            BasicHeader header2 = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
            BasicHeader header3 = new BasicHeader("Accept", "*/*");
            BasicHeader header4 = new BasicHeader("Accept-Encoding", "gzip, deflate, br");
            BasicHeader header5 = new BasicHeader("Connection", "keep-alive");

            httpPut.addHeader(header1);
            httpPut.addHeader(header2);
            httpPut.addHeader(header3);
            httpPut.addHeader(header4);
            httpPut.addHeader(header5);


            httpPut.setEntity(formEntity);
            System.out.println("发起的请求：" + httpPut);
            CloseableHttpResponse execute = httpClient.execute(httpPut);

            if (execute.getStatusLine().getStatusCode() == 200){
                String content = EntityUtils.toString(execute.getEntity());
                System.out.println(content.length());
            }
        }




    }
}
