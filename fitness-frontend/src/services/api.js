import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8084/api", // API Gateway
});

export default API;