import React from "react";
import axios from "axios";
import "../styles/TablaTransacciones.css";

export default function TablaTransacciones({ transacciones, fetchTransacciones }) {
  const cancelarTransaccion = async (id) => {
    if (!window.confirm("¿Seguro que deseas cancelar esta transacción?")) return;

    try {
      await axios.put(`http://localhost:8080/api/transacciones/${id}/cancelar`);
      alert("✅ Transacción cancelada correctamente");
      fetchTransacciones();
    } catch (error) {
      console.error("Error al cancelar transacción:", error);
      alert("⚠️ No se pudo cancelar la transacción");
    }
  };

  return (
    <div className="tabla-container">
      <h2>📊 Tabla de Transacciones</h2>

      <table className="tabla-transacciones">
        <thead>
          <tr>
            <th>ID</th>
            <th>Operación</th>
            <th>Cliente</th>
            <th>Importe</th>
            <th>Referencia</th>
            <th>Estatus</th>
            <th>Acción</th>
          </tr>
        </thead>
        <tbody>
          {transacciones.length > 0 ? (
            transacciones.map((t) => (
              <tr
                key={t.id}
                className={t.estatus === "Cancelada" ? "cancelada" : "aprobada"}
              >
                <td>{t.id}</td>
                <td>{t.operacion}</td>
                <td>{t.cliente}</td>
                <td>${t.importe}</td>
                <td>{t.referencia}</td>
                <td>{t.estatus}</td>
                <td>
                  {t.estatus === "Aprobada" ? (
                    <button
                      onClick={() => cancelarTransaccion(t.id)}
                      className="btn-cancelar"
                    >
                      ❌ Cancelar
                    </button>
                  ) : (
                    <span className="no-accion">—</span>
                  )}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="7" className="sin-registros">
                No hay transacciones registradas
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}





