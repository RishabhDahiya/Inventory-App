import React, { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TextField,
} from "@mui/material";
import SideNav from "./Sidenav";
import Box from "@mui/material/Box";
import { Button } from "@mui/material";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import DemandElement from "./DemandElement";

export default function Demand() {
  const [open, setOpen] = useState(false);
  const [demandData, setDemandData] = useState([]);

  useEffect(() => {
    getDemandData();
  }, []);

  async function getDemandData() {
    const response = await fetch("http://localhost:8099/demand", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    console.log(data);
    setDemandData(data);
  }

  // async function deleteDemand(demandId) {
  //   const isConfirmed = window.confirm(
  //     "Are you sure you want to delete demand"
  //   );
  //   if (isConfirmed) {
  //     const response = await fetch(`http://localhost:8099/demand/${demandId}`, {
  //       method: "DELETE",
  //       headers: {
  //         "Content-Type": "application/json",
  //       },
  //     });
  //     const data = await response.json();
  //     console.log(data);
  //     window.location.reload(true);
  //   } else {
  //   }
  // }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
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
            DEMANDS
          </h1>
          <Paper sx={{ width: "100%", overflow: "hidden" }}>
            <TableContainer sx={{ maxHeight: 620 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
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
                      Demand Type
                    </TableCell>
                    <TableCell align="center" style={{ fontWeight: "bold" }}>
                      Quantity
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {demandData.map((demand) => (
                    <>
                      <DemandElement demand={demand} />
                      {/* <DemandElement demand={demand} /> */}
                      {/* <TableRow key={demand.id}>
                        <TableCell component="th" scope="row" align="center">
                          {demand.demandId}
                        </TableCell>
                        <TableCell align="center">{demand.itemId}</TableCell>
                        <TableCell align="center">
                          {demand.locationId}
                        </TableCell>
                        <TableCell align="center">
                          {demand.demandType}
                        </TableCell>
                        <TableCell align="center">{demand.quantity}</TableCell>
                        <TableCell align="center">
                          <Button
                            variant="contained"
                            color="primary"
                            size="small"
                            onClick={handleClickOpen}
                          >
                            Edit
                          </Button>
                        </TableCell>
                        <TableCell align="center">
                          <Button
                            variant="contained"
                            color="secondary"
                            size="small"
                            onClick={() => deleteDemand(demand.demandId)}
                          >
                            Delete
                          </Button>
                        </TableCell>
                      </TableRow>
                      <Dialog
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="alert-dialog-title"
                        aria-describedby="alert-dialog-description"
                      >
                        <DialogTitle id="alert-dialog-title">
                          {"Update Item"}
                        </DialogTitle>
                        <DialogContent>
                          <DialogContentText id="alert-dialog-description"></DialogContentText>
                          <form>
                            <TextField
                              autoFocus
                              margin="dense"
                              id="name"
                              label="Quantity"
                              type="text"
                              // value={quantity}
                              // onChange={(e) => {
                              //   setQuantity(e.target.value);
                              // }}
                              fullWidth
                              required
                            />
                          </form>
                        </DialogContent>
                        <DialogActions>
                          <Button onClick={handleClose}>Cancel</Button>
                          <Button onClick={handleClose} autoFocus>
                            Update
                          </Button>
                        </DialogActions>
                      </Dialog> */}
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
