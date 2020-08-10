package com.main.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.main.security.document.ImageModel;


@Repository
public class ImageRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void crearImagen(ImageModel imagen) {
		mongoTemplate.insert(imagen);
	}
	
	public boolean existeImagen(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		if(mongoTemplate.findOne(query, ImageModel.class) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public ImageModel obtenerImagen(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return mongoTemplate.findOne(query, ImageModel.class);
	}
	
	public void borrarImagen(String name) {
		Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        mongoTemplate.remove(query, ImageModel.class);
	}
	

}
