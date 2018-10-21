package bank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

public class account_database {
	private Map<Integer, account> base_map = new HashMap<>();
	private Random ran = new Random();
	private Integer verification_code;


	public Map<Integer, account> getBase_map() {
		return base_map;
	}

	public void setBase_map(Map<Integer, account> base_maper) {
		base_map = base_maper;
	}

	public Integer getVerification_code() {
		return verification_code;
	}
	
	public void newAutoCode() {
		verification_code = ran.nextInt(900000) + 10000;
		if(verification_code < 0)
			verification_code *= -1;
	}

	public void setVerification_code(Integer verification_coder) {
		verification_code = verification_coder;
	}
	
	
}
