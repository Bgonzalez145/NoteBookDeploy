package com.main.repositorys;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.main.documents.Nota;

@Repository
public class NotaRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void crearNota(Nota nota) {
		mongoTemplate.insert(nota);
	}
	
	public void borrarNota(String idNota) {
		Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(idNota));
        mongoTemplate.remove(query, Nota.class);
	}
	
	public void actualizarNota(String idNota, Nota nota) {
		Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(idNota));
        Update update = new Update();
        update.set("titulo", nota.getTitulo());
        update.set("contenido", nota.getContenido());
        update.set("fecha", new Date());
        mongoTemplate.findAndModify(query, update, Nota.class);
	}
	
	public List<Nota> imprimirNotasUsuario(String nombreUsuario) {
	   Query query = new Query();
	   query.addCriteria(Criteria.where("nombreUsuario").is(nombreUsuario));
	   return mongoTemplate.find(query, Nota.class);
	}
	
	public List<Nota> imprimirNotas() {
       return mongoTemplate.findAll(Nota.class);
	}

	public Nota detalleNota(String idNota) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(idNota));
		return mongoTemplate.findOne(query, Nota.class);
	}
		
}
