package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.request.CriarUsuarioRequest;
import com.example.server_gerenciador_gastos.dto.request.LoginUsuarioRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.dto.response.LoginUsuarioResponse;
import com.example.server_gerenciador_gastos.entity.Usuario;
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
        // Verifica se o email já existe
        if (repository.findByEmail(request.email()).isPresent()) {
            return new BaseResponse("Email já cadastrado.", HttpStatus.CONFLICT, null);
        }
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(request.nome());
        novoUsuario.setEmail(request.email());
        novoUsuario.setSenha(request.senha());
        novoUsuario.setDataCriacao(LocalDateTime.now());
        repository.save(novoUsuario);
        return new BaseResponse("Usuário criado com sucesso.", HttpStatus.CREATED, novoUsuario);
    }

    public BaseResponse buscarUsuarioPorEmail(final String email){
        if(repository.findByEmail(email).isEmpty()){
            return new BaseResponse("Usuário não encontrado.", HttpStatus.NOT_FOUND, null);
        }
        return new BaseResponse("Usuário encontrado.", HttpStatus.OK, repository.findByEmail(email));
    }


    public BaseResponse listarUsuarios(){
        if(repository.findAll().isEmpty()){
            return new BaseResponse("Nenhum usuário cadastrado.",HttpStatus.NOT_FOUND,null);
        }
        return new BaseResponse("Usuários cadastrados encontrados.",HttpStatus.OK,repository.findAll());
    }

    public BaseResponse deletarUsuario(String email) {
        if(repository.findByEmail(email).isEmpty()){
            return new BaseResponse("Usuário não encontrado.", HttpStatus.NOT_FOUND, null);
        }

        Usuario usuario = repository.findByEmail(email).get(); // agora funciona
        repository.delete(usuario);
        return new BaseResponse("Usuário deletado com sucesso.", HttpStatus.OK, usuario);
    }

    public BaseResponse loginPorEmailESenha(LoginUsuarioRequest request){
        Usuario usuario = repository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!usuario.getSenha().equalsIgnoreCase(request.senha())){
            return new BaseResponse("Senha incorreta.",HttpStatus.UNAUTHORIZED,null);
        }

        LoginUsuarioResponse loginUsuarioResponse = new LoginUsuarioResponse(usuario.getId());

        return new BaseResponse("Login efetuado com suceso.",HttpStatus.OK,loginUsuarioResponse);
    }


}
