package com.fees.fee;

import com.fees.entity.FeeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FeeRepository extends PagingAndSortingRepository<FeeEntry, Integer> {

	@Query("SELECT c FROM FeeEntry c WHERE c.s_name LIKE %?1%")
	public Page<FeeEntry> search(String keyword, Pageable pageable);

	public Long countById(Integer id);

	public FeeEntry findByName(String name);

	public FeeEntry findByCode(String code);

//	@Query("UPDATE FeeEntry c SET c.enabled = ?2 WHERE c.id = ?1")
//	@Modifying
//	public void updateEnabledStatus(Integer id, boolean enabled);

	@Query("SELECT s FROM FeeEntry s ORDER BY s.id ASC")
	List<FeeEntry> findAll();
}
