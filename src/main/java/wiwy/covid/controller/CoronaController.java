package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wiwy.covid.apicall.AbrCoronaComparator;
import wiwy.covid.apicall.AbrCoronaRepository;
import wiwy.covid.apicall.CoronaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.coronadto.CoronaDto;
import wiwy.covid.apicall.coronadto.CoronaPerWeekDTO;

import javax.persistence.criteria.CriteriaBuilder;
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

        // 코로나 확진자 현황
        List<CoronaDto> hapGae = coronaRepository.findHapGae();
        CoronaDto coronaToday = hapGae.get(0);

        // 날짜별 코로나 확진자 현황
//        List<String> coronaDays = new ArrayList<>();
        List<CoronaDto> confirmedPerWeek = coronaRepository.findConfirmedPerWeek();
        List<Integer> confirmedCnts = new ArrayList<>();
        List<String> confirmedDays = new ArrayList<>();
        for (CoronaDto weekDTO : confirmedPerWeek) {
            confirmedCnts.add(weekDTO.getIncDec());
            confirmedDays.add(weekDTO.getStdDay());
        }
        confirmedDays.sort(Comparator.naturalOrder());
        Collections.reverse(confirmedCnts);
        model.addAttribute("confirmedCnts",confirmedCnts);
        model.addAttribute("confirmedDays", confirmedDays);

        // 시도별 코로나 확진자 현황
        List<CoronaDto> cities = coronaRepository.findCoronaPerDay();
        List<Integer> cityCnts = new ArrayList<>();
        for (CoronaDto city : cities) {
            cityCnts.add(city.getDefCnt());
        }
        cityCnts.sort(Comparator.reverseOrder());
        model.addAttribute("cityCnts",cityCnts);



        // 국외 확진자 현황
        List<AbrCoronaDto> recent = abrCoronaRepository.findRecent();


        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy년 MM월 d일");
        String curDate = date.format(df);
        curDate = curDate + " 00시";

        List<AbrCoronaDto> abroads = new ArrayList<>();

        // 국외 코로나 업데이트 된 경우
        if(curDate == recent.get(0).getStdDay()) {
            abroads = abrCoronaRepository.findByDate(curDate);
        } else { // 새벽이어서 업데이트 안 된 경우
            date = date.minusDays(1);
            curDate = date.format(df) + " 00시";
            abroads = abrCoronaRepository.findByDate(curDate);
        }


        model.addAttribute("coronaToday", coronaToday);
        model.addAttribute("abroads", abroads);
        return "main";
    }
}
