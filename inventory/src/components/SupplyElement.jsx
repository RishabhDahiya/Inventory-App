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
import { Button } from "@mui/material";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

export default function SupplyElement(props) {
  const [open, setOpen] = useState(false);
  const { supply } = props;

  const [supplyId, setSupplyId] = useState(supply.supplyId);
  const [quantity, setQuantity] = useState(supply.quantity);

  async function deleteSupply(supplyId) {
    const isConfirmed = window.confirm(
      "Are you sure you want to delete supply"
    );
    if (isConfirmed) {
      const response = await fetch(`http://localhost:8099/supply/${supplyId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });
      const data = await response.json();
      console.log(data);
      window.location.reload(true);
    } else {
    }
  }

  async function updateSupply(event) {
    if (supply.quantity !== quantity) {
      event.preventDefault();
      const response = await fetch(`http://localhost:8099/supply/${supplyId}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          quantity,
        }),
      });
      if (response.ok) {
        const data = await response.json();
        alert("Demand updated successfully.");
        window.location.reload(true);
      } else {
        const errorData = await response.json();
        alert(`Failed to update demand: ${errorData.message}`);
      }
    }
    handleClose();
  }
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  return (
    <>
      <TableRow key={supply.id}>
        <TableCell component="th" scope="row" align="center">
          {supply.supplyId}
        </TableCell>
        <TableCell align="center">{supply.itemId}</TableCell>
        <TableCell align="center">{supply.locationId}</TableCell>
        <TableCell align="center">{supply.supplyType}</TableCell>
        <TableCell align="center">{supply.quantity}</TableCell>
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
            onClick={() => deleteSupply(supply.supplyId)}
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
        <DialogTitle id="alert-dialog-title">{"Update Item"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description"></DialogContentText>
          <form>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              label="Quantity"
              type="text"
              value={quantity}
              onChange={(e) => {
                setQuantity(e.target.value);
              }}
              fullWidth
              required
            />
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={updateSupply} autoFocus>
            Update
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
