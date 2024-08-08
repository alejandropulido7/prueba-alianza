package com.co.backend.domain.usecase.clients;

import com.co.backend.domain.model.clients.Client;
import com.co.backend.infraestructure.adapters.r2dbc.clients.ClientsDao;

public class TransformerClientDaoToClient {

    public static Client convert (ClientsDao clientsDao){
        Client client = new Client();
        client.setSharedKey(clientsDao.getShared_key());
        client.setBusinessId(clientsDao.getBusiness_id());
        client.setEmail(clientsDao.getEmail());
        client.setPhone(clientsDao.getPhone());
        client.setDataAdded(clientsDao.getStart_date());

        return client;
    }
}
