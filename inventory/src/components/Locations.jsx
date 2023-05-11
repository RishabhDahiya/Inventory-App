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
import LocationItem from "./LocationElement";

export default function Locations() {
  const locations = [
    {
      id: 1,
      name: "location 1",
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
      name: "location 1",
      category: "category",
      type: "type",
      status: "status",
      price: 10.99,
      pickupAllowed: "true",
      shippingAllowed: "true",
      deliveryAllowed: "true",
    },
  ];
  const [locationData, setLocationData] = useState([]);

  useEffect(() => {
    getLocationData();
  }, []);

  async function getLocationData() {
    const response = await fetch("http://localhost:8099/locations", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    console.log(data);
    setLocationData(data);
  }
  async function deleteLocation(locationId) {
    const response = await fetch(
      `http://localhost:8099/locations/${locationId}`,
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    const data = await response.json();
    console.log(data);
    getLocationData();
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
            LOCATIONS
          </h1>
          <Paper sx={{ width: "100%", overflow: "hidden" }}>
            <TableContainer sx={{ maxHeight: 620 }}>
              <Table stickyHeader aria-label="sticky table">
              <TableHead>
                <TableRow>
                  <TableCell align="center" style={{ fontWeight: "bold" }}>
                    ID
                  </TableCell>
                  <TableCell align="center" style={{ fontWeight: "bold" }}>
                    Name
                  </TableCell>
                  <TableCell align="center" style={{ fontWeight: "bold" }}>
                    Type
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
                  <TableCell align="center" style={{ fontWeight: "bold" }}>
                    Address Line1
                  </TableCell>
                  <TableCell align="center" style={{ fontWeight: "bold" }}>
                    City
                  </TableCell>
                  <TableCell align="center" style={{ fontWeight: "bold" }}>
                    State
                  </TableCell>
                  <TableCell align="center" style={{ fontWeight: "bold" }}>
                    Country
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {locationData.map((location) => (
                  <LocationItem location={location}/>
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
