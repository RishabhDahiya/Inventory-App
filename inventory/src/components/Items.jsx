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
import ItemElement from "./ItemElement";

export default function Items() {
  const items = [
    {
      id: 1,
      name: "Item 1",
      category: "category",
      type: "type",
      status: "status",
      price: 10.99,
      pickupAllowed: "true",
      shippingAllowed: "true",
      deliveryAllowed: "true",
    },
    {
      id: 1,
      name: "Item 1",
      category: "category",
      type: "type",
      status: "status",
      price: 10.99,
      pickupAllowed: "true",
      shippingAllowed: "true",
      deliveryAllowed: "true",
    },
  ];
  const [itemsData, setItemsData] = useState([]);

  useEffect(() => {
    getItems();
  }, []);

  async function getItems() {
    const response = await fetch("http://localhost:8099/items", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    setItemsData(data);
  }
  async function deleteItem(itemId) {
    const isConfirmed = window.confirm("Are you sure you want to delete item");
    if (isConfirmed) {
      const response = await fetch(`http://localhost:8099/items/${itemId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });
      const data = await response.json();
      console.log(data);
      getItems();
    } else {
      getItems();
    }
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
            ITEMS
          </h1>
          <Paper sx={{ width: "100%", overflow: "hidden" }}>
            <TableContainer sx={{ maxHeight: 620 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead style={{ position: "sticky", top: "0" }}>
                  <TableRow>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      ID
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Name
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Category
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Type
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Status
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Price
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Pickup Allowed
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Shipping Allowed
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Delivery Allowed
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {itemsData.map((item) => (
                    <>
                    <ItemElement item={item}/>
                    </>
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
