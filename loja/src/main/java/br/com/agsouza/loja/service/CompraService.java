package br.com.agsouza.loja.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.agsouza.loja.controller.dto.CompraDTO;
import br.com.agsouza.loja.controller.dto.InfoDTO;

@Service
public class CompraService {
	@Autowired
	private RestTemplate client;
	/* implementado para demonstrar o funcionamento ribbo, load balance automatico*/
	@Autowired
	private DiscoveryClient eureka;
	
	public void compra(CompraDTO compra) {
		/*a cada requisição, o Ribbon rotaciona para uma instância diferente. 
		 * Todavia, é possível customizar o algoritmo de load balancing, 
		 * como pode ser visto na documentação.*/
		ResponseEntity<InfoDTO> response = client.exchange("http://fornecedor/info/"+compra.getEndereco().getEstado(),
				HttpMethod.GET,
				null,
				InfoDTO.class);
		/*implementado para demonstrar como ribbo pega as instancias do discovery*/
		eureka.getInstances("fornecedor").stream().forEach(c -> System.out.println("localhost:"+c.getPort()));
		
		System.out.println("com RestTemplate :"+response.getBody().getEndereco());
	}

}
