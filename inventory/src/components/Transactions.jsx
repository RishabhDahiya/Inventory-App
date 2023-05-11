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

export default function Transactions() {
  const [transactionArray, setTransactionArray] = useState([]);
  useEffect(() => {
    getTransactionArray();
  }, []);

  async function getTransactionArray() {
    const response = await fetch("http://localhost:8099/transaction", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    setTransactionArray(data);
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
            TRANSACTIONS
          </h1>
          <Paper sx={{ width: "100%", overflow: "hidden" }}>
            <TableContainer sx={{ maxHeight: 620 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Transaction Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Supply Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Demand Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Item Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Location Id
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Previous Quantity
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Updated Quantity
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {transactionArray.map((transaction) => (
                    <TableRow key={transaction.transactionId}>
                      <TableCell component="th" scope="row" align="center">
                        {transaction.transactionId}
                      </TableCell>
                      <TableCell component="th" scope="row" align="center">
                        {transaction.supplyId}
                      </TableCell>
                      <TableCell component="th" scope="row" align="center">
                        {transaction.demandId}
                      </TableCell>
                      <TableCell align="center">{transaction.itemId}</TableCell>
                      <TableCell align="center">
                        {transaction.locationId}
                      </TableCell>
                      <TableCell align="center">
                        {transaction.previousQuantity}
                      </TableCell>
                      <TableCell align="center">
                        {transaction.newQuantity}
                      </TableCell>
                    </TableRow>
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
