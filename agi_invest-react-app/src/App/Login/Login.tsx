import React, { useState } from "react";
import { ArrowLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";
import backgroundImage from "../../assets/images/background.png";
import { loginPorEmailESenha } from "../../services/api"; // ajuste o caminho se necessário

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      setLoading(true);

      // 🔸 Chama sua API de login
      const usuario = await loginPorEmailESenha({ email, senha });
      // retorno esperado: { id: "...", nome: "..." }

      // 🔸 Salva os dados localmente
      localStorage.setItem("usuarioId", usuario.id);
      localStorage.setItem("userName", usuario.nome);

      alert(`Bem-vindo, ${usuario.nome}!`);
      navigate("/home");
    } catch (err) {
      console.error("Erro no login:", err);
      alert("Falha no login. Verifique suas credenciais.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        position: "absolute",
        top: "0px",
        left: "0px",
        width: "393px",
        height: "852px",
        display: "flex",
        flexDirection: "column",
        overflowY: "auto",
        boxSizing: "border-box",
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
        scrollbarWidth: "none",
        msOverflowStyle: "none",
      }}
    >
      {/* formulário */}
      <form
        onSubmit={handleLogin}
        style={{
          position: "absolute",
          left: "45px",
          bottom: "30px",
          display: "flex",
          flexDirection: "column",
          gap: "20px",
          width: "calc(100% - 90px)",
          maxWidth: "350px",
          zIndex: 2,
        }}
      >
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          style={{
            padding: "12px",
            borderRadius: "8px",
            border: "1px solid #ccc",
          }}
        />
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          required
          style={{
            padding: "12px",
            borderRadius: "8px",
            border: "1px solid #ccc",
          }}
        />
        <button
          type="submit"
          disabled={loading}
          style={{
            width: "100%",
            padding: "16px",
            backgroundColor: "#2563eb",
            color: "white",
            border: "none",
            borderRadius: "12px",
            fontSize: "16px",
            fontWeight: "600",
            cursor: "pointer",
          }}
        >
          {loading ? "Entrando..." : "Entrar com senha"}
        </button>
      </form>
    </div>
  );
};

export default Login;
