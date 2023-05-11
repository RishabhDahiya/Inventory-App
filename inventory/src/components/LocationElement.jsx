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
import { Button } from "@mui/material";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

export default function LocationElement(props) {
  const { location } = props;
  const [open, setOpen] = useState(false);

  const [locationId, setLocationId] = useState(location.locationId);
  const [locationDesc, setLocationDesc] = useState(location.locationDesc);
  const [locationType, setLocationType] = useState(location.locationType);
  const [pickupAllowed, setPickupAllowed] = useState(
    location.pickupAllowed.toString()
  );
  const [shippingAllowed, setShippingAllowed] = useState(
    location.shippingAllowed.toString()
  );
  const [deliveryAllowed, setDeliveryAllowed] = useState(
    location.deliveryAllowed.toString()
  );
  const [addressLine1, setAddressLine1] = useState(location.addressLine1);
  const [addressLine2, setAddressLine2] = useState(location.addressLine2);
  const [addressLine3, setAddressLine3] = useState(location.addressLine3);

  const [city, setCity] = useState(location.city);
  const [state, setState] = useState(location.state);
  const [country, setCountry] = useState(location.country);
  const [pincode, setPincode] = useState(location.pincode);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  async function deleteLocation(locationId) {
    const isConfirmed = window.confirm(
      "Are you sure you want to delete location"
    );
    if (isConfirmed) {
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
      window.location.reload();
      console.log(data);
    } else {
    }
  }

  async function updateLocation(event) {
    event.preventDefault();
    const response = await fetch(
      `http://localhost:8099/locations/${locationId}`,
      {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          locationDesc,
          locationType,
          pickupAllowed,
          shippingAllowed,
          deliveryAllowed,
          addressLine1,
          addressLine2,
          addressLine3,
          city,
          state,
          country,
          pincode,
        }),
      }
    );
    const data = await response.json();
    window.location.reload(true);
  }
  return (
    <>
      <TableRow key={location.id}>
        <TableCell component="th" scope="row" align="center">
          {location.locationId}
        </TableCell>
        <TableCell align="center">{location.locationDesc}</TableCell>
        <TableCell align="center">{location.locationType}</TableCell>
        <TableCell align="center">
          {location.pickupAllowed.toString()}
        </TableCell>
        <TableCell align="center">
          {location.shippingAllowed.toString()}
        </TableCell>
        <TableCell align="center">
          {location.deliveryAllowed.toString()}
        </TableCell>
        <TableCell align="center">{location.addressLine1}</TableCell>
        <TableCell align="center">{location.city}</TableCell>
        <TableCell align="center">{location.state}</TableCell>
        <TableCell align="center">{location.country}</TableCell>
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
            onClick={() => deleteLocation(location.locationId)}
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
              label="Location Description"
              type="text"
              value={locationDesc}
              onChange={(e) => {
                setLocationDesc(e.target.value);
              }}
              fullWidth
              required
            />
            <TextField
              autoFocus
              margin="dense"
              id="name"
              label="Location Type"
              type="text"
              value={locationType}
              onChange={(e) => {
                setLocationType(e.target.value);
              }}
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="description"
              label="Pickup Allowed"
              value={pickupAllowed}
              onChange={(e) => {
                setPickupAllowed(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Shipping Allowed"
              value={shippingAllowed}
              onChange={(e) => {
                setShippingAllowed(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Delivery Allowed"
              value={deliveryAllowed}
              onChange={(e) => {
                setDeliveryAllowed(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Address Line1"
              value={addressLine1}
              onChange={(e) => {
                setAddressLine1(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Address Line2"
              value={addressLine2}
              onChange={(e) => {
                setAddressLine2(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Address Line3"
              value={addressLine3}
              onChange={(e) => {
                setAddressLine3(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="City"
              value={city}
              onChange={(e) => {
                setCity(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="State"
              value={state}
              onChange={(e) => {
                setState(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Country"
              value={country}
              onChange={(e) => {
                setCountry(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
            <TextField
              margin="dense"
              id="price"
              label="Pincode"
              value={pincode}
              onChange={(e) => {
                setPincode(e.target.value);
              }}
              type="text"
              fullWidth
              required
            />
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={updateLocation} autoFocus>
            Update
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
