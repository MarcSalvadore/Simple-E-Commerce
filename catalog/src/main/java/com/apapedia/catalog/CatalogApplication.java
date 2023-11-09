package com.apapedia.catalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apapedia.catalog.model.Category;
import com.apapedia.catalog.restservice.CategoryRestService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class CatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(CategoryRestService categoryRestService) {
		return args -> {
			var categoryElektronik = new Category();
			categoryElektronik.setName("Gadget & Elektronik");
			categoryRestService.createRestCategory(categoryElektronik);

			var categoryFashion = new Category();
			categoryFashion.setName("Fashion & Kecantikan");
			categoryRestService.createRestCategory(categoryFashion);

			var categoryHarian = new Category();
			categoryHarian.setName("Kebutuhan Harian");
			categoryRestService.createRestCategory(categoryHarian);

			var categoryHobi = new Category();
			categoryHobi.setName("Mainan & Hobi");
			categoryRestService.createRestCategory(categoryHobi);

			var categoryDekorasi = new Category();
			categoryDekorasi.setName("Perlengkapan Rumah & Dekorasi");
			categoryRestService.createRestCategory(categoryDekorasi);
		};
	}
}
