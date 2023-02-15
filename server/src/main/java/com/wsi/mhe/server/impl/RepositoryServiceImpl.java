package com.wsi.mhe.server.impl;

import com.wsi.mhe.server.api.RepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RepositoryServiceImpl implements RepositoryService {

    public RepositoryServiceImpl(){}

    private final String insertQuery = "INSERT INTO ITEM_ATTRIBUTE_TO_RMS (DIRECTION, MSG) VALUES ( ? , ?)";

    @Autowired
    @Qualifier("jdbcTemplateMCS")
    private JdbcTemplate jdbcTemplate;

    @Override
    public void logSocketMessage(String msg) throws DataAccessException {

            try {
                jdbcTemplate.update(insertQuery, "OUTBOUND", msg);
            } catch (DataAccessException e) {
                log.error("Error inserting outbound message from WMOS to Savoye to MCS table WM_SAVOYE_MSG_LOG: {}", msg);
                throw new DataAccessException("Error inserting MSG to microservices DB",e) {};
            }
        }

    }
