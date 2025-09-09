package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.request.CriarUsuarioRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.entity.Usuario;
import com.example.server_gerenciador_gastos.mapper.UsuarioMapper;
import com.example.server_gerenciador_gastos.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public BaseResponse criarUsuario(CriarUsuarioRequest request) {
        if (Objects.isNull(request)) {
            return new BaseResponse("Request está nulo.", HttpStatus.NO_CONTENT, null);
        }
        Usuario novoUsuario = UsuarioMapper.map(request);
        novoUsuario.setData(LocalDateTime.now());
        repository.save(novoUsuario);
        return new BaseResponse("Usuário criado com sucesso.", HttpStatus.CREATED, novoUsuario);
    }

    public BaseResponse buscarUsuarioPorEmail(String email){
        if(repository.findByEmail(email).isEmpty()){
            return new BaseResponse("Usuário não encontrado.",HttpStatus.NOT_FOUND,null);
        }
        Usuario usuario = repository.findByEmail(email).get();
        return new BaseResponse("Usuário encontrado.",HttpStatus.OK,usuario);
    }

    public BaseResponse listarUsuarios(){
        if(repository.findAll().isEmpty()){
            return new BaseResponse("Nenhum usuário cadastrado.",HttpStatus.NOT_FOUND,null);
        }
        return new BaseResponse("Usuários cadastrados encontrados.",HttpStatus.OK,repository.findAll());
    }

    public BaseResponse deletarUsuario(String email) {
        var usuarioOpt = repository.findByEmail(email);
        if(usuarioOpt.isEmpty()) {
            return new BaseResponse("Usuário não encontrado.", HttpStatus.NOT_FOUND, null);
        }

        Usuario usuario = usuarioOpt.get();
        repository.delete(usuario);
        return new BaseResponse("Usuário deletado com sucesso.", HttpStatus.OK, null);
    }

}
