package com.example.server_gerenciador_gastos.service;

import ch.qos.logback.core.net.server.Client;
import com.example.server_gerenciador_gastos.dto.request.CriarUsuarioRequest;
import com.example.server_gerenciador_gastos.dto.request.LoginUsuarioRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.dto.response.LoginUsuarioResponse;
import com.example.server_gerenciador_gastos.entity.Conta;
import com.example.server_gerenciador_gastos.entity.Usuario;
import com.example.server_gerenciador_gastos.repository.ContaRepository;
import com.example.server_gerenciador_gastos.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ContaRepository contaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.contaRepository = contaRepository;
    }

    public BaseResponse criarUsuario(CriarUsuarioRequest request) {
        if (Objects.isNull(request)) {
            return new BaseResponse("Request está nulo.", HttpStatus.NO_CONTENT, null);
        }
        // Verifica se o email já existe
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            return new BaseResponse("Email já cadastrado.", HttpStatus.CONFLICT, null);
        }

        // Cria e salva o usuário
        Usuario novoUsuario = new Usuario(request.nome(), request.email(), request.senha());
        novoUsuario.setDataCriacao(LocalDateTime.now());
        usuarioRepository.save(novoUsuario);

        // Cria conta corrente automática
        Conta contaCorrente = new Conta();
        contaCorrente.setSaldo(BigDecimal.ZERO);
        contaCorrente.setTipo("corrente");
        contaCorrente.setTitular(novoUsuario);
        contaRepository.save(contaCorrente);

        return new BaseResponse("Usuário criado com sucesso e conta corrente gerada.", HttpStatus.CREATED, novoUsuario);
    }

    public BaseResponse buscarUsuarioPorEmail(final String email){
        if(usuarioRepository.findByEmail(email).isEmpty()){
            return new BaseResponse("Usuário não encontrado.", HttpStatus.NOT_FOUND, null);
        }
        return new BaseResponse("Usuário encontrado.", HttpStatus.OK, usuarioRepository.findByEmail(email));
    }

    public BaseResponse buscarContasPorIdUsuario(final String id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()->new RuntimeException("Usuário não encontrado."));
        List<Conta> contasUsuario = usuario.getContas();
        return new BaseResponse("Contas encontradas.",HttpStatus.OK,contasUsuario);
    }


    public BaseResponse listarUsuarios(){
        if(usuarioRepository.findAll().isEmpty()){
            return new BaseResponse("Nenhum usuário cadastrado.",HttpStatus.NOT_FOUND,null);
        }
        return new BaseResponse("Usuários cadastrados encontrados.",HttpStatus.OK,usuarioRepository.findAll());
    }

    public BaseResponse deletarUsuario(String email) {
        if(usuarioRepository.findByEmail(email).isEmpty()){
            return new BaseResponse("Usuário não encontrado.", HttpStatus.NOT_FOUND, null);
        }

        Usuario usuario = usuarioRepository.findByEmail(email).get(); // agora funciona
        usuarioRepository.delete(usuario);
        return new BaseResponse("Usuário deletado com sucesso.", HttpStatus.OK, usuario);
    }

    public BaseResponse loginPorEmailESenha(LoginUsuarioRequest request){
        Usuario usuario = usuarioRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!usuario.getSenha().equalsIgnoreCase(request.senha())){
            return new BaseResponse("Senha incorreta.",HttpStatus.UNAUTHORIZED,null);
        }

        LoginUsuarioResponse loginUsuarioResponse = new LoginUsuarioResponse(usuario.getId());

        return new BaseResponse("Login efetuado com suceso.",HttpStatus.OK,loginUsuarioResponse);
    }


}
