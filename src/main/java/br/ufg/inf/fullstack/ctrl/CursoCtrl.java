package br.ufg.inf.fullstack.ctrl;

import java.util.List;
import java.util.Optional;

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


import br.ufg.inf.fullstack.ctrl.business.CursoBusiness;
import br.ufg.inf.fullstack.ctrl.exception.CursoException;
import br.ufg.inf.fullstack.model.entities.Curso;
import br.ufg.inf.fullstack.util.Message;

@RestController
@RequestMapping(value="/curso")
public class CursoCtrl {

  @Autowired
  private CursoBusiness business;

  @GetMapping
  public ResponseEntity<List<Curso>> findall(){
    HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		
    List<Curso> list = business.findall();
    if( list.size() == 0 ){ 
      status = HttpStatus.NO_CONTENT;
			headers.add("message", Message.get("0111"));
    }
    return new ResponseEntity<List<Curso>>(list, headers, status);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Curso> findByID(@PathVariable Integer id){

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.OK;
    Curso retorno = new Curso();
    try {
      
      retorno = business.findById(id);

    } catch (CursoException e) {
      headers.add("message", Message.get(e.getMessage()));
      status = HttpStatus.NO_CONTENT;
    } catch (Exception e) {
      headers.add("message", Message.get(e.getMessage()));
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return new ResponseEntity<Curso>(retorno, headers, status);

  }

  @GetMapping(value="/find/{str}")
	public ResponseEntity<List<Curso>> findByName(@PathVariable Optional<String> str){
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		
		List<Curso> list = business.findByNnCurso(str.get()); 
		if(list.size() == 0) {
			status = HttpStatus.NO_CONTENT;
			headers.add("message", Message.get("0111"));
		}
		return new ResponseEntity<List<Curso>>(list, headers, status);
	}

  @PostMapping
  public ResponseEntity<Curso> insert(@RequestBody Curso curso){
    HttpStatus status = HttpStatus.OK;
		HttpHeaders headers = new HttpHeaders();
	
    try {
      curso = business.insert(curso);
    } catch (CursoException e) {
      status = HttpStatus.BAD_REQUEST;
			headers.add("message", Message.get(e.getMessage()));
    } catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			headers.add("message", Message.get("0102"));
		}

    return new ResponseEntity<Curso>(curso, headers, status);
  }

  @DeleteMapping(value="/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id){
    HttpStatus status = HttpStatus.OK;
		HttpHeaders headers = new HttpHeaders();

    try {
			business.delete(id);
			headers.add("message", Message.get("0107"));
		}catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			headers.add("message", Message.get("0108"));
		}
		return new ResponseEntity<Void>(null, headers, status);

  }

  @PutMapping
  public ResponseEntity<Curso> update(@RequestBody Curso curso){
    
    HttpStatus status = HttpStatus.OK;
		HttpHeaders headers = new HttpHeaders();

    try {
      curso = business.update(curso);
      headers.add("message", Message.get("0105"));
    } catch (CursoException e) {
      status = HttpStatus.BAD_REQUEST;
			headers.add("message", Message.get(e.getMessage()));
    } catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			headers.add("message", Message.get("0106"));	
		}

    return new ResponseEntity<Curso>(curso, headers, status);
  }

  
}
