package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wiwy.covid.apicall.AbrCoronaComparator;
import wiwy.covid.apicall.AbrCoronaRepository;
import wiwy.covid.apicall.CoronaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.coronadto.CoronaDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CoronaController {

    private final CoronaRepository coronaRepository;
    private final AbrCoronaRepository abrCoronaRepository;

    @GetMapping("/")
    public String showCovid(Model model) {
        List<CoronaDto> hapGae = coronaRepository.findHapGae();
        CoronaDto coronaToday = hapGae.get(0);
        List<AbrCoronaDto> abroads = abrCoronaRepository.findAbrCoronaPerDay();

        // seq를 기준으로 오름차순 정렬
        AbrCoronaComparator comp = new AbrCoronaComparator();
        Collections.sort(abroads, comp);

        model.addAttribute("coronaToday", coronaToday);
        model.addAttribute("abroads", abroads);
        return "main";
    }
}
