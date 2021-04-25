package wiwy.covid.apicall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wiwy.covid.apicall.coronadto.CoronaDto;
import wiwy.covid.apicall.coronadto.Response;
import wiwy.covid.apicall.dismsgdto.DisMsg;
import wiwy.covid.apicall.dismsgdto.DisMsgTotal;

import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiExplorer {

    private final CoronaRepository coronaRepository;
    private final DisMsgRepository disMsgRepository;
    private ObjectMapper xmlMapper = new XmlMapper();

    @Transactional
    public void fetching(String startDate, String endDate) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=PPcz55RLIRHgBc%2B5Kzjvbqey%2BsWKrDNmUGNinjzzMcrOygzB%2FI8Tin7bENsGHgDV9puW%2BxpcymvgAU79Rl8S5Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        Response response= xmlMapper.readValue(sb.toString(), Response.class);
        List<CoronaDto> items = response.getBody().getItems();
        int currentSeq = items.get(0).getSeq();

        validateCoronaData(items, currentSeq);

    }

    private void validateCoronaData(List<CoronaDto> items, int currentSeq) {
        List<CoronaDto> recentCorona = coronaRepository.findRecentCorona();
        int recentSeq = recentCorona.get(0).getSeq();
        int validateSeq = currentSeq - recentSeq;

        if(validateSeq != 0) {
            for(int i=0;i<validateSeq;i++) {
                coronaRepository.save(items.get(i));
            }
        }
    }

    @Transactional
    public void updateDisMsg() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1741000/DisasterMsg3/getDisasterMsg1List"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=PPcz55RLIRHgBc%2B5Kzjvbqey%2BsWKrDNmUGNinjzzMcrOygzB%2FI8Tin7bENsGHgDV9puW%2BxpcymvgAU79Rl8S5Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("인증키", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*호출문서 형식*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        DisMsgTotal disMsgTotal = xmlMapper.readValue(sb.toString(), DisMsgTotal.class);
        List<DisMsg> rows = disMsgTotal.getRows();
        int currentSN = rows.get(0).getMd101_sn();

        validateDisMsgSN(rows, currentSN);
    }

    private void validateDisMsgSN(List<DisMsg> rows, int currentSN) {
        List<DisMsg> recentDisMsg = disMsgRepository.findRecentDisMsg();
        int recentSN = recentDisMsg.get(0).getMd101_sn();

        int validateSN = currentSN -recentSN;

        if(validateSN != 0) {
            for (int i = 0 ; i < validateSN ; i++) {
                disMsgRepository.save(rows.get(i));
            }
        }
    }
}