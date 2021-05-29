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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class CoronaController {

    private final CoronaRepository coronaRepository;
    private final AbrCoronaRepository abrCoronaRepository;

    @GetMapping("/")
    public String showCovid(Model model) {
        List<CoronaDto> hapGae = coronaRepository.findHapGae();
        CoronaDto coronaToday = hapGae.get(0);

        List<AbrCoronaDto> recent = abrCoronaRepository.findRecent();


        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy년 MM월 d일");
        String curDate = date.format(df);
        curDate = curDate + " 00시";

        List<AbrCoronaDto> abroads = new ArrayList<>();

        // 업데이트 된 경우
        if(curDate == recent.get(0).getStdDay()) {
            abroads = abrCoronaRepository.findByDate(curDate);
        } else { // 새벽이어서 업데이트 안 된 경우
            date = date.minusDays(1);
            curDate = date.format(df) + " 00시";
            abroads = abrCoronaRepository.findByDate(curDate);
        }

//        // seq를 기준으로 오름차순 정렬
//        AbrCoronaComparator comp = new AbrCoronaComparator();
//        Collections.sort(abroads, comp);

        model.addAttribute("coronaToday", coronaToday);
        model.addAttribute("abroads", abroads);
        return "main";
    }
}
