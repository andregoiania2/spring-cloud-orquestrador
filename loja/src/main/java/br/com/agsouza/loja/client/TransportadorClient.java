package br.com.agsouza.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.agsouza.loja.controller.dto.EntregaDTO;
import br.com.agsouza.loja.controller.dto.VoucherDTO;

@FeignClient("transportador")
public interface TransportadorClient {
	
	@PostMapping(path = "/entrega")
	VoucherDTO reservaEntrega(@RequestBody EntregaDTO pedidoDTO);
}
