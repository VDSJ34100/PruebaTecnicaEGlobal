import axios from "axios";

const BASE_URL = "http://localhost:8080/api/operaciones";
const TRANS_URL = "http://localhost:8080/api/transacciones";

export async function enviarOperacion(datos) {
  return await axios.post(BASE_URL, datos);
}

export async function obtenerTransacciones() {
  return await axios.get(TRANS_URL);
}

export async function cancelarTransaccion(referencia) {
  return await axios.patch(TRANS_URL, { referencia });
}
