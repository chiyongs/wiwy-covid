package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    public String showCovid() {
        List<CoronaDto> coronaPerDay = coronaRepository.findCoronaPerDay();
        for (CoronaDto coronaDto : coronaPerDay) {
            System.out.println(coronaDto.getGubun());
        }
        return "main";
    }
}
