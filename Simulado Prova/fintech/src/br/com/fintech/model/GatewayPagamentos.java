package br.com.fintech.model;

import java.util.HashMap;
import java.util.Map;

public class GatewayPagamentos {
private Map<String,CarteiraDigital> carteiras = new HashMap<>();

public GatewayPagamentos() {
    this.carteiras = new HashMap<>();
}
public void cadastrarCarteira(CarteiraDigital carteira){
this.carteiras.put(carteira.getCodigoCarteira(), carteira);
}

public CarteiraDigital buscarCarteira (String codigoCarteira) {
 return this.carteiras.get(codigoCarteira);
}
}

