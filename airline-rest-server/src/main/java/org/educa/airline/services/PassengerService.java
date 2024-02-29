package org.educa.airline.services;

import org.educa.airline.entity.Flight;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.NotExistFlight;
import org.educa.airline.exceptions.NotExistPassenger;
import org.educa.airline.repository.inmemory.InMemoryPassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PassengerService {
    private final InMemoryPassengerRepository inMemoryPassengerRepository;
    private final FlightService flightService;


    @Autowired
    public PassengerService(InMemoryPassengerRepository inMemoryPassengerRepository, FlightService flightService) {
        this.inMemoryPassengerRepository = inMemoryPassengerRepository;
        this.flightService = flightService;
    }


    public boolean create(String id, Passenger passenger) throws NotExistFlight {
        if (flightService.existFlight(id)) {
            return inMemoryPassengerRepository.addPassenger(passenger);
        } else {
            throw new NotExistFlight();
        }

    }

    public boolean update(Passenger passenger, String id, String nif) throws Exception {

        if (inMemoryPassengerRepository.existPassenger(id, nif)) {
            inMemoryPassengerRepository.updatePassenger(nif, passenger);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(String id, String nif) throws NotExistFlight, NotExistPassenger {
        if (flightService.existFlight(id)) {
            if (exitPassenger(id, nif)) {
                return inMemoryPassengerRepository.deletePassenger(id, nif);
            } else {
                throw new NotExistPassenger();
            }

        } else {
            throw new NotExistFlight();
        }
    }

    public List<Passenger> findAllPassangerOfFlight(String id) throws NotExistFlight {
        if (flightService.existFlight(id)) {
            return inMemoryPassengerRepository.listPassengers(id);
        } else {
            throw new NotExistFlight();
        }

    }

    public void passengerGetFlight(String code, Date date, Passenger passenger) throws Exception {
        Flight fligthFind = flightService.findFlightByIdDate(code, date);
        passenger.setFlightId(fligthFind.getId());
    }

    public boolean exitPassenger(String id, String nif) {
        return inMemoryPassengerRepository.existPassenger(id, nif);
    }

    public Passenger findOnePassanger(String id, String nif) throws NotExistPassenger, NotExistFlight {
        if (flightService.existFlight(id)) {
            if (exitPassenger(id, nif)) {
                return inMemoryPassengerRepository.getPassenger(id, nif);
            } else {
                throw new NotExistPassenger();
            }
        } else {
            throw new NotExistFlight();
        }

    }

}
