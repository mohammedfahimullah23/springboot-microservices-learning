package com.example.Study.Hall.Management.repository;

import com.example.Study.Hall.Management.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRespository extends MongoRepository<Admin, String> {

    public Admin findByUsername(String name);
}
