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
			var category1 = new Category();
			category1.setName("Aksesoris Fashion");
			categoryRestService.createRestCategory(category1);

			var category2 = new Category();
			category2.setName("Buku & Alat Tulis");
			categoryRestService.createRestCategory(category2);

			var category3 = new Category();
			category3.setName("Elektronik");
			categoryRestService.createRestCategory(category3);

			var category4 = new Category();
			category4.setName("Fashion Bayi & Anak");
			categoryRestService.createRestCategory(category4);

			var category5 = new Category();
			category5.setName("Fashion Muslim");
			categoryRestService.createRestCategory(category5);

			var category6 = new Category();
			category6.setName("Fotografi");
			categoryRestService.createRestCategory(category6);

			var category7 = new Category();
			category7.setName("Hobi & Koleksi");
			categoryRestService.createRestCategory(category7);

			var category8 = new Category();
			category8.setName("Jam Tangan");
			categoryRestService.createRestCategory(category8);
			
			var category9 = new Category();
			category9.setName("Perawatan & Kecantikan");
			categoryRestService.createRestCategory(category9);

			var category10 = new Category();
			category10.setName("Makanan & Minuman");
			categoryRestService.createRestCategory(category10);

			var category11 = new Category();
			category11.setName("Otomotif");
			categoryRestService.createRestCategory(category11);

			var category12 = new Category();
			category12.setName("Perlengkapan Rumah");
			categoryRestService.createRestCategory(category12);

			var category13 = new Category();
			category13.setName("Souvenir & Party Supplies");
			categoryRestService.createRestCategory(category13);
		};
	}
}
