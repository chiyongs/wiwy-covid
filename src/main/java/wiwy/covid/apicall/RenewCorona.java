package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class RenewCorona {

    private final ApiExplorer apiExplorer;

//    @Scheduled(fixedRate = 1000*60*5)
//    @Scheduled(cron = "0/10 * * * * *")   // 10초마다
    @Scheduled(cron = "0 0/30 * * * *") // 30분마다
    public void renewingData() throws IOException {
        LocalDate currentDate = LocalDate.now();
        String curDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);

        log.info("renew start");
        apiExplorer.fetching(curDate,curDate);
        log.info("renew end");
    }
}
