package org.educa.airline.controllers;

import org.educa.airline.mappers.LuggageMapper;
import org.educa.airline.services.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;

public class LuggageController {
    private final LuggageService luggageService;

    private final LuggageMapper luggageMapper;

    @Autowired
    public LuggageController(LuggageService luggageService, LuggageMapper luggageMapper) {
        this.luggageService = luggageService;
        this.luggageMapper = luggageMapper;
    }

    //////////////////////////////////////////
    ///////////TODO
    /////////////////////////////////////////
}
