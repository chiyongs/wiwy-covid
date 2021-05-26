package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wiwy.covid.apicall.CoronaRepository;
import wiwy.covid.apicall.coronadto.CoronaDto;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CoronaController {

    private final CoronaRepository coronaRepository;

    @GetMapping("/")
    public String showCovid(Model model) {
        List<CoronaDto> hapGae = coronaRepository.findHapGae();
        CoronaDto coronaToday = hapGae.get(0);
        model.addAttribute("coronaToday", coronaToday);
        return "main";
    }
}
