package storm.starter;

import java.util.HashMap;
import java.util.Map;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class KafkaToplogogy {

	public static class WordCount extends BaseBasicBolt {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9080948772140456741L;
		Map<String, Integer> counts = new HashMap<String, Integer>();

		@Override
		public void execute(Tuple tuple, BasicOutputCollector collector) {
			String word = tuple.getString(0);
			if (word != null && word.indexOf("#01#") != -1) {
				System.out.println(word);
			}
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("word", "count"));
		}
	}
	
	public static class WordCount2 extends BaseBasicBolt {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9080948772140456741L;
		Map<String, Integer> counts = new HashMap<String, Integer>();

		@Override
		public void execute(Tuple tuple, BasicOutputCollector collector) {
			String word = tuple.getString(0);
			if (word != null && word.indexOf("#01#") != -1) {
				System.out.println(word);
			}
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("word", "count"));
		}
	}

	public static void main(String[] args) {
		String topic = "EAGLEYE_LOG_CHANNEL";
		String zkRoot = "";
		String spoutId = "pay_analysis";
		String zkHostsStr = "192.168.5.159:2181,192.168.5.158:2181";
		BrokerHosts brokerHosts = new ZkHosts(zkHostsStr);

		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot,
				spoutId);
		spoutConfig.scheme = new SchemeAsMultiScheme(new TestMessageScheme());

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(spoutId, new KafkaSpout(spoutConfig), 3);
		builder.setBolt("count", new WordCount(), 12).shuffleGrouping(spoutId);
		builder.setBolt("count2", new WordCount2(), 12).shuffleGrouping(spoutId);
		Config conf = new Config();
		conf.setDebug(false);
		conf.setMaxTaskParallelism(3);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("word-count", conf, builder.createTopology());
	}

}
