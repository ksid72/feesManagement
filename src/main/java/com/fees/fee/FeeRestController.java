/*
package com.fees.fee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeeRestController {

	@Autowired
	private StudioService service;
	
	@PostMapping("/studios/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name,
			@Param("alias") String alias) {
		return service.checkUnique(id, name, alias);
	}
}
*/
