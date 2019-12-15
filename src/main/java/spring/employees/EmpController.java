package spring.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.mail.SendEmailExecutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EmpController {
    private List<Emp> list;

    public EmpController() {
        list = new ArrayList<>();
        list.add(new Emp(1, "Janek", 120000, "Radom"));
        list.add(new Emp(2, "Zosia", 9000, "Makowiec"));
        list.add(new Emp(3, "Marek", 10000, "Warszawa"));
        list.add(new Emp(4, "Krysytna", 13000, "Ryzowice"));
    }


    @RequestMapping("/")
    public String indexGet() {
        return "emp/index";
    }

    @RequestMapping(value = "/empform", method = RequestMethod.GET)
    public ModelAndView showform(Model model) {
        return new ModelAndView("emp/empform", "emp", new Emp());
    }

    @RequestMapping(value = "/save_emp")
    public ModelAndView save(@ModelAttribute(value = "emp") Emp emp) {

            if (emp.getId() < 1) {
            emp.setId((list.get((list.size()-1)).getId()+1));
            list.add(emp);
            } else {
                Emp empTemp = getEmployeesById(emp.getId());
                empTemp.setName(emp.getName());
                empTemp.setSalary(emp.getSalary());
                empTemp.setDesignation(emp.getDesignation());
            }
            SendEmailExecutor.sendEmailAfterAction("geograf33@gmail.com");
            return new ModelAndView("redirect:/viewemp");
        }


    @RequestMapping(value = "/viewemp")
    public ModelAndView viewemp(Model model) {
        return new ModelAndView("emp/viewemp", "list", list);
    }

    @RequestMapping(value = "/printTest")
    public ModelAndView printTest(@ModelAttribute(value = "emp") Emp emp) {
        System.out.println("Witaj Adrian Bajer");
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping(value = "/delete_emp", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "emp_id") String emp_id) {
        list.remove(getEmployeesById(Integer.parseInt(emp_id)));
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping(value = "/edit_emp", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "emp_id") String emp_id) {
        Emp employee =  getEmployeesById(Integer.parseInt(emp_id));

        return new ModelAndView("emp/empform", "emp", employee);
        }

//        list.remove(getEmployeesById(Integer.parseInt(emp_id)));
//        showform(Model model) {
//            return new ModelAndView("emp/empform", "emp", new Emp());
//        }
//        return new ModelAndView("redirect:/empform");



    private Emp getEmployeesById(@RequestParam int id) {

//      wykomentowane, edukacyjnie, przykłądy użycia streamów

//        List<Emp> listEmp = list.stream().filter(f -> f.getId() == id).collect(Collectors.toList());
//
//        list.stream().filter(f -> f.getId() == id).forEach(System.out::println);
        return list.stream().filter(f -> f.getId() == id).findFirst().get();
    }
}