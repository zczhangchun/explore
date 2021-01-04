import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
//14:22 20个线程
public class Main {
    public static void main(String[] args) throws Exception {
        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000)
//                .addNodeAddress("redis://172.16.38.201:8001")
//                .addNodeAddress("redis://172.16.38.202:8002")
//                .addNodeAddress("redis://172.16.38.203:8003")
//                .addNodeAddress("redis://172.16.38.201:8004")
//                .addNodeAddress("redis://172.16.38.202:8005")
//                .addNodeAddress("redis://172.16.38.203:8006");
        config.useSingleServer().setAddress("redis://r-bp1exltnezv0r4yb74pd.redis.rds.aliyuncs.com:6379").setPassword("zc123123!!");
        RedissonClient client = Redisson.create(config);

        client.getBucket("k1").set("v1");
        System.out.println(client.getBucket("k1").get());
        client.shutdown();
    }
}
