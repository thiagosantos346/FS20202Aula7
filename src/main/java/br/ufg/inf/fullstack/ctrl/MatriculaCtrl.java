package br.ufg.inf.fullstack.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufg.inf.fullstack.ctrl.business.MatriculaBusiness;
import br.ufg.inf.fullstack.ctrl.exception.MatriculaException;
import br.ufg.inf.fullstack.model.entities.Matricula;
import br.ufg.inf.fullstack.util.Message;

@RestController
@RequestMapping(value="/matriculas")
public class MatriculaCtrl {
	
	@Autowired
	private MatriculaBusiness business;
	
	@GetMapping
	public ResponseEntity<List<Matricula>> findAll() {
		List<Matricula> list = business.findall();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Matricula> findById(@PathVariable Integer id){
		HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    Matricula retorno = new Matricula();

		try {
			retorno = business.findById(id);
		} catch (MatriculaException e) {
			headers.add("message", Message.get(e.getMessage()));
      status = HttpStatus.NO_CONTENT;
		} catch (Exception e) {
      headers.add("message", Message.get(e.getMessage()));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
		return new ResponseEntity<Matricula>(retorno, headers, status);
	}
	
	@PostMapping
	public ResponseEntity<Matricula> insert(@RequestBody Matricula matricula){
		HttpStatus status = HttpStatus.OK;
		HttpHeaders headers = new HttpHeaders();

		try {
			matricula = business.insert(matricula);
		} catch (MatriculaException e) {
			status = HttpStatus.BAD_REQUEST;
			headers.add("message", Message.get(e.getMessage()));
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			headers.add("message", Message.get("0102"));
		}

		return  new ResponseEntity<Matricula>(matricula, headers, status);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		HttpStatus status = HttpStatus.OK;
		HttpHeaders headers = new HttpHeaders();

		try {
			business.delete(id);
			headers.add("message", Message.get("0107"));
		} catch (MatriculaException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(null, headers, status);
	}
	
	@PutMapping
	public ResponseEntity<Matricula> update(@RequestBody Matricula matricula){
		HttpStatus status = HttpStatus.OK;
		HttpHeaders headers = new HttpHeaders();

    try {
      matricula = business.update(matricula);
      headers.add("message", Message.get("0105"));
    } catch (MatriculaException e) {
      status = HttpStatus.BAD_REQUEST;
			headers.add("message", Message.get(e.getMessage()));
    } catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			headers.add("message", Message.get("0106"));	
		}

    return new ResponseEntity<Matricula>(matricula, headers, status);
	}
	
}
