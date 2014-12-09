package com.colobu.springmongo.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.colobu.springmongo.entity.Customer;


public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {

	List<Customer> findByLastname(String lastname, Sort sort);
	
	List<Customer> findByFirstname(String firstname, Sort sort);

	GeoResults<Customer> findByAddressLocationNear(Point point, Distance distance);
}