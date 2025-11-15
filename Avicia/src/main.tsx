import { createRoot } from "react-dom/client";
import React from 'react';
import App from "./App.tsx";
import "./index.css";
import { AuthProvider } from "@/context/AuthContext";

const container = document.getElementById("root");

if (container) {
  const root = createRoot(container);
  root.render(
    <React.StrictMode> 
      <AuthProvider>
        <App /> 
      </AuthProvider>
    </React.StrictMode>
  );
} else {
  console.error("ERRO FATAL: Elemento com id='root' não encontrado no index.html");
}