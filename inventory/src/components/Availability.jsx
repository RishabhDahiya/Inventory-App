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
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormHelperText from "@mui/material/FormHelperText";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";

export default function Availability() {
  const [itemIds, setItemIds] = useState(["10", "20", "30", "40"]);
  const [availabilityArray, setAvailabilityArray] = useState([
    {
      itemId: "10",
      locationId: "mumbai",
      quantity: "80",
    },
    {
      itemId: "20",
      locationId: "mumbai",
      quantity: "80",
    },
    {
      itemId: "30",
      locationId: "mumbai",
      quantity: "80",
    },
    {
      itemId: "40",
      locationId: "mumbai",
      quantity: "80",
    },
    {
      itemId: "10",
      locationId: "delhi",
      quantity: "80",
    },
    {
      itemId: "34",
      locationId: "mumbai",
      quantity: "80",
    },
    {
      itemId: "45",
      locationId: "mumbai",
      quantity: "80",
    },
  ]);

  useEffect(() => {
    getItems();
    getAvailabilityArray();
  }, []);
  async function getItems() {
    const response = await fetch("http://localhost:8099/items", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    let data = await response.json();
    console.log(data);
    setItemIds(data);
  }

  async function getAvailabilityArray() {
    const response = await fetch("http://localhost:8099/v1/availability", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    setAvailabilityArray(data);
  }
  const [itemId, setItemId] = useState("");

  const handleChange = (event) => {
    setItemId(event.target.value);
  };

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
            AVAILABILITY
          </h1>
          <Paper sx={{ width: "100%", overflow: "hidden" }}>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
              <InputLabel id="demo-simple-select-helper-label">
                ItemId
              </InputLabel>
              <Select
                labelId="demo-simple-select-helper-label"
                id="demo-simple-select-helper"
                value={itemId}
                label="ItemId"
                onChange={handleChange}
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                {itemIds.map((item) => (
                  <MenuItem value={item?.itemId}>{item?.itemId}</MenuItem>
                ))}
              </Select>
            </FormControl>
            <TableContainer sx={{ maxHeight: 620 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                    <TableCell align="center">Item ID</TableCell>
                    <TableCell align="center">Item Desc</TableCell>
                    <TableCell align="center">Location Id</TableCell>
                    <TableCell align="center">Location Desc</TableCell>
                    <TableCell align="center">Quantity</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {availabilityArray
                    .filter((avail) => avail.itemId === itemId)
                    .map((availability) => (
                      <TableRow key={availability.id}>
                        <TableCell component="th" scope="row" align="center">
                          {availability.itemId}
                        </TableCell>
                        <TableCell align="center">
                          {availability.itemDesc}
                        </TableCell>
                        <TableCell align="center">
                          {availability.locationId}
                        </TableCell>
                        <TableCell align="center">
                          {availability.locationDesc}
                        </TableCell>
                        <TableCell align="center">
                          {availability.quantity}
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
