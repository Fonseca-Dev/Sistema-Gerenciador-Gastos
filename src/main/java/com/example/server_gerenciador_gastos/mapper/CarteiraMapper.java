package com.example.server_gerenciador_gastos.mapper;

import com.example.server_gerenciador_gastos.dto.request.CriarCarteiraRequest;
import com.example.server_gerenciador_gastos.entity.Carteira;
import org.springframework.stereotype.Component;

@Component
public class CarteiraMapper {
    public static Carteira map(final CriarCarteiraRequest request){
        return new Carteira(request.nome(), request.saldo(), request.meta(), request.idConta());
    }

    public Carteira toEntity(Carteira carteira) {
        return carteira;
    }
}
