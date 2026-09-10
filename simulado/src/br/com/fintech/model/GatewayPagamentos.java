package br.com.fintech.model;

import java.util.Map;

public abstract class GatewayPagamentos {

   private Map<String, CarteiraDigital> carteira;

   public GatewayPagamentos() {
}

   public abstract void cadastrarCarteira(CarteiraDigital carteira);





   
}
