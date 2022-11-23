package com.sb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.exception.ResourceNotFoundException;
import com.sb.model.Item1;
import com.sb.repo.ItemRepo;
import com.sb.service.ItemService;

@RestController
@RequestMapping("/itemApi")

public class ItemController {


	@Autowired

	ItemService is;

	@Autowired
	ItemRepo ir;



	@PostMapping("/create")
	public ResponseEntity<Item1> createTutorial(@Valid @RequestBody Item1 item) {


		try {
			Item1 y = is.m1(item);
			return new ResponseEntity<>(y, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}



	@GetMapping("/item1/{id}")
	public ResponseEntity < Item1 > getItemsById
	(@PathVariable(value = "id") Long ItemsId)
			throws ResourceClosedException {
		Item1 item =ir.findById(ItemsId)
				.orElseThrow(() -> new ResourceClosedException("Your Entered Employee Number is not available in Database,Could you please try with other Emp Number :: " + ItemsId));
		return ResponseEntity.ok().body(item);
	}


	@GetMapping("/its")

	public ResponseEntity<List<Item1>> getAllProducts(@RequestParam(required = false) String name)
			throws ResourceNotFoundException {

		List<Item1> items = new ArrayList<Item1>();

		if (name == null)
			ir.findAll().forEach(items::add);
		else
			ir.findByNameContaining(name).forEach(items::add);

		if (items.isEmpty()) {
			throw new ResourceNotFoundException("NO Items are found");
		}

		return new ResponseEntity<>(items, HttpStatus.OK);
	}



	@PatchMapping("/iteam2/{id}/{cost}")
	public ResponseEntity<Item1> updateIteamPartially(@PathVariable Long id, @PathVariable Integer cost) {
		try {
			Item1 item = ir.findById(id).get();
			item.setIcost(cost);
			return new ResponseEntity<Item1>(ir.save(item), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
