package com.example.testes;

import com.example.server_gerenciador_gastos.dto.request.CriarTransacaoRequest;
import com.example.server_gerenciador_gastos.dto.response.CriarTransacaoResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MenuUsuarioTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("===== MENU USUARIO =====");
            System.out.println("1 - Cadastrar usuario");
            System.out.println("2 - Listar usuarios");
            System.out.println("3 - Buscar usuario por email");
            System.out.println("4 - Deletar usuario");
            System.out.println("5 - Login usuario");
            System.out.println("6 - Buscar contas por ID de usuario");
            System.out.println("7 - Criar transacao");
            System.out.println("8 - Listar transacoes por conta");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuarioPorEmail();
                case 4 -> deletarUsuario();
                case 5 -> loginUsuario();
                case 6 -> buscarContasPorIdUsuario();
                case 7 -> criarTransacao();
                case 8 -> listarTransacoesPorConta();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private static void criarTransacao() {
        System.out.println("===== CRIAR TRANSACAO =====");

        System.out.print("Valor: ");
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine();
        int opcao = 6;
        String idContaOrigem = null;
        String idContaDestino = null;
        String idCarteiraOrigem = null;
        String idCarteiraDestino = null;

        while (opcao > 5) {
            System.out.println("    1 - DEPOSITO NA CONTA");
            System.out.println("    2 - SAQUE NA CONTA");
            System.out.println("    3 - DEPOSITO PARA CARTEIRA");
            System.out.println("    4 - SAQUE NA CARTEIRA");
            System.out.println("    5 - TRANSFERENCIA");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
        }
        sc.nextLine();

        String tipo = null;
        switch (opcao) {
            case 1: {
                tipo = "DEPOSITO";
                System.out.print("ID Conta: ");
                idContaDestino = sc.nextLine();
                idContaDestino = idContaDestino.isBlank() ? null : idContaDestino;
                break;
            }
            case 2: {
                tipo = "SAQUE";
                System.out.print("ID Conta: ");
                idContaOrigem = sc.nextLine();
                idContaOrigem = idContaOrigem.isBlank() ? null : idContaOrigem;
                break;
            }
            case 3: {
                tipo = "CONTA_CARTEIRA";
                System.out.print("ID Conta: ");
                idContaOrigem = sc.nextLine();
                idContaOrigem = idContaOrigem.isBlank() ? null : idContaOrigem;
                System.out.print("ID Carteira: ");
                idCarteiraDestino = sc.nextLine();
                idCarteiraDestino = idCarteiraDestino.isBlank() ? null : idCarteiraDestino;
                break;
            }
            case 4: {
                tipo = "CARTEIRA_CONTA";
                System.out.print("ID Conta: ");
                idContaDestino = sc.nextLine();
                idContaDestino = idContaDestino.isBlank() ? null : idContaDestino;
                System.out.print("ID Carteira: ");
                idCarteiraOrigem = sc.nextLine();
                idCarteiraOrigem = idCarteiraOrigem.isBlank() ? null : idCarteiraOrigem;
                break;
            }
            case 5: {
                tipo = "TRANSFERENCIA_CONTA";
                System.out.print("ID Conta Origem: ");
                idContaOrigem = sc.nextLine();
                idContaOrigem = idContaOrigem.isBlank() ? null : idContaOrigem;
                System.out.print("ID Conta Destino: ");
                idContaDestino = sc.nextLine();
                idContaDestino = idContaDestino.isBlank() ? null : idContaDestino;
                break;
            }
            default: {
                System.out.println("Opção de transação inválida");
                break;
            }
        }


        // Cria o DTO diretamente
        CriarTransacaoRequest request = new CriarTransacaoRequest(
                valor,
                tipo,
                idContaOrigem,
                idContaDestino,
                idCarteiraOrigem,
                idCarteiraDestino
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CriarTransacaoRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<CriarTransacaoResponse> response = restTemplate.postForEntity(
                    BASE_URL + "/transacao",
                    entity,
                    CriarTransacaoResponse.class
            );
            System.out.println("Resposta: " + response.getBody());

        } catch (Exception e) {
            // Captura qualquer outro erro inesperado
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }


    private static void listarTransacoesPorConta() {
        System.out.print("ID da conta: ");
        String idConta = sc.nextLine();

        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/transacao/conta/" + idConta, String.class);
        System.out.println("Resposta: " + response.getBody());
    }

    private static void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String json = String.format("{\"nome\":\"%s\",\"email\":\"%s\",\"senha\":\"%s\"}", nome, email, senha);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/usuario", request, String.class);
        System.out.println(response.getBody());
    }

    private static void listarUsuarios() {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/usuario", String.class);
        System.out.println(response.getBody());
    }

    private static void buscarUsuarioPorEmail() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/usuario/" + email, String.class);
        System.out.println(response.getBody());
    }

    private static void deletarUsuario() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        restTemplate.delete(BASE_URL + "/usuario/" + email);
        System.out.println("Usuário deletado (se existia).");
    }

    private static void loginUsuario() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String json = String.format("{\"email\":\"%s\",\"senha\":\"%s\"}", email, senha);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/usuario/login", request, String.class);
        System.out.println(response.getBody());
    }

    private static void buscarContasPorIdUsuario() {
        System.out.print("ID do usuário: ");
        String id = sc.nextLine();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/usuario/contas/" + id, String.class);
        System.out.println(response.getBody());
    }
}
