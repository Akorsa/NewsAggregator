package ru.akorsa.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akorsa.test.ESrepository.ItemESRepository;

@Service
public class ESItemService {

    @Autowired
    ItemESRepository itemESRepository;
}
