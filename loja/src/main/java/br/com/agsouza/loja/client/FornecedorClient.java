package br.com.agsouza.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.agsouza.loja.controller.dto.InfoDTO;
import br.com.agsouza.loja.controller.dto.InfoPedidoDTO;
import br.com.agsouza.loja.controller.dto.ItemDTO;

@FeignClient("fornecedor")
public interface FornecedorClient {

	@GetMapping(value = "/info/{estado}")
	InfoDTO getInfoPorEstado(@PathVariable String estado);

	@PostMapping(value = "/pedido")
	InfoPedidoDTO realizaPedido(List<ItemDTO> itens); 
}
