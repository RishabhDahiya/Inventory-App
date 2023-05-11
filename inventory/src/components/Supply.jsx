import React, { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import SideNav from "./Sidenav";
import Box from "@mui/material/Box";
import { Button } from "@mui/material";
import SupplyElement from "./SupplyElement";

export default function Supply() {
  const [supplyData, setSupplyData] = useState([]);

  useEffect(() => {
    getSupply();
  }, []);

  async function getSupply() {
    const response = await fetch("http://localhost:8099/supply", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    setSupplyData(data);
  }



  return (
    <>
      <Box sx={{ display: "flex" }}>
        <SideNav></SideNav>
        <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
          <h1
            style={{
              textAlign: "center",
              color: "navy",
              fontSize: "2rem",
              marginBottom: "1rem",
            }}
          >
            SUPPLY
          </h1>
          <Paper sx={{ width: "100%", overflow: "hidden" }}>
            <TableContainer sx={{ maxHeight: 620 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Supply Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Item Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Location Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Supply Type
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Quantity
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {supplyData.map((supply) => (
                    <SupplyElement supply={supply}/>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Paper>
        </Box>
      </Box>
    </>
  );
}
