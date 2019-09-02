package orthae.com.github.medicalmanagementsystem.client.employees;

import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.AuthorityDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDto;
import orthae.com.github.medicalmanagementsystem.client.employees.service.EmployeesService;

@Component
public class EditEmployeeWindowController extends AbstractAddEditEmployeeWindowController {

    private EmployeesService employeesService;
    private EmployeeDetailsDto detailsDto;

    public EditEmployeeWindowController(EmployeesService employeesService){
        this.employeesService = employeesService;
    }


    public void initialize(EmployeeDto dto){
        this.detailsDto =  employeesService.find(dto.getId());
        getNameTextfield().setText(dto.getName());
        getSurnameTextfield().setText(dto.getSurname());
        getUsernameTextfield().setText(dto.getUsername());
        getEmailTextfield().setText(dto.getEmail());
        System.out.println(detailsDto.getAuthorities());
        for(AuthorityDto authorityDto : detailsDto.getAuthorities()){
            switch (authorityDto.getAuthority()){
                case "MANAGEMENT":
                    getManagementCheckbox().selectedProperty().setValue(true);
                    break;
            }
        }
    }

    public void edit(){
        EmployeeDetailsDto formData = processForm();
        System.out.println(formData.getId());
        System.out.println(formData.getName());
        System.out.println(formData.getSurname());
        System.out.println(formData.getUsername());
        System.out.println(formData.getEmail());
        System.out.println(formData.getPassword());
        System.out.println(formData.getAuthorities());
        employeesService.update(formData);
    }


    @Override
    public EmployeeDetailsDto processForm(){
        EmployeeDetailsDto formData = super.processForm();
        formData.setId(detailsDto.getId());
        if(formData.getName().trim().equals(detailsDto.getName().trim())){
            formData.setName(null);
        }
        if(formData.getSurname().trim().equals(detailsDto.getSurname().trim())){
            formData.setSurname(null);
        }
        if(formData.getUsername().trim().equals(detailsDto.getUsername().trim())){
            formData.setUsername(null);
        }
        if(formData.getEmail().trim().equals(detailsDto.getEmail().trim())){
            formData.setEmail(null);
        }
        if(getPasswordField().getText().isEmpty())
            formData.setPassword(null);
        return formData;
    }

}
