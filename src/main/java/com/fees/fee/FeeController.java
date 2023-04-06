package com.fees.fee;

import com.producing.common.entity.studio.*;
import com.producing.common.exception.StudioNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FeeController {

	@Autowired
	private StudioService studioService;


	@GetMapping("/studios")
	public String listFirstPage(String sortDir, Model model) {
		return listByPage(1, sortDir, null, model);
	}
	
	@GetMapping("/studios/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, 
			String sortDir,	String keyword,	Model model) {
		if (sortDir ==  null || sortDir.isEmpty()) {
			sortDir = "asc";
		}
		
		StudioPageInfo pageInfo = new StudioPageInfo();
		List<Studio> listStudios = studioService.listByPage(pageInfo, pageNum, sortDir, keyword);
		
		long startCount = (pageNum - 1) * StudioService.ROOT_STUDIOS_PER_PAGE + 1;
		long endCount = startCount + StudioService.ROOT_STUDIOS_PER_PAGE - 1;
		if (endCount > pageInfo.getTotalElements()) {
			endCount = pageInfo.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("totalPages", pageInfo.getTotalPages());
		model.addAttribute("totalItems", pageInfo.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", "name");
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);		
		
		model.addAttribute("listStudios", listStudios);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("moduleURL", "/studios");
		
		return "studios/studios";
	}
	
	@GetMapping("/studios/new")
	public String newStudio(Model model) {
		List<Studio> listStudios = studioService.listofStudios();

		List<StudioAddress> listStudioAddress =new ArrayList<>();//service.listofStudiosAddress();
		StudioAddress s1=new StudioAddress("",false);
		StudioAddress s2=new StudioAddress("",false);
		StudioAddress s3=new StudioAddress("",false);
		StudioAddress s4=new StudioAddress("",false);
		listStudioAddress.add(s1);
		listStudioAddress.add(s2);
		listStudioAddress.add(s3);
		listStudioAddress.add(s4);

		model.addAttribute("studio", new Studio());
		model.addAttribute("studioAddress", new StudioAddress());
		model.addAttribute("listStudios", listStudios);
		model.addAttribute("listStudioAddress", listStudioAddress);
		model.addAttribute("pageTitle", "Create New Studio");

		return "studios/registration";
	}
	
	@PostMapping("/studios/save")
	public String saveCategory(Studio studio,
			RedirectAttributes ra) throws IOException {

			studioService.save(studio);

		
		ra.addFlashAttribute("message", "The Studio has been saved successfully.");
		return "redirect:/studios";
	}
	
	@GetMapping("/studios/edit/{id}")
	public String editCategory(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Studio studio = studioService.get(id);
			List<Studio> listStudios = studioService.listofStudios();
			List<StudioAddress> listStudioAddress =studioAddressService.listofStudioAdress(id);
            List<StudioContactPerson> listStudioContact=studioContactService.listofStudioContact(id);
			model.addAttribute("studioAddress", new StudioAddress());
			model.addAttribute("studioContact", new StudioContactPerson());
			model.addAttribute("studio", studio);
			model.addAttribute("listStudios", listStudios);
			model.addAttribute("listStudioAddress", listStudioAddress);
			model.addAttribute("listStudioContact", listStudioContact);
			model.addAttribute("pageTitle", "Edit Studio (ID: " + id + ")");
			
			return "studios/registration";
		} catch (StudioNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/studios";
		}
	}
	
	@GetMapping("/studios/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		studioService.updateCategoryEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The studio ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/studios";
	}
	
	@GetMapping("/studios/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			studioService.delete(id);
			//String categoryDir = "studio-images/" + id;

			//AmazonS3Util.removeFolder(categoryDir);
			
			redirectAttributes.addFlashAttribute("message", 
					"The Studio ID " + id + " has been deleted successfully");
		} catch (StudioNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/studios";
	}
	
	@GetMapping("/studios/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<Studio> listStudios = studioService.listofStudios();
		StudioCsvExporter exporter = new StudioCsvExporter();
		exporter.export(listStudios, response);
	}


	@PostMapping("/saveControls")
	public String saveControl(Studio controls, Errors errors, Model model){
		if(null!=errors && errors.getErrorCount()>0){
			errors.getAllErrors().forEach(a -> System.out.println(a.getDefaultMessage()));
			return "studios/registration";
		} else {
			System.out.println(controls);
			model.addAttribute("successMsg", "Control Saved Successfully");
			studioService.save(controls);
			return "redirect:/studios";
		}
	}

	@PostMapping("/saveControls/{id}")
	public String saveControl(@PathVariable(name = "id") Integer id){
		Studio controls=studioService.findById(id);
		studioService.delete(controls);
		studioService.save(controls);
		return "studios/studios";

	}

	@PostMapping(value = "/saveControls", params = {"addScript"})
	public String addScript(Studio controls, BindingResult bindingResult){
		if(null!=controls){
			if(null==controls.getAddressList()){
				List<StudioAddress> scriptList = new ArrayList<>();
				StudioAddress s =new StudioAddress();
				s.setAddressname("");
				scriptList.add(s);
				controls.setAddressList(scriptList);
			} else {
				StudioAddress s =new StudioAddress();
				controls.getAddressList().add(s);
			}
		}

		return "studios/registration";
	}



	@PostMapping(value = "/saveControls", params = {"removeScript"})
	public String removeScript(Studio controls, BindingResult bindingResult, HttpServletRequest request){
		controls.getAddressList().remove(Integer.parseInt(request.getParameter("removeScript")));
		return "studios/registration";
	}
}
