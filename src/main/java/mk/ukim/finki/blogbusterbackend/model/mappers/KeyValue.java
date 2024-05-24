package mk.ukim.finki.blogbusterbackend.model.mappers;

import lombok.Data;

@Data
public class KeyValue{
    Long key;
    String value;

    public KeyValue(Long key, String value){
        this.key = key;
        this.value= value;
    }
}
