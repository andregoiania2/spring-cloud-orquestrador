package br.com.agsouza.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agsouza.loja.controller.form.CompraForm;
import br.com.agsouza.loja.model.Compra;
import br.com.agsouza.loja.service.CompraFeignService;

@RestController
@RequestMapping("/compra")
public class CompraController {
	
	//@Autowired
	//private CompraService compraService;
	
	@Autowired
	private CompraFeignService compraFService;
	
	@PostMapping
	public Compra realizaCompra(@RequestBody CompraForm compraForm) {
		//compraService.compra(compra);		
		return compraFService.efetivaCompra(compraFService.compraInicial(compraForm.convert()));
	}
	
	@PostMapping(path = "/reprocessar/{idCompra}")
	public Compra realizaCompra(@PathVariable Long idCompra) {
		return compraFService.reprocessaCompra(compraFService.getCompraId(idCompra));
	}
	
	@GetMapping(value = "/{id}")
	public Compra getCompraId(@PathVariable("id") Long id) {
		return compraFService.getCompraId(id);
	}

}
