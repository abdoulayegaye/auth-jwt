package app.auth.auth_jwt.shared.dto.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoSearChewable {
    int page = 1;
    int size = 10;
    boolean isAnd = true;
    Map<String,String> filters= new HashMap<>();
    public int getPage(){
        if(page <= 0)
            page = 1;
        return page - 1;
    }

    public int getSize() {
        if(size > 100){
            size = 100;
        }
        return size;
    }
}
