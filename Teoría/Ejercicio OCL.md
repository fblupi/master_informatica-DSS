# DSS - Especificación de operaciones con OCL

> Francisco Javier Bolívar Lupiáñez

### Especificar completamente la operación `IHotelMgt::makeReservation(...)` con OCL; utilizando los operadores: `exists`, `select` y `asSequence->first`.

```
context: IHotelMgt::makeReservation(in_res: ReservationDetails, in_cus: CustId,
                                    out_resRef: String): boolean
pre:
  -- cus es un identificador valido de cliente
  customer->exists(c | c.id = in_cus) and
  -- el hotel y la habitacion de la reserva son validos
  hotel->exists(h | h.id = in_res.hotel and h.room.roomType = in_res.roomType)
post:
  result implies
    -- encontrar el hotel
    Let elHotel = hotel->select(h | h.id = in_res.hotel)->asSequence->first in
      -- solo una reserva más que antes
      (elHotel.reservation - elHotel.reservation@pre)->size = 1 and
      -- encontrar la reserva
      Let laReserva = (elHotel.reservation - elHotel.reservation@pre)->asSequence->first in
        -- comprueba atributos de la nueva reserva
        laReserva.resRef = out_resRef and
        laReserva.claimed = false and
        laReserva.dates = in_res.dates and
        laReserva.roomType = in_res.roomType and
        laReserva.customer.id = in_cus
```
