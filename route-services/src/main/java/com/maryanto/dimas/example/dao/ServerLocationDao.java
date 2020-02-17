package com.maryanto.dimas.example.dao;

import com.maryanto.dimas.example.entity.ServerLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ServerLocationDao {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public Optional<ServerLocation> findByLocation(String location) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String query = "select id       as id,\n" +
                "       location as location,\n" +
                "       hostUrl  as url\n" +
                "from ServerLocations \n" +
                "where location = :location";
        params.addValue("location", location);
        try {
            ServerLocation server = this.namedJdbcTemplate.queryForObject(query, params, (resultSet, i) -> new ServerLocation(
                    resultSet.getString("id"),
                    resultSet.getString("location"),
                    resultSet.getString("url")
            ));
            return Optional.of(server);
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

}
