package com.monkey.storm.wordcount;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 单词技术拓扑
 * 一个拓扑包含spout、bolt。有几个bolt取决于业务
 * 总体流程就是
 * RandomSentenceSpout（源源不断的随机发射一条谚语） --> bolt1（切割谚语） --> bolt2（计算单词的数量）
 */
public class WordCountTopology {

    /**
     * spout
     * spout继承一个基类
     * spout就是用来从数据源中读取数据，并将数据传递给下一个bolt的
     * 我们这里做简化，就不从外部获取数据了，直接内部不停发射一些句子
     */
    @Slf4j
    public static class RandomSentenceSpout extends BaseRichSpout{


        private static final long serialVersionUID = -9215580110986935731L;
        /**
         * 用来射射数据出去的对象
         * 在open方法中可以初始化
         */
        private SpoutOutputCollector collector;
        private Random random;
        /**
         * open方法
         *
         * 用来对spout进行初始化
         * 比如说，创建一个线程池，或者创建一个数据库连接池，获取构造一个httpClient
         * 我们这里就是将内部的变量初始化。
         */
        public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
            this.collector = spoutOutputCollector;
            this.random = new Random();
        }

        /**
         * nextTuple方法
         *
         * 这个spout类，之前说过，最终会运行在task中
         * 也就是运行在某个worker进程的某个executor线程内部的某个task中
         * 那个task会负责去不断的无限循环调用nextTuple()方法
         * 这样的话呢，无限循环调用就可以不断发射最新的数据出去，形成一个数据流
         * collector.emit就是发射数据的方法
         *
         * 我们这里发射数据就随机一个谚语发射出去
         */
        public void nextTuple() {
            Utils.sleep(100);
            String[] sentences = new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
                    "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature"};

            String sentence = sentences[random.nextInt(sentences.length)];
            log.info("【发射句子sentence={}】", sentence);
            //这个values，可以认为就是构建一个tuple，他其实就是tuple里的数据，下面的fields会指定数据数据所属的字段名称。
            //tuple是最小的数据单位，无限个tuple组成的流就是一个stream
            collector.emit(new Values(sentence));
        }

        /**
         * declareOutputFields这个方法
         *
         * 很重要，这个方法是定义一个你发射出去的每个tuple中的每个field的名称是什么
         *
         * 这里我们只存一个字段，就是sentence，存的就是上面的那个谚语
         */
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("sentence"));
        }
    }

    /**
     * 写一个bolt，直接继承一个BaseRichBolt基类
     *
     * 实现里面的所有的方法即可，每个bolt代码，同样是发送到worker某个executor的task里面去运行
     * 这个bolt的任务就是把数据进行切割，然后发射给下一个bolt
     *
     */
    public static class SplitSentence extends BaseRichBolt {

        private static final long serialVersionUID = 1772095170212800729L;

        /**
         * 用来发射数据用的
         */
        private OutputCollector collector;

        /**
         * prepare方法
         *
         * 对于bolt来说，第一个方法，就是prepare方法
         * 这个方法用来初始化{@link OutputCollector}
         */
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        /**
         * execute方法
         *
         * 就是说，每次接收到一条数据后，就会交给这个executor方法来执行
         * 我们这里要做的就是通过空格分割字符串，然后将分割后的每个字符串发射给下一个bolt
         */
        public void execute(Tuple tuple) {
            String sentence = tuple.getStringByField("sentence");
            String[] words = sentence.split(" ");
            for (String word : words) {
                collector.emit(new Values(word));
            }
        }

        /**
         * 定义发射出去的tuple的每个filed的名称
         */
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }

    }

    /**
     * 这个bolt用来计算单词的数量的
     */
    @Slf4j
    public static class WordCount extends BaseRichBolt {

        private static final long serialVersionUID = -5348170607019449624L;

        private OutputCollector collector;
        private Map<String, Long> wordCounts;

        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
            this.wordCounts = new HashMap<String, Long>();
        }

        public void execute(Tuple tuple) {
            String word = tuple.getStringByField("word");

            Long count = wordCounts.get(word);
            if (count == null){
                count = 0L;
            }
            count++;

            wordCounts.put(word, count);
            log.info(" 【单词计数】 {} 出现的次数是：{}", word, count);

            collector.emit(new Values(word, count));
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }

    public static void main(String[] args) throws Exception {
        //在main方法中，将spout和bolt组合起来，构建成一个topology

        TopologyBuilder builder = new TopologyBuilder();

        // 这里的第一个参数的意思，就是给这个spout设置一个名字
        // 第二个参数的意思，就是创建一个spout的对象
        // 第三个参数的意思，就是设置spout的executor有几个
        // 下面也可以不设置task的数量，那么默认就是和executor的数量一样，也就是每个executor有一个task
        builder.setSpout("RandomSentence", new RandomSentenceSpout(), 2)
                .setNumTasks(2);

        builder.setBolt("SplitSentence", new SplitSentence(), 10)
                .setNumTasks(10)
                .shuffleGrouping("RandomSentence");
        // 这个很重要，就是说，相同的单词，从SplitSentence发射出来时，一定要进入到下游的指定的同一个task中
        // 只有这样子，才能准确的统计出每个单词的数量
        // 比如你有个单词，hello，下游task1接收到3个hello，task2接收到2个hello，那么统计就会出问题
        // 需要5个hello，要全都进入一个task，结果才是正确的
        builder.setBolt("WordCount", new WordCount(), 10)
                .setNumTasks(20)
                .fieldsGrouping("SplitSentence", new Fields("word"));
        Config config = new Config();


        if (args != null && args.length > 0){
            // 说明是在命令行执行，打算提交到storm集群上去
            config.setNumWorkers(3);
            try {
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            }catch (Exception e){

            }
        }else {
            //说明是在idea本地运行
            //设置最大的task并行数量
            config.setMaxTaskParallelism(20);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("WordCountTopology", config, builder.createTopology());

            Utils.sleep(60000);
            cluster.shutdown();
        }

    }


}
