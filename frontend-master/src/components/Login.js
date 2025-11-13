import React, { useState } from "react";
import axios from "axios";
import "../styles/login.css";

const Login = ({ setUserLogged }) => {
  const [usuario, setUsuario] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/api/login", {
        usuario,
        password,
      });

      // Ahora revisamos el campo "mensaje" en lugar de "status"
      if (response.data.mensaje === "Login exitoso") {
        alert("✅ Inicio de sesión correcto");
        setUserLogged(true);
      } else {
        alert("⚠️ Usuario o contraseña incorrectos");
      }
    } catch (error) {
      alert("❌ Error al conectar con el servidor");
      console.error(error);
    }
  };

  return (
    <div className="login-container">
      <form onSubmit={handleSubmit} className="login-form">
        <h2 className="login-title">Acceso</h2>

        <div className="input-group">
          <input
            type="text"
            placeholder="Usuario"
            value={usuario}
            onChange={(e) => setUsuario(e.target.value)}
            required
          />
        </div>

        <div className="input-group">
          <input
            type="password"
            placeholder="Contraseña"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button type="submit" className="login-button">
          Entrar
        </button>
      </form>
    </div>
  );
};

export default Login;








