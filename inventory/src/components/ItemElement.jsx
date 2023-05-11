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

export default function ItemElement(props) {
  const { item } = props;
  const [open, setOpen] = useState(false);

  const [itemId, setItemId] = useState(item.itemId);
  const [status, setStatus] = useState(item.status);
  const [price, setPrice] = useState(item.price);
  const [pickupAllowed, setPickupAllowed] = useState(item.pickupAllowed);
  const [shippingAllowed, setShippingAllowed] = useState(item.shippingAllowed);
  const [deliveryAllowed, setDeliveryAllowed] = useState(item.deliveryAllowed);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
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
      window.location.reload();
      console.log(data);
    } else {
    }
  }

  async function updateItem(event) {
    event.preventDefault();
    // hideBox();
    const response = await fetch(`http://localhost:8099/items/${itemId}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        status,
        price,
        pickupAllowed,
        shippingAllowed,
        deliveryAllowed,
      }),
    });
    const data = await response.json();
    window.location.reload(true);
  }
  return (
    <>
      <TableRow key={item.id}>
        <TableCell component="th" scope="row" align="center">
          {item.itemId}
        </TableCell>
        <TableCell align="center">{item.itemDescription}</TableCell>
        <TableCell align="center">{item.category}</TableCell>
        <TableCell align="center">{item.type}</TableCell>
        <TableCell align="center">{item.status}</TableCell>
        <TableCell align="center">{item.price}</TableCell>
        <TableCell align="center">{item.pickupAllowed.toString()}</TableCell>
        <TableCell align="center">{item.shippingAllowed.toString()}</TableCell>
        <TableCell align="center">{item.deliveryAllowed.toString()}</TableCell>
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
            onClick={() => deleteItem(item.itemId)}
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
              label="Status"
              type="text"
              value={status}
              onChange={(e) => {
                setStatus(e.target.value);
              }}
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="description"
              label="Price"
              type="text"
              value={price}
              onChange={(e) => {
                setPrice(e.target.value);
              }}
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Pickup Allowed"
              type="text"
              value={pickupAllowed}
              onChange={(e) => {
                setPickupAllowed(e.target.value);
              }}
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Shipping Allowed"
              type="text"
              value={shippingAllowed}
              onChange={(e) => {
                setShippingAllowed(e.target.value);
              }}
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Delivery Allowed"
              type="text"
              value={deliveryAllowed}
              onChange={(e) => {
                setDeliveryAllowed(e.target.value);
              }}
              fullWidth
              required
            />
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={updateItem} autoFocus>
            Update
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
