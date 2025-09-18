package com.example.server_gerenciador_gastos.service;
import com.example.server_gerenciador_gastos.entity.Carteira;
import com.example.server_gerenciador_gastos.entity.Usuario;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class ContaService {
    public BigDecimal calculoSaldo(Usuario usuario, ArrayList<Carteira> carteiras){
        BigDecimal soma = BigDecimal.valueOf(0);
        for(Carteira c : carteiras){
            soma = soma.add(c.getSaldo());
        }
        return soma;
    }
}
