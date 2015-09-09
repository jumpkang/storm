package storm.starter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TestMessageScheme implements Scheme {  
	  
    /**
	 * 
	 */
	private static final long serialVersionUID = -5538804815227267894L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestMessageScheme.class);  
      
    @Override  
    public List<Object> deserialize(byte[] bytes) {  
		try {
			String msg = new String(bytes, "UTF-8");
	        return new Values(msg); 
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Cannot parse the provided message!", e);  
		}  
		return null;
    }  
  
    @Override  
    public Fields getOutputFields() {  
        return new Fields("msg");  
    }  
  
} 