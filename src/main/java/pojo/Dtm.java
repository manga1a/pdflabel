package pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record Dtm(String label, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd") Date date) {
}
