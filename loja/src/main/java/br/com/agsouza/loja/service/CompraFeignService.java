package br.com.agsouza.loja.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.agsouza.loja.client.FornecedorClient;
import br.com.agsouza.loja.client.TransportadorClient;
import br.com.agsouza.loja.controller.dto.EntregaDTO;
import br.com.agsouza.loja.controller.dto.InfoDTO;
import br.com.agsouza.loja.controller.dto.InfoPedidoDTO;
import br.com.agsouza.loja.controller.dto.VoucherDTO;
import br.com.agsouza.loja.model.Compra;
import br.com.agsouza.loja.model.CompraState;
import br.com.agsouza.loja.repository.CompraRepository;

@Service
public class CompraFeignService {
	
	private final Logger LOG =  LoggerFactory.getLogger(CompraFeignService.class);
	
	@Autowired	
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private TransportadorClient transportadorClient;
	
	public Compra compraInicial(Compra compra) {
		//não coloquei o hystrix para trabalhar em pool de Thread sepadas, mas é muito interessante devido o problema de instabilidade de banco.		
		return compraRepository.save(compra);
	}	
	
	@HystrixCommand(fallbackMethod = "efetivaCompraFallback",
			threadPoolKey = "efetivaCompraThreadPool")
	public Compra efetivaCompra(Compra compra) {
		realizaPedidoFornecedor(compra);
//		if (1==1) {throw new RuntimeException(); } para testar as etapas
		realizaPedidoTransportador(compra);
		return compra;		
	}
	
	@HystrixCommand(fallbackMethod = "efetivaCompraFallback",
			threadPoolKey = "efetivaCompraThreadPool")
	
	public void realizaPedidoTransportador(Compra compra) {
		LOG.info("buscando informação de fornecedor do estado {}", compra.getEstado());
		InfoDTO infoDTO = fornecedorClient.getInfoPorEstado(compra.getEstado());
		
		EntregaDTO entregaDTO = new EntregaDTO();
		entregaDTO.setPedidoId(compra.getPedidoId());
		entregaDTO.setDataParaEntrega(compra.getDataParaEntrega());
		entregaDTO.setEnderecoDestino(compra.getRua().concat(" ").concat(""+compra.getNumero()).concat(" ")
				.concat(compra.getEstado()));
		entregaDTO.setEnderecoOrigem(infoDTO.getEndereco());	
		
		VoucherDTO voucherDTO = transportadorClient.reservaEntrega(entregaDTO);
		LOG.info(voucherDTO.toString());
		compra.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		compra.setDataParaEntrega(voucherDTO.getPrevisaoParaEntrega());
		compra.setVoucher(voucherDTO.getNumero());
		compraRepository.save(compra);
	}
	
	public void realizaPedidoFornecedor(Compra compra) {
		LOG.info("realiza um pedido");
		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItensDTO());

		compra.setPedidoId(pedido.getId());
		compra.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));		
		compra.setState(CompraState.PEDIDO_REALIZADO);
		compraRepository.save(compra);
	}

	public Compra efetivaCompraFallback(Compra compra) {		
		return compra;
	}
	
	@HystrixCommand(threadPoolKey = "getCompraIdThreadPool")
	public Compra getCompraId(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}

	@HystrixCommand(fallbackMethod = "reprocessaCompraFallback",
			threadPoolKey = "efetivaCompraThreadPool")
	@Transactional
	public Compra reprocessaCompra(Compra compra) {
		if (compra.getState().equals(CompraState.RECEBIDO)) {
			realizaPedidoFornecedor(compra);			
		}
		
		if (compra.getState().equals(CompraState.PEDIDO_REALIZADO)) {
			realizaPedidoTransportador(compra);			
		}
		return compra;
	}
	

	public Compra reprocessaCompraFallback(Compra compra) {
		return compra;
	}
}
