package bj.highfive.springbootdemo;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // ceci est signifie que cette classe est un controller 
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api") // Ceci signifie que l'url commence par /api (après l'url de l'application)
public class AlbumController {
    @Autowired // ceci demande à java d'injecter le "bean" AlbumRepository
    AlbumRepository albumRepository; // instance du bean AlbumRepository

	//Create new album
	@PostMapping("/album")
	public @ResponseBody Album createAlbum(@RequestBody Album album) {
		return this.albumRepository.save(album);
	}

	@PostMapping("/albums")
	public @ResponseBody Iterable<Album> createAlbums(@RequestBody Iterable<Album> albums) {
		return this.albumRepository.saveAll(albums);
	}

	// Read album
    @GetMapping("/albums")
	public @ResponseBody Iterable<Album> getAlbums() {
		// return generateAlbums();
        return this.albumRepository.findAll();
	}

	/**
	 * Comment : 
	 * RequestParmaeter : monsite.fr?id=1
	 * PathVariable : monsite.fr/1
	 * @param id
	 * @return
	 */

	@GetMapping("/album/{id}")
	public @ResponseBody Album getAlbumById(@PathVariable Long id) { // avant @RequestParam
		return this.albumRepository.findById(id).orElse(null);
	}

	// Update 
	@PutMapping("/album")
	public @ResponseBody Album updateAlbum(@RequestBody Album album) {
		Album existingAlbum = this.albumRepository.findById(album.getId()).orElse(null);

		existingAlbum.setRef(album.getRef());
		existingAlbum.setName(album.getName());
		existingAlbum.setTitle(album.getTitle());
		existingAlbum.setDescription(album.getDescription());
		existingAlbum.setDuration(album.getDuration());
		existingAlbum.setStatus(album.getStatus());
		existingAlbum.setUrl(album.getUrl());
		existingAlbum.setTags(album.getTags());

		return this.albumRepository.save(album);
	}

	// Delete 
	@DeleteMapping("/album/{id}")
	public @ResponseBody String deleteAlbum(@PathVariable Long id) {
		this.albumRepository.deleteById(id);

		return "Album Supprimé : " + id;
	}
}
