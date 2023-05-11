import React, { useState } from "react";
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
import Dialog from "@mui/material/Dialog";
import { Button } from "@mui/material";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

export default function DemandElement(props) {
  const [open, setOpen] = useState(false);

  const { demand } = props;

  const [demandId, setDemandId] = useState(demand.demandId);
  const [quantity, setQuantity] = useState(demand.quantity);

  async function deleteDemand(demandId) {
    const isConfirmed = window.confirm(
      "Are you sure you want to delete demand"
    );
    if (isConfirmed) {
      const response = await fetch(`http://localhost:8099/demand/${demandId}`, {
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

  async function updateDemand(event) {
    if (demand.quantity !== quantity) {
      event.preventDefault();
      const response = await fetch(`http://localhost:8099/demand/${demandId}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          quantity,
        }),
      });
      //   const data = await response.json();
      //   window.location.reload(true);
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
      <TableRow key={demand.id}>
        <TableCell component="th" scope="row" align="center">
          {demand.demandId}
        </TableCell>
        <TableCell align="center">{demand.itemId}</TableCell>
        <TableCell align="center">{demand.locationId}</TableCell>
        <TableCell align="center">{demand.demandType}</TableCell>
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
          <Button onClick={updateDemand} autoFocus>
            Update
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
