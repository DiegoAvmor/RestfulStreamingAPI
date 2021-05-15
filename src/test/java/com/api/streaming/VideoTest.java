package com.api.streaming;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

import com.api.streaming.exception.NotFoundException;
import com.api.streaming.model.Video;
import com.api.streaming.service.impl.VideoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VideoTest {

	@Autowired
	VideoServiceImpl videoServiceImpl;

	@Test
	public void testNoSearchResult(){
		assertThrows(NotFoundException.class, ()-> videoServiceImpl.searchVideos("Invalid Name"));
	}

	@Test
	public void testSearchResult(){
		List<Video> videos = videoServiceImpl.searchVideos("Video");
		assertFalse(videos.isEmpty(),"No se encontro resultados a pesar de que existen recursos que cumplen el criterio para la busqueda");
	}

	@Test
	public void testInvalidQuery(){
		try {
			videoServiceImpl.searchVideos("1;1--");
		} catch (Exception e) {
			assertFalse(e instanceof SQLSyntaxErrorException, "Lanzo excepción de sintaxis de SQL");
		}
	}


}
