package wiwy.covid.apicall.dismsgdto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DisMsgTotal {
    private DisMsgHeader head;
    private DisMsgResult result;
    private List<DisMsg> rows = new ArrayList<>();
}
