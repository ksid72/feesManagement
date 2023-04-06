/*
package com.fees.fee;

import com.producing.common.entity.Category;
import com.producing.common.entity.studio.Studio;
import com.producing.common.entity.studio.StudioAddress;
import com.producing.common.exception.StudioNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class StudioService {
	public static final int ROOT_STUDIOS_PER_PAGE = 4;
	
	@Autowired
	private FeeRepository repo;
	@Autowired
	private StudioAddressRepository repoAdd;
	
	public List<Studio> listByPage(StudioPageInfo pageInfo, int pageNum, String sortDir,
								   String keyword) {
		Sort sort = Sort.by("name");
		
		if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort = sort.descending();
		}
		
		Pageable pageable = PageRequest.of(pageNum - 1, ROOT_STUDIOS_PER_PAGE, sort);
		
		Page<Studio> pageCategories = null;
		
		if (keyword != null && !keyword.isEmpty()) {
			pageCategories = repo.search(keyword, pageable);	
		} else {
			pageCategories = repo.findAll(pageable);
		}

		pageInfo.setTotalElements(pageCategories.getTotalElements());
		pageInfo.setTotalPages(pageCategories.getTotalPages());
			
		return pageCategories.getContent();

	}

	public List<Studio> listofStudios(){
		return repo.findAll();
	}

	public Studio save(Studio studio) {

		return repo.save(studio);
	}

	
	public Studio get(Integer id) throws StudioNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new StudioNotFoundException("Could not find any studio with ID " + id);
		}
	}
	
	public String checkUnique(Integer id, String name, String code) {
		boolean isCreatingNew = (id == null || id == 0);
		
		Studio studioByName = repo.findByName(name);
		
		if (isCreatingNew) {
			if (studioByName != null) {
				return "DuplicateName";
			} else {
				Studio categoryByCode = repo.findByCode(code);
				if (categoryByCode != null) {
					return "DuplicateCode";
				}
			}
		} else {
			if (studioByName != null && studioByName.getId() != id) {
				return "DuplicateName";
			}
			
			Studio studioByCode = repo.findByCode(code);
			if (studioByCode != null && studioByCode.getId() != id) {
				return "DuplicateCode";
			}
			
		}
		
		return "OK";
	}
	
	private SortedSet<Category> sortSubCategories(Set<Category> children) {
		return sortSubCategories(children, "asc");
	}
	
	private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
		SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
			@Override
			public int compare(Category cat1, Category cat2) {
				if (sortDir.equals("asc")) {
					return cat1.getName().compareTo(cat2.getName());
				} else {
					return cat2.getName().compareTo(cat1.getName());
				}
			}
		});
		
		sortedChildren.addAll(children);
		
		return sortedChildren;
	}
	
	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	
	public void delete(Integer id) throws StudioNotFoundException {
		Long countById = repo.countById(id);
		if (countById == null || countById == 0) {
			throw new StudioNotFoundException("Could not find any studio with ID " + id);
		}
		
		repo.deleteById(id);
	}

	public List<StudioAddress> listofStudiosAddress(){
		return (List<StudioAddress>)repoAdd.findAll();
	}

	public Studio findById(Integer id) {

		return repo.findById(id).get();
	}

	public void delete(Studio id) {

		repo.delete(id);
	}
}
*/
