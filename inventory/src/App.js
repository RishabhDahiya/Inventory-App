import "./App.css";
import { Routes, Route, Navigate } from "react-router-dom";
import SideNav from "./components/Sidenav";
import Items from "./components/Items";
import Locations from "./components/Locations";
import Supply from "./components/Supply";
import Demand from "./components/Demand";
import Transactions from "./components/Transactions";
import Availability from "./components/Availability";

function App() {
  return (
    <>
      <Routes>
        <Route path="/items" element={<Items />}></Route>
        <Route path="/locations" element={<Locations />}></Route>
        <Route path="/supply" element={<Supply />}></Route>
        <Route path="/demand" element={<Demand />}></Route>
        <Route path="/availability" element={<Availability />}></Route>
        <Route path="/transactions" element={<Transactions />}></Route>
        <Route path="*" element={<Navigate to="/items" replace />} />
      </Routes>
    </>
  );
}

export default App;
