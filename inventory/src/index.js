import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider, createTheme } from "@mui/material/styles";
const theme = createTheme({
  palette: {
    primary: {
      main: "#2a9461"
    }
  }
});
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  // <ThemeProvider theme={theme}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  // </ThemeProvider>
);
