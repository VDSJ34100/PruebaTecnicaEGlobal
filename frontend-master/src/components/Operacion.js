import React, { useState } from "react";
import axios from "axios";
import { calcularSHA256 } from "../utils/sha256";

const Operacion = () => {
  const [operacion, setOperacion] = useState("");
  const [importe, setImporte] = useState("");
  const [cliente, setCliente] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const firma = await calcularSHA256(operacion + importe + cliente);

      const response = await axios.post("http://localhost:8080/api/operacion/procesar", {
        operacion,
        importe,
        cliente,
        firma
      });

      if (response.data.id) {
        alert(`✅ Transacción registrada. Referencia: ${response.data.referencia}`);
        setOperacion("");
        setImporte("");
        setCliente("");
      } else {
        alert("⚠️ Error en la transacción");
      }
    } catch (error) {
      console.error(error);
      alert("❌ Error al procesar la transacción");
    }
  };

  return (
    <div>
      <h2>Registrar Operación</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Operación"
          value={operacion}
          onChange={(e) => setOperacion(e.target.value)}
          required
        />
        <input
          type="text"
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
        <button type="submit">Enviar</button>
      </form>
    </div>
  );
};

export default Operacion;
