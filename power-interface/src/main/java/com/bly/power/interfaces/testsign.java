package com.bly.power.interfaces;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class testsign {

	@Test
	public void call() {
		IRSAEncryptService service = new RSAEncryptServiceImpl();
		Map<String,String>  test = new HashMap<>();
		test.put('"'+"username"+'"', '"'+"admin"+'"');
		test.put('"'+"password"+'"', '"'+"8888"+'"');
		String name = "[{\"password\":\"8888\",\"username\":\"admin\"}]";
		String sign = service.sign("\r\n" + 
				"" + 
				"MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAxMV3uDUGYocq57L5DP0k7f+RxuT4\r\n" + 
				"zXxWmtjPP9pXefgwO/kpTOtUCC0Oy3Ts83/JxmLhj+rIwot1/GCds7fRPwIDAQABAkBO54BidaDh\r\n" + 
				"0aH612HXO4ubhGhIgXjjrYBtB5PT3xJE7fSBlph02oY87ZyBsTlVnaeWN89sll8BkEo9Yh1cFHxB\r\n" + 
				"AiEA9GqQOjZNHCSoLAVFCKP0YEHldTl0eHwaUPhvtjc9HeMCIQDOGNdrMnzpfxuWpLMEvatkBlPA\r\n" + 
				"7Uc1d8dMUJTrjmKd9QIgSWU2qkRkI29ekNmEQXP6jm07WlhGgWfC/02bPjyYPcECIEoj9ZrClt0I\r\n" + 
				"tMwEg8H42WcqP4bv1OYPKMrzUh9LIX7hAiEA0aoYDNgGFPaVrKipQx8XvKSpOqovXIL7xAKJxKlQ\r\n" + 
				"nx0=\r\n" + 
				"", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMTFd7g1BmKHKuey+Qz9JO3/kcbk+M18VprYzz/aV3n4MDv5KUzrVAgtDst07PN/ycZi4Y/qyMKLdfxgnbO30T8CAwEAAQ==", "0782ee869ca541d59bcf3713c262fe3a", 8076, "2018-12-21 16:05:30",name);
	
		System.out.println(sign);
		
	}
}
