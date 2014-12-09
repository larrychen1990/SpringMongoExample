package com.colobu.springmongo;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;

import com.colobu.springmongo.config.MongoConfig;
import com.colobu.springmongo.entity.Address;
import com.colobu.springmongo.entity.Customer;
import com.colobu.springmongo.repository.CustomerRepository;


public class App {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);
		CustomerRepository repository = context.getBean(CustomerRepository.class);
		
		repository.deleteAll();
		Customer dave, oliver, carter;
		dave = repository.save(new Customer("Dave", "Matthews"));
		oliver = repository.save(new Customer("Oliver August", "Matthews"));
		carter = repository.save(new Customer("Carter", "Beauford"));
		
		List<Customer> result = repository.findByLastname("Matthews", new Sort(Direction.ASC, "firstname"));
		System.out.println("Find by lastname Matthews: "+result.size());
		for(Customer customer:result){
			System.out.println(customer.getFirstname()+" "+customer.getLastname());
		}
		System.out.println();
		
		List<Customer> resultByFirstname = repository.findByFirstname("Oliver August", new Sort(Direction.ASC, "firstname"));
		System.out.println("Find by firstname Oliver August: "+resultByFirstname.size());
		for(Customer customer:resultByFirstname){
			System.out.println(customer.getFirstname()+" "+customer.getLastname());
		}
		System.out.println();
		
		GeospatialIndex indexDefinition = new GeospatialIndex("address.location");
		indexDefinition.getIndexOptions().put("min", -180);
		indexDefinition.getIndexOptions().put("max", 180);
		mongoTemplate.indexOps(Customer.class).ensureIndex(indexDefinition);
		
		Customer ollie = new Customer("Oliver", "Gierke");
		ollie.setAddress(new Address(new Point(52.52548, 13.41477)));
		ollie = repository.save(ollie);
		Point referenceLocation = new Point(52.51790, 13.41239);
		Distance oneKilometer = new Distance(1, Metrics.KILOMETERS);
		GeoResults<Customer> nearResult = repository.findByAddressLocationNear(referenceLocation, oneKilometer);
		System.out.println("Find "+nearResult.getContent().size()+" by address near : "+referenceLocation.toString());
		
		Iterator<GeoResult<Customer>> it = nearResult.iterator();
		while(it.hasNext()){
			GeoResult<Customer> res = it.next();
			Customer content = res.getContent();
			Distance distance = res.getDistance();
			System.out.println(content.getFirstname()+" "+content.getLastname()
					+" at "+content.getAddress().getLocation().toString()+" near "+distance.toString());
		}
		
	}
}
