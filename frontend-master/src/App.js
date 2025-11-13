import React, { useState, useEffect } from "react";
import axios from "axios";
import Login from "./components/Login";
import RegistroTransaccion from "./components/RegistroTransaccion";
import TablaTransacciones from "./components/TablaTransacciones";
//import "./styles/App.css";

function App() {
  const [userLogged, setUserLogged] = useState(false);
  const [transacciones, setTransacciones] = useState([]);

  const fetchTransacciones = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/transacciones");
      setTransacciones(res.data);
    } catch (err) {
      console.error("Error al cargar transacciones:", err);
    }
  };

  useEffect(() => {
    if (userLogged) fetchTransacciones();
  }, [userLogged]);

  return (
    <div className="app-container">
      {!userLogged ? (
        <Login setUserLogged={setUserLogged} />
      ) : (
        <>
          <h1 className="titulo-dashboard">💎 Dashboard de Transacciones</h1>

          <RegistroTransaccion fetchTransacciones={fetchTransacciones} />
          <TablaTransacciones
            transacciones={transacciones}
            fetchTransacciones={fetchTransacciones}
          />
        </>
      )}
    </div>
  );
}

export default App;







