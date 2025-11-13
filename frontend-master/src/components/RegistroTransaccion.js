import React, { useState } from "react";
import axios from "axios";
import "../styles/RegistroTransaccion.css";
import sha256 from "crypto-js/sha256"; // npm install crypto-js

export default function RegistroTransaccion({ fetchTransacciones }) {
  const [operacion, setOperacion] = useState("");
  const [importe, setImporte] = useState("");
  const [cliente, setCliente] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!operacion || !importe || !cliente) {
      alert("⚠️ Todos los campos son obligatorios");
      return;
    }

    try {
      setLoading(true);

      // Calculamos la firma SHA-256 igual que en el backend
      const firma = sha256(operacion + importe + cliente).toString();

      await axios.post("http://localhost:8080/api/operacion/procesar", {
        operacion,
        importe,
        cliente,
        firma,
      });

      alert("✅ Transacción registrada con éxito");
      setOperacion("");
      setImporte("");
      setCliente("");
      fetchTransacciones();
    } catch (error) {
      console.error(error);
      alert("⚠️ Error al registrar transacción");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="registro-container">
      <h2>Registrar Transacción</h2>
      <form onSubmit={handleSubmit} className="registro-form">
        <input
          type="text"
          placeholder="Operación"
          value={operacion}
          onChange={(e) => setOperacion(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Importe"
          value={importe}
          onChange={(e) => setImporte(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Cliente"
          value={cliente}
          onChange={(e) => setCliente(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? "Guardando..." : "💾 Enviar"}
        </button>
      </form>
    </div>
  );
}





