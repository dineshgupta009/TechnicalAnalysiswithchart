package com.example.TechnicalAnalysis.feign;
import java.util.HashMap;
import java.util.Map;

public class FeignBuilder {

	public static Map<String, String> builder() {
		Map<String, String> headerMap = new HashMap<String, String>();
		
		String nsit = "nsit=wes0AwEKvByjMnl-g3c3IxnU;";
		String nseappid = "nseappid=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhcGkubnNlIiwiYXVkIjoiYXBpLm5zZSIsImlhdCI6MTY0MDI2NzQ2MCwiZXhwIjoxNjQwMjcxMDYwfQ.-ehe3ZqLvItmdZ4j3A3aQYhY6kU4Bue0sBOFa5LA4ak;";
		String bmsv = "bm_sv=73598FBEA1879C2F84D841B2AE489B2E~7fQRDs0McQyuGplpJ5B1QYT+Jlax+AbiWqrQLXXT0/2IjfPL+P7k9GeovMO8oOtq4kLCWkl/C7+VJgBamRiMYYoljQoRMON5Kyo0anCzoFc9jlGgX6glrOeCYmB2xSSRh/nd5SGt1nhgmJOHHxZwSAkFoSDrfdVYQZO3K+2rdMw=";		
		
		String cookies =nsit + nseappid +bmsv;
		headerMap.put("accept-language", "en-US,en;q=0.9");
		headerMap.put("referer", "https://www.nseindia.com/option-chain");
		headerMap.put("accept-encoding", "identity");
		headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36");
		headerMap.put("cookie", cookies);
		
		return headerMap;
	}
}
