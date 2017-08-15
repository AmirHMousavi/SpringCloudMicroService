package com.example;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

	@Autowired
	DiscoveryClient discoveryClient;

	@RequestMapping("/sentence")
	public String getSentence() {
		return String.format("%s %s %s %s %s", getWord("Lab-4-subject"), getWord("Lab-4-verb"),
				getWord("Lab-4-article"), getWord("Lab-4-adjective"), getWord("Lab-4-noun"));

	}

	public String getWord(String service) {
		List<ServiceInstance> list = discoveryClient.getInstances(service);
		if (list != null && list.size() > 0) {
			URI uri = list.get(0).getUri();
			if (uri != null) {
				return (new RestTemplate()).getForObject(uri, String.class);
			}
		}
		return null;
	}

}
