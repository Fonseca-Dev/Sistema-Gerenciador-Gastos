package com.example.server_gerenciador_gastos.mapper;

import com.example.server_gerenciador_gastos.dto.request.CriarUsuarioRequest;
import com.example.server_gerenciador_gastos.entity.Usuario;

public class UsuarioMapper {
    public static Usuario map(CriarUsuarioRequest request){
        return new Usuario(request.nome(), request.email(),request.senha());
    }
}
