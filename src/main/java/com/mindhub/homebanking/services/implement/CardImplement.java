package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardImplement implements CardService {

    @Autowired
    CardRepository cardRepository;
}
